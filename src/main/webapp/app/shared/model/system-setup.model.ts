export interface ISystemSetup {
  id?: number;
  paramKey?: string;
  paramVal?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ISystemSetup> = {};
