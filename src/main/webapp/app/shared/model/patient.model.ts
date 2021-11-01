import dayjs from 'dayjs';

export interface IPatient {
  id?: number;
  firstName?: string;
  lastName?: string;
  phone?: string | null;
  birthDate?: string | null;
  citizenNumber?: string;
  passportNumber?: string;
}

export const defaultValue: Readonly<IPatient> = {};
