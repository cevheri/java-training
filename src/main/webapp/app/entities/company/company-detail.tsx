import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">
          <Translate contentKey="javaTrainingApp.company.detail.title">Company</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="javaTrainingApp.company.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="javaTrainingApp.company.description">Description</Translate>
            </span>
          </dt>
          <dd>{companyEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="javaTrainingApp.company.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{companyEntity.isActive ? 'true' : 'false'}</dd>

          <dt>
            <span id="createdDate">
              <Translate contentKey="javaTrainingApp.company.createdDate">Created date</Translate>
            </span>
          </dt>
          <dd>{companyEntity.createdDate}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="javaTrainingApp.company.createdBy">Created by</Translate>
            </span>
          </dt>
          <dd>{companyEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="javaTrainingApp.company.lastModifiedDate">Modified date</Translate>
            </span>
          </dt>
          <dd>{companyEntity.lastModifiedDate}</dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="javaTrainingApp.company.lastModifiedBy">Modified by</Translate>
            </span>
          </dt>
          <dd>{companyEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
