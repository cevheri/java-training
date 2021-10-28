import { IDepartment } from 'app/shared/model/department.model';

export interface IDoctor {
  id?: number;
  name?: string;
  phone?: string | null;
  departments?: IDepartment[] | null;
}

export const defaultValue: Readonly<IDoctor> = {};
