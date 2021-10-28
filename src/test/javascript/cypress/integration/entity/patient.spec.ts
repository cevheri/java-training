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

describe('Patient e2e test', () => {
  const patientPageUrl = '/patient';
  const patientPageUrlPattern = new RegExp('/patient(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const patientSample = { name: 'Garden Avon', citizenNumber: 'Usability H' };

  let patient: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/patients+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/patients').as('postEntityRequest');
    cy.intercept('DELETE', '/api/patients/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (patient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/patients/${patient.id}`,
      }).then(() => {
        patient = undefined;
      });
    }
  });

  it('Patients menu should load Patients page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('patient');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Patient').should('exist');
    cy.url().should('match', patientPageUrlPattern);
  });

  describe('Patient page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(patientPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Patient page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/patient/new$'));
        cy.getEntityCreateUpdateHeading('Patient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/patients',
          body: patientSample,
        }).then(({ body }) => {
          patient = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/patients+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [patient],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(patientPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Patient page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('patient');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });

      it('edit button click should load edit Patient page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Patient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });

      it('last delete button click should delete instance of Patient', () => {
        cy.intercept('GET', '/api/patients/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('patient').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);

        patient = undefined;
      });
    });
  });

  describe('new Patient page', () => {
    beforeEach(() => {
      cy.visit(`${patientPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Patient');
    });

    it('should create an instance of Patient', () => {
      cy.get(`[data-cy="name"]`).type('synthesizing').should('have.value', 'synthesizing');

      cy.get(`[data-cy="phone"]`).type('1-564-847-1285').should('have.value', '1-564-847-1285');

      cy.get(`[data-cy="birthDate"]`).type('2021-10-27').should('have.value', '2021-10-27');

      cy.get(`[data-cy="citizenNumber"]`).type('Tuna Automo').should('have.value', 'Tuna Automo');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        patient = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', patientPageUrlPattern);
    });
  });
});
