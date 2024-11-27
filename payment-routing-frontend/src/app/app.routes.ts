import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'pages',
    loadChildren: () =>
      import('./layouts/pages.routes').then((m) => m.PAGES_ROUTES),
  },
  { path: '**', redirectTo: 'login' },
];
