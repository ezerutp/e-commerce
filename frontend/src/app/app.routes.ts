import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { CreateCategoriaComponent } from './admin/pages/gestion-categoria/create-categoria/create-categoria.component';
import { CreateProductosComponent } from './admin/pages/gestion-productos/create-productos/create-productos.component'
import { NotFoundComponent } from './pages/not-found/not-found.component';

import { authGuard } from './core/guards/auth.guards';

export const routes: Routes = [


    {
        path: '',
        component: HomeComponent,
        title: 'Inicio',
    },
    {
        path: 'login',
        component: LoginComponent,
        title: 'Iniciar Sesión',
    },
    {
        path: 'registroUsuario',
        component: RegisterComponent,
        canActivate: [authGuard],
        title: 'Registro de Usuario',
    },
    {
        path: 'registroCategoria',
        component: CreateCategoriaComponent,
        canActivate: [authGuard],
        title: 'Registro de Categoría',
    },
    {
        path: 'registroProducto',
        component: CreateProductosComponent,
        canActivate: [authGuard],
        title: 'Registro de Producto',
    },
    {
        path: '**',
        component: NotFoundComponent,
        title: 'Página No Encontrada',
    }
];
