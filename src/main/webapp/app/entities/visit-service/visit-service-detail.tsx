import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './visit-service.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VisitServiceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const visitServiceEntity = useAppSelector(state => state.visitService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="visitServiceDetailsHeading">
          <Translate contentKey="javaTrainingApp.visitService.detail.title">VisitService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{visitServiceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="javaTrainingApp.visitService.name">Name</Translate>
            </span>
          </dt>
          <dd>{visitServiceEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="javaTrainingApp.visitService.description">Description</Translate>
            </span>
          </dt>
          <dd>{visitServiceEntity.description}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="javaTrainingApp.visitService.active">Active</Translate>
            </span>
          </dt>
          <dd>{visitServiceEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="javaTrainingApp.visitService.price">Price</Translate>
            </span>
          </dt>
          <dd>{visitServiceEntity.price}</dd>
        </dl>
        <Button tag={Link} to="/visit-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/visit-service/${visitServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VisitServiceDetail;
