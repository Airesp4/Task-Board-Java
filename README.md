# Desafio DIO: Gerenciador de Boards (CLI)

Este projeto é um desafio da DIO que consiste em criar uma aplicação Java em linha de comando (CLI) para gerenciamento de *boards* (quadros de tarefas).

## 💡 Objetivo

Construir uma aplicação baseada em menus que permita ao usuário:
- Criar um board com colunas (iniciais, pendentes, finalizadas, canceladas e adicionais);
- Listar e selecionar boards existentes;
- Excluir boards;
- Persistir os dados em banco relacional.

## 🛠️ Tecnologias Utilizadas

- Java 17
- JDBC
- Maven
- H2 e PostgreSQL (opcional)
- Liquibase (para migrações de banco de dados)
- Lombok

## 📦 Funcionalidades

- Menu interativo via terminal
- Criação de boards com colunas personalizadas
- Armazenamento e recuperação de dados usando JDBC
- Organização do código em camadas (UI, Service, DAO, Entity)

## 📚 Aprendizados

- Uso de JDBC para persistência
- Separação de responsabilidades em projetos Java
- Manipulação de entrada de usuário via `Scanner`
- Versionamento de banco de dados com Liquibase