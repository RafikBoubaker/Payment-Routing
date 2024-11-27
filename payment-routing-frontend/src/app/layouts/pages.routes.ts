import { Routes } from '@angular/router';
import { PagesComponent } from './pages.component';

export const PAGES_ROUTES: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
      {
        path: 'welcome',
        loadChildren: () =>
          import('./home/welcome.routes').then((m) => m.WELCOME_ROUTES),
      },
      {
        path: 'partners',
        loadChildren: () =>
          import('./partner/partner.routes').then((m) => m.PARTNERS_ROUTES),
      },
      {
        path: 'messages',
        loadChildren: () =>
          import('./messages/message.routes').then((m) => m.MessageRoutes),
      },
    ],
  },
];
