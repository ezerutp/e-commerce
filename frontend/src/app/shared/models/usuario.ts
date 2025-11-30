export interface Usuario {
  id?: number;
  email: string;
  username: string;
  password: string;
  nombre: string;
  apellido: string;
  telefono?: string;
  estado?: string;
  rol?: string;
}
