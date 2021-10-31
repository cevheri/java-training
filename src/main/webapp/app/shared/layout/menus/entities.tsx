import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/patient">
      <Translate contentKey="global.menu.entities.patient" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/department">
      <Translate contentKey="global.menu.entities.department" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/doctor">
      <Translate contentKey="global.menu.entities.doctor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/visit-service">
      <Translate contentKey="global.menu.entities.visitService" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/visit">
      <Translate contentKey="global.menu.entities.visit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/system-setup">
      <Translate contentKey="global.menu.entities.systemSetup" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
