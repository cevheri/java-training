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

describe('Products e2e test', () => {
  const productsPageUrl = '/products';
  const productsPageUrlPattern = new RegExp('/products(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const productsSample = {};

  let products: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/products+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/products').as('postEntityRequest');
    cy.intercept('DELETE', '/api/products/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (products) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/products/${products.id}`,
      }).then(() => {
        products = undefined;
      });
    }
  });

  it('Products menu should load Products page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('products');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Products').should('exist');
    cy.url().should('match', productsPageUrlPattern);
  });

  describe('Products page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(productsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Products page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/products/new$'));
        cy.getEntityCreateUpdateHeading('Products');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', productsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/products',
          body: productsSample,
        }).then(({ body }) => {
          products = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/products+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [products],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(productsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Products page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('products');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', productsPageUrlPattern);
      });

      it('edit button click should load edit Products page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Products');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', productsPageUrlPattern);
      });

      it('last delete button click should delete instance of Products', () => {
        cy.intercept('GET', '/api/products/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('products').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', productsPageUrlPattern);

        products = undefined;
      });
    });
  });

  describe('new Products page', () => {
    beforeEach(() => {
      cy.visit(`${productsPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Products');
    });

    it('should create an instance of Products', () => {
      cy.get(`[data-cy="name"]`).type('Paradigm user-centric').should('have.value', 'Paradigm user-centric');

      cy.get(`[data-cy="category"]`).type('Investor secondary online').should('have.value', 'Investor secondary online');

      cy.get(`[data-cy="price"]`).type('48468').should('have.value', '48468');

      cy.get(`[data-cy="description"]`).type('Account invoice').should('have.value', 'Account invoice');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        products = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', productsPageUrlPattern);
    });
  });
});
