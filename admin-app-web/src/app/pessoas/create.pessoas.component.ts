import { Component, OnInit } from '@angular/core';
import { AlertService, PessoaService} from '../_services/index';
import { Router} from '@angular/router';

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
      private router: Router
    ) {}

    ngOnInit() {

    }

    mudarImagem(event: any){
      let arquivos: FileList = event.target.files;
      if(arquivos.length > 0) {
        this.pessoaService.uploadFoto(arquivos[0])
           .subscribe(
               data => {
                  console.log(data);
               },
               error => {
                   console.log(error);
               }
             );

          let arquivo: File = arquivos[0];
          this.nomeImagem = arquivo.name;
          var leitorArquivo:FileReader = new FileReader();
          leitorArquivo.onloadend = (e) => {
              this.model.imagemBase64 = this.nomeImagem +','+leitorArquivo.result;
          }
        leitorArquivo.readAsDataURL(arquivo);
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
                  }else{
                     this.alertService.error(data.mensagem);
                  }
                this.loading = false;
             },
             error => {
                 console.log(error);
                 this.alertService.error(error.mensagem || error);
                 this.loading = false;
             }
           );

      ngForm._submitted = false
    }

    redirecionar(pagina: string){
      this.model = {};
      console.log(pagina);
      this.router.navigate([pagina]);
    }
}
