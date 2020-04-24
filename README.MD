`obs: É necessário a instalação do Docker: https://www.docker.com/, caso ainda não tenha`

## Contexto da Aplicação
Implementação de uma API Rest capaz de realizar a conversão entre duas moedas utilizando taxas de conversões atualizadas de um serviço externo. 
É necessário o ID do usuário que deseja realizar a conversão.
A API deverá registrar cada transação de conversão com todas as informações relacionadas e também disponibilizar um endpoint para consulta das transações realizadas por um usuário.

## Stack
  - Kotlin 1.3.70
  - JDK 8
  - Javalin 3.8.0 -> Creation REST APIs
  - Khttp 1.0.0 -> HTTP client requests 
  - Docker 18.09.3 -> OS-level virtualization
  - Exposed 0.14.1 -> Object Relational Mapping
  - H2 Database 1.4.197 -> Database embedded
  - HikariCP 2.7.8 -> Pool de conexão JDBC
  - Swagger 2.0.9 -> Documentação API
  - Koin 2.1.0  -> Injeção de dependência
  - Mockk 1.9.3 -> Criação de mocks
  - JUnit 5.6.0 -> Testes de unidade e de integração
      
## Executando a aplicação
```bash
  $ docker build . -t example-javalin
  $ docker run example-javalin
```
## Documentação da API
 - http://localhost:7000/swagger-ui

