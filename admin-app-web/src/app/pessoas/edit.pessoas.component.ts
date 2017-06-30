import { Component, OnInit } from '@angular/core';
import { AlertService, PessoaService} from '../_services/index';
import { Router, ActivatedRoute} from '@angular/router';
import {HtmlUtil} from "app/_utils/index";

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'edit.pessoas.component.html'
})
export class EditPessoasComponent implements OnInit {
    model: any = {};
    loading: boolean;
    nomeImagem: string = "";
    id: any;
    
    constructor(
      private alertService: AlertService,
      private pessoaService: PessoaService,
      private router: Router,
      private route: ActivatedRoute,
      private htmlUtil: HtmlUtil
    ) {}

    ngOnInit() {
      this.id = this.route.snapshot.params['id'];
      this.carregarPessoaParaEdicao();
    }

    mudarImagem(event: any){
      let arquivos: FileList = event.target.files;
      if(arquivos.length > 0) {
        this.pessoaService.uploadFoto(arquivos[0])
           .subscribe(
               data => {
                  if(data.codigoResposta == 200){
                    this.model.foto = data.objetoResposta;
                  }else{
                     this.alertService.error(data.mensagem);
                     this.limparImagem();
                  }
               },
               error => {
                   this.alertService.error(error);
                   this.limparImagem();
               }
           );
      }
    }

    atualizar(ngForm: any){
      this.loading = true;
      this.pessoaService.atualizar(this.id, this.model)
         .subscribe(
             data => {
                console.log(data);
                if(data.codigoResposta == 200){
                    this.alertService.success("Operação realizada com sucesso.", true);
                    this.carregarPessoaParaEdicao();
                  }else{
                     this.alertService.error(data.mensagem);
                     this.carregarImagemPerfil();
                     this.formatarDataNascimento(); 
                  }
                this.loading = false;
             },
             error => {
                 console.log(error);
                 this.alertService.error(error.mensagem || error);
                 this.loading = false;
                 this.carregarImagemPerfil();
                 this.formatarDataNascimento();  
             }
           );

      ngForm._submitted = false
    }

    carregarPessoaParaEdicao(){
      this.loading = true;
      this.pessoaService.buscarPorId(this.id)
         .subscribe(
             data => {
                if(data.codigoResposta == 200){
                    this.model = data.objetoResposta;
                    console.log(this.model.foto);
                    this.formatarDataNascimento();
                    this.carregarImagemPerfil();
                  }else{
                     this.alertService.error(data.mensagem);
                  }
                this.loading = false;
             },
             error => {
                 console.log(error);
                 this.alertService.error(error.mensagem || error); 
             }
           );
    }

    limparImagem(){
      this.htmlUtil.limparHtmlPorId("ver_imagem");
    }

    formatarDataNascimento(){
      let data = this.model.dataNascimento.split("/");
      this.model.dataNascimento = data[2]+'-'+data[1]+'-'+data[0];
    }

    carregarImagemPerfil(){
      if(this.model.foto != null && this.model.foto != ""){
        let url = "http://localhost:8080/api/arquivos/"+this.model.foto;
                    this.htmlUtil.visualizarImagemNoId("ver_imagem", url);
      }
    }
}
