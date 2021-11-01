import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SystemSetup from './system-setup';
import SystemSetupDetail from './system-setup-detail';
import SystemSetupUpdate from './system-setup-update';
import SystemSetupDeleteDialog from './system-setup-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SystemSetupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SystemSetupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SystemSetupDetail} />
      <ErrorBoundaryRoute path={match.url} component={SystemSetup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SystemSetupDeleteDialog} />
  </>
);

export default Routes;
