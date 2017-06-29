import * as _ from "lodash";
import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: "dataFilter"
})
export class DataFilterPipe implements PipeTransform {

    transform(array: any[], query: any): any {
        let data = JSON.parse(JSON.stringify(query));
        if (data.nome || data.cpf || data.email || data.dataNascimento) {
        	let cpf = data.cpf != "" ? data.cpf.replace(".", "").replace("-","") : "";

            return _.filter(array, row=>row.nome.indexOf(data.nome) > -1)
            	|| _.filter(array, row=>row.cpf.indexOf(cpf) > -1)
        		|| _.filter(array, row=>row.email.indexOf(data.email) > -1)
        		|| _.filter(array, row=>row.dataNascimento.indexOf(data.dataNascimento) > -1);

        }	
        return array;
    }
}