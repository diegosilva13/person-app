# pessoa-service

Aplicação que contém as apis que serão consumida pela aplicação cliente.

## Requisitos

* Java 8
* Maven
* PostgreSQL


# Configurando

Acesse a raiz da aplicação e edite o arquivo application.yml.
Será necessário configurar os dados para acesso ao banco e a pasta para upload dos arquivos da aplicação(Certifique-se que sua aplicação tera permissão de escrita para a pasta selecionada).
Veja que existem dois profiles para a aplicação aponte-os para bases diferentes.

# Executando

Abra o terminal na raiz do seu projeto e execute 

```sh
    $ mvn spring-boot:run
```
