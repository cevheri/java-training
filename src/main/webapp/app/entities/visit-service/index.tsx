import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VisitService from './visit-service';
import VisitServiceDetail from './visit-service-detail';
import VisitServiceUpdate from './visit-service-update';
import VisitServiceDeleteDialog from './visit-service-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VisitServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VisitServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VisitServiceDetail} />
      <ErrorBoundaryRoute path={match.url} component={VisitService} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VisitServiceDeleteDialog} />
  </>
);

export default Routes;
