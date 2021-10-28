import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Visit from './visit';
import VisitDetail from './visit-detail';
import VisitUpdate from './visit-update';
import VisitDeleteDialog from './visit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VisitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VisitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VisitDetail} />
      <ErrorBoundaryRoute path={match.url} component={Visit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VisitDeleteDialog} />
  </>
);

export default Routes;
