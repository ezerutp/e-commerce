import { VarianteResponse } from './varianteresponse';

export interface Producto {
  id?: number;
  nombre: string;
  descripcion: string;
  marca?: string;
  activo?: boolean;
  slug: string;
  variantes?: VarianteResponse[];
}