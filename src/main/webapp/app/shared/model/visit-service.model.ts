import { IVisit } from 'app/shared/model/visit.model';

export interface IVisitService {
  id?: number;
  name?: string;
  description?: string | null;
  active?: boolean | null;
  price?: number | null;
  visits?: IVisit[] | null;
}

export const defaultValue: Readonly<IVisitService> = {
  active: false,
};
