import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './patient.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PatientDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const patientEntity = useAppSelector(state => state.patient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientDetailsHeading">
          <Translate contentKey="javaTrainingApp.patient.detail.title">Patient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{patientEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="javaTrainingApp.patient.firstName"></Translate>
            </span>
          </dt>
          <dd>{patientEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="javaTrainingApp.patient.lastName"></Translate>
            </span>
          </dt>
          <dd>{patientEntity.lastName}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="javaTrainingApp.patient.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{patientEntity.phone}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="javaTrainingApp.patient.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>
            {patientEntity.birthDate ? <TextFormat value={patientEntity.birthDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="citizenNumber">
              <Translate contentKey="javaTrainingApp.patient.citizenNumber">Citizen Number</Translate>
            </span>
          </dt>
          <dd>{patientEntity.citizenNumber}</dd>
          <dt>
            <span id="passportNumber">
              <Translate contentKey="javaTrainingApp.patient.passportNumber">Citizen Number</Translate>
            </span>
          </dt>
          <dd>{patientEntity.passportNumber}</dd>
        </dl>
        <Button tag={Link} to="/patient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient/${patientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientDetail;
