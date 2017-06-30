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

    visualizarImagemNoId(idElemento: string, urlImagem: string){
      try {
        let elemento = document.getElementById(idElemento);
        elemento.innerHTML = "<div class='col-md-3'><img class='img-responsive' src='"+urlImagem+"'></div>";
      }catch (e){
        console.log(e);
      }
    }
}
