import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Patient from './patient';
import Department from './department';
import Doctor from './doctor';
import VisitService from './visit-service';
import Visit from './visit';
import Company from './company';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}patient`} component={Patient} />
      <ErrorBoundaryRoute path={`${match.url}department`} component={Department} />
      <ErrorBoundaryRoute path={`${match.url}doctor`} component={Doctor} />
      <ErrorBoundaryRoute path={`${match.url}visit-service`} component={VisitService} />
      <ErrorBoundaryRoute path={`${match.url}visit`} component={Visit} />
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
