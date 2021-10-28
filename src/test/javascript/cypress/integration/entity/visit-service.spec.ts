import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('VisitService e2e test', () => {
  const visitServicePageUrl = '/visit-service';
  const visitServicePageUrlPattern = new RegExp('/visit-service(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const visitServiceSample = { name: 'neural feed' };

  let visitService: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/visit-services+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/visit-services').as('postEntityRequest');
    cy.intercept('DELETE', '/api/visit-services/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (visitService) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/visit-services/${visitService.id}`,
      }).then(() => {
        visitService = undefined;
      });
    }
  });

  it('VisitServices menu should load VisitServices page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('visit-service');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('VisitService').should('exist');
    cy.url().should('match', visitServicePageUrlPattern);
  });

  describe('VisitService page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(visitServicePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create VisitService page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/visit-service/new$'));
        cy.getEntityCreateUpdateHeading('VisitService');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitServicePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/visit-services',
          body: visitServiceSample,
        }).then(({ body }) => {
          visitService = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/visit-services+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [visitService],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(visitServicePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details VisitService page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('visitService');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitServicePageUrlPattern);
      });

      it('edit button click should load edit VisitService page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('VisitService');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitServicePageUrlPattern);
      });

      it('last delete button click should delete instance of VisitService', () => {
        cy.intercept('GET', '/api/visit-services/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('visitService').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitServicePageUrlPattern);

        visitService = undefined;
      });
    });
  });

  describe('new VisitService page', () => {
    beforeEach(() => {
      cy.visit(`${visitServicePageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('VisitService');
    });

    it('should create an instance of VisitService', () => {
      cy.get(`[data-cy="name"]`).type('Interactions Bacon Assistant').should('have.value', 'Interactions Bacon Assistant');

      cy.get(`[data-cy="description"]`).type('Avon Streets Principal').should('have.value', 'Avon Streets Principal');

      cy.get(`[data-cy="active"]`).should('not.be.checked');
      cy.get(`[data-cy="active"]`).click().should('be.checked');

      cy.get(`[data-cy="price"]`).type('10932').should('have.value', '10932');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        visitService = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', visitServicePageUrlPattern);
    });
  });
});
