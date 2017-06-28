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

        return this.http.post(url, pessoa, new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());
    }

    uploadFoto(arquivo: any){
      let url = 'http://localhost:8080/api/upload';
        let headers = new Headers({
        });

        let formData = new FormData();
            formData.append("file", arquivo);
            console.log(url);
        return this.http.post(url, formData, new RequestOptions({headers: headers}))
            .map((response: Response) => response.json());
    }
}
