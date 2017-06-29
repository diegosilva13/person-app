import { Component, OnInit } from '@angular/core';
import { AlertService, PessoaService} from '../_services/index';
import { Router} from '@angular/router';
import {HtmlUtil} from "app/_utils/index";

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'create.pessoas.component.html'
})
export class CreatePessoasComponent implements OnInit {
    model: any = {};
    loading: boolean;
    nomeImagem: string = "";
    
    constructor(
      private alertService: AlertService,
      private pessoaService: PessoaService,
      private router: Router,
      private htmlUtil: HtmlUtil
    ) {}

    ngOnInit() {

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

    salvar(ngForm: any){
      this.loading = true;
      this.pessoaService.salvar(this.model)
         .subscribe(
             data => {
                console.log(data);
                if(data.codigoResposta == 200){
                    this.alertService.success("Operação realizada com sucesso.", true);
                    this.model = {};
                    this.limparImagem();
                  }else{
                     this.alertService.error(data.mensagem);
                     this.model.dataNascimento = ""; 
                  }
                this.loading = false;
             },
             error => {
                 console.log(error);
                 this.alertService.error(error.mensagem || error);
                 this.loading = false;
                 this.model.dataNascimento = ""; 
             }
           );

      ngForm._submitted = false
    }

    redirecionar(pagina: string){
      this.model = {};
      console.log(pagina);
      this.router.navigate([pagina]);
    }

    limparImagem(){
      this.htmlUtil.limparHtmlPorId("ver_imagem");
    }
}
