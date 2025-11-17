export interface Producto {
  id?: number;
  nombre: string;
  descripcion: string;
  marca?: string;
  activo?: boolean;
  slug: string;
}