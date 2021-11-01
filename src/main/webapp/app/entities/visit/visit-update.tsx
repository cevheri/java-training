import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { getEntities as getDoctors } from 'app/entities/doctor/doctor.reducer';
import { IDepartment } from 'app/shared/model/department.model';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { IVisitService } from 'app/shared/model/visit-service.model';
import { getEntities as getVisitServices } from 'app/entities/visit-service/visit-service.reducer';
import { getEntity, updateEntity, createEntity, reset } from './visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { VisitType } from 'app/shared/model/enumerations/visit-type.model';

export const VisitUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const doctors = useAppSelector(state => state.doctor.entities);
  const departments = useAppSelector(state => state.department.entities);
  const visitServices = useAppSelector(state => state.visitService.entities);
  const visitEntity = useAppSelector(state => state.visit.entity);
  const loading = useAppSelector(state => state.visit.loading);
  const updating = useAppSelector(state => state.visit.updating);
  const updateSuccess = useAppSelector(state => state.visit.updateSuccess);
  const visitTypeValues = Object.keys(VisitType);
  const handleClose = () => {
    props.history.push('/visit' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getDoctors({}));
    dispatch(getDepartments({}));
    dispatch(getVisitServices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...visitEntity,
      ...values,
      visitServices: mapIdList(values.visitServices),
      patient: patients.find(it => it.id.toString() === values.patient.toString()),
      doctor: doctors.find(it => it.id.toString() === values.doctor.toString()),
      department: departments.find(it => it.id.toString() === values.department.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          type: 'EXAMINATION',
          ...visitEntity,
          date: convertDateTimeFromServer(visitEntity.date),
          patient: visitEntity?.patient?.id,
          doctor: visitEntity?.doctor?.id,
          department: visitEntity?.department?.id,
          visitServices: visitEntity?.visitServices?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="javaTrainingApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">
            <Translate contentKey="javaTrainingApp.visit.home.createOrEditLabel">Create or edit a Visit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="visit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('javaTrainingApp.visit.date')}
                id="visit-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('javaTrainingApp.visit.type')} id="visit-type" name="type" data-cy="type" type="select">
                {visitTypeValues.map(visitType => (
                  <option value={visitType} key={visitType}>
                    {translate('javaTrainingApp.VisitType' + visitType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="visit-patient"
                name="patient"
                data-cy="patient"
                label={translate('javaTrainingApp.visit.patient')}
                type="select"
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="visit-doctor"
                name="doctor"
                data-cy="doctor"
                label={translate('javaTrainingApp.visit.doctor')}
                type="select"
              >
                <option value="" key="0" />
                {doctors
                  ? doctors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="visit-department"
                name="department"
                data-cy="department"
                label={translate('javaTrainingApp.visit.department')}
                type="select"
              >
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('javaTrainingApp.visit.visitService')}
                id="visit-visitService"
                data-cy="visitService"
                type="select"
                multiple
                name="visitServices"
              >
                <option value="" key="0" />
                {visitServices
                  ? visitServices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/visit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VisitUpdate;
