import * as _ from "lodash";
import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: "cpf"
})
export class CPFPipe implements PipeTransform {

    transform(cpf: any, query: any): any {
        let grupo1 = cpf.slice(0, 3);
        let grupo2 = cpf.slice(3, 6);
        let grupo3 = cpf.slice(6, 9);
        let grupo4 = cpf.slice(9, 11);

        return grupo1+'.'+grupo2+'.'+grupo3+'-'+grupo4;
    }
}