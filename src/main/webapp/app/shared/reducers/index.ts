import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import patient from 'app/entities/patient/patient.reducer';
// prettier-ignore
import department from 'app/entities/department/department.reducer';
// prettier-ignore
import doctor from 'app/entities/doctor/doctor.reducer';
// prettier-ignore
import visitService from 'app/entities/visit-service/visit-service.reducer';
// prettier-ignore
import visit from 'app/entities/visit/visit.reducer';
// prettier-ignore
import company from 'app/entities/company/company.reducer';
// prettier-ignore
import systemSetup from 'app/entities/system-setup/system-setup.reducer';
// prettier-ignore
import products from 'app/entities/products/products.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  patient,
  department,
  doctor,
  visitService,
  visit,
  systemSetup,
  products,
  company,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
