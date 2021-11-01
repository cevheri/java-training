export interface IProducts {
  id?: number;
  name?: string | null;
  category?: string | null;
  price?: number | null;
  description?: string | null;
}

export const defaultValue: Readonly<IProducts> = {};
