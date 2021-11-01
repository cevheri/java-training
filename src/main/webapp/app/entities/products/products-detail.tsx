import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './products.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productsEntity = useAppSelector(state => state.products.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productsDetailsHeading">
          <Translate contentKey="javaTrainingApp.products.detail.title">Products</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="javaTrainingApp.products.name">Name</Translate>
            </span>
          </dt>
          <dd>{productsEntity.name}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="javaTrainingApp.products.category">Category</Translate>
            </span>
          </dt>
          <dd>{productsEntity.category}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="javaTrainingApp.products.price">Price</Translate>
            </span>
          </dt>
          <dd>{productsEntity.price}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="javaTrainingApp.products.description">Description</Translate>
            </span>
          </dt>
          <dd>{productsEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/products" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/products/${productsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductsDetail;
