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
}
