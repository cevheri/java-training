import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './department.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DepartmentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const departmentEntity = useAppSelector(state => state.department.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="departmentDetailsHeading">
          <Translate contentKey="javaTrainingApp.department.detail.title">Department</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="javaTrainingApp.department.name">Name</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="javaTrainingApp.department.type">Type</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.type}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="javaTrainingApp.department.description">Description</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.description}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="javaTrainingApp.department.active">Active</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/department" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/department/${departmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepartmentDetail;
