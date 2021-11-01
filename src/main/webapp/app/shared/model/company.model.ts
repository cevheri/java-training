export interface ICompany {
  id?: number;
  name?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createdBy?: string;
  createdDate?: Date | null;
  lastModifiedBy?: string;
  lastModifiedDate?: Date | null;
}

export const defaultValue: Readonly<ICompany> = {
  isActive: false,
  createdBy: '',
  createdDate: null,
  lastModifiedBy: '',
  lastModifiedDate: null,
};
