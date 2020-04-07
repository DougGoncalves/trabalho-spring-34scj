# Trabalho da disciplina de Spring 34SCJ.
**Aluno:** Bruno Delphino Zambotti (RM 334242)

**Professor:** Fabio Tadashi Miyasato.

**Descrição:** Projeto do trabalho final da disciplina de Spring da turma 34SCJ da FIAP.

## Descritivo do Trabalho
A FIAP resolveu criar seu próprio cartão de crédito para ser utilizado pelos alunos e para isso necessita de um sistema para gerenciamento e integração com outras empresas.

O trabalho deverá seguir os requisitos abaixo:

Requisitos funcionais:
- RF1 - Cadastro de Alunos
- RF2 - O cadastro inicial dos potenciais clientes do cartão será realizado via integração com um arquivo de texto
 [(exemplo)](https://drive.google.com/open?id=19ILqrYjOEe4C840ZRwhKDauvhDZCKc).
- RF3 - As compras realizadas nos cartões dos clientes serão recebidas via integração com uma Autorizadora. 
Criar os endpoints necessários para receber as realizações de transações.
- RF4 - Possibilitar a geração de um extrato via download (endpoint) ou envio no email do cliente 

Requisito não funcionais:
- RNF1 - Utilizar o Spring Framework.
- RNF2 - Criar um arquivo readme.md com as instruções para subir o/os projeto/s.
- RNF3 - Criar testes unitários e integrados para o/os projeto/s.
- RNF4 - Gerar uma massa simulada de transações.
- RNF5 - Documentação Swagger

## Tecnologias e ferramentas utilizadas
- Java 8 - Para compilar e executar os códigos descritos nesta aplicação.
- Maven - Para gerenciamento das dependências da aplicação.
- Swagger - Para expor a documentação dos endpoins via especificação OpenAPI.
- H2 Database - Para ser utilizado como banco de dados da aplicação.
- Spring Boot - Para expor os endpoints via dependência web, subir a aplicação via tomcat e carregar os contextos da aplicação.
- Spring Batch - Para processamento do arquivo com o cadastro inicial dos potenciais alunos/clientes do produto.
- Spring Data - Para manipulação e gerenciamento de artefatos no banco de dados.
- Spring Security - Para habilitar autorização nas rotas por meio de Basic Authentication.
- IntelliJ IDEA - Para realização do densenvolvimento da aplicação. (Porém, pode ser utiliziada outra IDE de sua preferência, como Eclipse por exemplo).

## Exemplo de Funcionamento
![](example.gif)

## Funcionalidades:

### Gerenciamento de Cartão dos Estudantes

- Consultar todos os estudantes e os dados de seu cartão
```(GET -> /spring/v1/student/credit-card)```

- Associar um estudante a dados de seu cartão
```(POST -> /spring/v1/student/credit-card)```

- Consultar um estudante específico com os dados do seu cartão
```(GET -> /spring/v1/student/credit-card/{id})```

- Alterar informações de um estudante ou dos dados de seu cartão
```(PUT -> /spring/v1/student/credit-card/{id})```

- Excluir um estudante e os e dados do seu cartão
```(DELETE -> /spring/v1/student/credit-card/{id})```

### Gerenciamento de Pagamentos

- Processar um pagamento
```(POST -> /spring/v1/payment)```

### Gerenciamento do Cartão
- Consultar extrato do cartão de crédito
```(GET -> /spring/v1/credit-card/statement)```

### Actuator:

A API desenvolvida possui a dependência do Spring Boot Actuator e dessa forma, caso você deseje listar todas as rotas disponíveis, ou ainda verificar informações adicionais sobre o estado da aplciação ou métricas, você deverá informar as seguintes url's no navegador: 

Para listar todas as informações disponibilizadas pela biblioteca do Spring Boot Actuator, informe a url: http://localhost:8080/actuator

Caso deseje verificar se a aplicação está em funcionamento, você pode utilizar a seguinte url: http://localhost:8080/actuator/health

Caso deseje verificar as rotas mapeadas pela aplicação, você pode utilizar a seguinte url: http://localhost:8080/actuator/mappings


### Testando a aplicação via Swagger:
Para realizar o teste da aplicação via interface gráfica do swagger, você deve subir a aplicação desejada via docker, ou utilizando o código fonte presente neste repositório, diretamente por meio de uma IDE como Eclipse ou IntelliJ, e acessar a seguinte url de exemplo: http://localhost:8080/swagger-ui.html.
![](swagger-example.png)


## Trabalhos futuros: 
- Desenvolver telas para facilitar o trabalho dos operadores.
- Desenvolver um cadastro de alunos assim como um cadastro de cartões de crédito isoladamente, e refatorar o gerenciamento de associação de cartão a um aluno para tratar apenas com os identificadores. 
- Refinar os fluxos e desenvolver as validações adequadas de persistência.

## Links:

- #### [Repositório](https://github.com/bruno-zambotti/trabalho-spring-34scj)