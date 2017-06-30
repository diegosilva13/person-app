import { Routes, RouterModule } from '@angular/router';

import {
	ListPessoasComponent, 
	CreatePessoasComponent, 
	EditPessoasComponent
} from "./pessoas/index";

const appRoutes: Routes = [
  { path: 'pessoas', component: ListPessoasComponent},
  { path: 'pessoas/cadastrar', component: CreatePessoasComponent},
  { path: 'pessoas/editar/:id', component: EditPessoasComponent},

  // otherwise redirect to home
  { path: '**', redirectTo: 'pessoas'}
];

export const routing = RouterModule.forRoot(appRoutes);
