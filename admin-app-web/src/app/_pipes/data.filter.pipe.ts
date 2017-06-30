import * as _ from "lodash";
import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: "filtroPeloNome"
})
export class FiltroPeloNomePipe implements PipeTransform {

    transform(array: any[], query: any): any {
        if (query) {
            return _.filter(array, row=>row.nome.indexOf(query) > -1);

        }   
        return array;
    }
}

@Pipe({
    name: "filtroPeloEmail"
})
export class FiltroPeloNomeEmail implements PipeTransform {

    transform(array: any[], query: any): any {
        if (query) {
            return _.filter(array, row=>row.email.indexOf(query) > -1);

        }   
        return array;
    }
}

@Pipe({
    name: "filtroPeloCpf"
})
export class FiltroPeloCPFPipe implements PipeTransform {

    transform(array: any[], query: any): any {
        if (query) {
            query = query.replace(".","").replace("-","");
            return _.filter(array, row=>row.cpf.indexOf(query) > -1);

        }   
        return array;
    }
}


@Pipe({
    name: "filtroPeloNasc"
})
export class FiltroPeloNascPipe implements PipeTransform {

    transform(array: any[], query: any): any {
        if (query) {
            return _.filter(array, row=>row.dataNascimento.indexOf(query) > -1);

        }   
        return array;
    }
}

