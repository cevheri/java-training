import React from 'react';
import { Switch } from 'react-router-dom';
import Newpage from './newpage';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

const Routes = match => {
  return (
    <>
      <Switch>
        <ErrorBoundaryRoute path={`${match.url}`} component={Newpage} />
      </Switch>
    </>
  );
};

export default Routes;
