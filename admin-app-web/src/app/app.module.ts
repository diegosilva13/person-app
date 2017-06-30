import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
import {routing} from "./app.routing";
import {APP_BASE_HREF} from "@angular/common";
import {
  ListPessoasComponent, 
  CreatePessoasComponent, 
  EditPessoasComponent
  } from "./pessoas/index";
import {NavBarComponent, AlertComponent} from "./_directives/index";
import {AlertService, PessoaService} from "./_services/index";
import {HtmlUtil} from "app/_utils/index";
import {
  FiltroPeloNascPipe, 
  FiltroPeloCPFPipe, 
  FiltroPeloNomeEmail,
  FiltroPeloNomePipe,
  CPFPipe 
  } from "app/_pipes/index";
import {DataTableModule} from "angular2-datatable";

@NgModule({
  declarations: [
    AppComponent,
    ListPessoasComponent,
    CreatePessoasComponent,
    EditPessoasComponent,
    NavBarComponent,
    AlertComponent,
    FiltroPeloNascPipe, 
    FiltroPeloCPFPipe, 
    FiltroPeloNomeEmail,
    FiltroPeloNomePipe,
    CPFPipe
  ],
    imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      routing,
      DataTableModule
    ],
  providers: [
    {provide: APP_BASE_HREF, useValue : '/' },
    BaseRequestOptions,
    AlertService,
    PessoaService,
    HtmlUtil
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
