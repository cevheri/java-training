import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IVisitService } from 'app/shared/model/visit-service.model';
import { VisitType } from 'app/shared/model/enumerations/visit-type.model';

export interface IVisit {
  id?: number;
  date?: string | null;
  type?: VisitType | null;
  patient?: IPatient | null;
  doctor?: IDoctor | null;
  department?: IDepartment | null;
  visitServices?: IVisitService[] | null;
}

export const defaultValue: Readonly<IVisit> = {};
