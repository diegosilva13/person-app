import { Component, OnInit } from '@angular/core';

import { AlertService, PessoaService} from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'list.pessoas.component.html'
})

export class ListPessoasComponent implements OnInit {
    public data;
    public filterQuery = {};
    public rowsOnPage = 10;
    public sortBy = "nome";
    public sortOrder = "asc";
    private id = "";


    constructor(
    	private alertService: AlertService,
      	private pessoaService: PessoaService
    ) {
    }

    ngOnInit(): void {
        this.carregarListagemDePessoas();
    }

    public toInt(num: string) {
        return +num;
    }

    carregarListagemDePessoas(){
    	this.pessoaService.buscarTodos()
           .subscribe(
               data => {
                  if(data.codigoResposta == 200){
                    this.data = data.objetoResposta;
                  }else{
                     this.alertService.error(data.mensagem);
                  }
               },
               error => {
                   this.alertService.error(error);
               }
           );
      
    }

    iniciarExcluir(id: any){
    	this.id = id;
    }

    cancelarExcluir(){
    	this.id = "";
    }

    excluirRegistro(){
    	this.pessoaService.excluir(this.id)
           .subscribe(
               data => {
                  if(data.codigoResposta == 200){
                  	this.carregarListagemDePessoas();
                    this.alertService.success("Operação realizada com sucesso.", true);
                  }else{
                     this.alertService.error(data.mensagem);
                  }
               },
               error => {
                   this.alertService.error(error);
               }
           );
    }
}
