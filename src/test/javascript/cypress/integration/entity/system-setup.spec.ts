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

describe('SystemSetup e2e test', () => {
  const systemSetupPageUrl = '/system-setup';
  const systemSetupPageUrlPattern = new RegExp('/system-setup(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const systemSetupSample = { paramKey: 'haptic' };

  let systemSetup: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/system-setups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/system-setups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/system-setups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (systemSetup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/system-setups/${systemSetup.id}`,
      }).then(() => {
        systemSetup = undefined;
      });
    }
  });

  it('SystemSetups menu should load SystemSetups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('system-setup');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SystemSetup').should('exist');
    cy.url().should('match', systemSetupPageUrlPattern);
  });

  describe('SystemSetup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(systemSetupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SystemSetup page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/system-setup/new$'));
        cy.getEntityCreateUpdateHeading('SystemSetup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', systemSetupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/system-setups',
          body: systemSetupSample,
        }).then(({ body }) => {
          systemSetup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/system-setups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [systemSetup],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(systemSetupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SystemSetup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('systemSetup');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', systemSetupPageUrlPattern);
      });

      it('edit button click should load edit SystemSetup page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SystemSetup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', systemSetupPageUrlPattern);
      });

      it('last delete button click should delete instance of SystemSetup', () => {
        cy.intercept('GET', '/api/system-setups/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('systemSetup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', systemSetupPageUrlPattern);

        systemSetup = undefined;
      });
    });
  });

  describe('new SystemSetup page', () => {
    beforeEach(() => {
      cy.visit(`${systemSetupPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('SystemSetup');
    });

    it('should create an instance of SystemSetup', () => {
      cy.get(`[data-cy="paramKey"]`).type('York').should('have.value', 'York');

      cy.get(`[data-cy="paramVal"]`).type('Metal').should('have.value', 'Metal');

      cy.get(`[data-cy="description"]`).type('Money Representative partnerships').should('have.value', 'Money Representative partnerships');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        systemSetup = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', systemSetupPageUrlPattern);
    });
  });
});
