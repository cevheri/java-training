import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './products.reducer';
import { IProducts } from 'app/shared/model/products.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Products = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const productsList = useAppSelector(state => state.products.entities);
  const loading = useAppSelector(state => state.products.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="products-heading" data-cy="ProductsHeading">
        <Translate contentKey="javaTrainingApp.products.home.title">Products</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="javaTrainingApp.products.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="javaTrainingApp.products.home.createLabel">Create new Products</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productsList && productsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="javaTrainingApp.products.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="javaTrainingApp.products.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="javaTrainingApp.products.category">Category</Translate>
                </th>
                <th>
                  <Translate contentKey="javaTrainingApp.products.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="javaTrainingApp.products.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productsList.map((products, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${products.id}`} color="link" size="sm">
                      {products.id}
                    </Button>
                  </td>
                  <td>{products.name}</td>
                  <td>{products.category}</td>
                  <td>{products.price}</td>
                  <td>{products.description}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${products.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${products.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${products.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="javaTrainingApp.products.home.notFound">No Products found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Products;
