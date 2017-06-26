import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
import {routing} from "./app.routing";
import {APP_BASE_HREF} from "@angular/common";
import {ListPessoasComponent, CreatePessoasComponent} from "./pessoas/index";
import {NavBarComponent, AlertComponent} from "./_directives/index";
import {AlertService, PessoaService} from "./_services/index";

@NgModule({
  declarations: [
    AppComponent,
    ListPessoasComponent,
    CreatePessoasComponent,
    NavBarComponent,
    AlertComponent
  ],
    imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      routing
    ],
  providers: [
    {provide: APP_BASE_HREF, useValue : '/' },
    BaseRequestOptions,
    AlertService,
    PessoaService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
