import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Products from './products';
import ProductsDetail from './products-detail';
import ProductsUpdate from './products-update';
import ProductsDeleteDialog from './products-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Products} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductsDeleteDialog} />
  </>
);

export default Routes;
