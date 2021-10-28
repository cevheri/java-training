import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './doctor.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DoctorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const doctorEntity = useAppSelector(state => state.doctor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doctorDetailsHeading">
          <Translate contentKey="javaTrainingApp.doctor.detail.title">Doctor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{doctorEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="javaTrainingApp.doctor.name">Name</Translate>
            </span>
          </dt>
          <dd>{doctorEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="javaTrainingApp.doctor.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{doctorEntity.phone}</dd>
          <dt>
            <Translate contentKey="javaTrainingApp.doctor.department">Department</Translate>
          </dt>
          <dd>
            {doctorEntity.departments
              ? doctorEntity.departments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {doctorEntity.departments && i === doctorEntity.departments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/doctor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor/${doctorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoctorDetail;
