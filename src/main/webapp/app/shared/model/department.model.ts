import { IDoctor } from 'app/shared/model/doctor.model';
import { DepartmentType } from 'app/shared/model/enumerations/department-type.model';

export interface IDepartment {
  id?: number;
  name?: string;
  type?: DepartmentType | null;
  description?: string | null;
  active?: boolean | null;
  doctors?: IDoctor[] | null;
}

export const defaultValue: Readonly<IDepartment> = {
  active: false,
};
