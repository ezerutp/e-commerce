export interface VarianteResponse {
  id: number;
  productoId: number;
  productoNombre: string;
  sku: string;
  precio: number;
  peso: number;
  atributosJson?: string;
  activo: boolean;
  updatedAt?: string;
}