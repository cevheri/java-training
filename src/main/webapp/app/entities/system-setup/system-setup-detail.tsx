import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './system-setup.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SystemSetupDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const systemSetupEntity = useAppSelector(state => state.systemSetup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="systemSetupDetailsHeading">
          <Translate contentKey="javaTrainingApp.systemSetup.detail.title">SystemSetup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{systemSetupEntity.id}</dd>
          <dt>
            <span id="paramKey">
              <Translate contentKey="javaTrainingApp.systemSetup.paramKey">Param Key</Translate>
            </span>
          </dt>
          <dd>{systemSetupEntity.paramKey}</dd>
          <dt>
            <span id="paramVal">
              <Translate contentKey="javaTrainingApp.systemSetup.paramVal">Param Val</Translate>
            </span>
          </dt>
          <dd>{systemSetupEntity.paramVal}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="javaTrainingApp.systemSetup.description">Description</Translate>
            </span>
          </dt>
          <dd>{systemSetupEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/system-setup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system-setup/${systemSetupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SystemSetupDetail;
