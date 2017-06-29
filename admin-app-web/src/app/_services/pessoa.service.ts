import { Injectable} from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/share';

@Injectable()
export class PessoaService {
    constructor(private http: Http) { }

    salvar(pessoa: any) {
      let url = 'http://localhost:8080/api/pessoas';
        let headers = new Headers({
            'Content-type': 'application/json',
            'Accept': 'application/json'
        });

        let data = pessoa.dataNascimento.split("-");
        pessoa.dataNascimento = data[2]+'/'+data[1]+'/'+data[0];

        return this.http.post(url, JSON.stringify(pessoa), new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());
    }

    uploadFoto(arquivo: any){
      let url = 'http://localhost:8080/api/pessoas/upload';
        let headers = new Headers({
        });

        let form = new FormData();
            form.append("arquivo", arquivo);
        return this.http.post(url, form, new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());
    }

    buscarTodos(){
       let url = 'http://localhost:8080/api/pessoas';
        let headers = new Headers({
            'Content-type': 'application/json',
            'Accept': 'application/json'
        });

        return this.http.get(url, new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());     
    }

    excluir(id: any){
       let url = 'http://localhost:8080/api/pessoas/excluir';
        let headers = new Headers({
        });

        let form = new FormData();
            form.append("id", id);

        return this.http.post(url, form, new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());     
    }
}
