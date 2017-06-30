# Projeto para Gerenciamento de Pessoas.



Para esse projeto criei duas aplicações, uma com Angular4 e outra com SpringBoot, para a persistencia dos dados utilizei 
PostgreSql. 
A aplicação cliente está totalmente desaclopada da aplicação servidor, toda comunicação entre as duas são por meio de chamadas rest. Os arquivos podem ser configurados para serem salvos fora da aplicação backend, isso é importante, pois permite que a aplicação possa "cair" ou ser "destruida" sem afetar os arquivos salvos, uma melhoria futura seria a definição de de politicas para apagar os arquivos temporários(Já está implementado um método na classe ArquivoUtil para isso, onde ela pode apagar arquivos por data). Para possibilitar o acesso ao arquivo na maquina por meio da url no browser eu adicionei um `ResourceHandler(CustomWebMvcAutoConfig)` para fazer o mapemaneto de todo caminho contendo `/arquivos/` para o endereço correspondente segundo o filesystem configurado.
Para as validações utilizei a as implementação do JSR-303/JSR-349 Bean Validation no Spring. 
Para a exclusão lógica dos dados utilizei a anotação 
  `@SQLDelete(sql = "Update Pessoa set ativo = false where id = ?")` que possibilita, ao executar o método `delete(id)` do repository, atualizar o status da coluna ativo para `false`. Também utilizei a anotação `@Where(clause = "ativo = true")` para possibilitar trabalhar sem me preocupar em colocar `ativo=true` em cada select executado. Como a exclusão lógica mantém o dado no banco foi necessário tirar a restrição `Unique` das tabelas.
  Por limitações de Hardware não foi possível utilizar o Docker no projeto.
  

