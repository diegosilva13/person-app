import { Injectable } from '@angular/core';

@Injectable()
export class HtmlUtil{

  constructor() { }

    limparHtmlPorId(idElemento: string){
      try {
        let elemento = document.getElementById(idElemento);
        elemento.innerHTML = "";
      }catch (e){
        console.log(e);
      }
    }
}
