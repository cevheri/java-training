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

describe('Visit e2e test', () => {
  const visitPageUrl = '/visit';
  const visitPageUrlPattern = new RegExp('/visit(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const visitSample = {};

  let visit: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/visits+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/visits').as('postEntityRequest');
    cy.intercept('DELETE', '/api/visits/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (visit) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/visits/${visit.id}`,
      }).then(() => {
        visit = undefined;
      });
    }
  });

  it('Visits menu should load Visits page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('visit');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Visit').should('exist');
    cy.url().should('match', visitPageUrlPattern);
  });

  describe('Visit page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(visitPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Visit page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/visit/new$'));
        cy.getEntityCreateUpdateHeading('Visit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/visits',
          body: visitSample,
        }).then(({ body }) => {
          visit = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/visits+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [visit],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(visitPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Visit page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('visit');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitPageUrlPattern);
      });

      it('edit button click should load edit Visit page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Visit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitPageUrlPattern);
      });

      it('last delete button click should delete instance of Visit', () => {
        cy.intercept('GET', '/api/visits/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('visit').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', visitPageUrlPattern);

        visit = undefined;
      });
    });
  });

  describe('new Visit page', () => {
    beforeEach(() => {
      cy.visit(`${visitPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Visit');
    });

    it('should create an instance of Visit', () => {
      cy.get(`[data-cy="date"]`).type('2021-10-27T08:14').should('have.value', '2021-10-27T08:14');

      cy.get(`[data-cy="type"]`).select('ANALYSIS');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        visit = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', visitPageUrlPattern);
    });
  });
});
