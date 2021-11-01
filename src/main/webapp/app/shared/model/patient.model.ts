import dayjs from 'dayjs';

export interface IPatient {
  id?: number;
  name?: string;
  phone?: string | null;
  birthDate?: string | null;
  citizenNumber?: string;
}

export const defaultValue: Readonly<IPatient> = {};
