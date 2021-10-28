import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Department from './department';
import DepartmentDetail from './department-detail';
import DepartmentUpdate from './department-update';
import DepartmentDeleteDialog from './department-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DepartmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DepartmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DepartmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Department} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DepartmentDeleteDialog} />
  </>
);

export default Routes;
