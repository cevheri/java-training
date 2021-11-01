import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './visit.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VisitDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const visitEntity = useAppSelector(state => state.visit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="visitDetailsHeading">
          <Translate contentKey="javaTrainingApp.visit.detail.title">Visit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{visitEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="javaTrainingApp.visit.date">Date</Translate>
            </span>
          </dt>
          <dd>{visitEntity.date ? <TextFormat value={visitEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="javaTrainingApp.visit.type">Type</Translate>
            </span>
          </dt>
          <dd>{visitEntity.type}</dd>
          <dt>
            <Translate contentKey="javaTrainingApp.visit.patient">Patient</Translate>
          </dt>
          <dd>{visitEntity.patient ? visitEntity.patient.name : ''}</dd>
          <dt>
            <Translate contentKey="javaTrainingApp.visit.doctor">Doctor</Translate>
          </dt>
          <dd>{visitEntity.doctor ? visitEntity.doctor.name : ''}</dd>
          <dt>
            <Translate contentKey="javaTrainingApp.visit.department">Department</Translate>
          </dt>
          <dd>{visitEntity.department ? visitEntity.department.name : ''}</dd>
          <dt>
            <Translate contentKey="javaTrainingApp.visit.visitService">Visit Service</Translate>
          </dt>
          <dd>
            {visitEntity.visitServices
              ? visitEntity.visitServices.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {visitEntity.visitServices && i === visitEntity.visitServices.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/visit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/visit/${visitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VisitDetail;
