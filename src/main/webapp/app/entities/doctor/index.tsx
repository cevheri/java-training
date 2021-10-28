import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Doctor from './doctor';
import DoctorDetail from './doctor-detail';
import DoctorUpdate from './doctor-update';
import DoctorDeleteDialog from './doctor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DoctorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DoctorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DoctorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Doctor} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DoctorDeleteDialog} />
  </>
);

export default Routes;
