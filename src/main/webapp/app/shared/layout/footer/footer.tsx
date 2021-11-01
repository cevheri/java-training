import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <footer className="bg-secondary text-white text-center fixed-bottom">
    <div className="text-center p-3">
      © 2021 Copyright:
      <a className="text-white" href="https://www.pia-team.com/">
        Pia.com
      </a>
    </div>
  </footer>
);

export default Footer;
