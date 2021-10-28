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

describe('Doctor e2e test', () => {
  const doctorPageUrl = '/doctor';
  const doctorPageUrlPattern = new RegExp('/doctor(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const doctorSample = { name: 'Coordinator state Soft' };

  let doctor: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/doctors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/doctors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/doctors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (doctor) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/doctors/${doctor.id}`,
      }).then(() => {
        doctor = undefined;
      });
    }
  });

  it('Doctors menu should load Doctors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('doctor');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Doctor').should('exist');
    cy.url().should('match', doctorPageUrlPattern);
  });

  describe('Doctor page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(doctorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Doctor page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/doctor/new$'));
        cy.getEntityCreateUpdateHeading('Doctor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', doctorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/doctors',
          body: doctorSample,
        }).then(({ body }) => {
          doctor = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/doctors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [doctor],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(doctorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Doctor page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('doctor');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', doctorPageUrlPattern);
      });

      it('edit button click should load edit Doctor page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Doctor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', doctorPageUrlPattern);
      });

      it('last delete button click should delete instance of Doctor', () => {
        cy.intercept('GET', '/api/doctors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('doctor').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', doctorPageUrlPattern);

        doctor = undefined;
      });
    });
  });

  describe('new Doctor page', () => {
    beforeEach(() => {
      cy.visit(`${doctorPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Doctor');
    });

    it('should create an instance of Doctor', () => {
      cy.get(`[data-cy="name"]`).type('Wooden Account').should('have.value', 'Wooden Account');

      cy.get(`[data-cy="phone"]`).type('951.812.0827 x8179').should('have.value', '951.812.0827 x8179');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        doctor = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', doctorPageUrlPattern);
    });
  });
});
