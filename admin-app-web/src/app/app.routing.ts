import { Routes, RouterModule } from '@angular/router';

import {ListPessoasComponent, CreatePessoasComponent} from "./pessoas/index";

const appRoutes: Routes = [
  { path: 'pessoas', component: ListPessoasComponent},
  { path: 'pessoas/new', component: CreatePessoasComponent},

  // otherwise redirect to home
  { path: '**', redirectTo: 'pessoas'}
];

export const routing = RouterModule.forRoot(appRoutes);
