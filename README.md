# 🏨 Sistema de Gerenciamento Hoteleiro

Um sistema completo para gerenciamento de quartos e reservas de hotel, desenvolvido em Java 17 com Spring Boot.

## 📋 Funcionalidades

### 🏠 Gestão de Quartos
- ✅ Cadastro, listagem, busca, atualização e exclusão de quartos
- ✅ Tipos de quarto: STANDARD, DELUXE, SUITE
- ✅ Controle de status: ATIVO/INATIVO
- ✅ Capacidade e valor da diária

### 📅 Gestão de Reservas
- ✅ Criação de reservas com validação de datas
- ✅ Fluxo de estados: CREATED → CHECKED_IN → CHECKED_OUT
- ✅ Cancelamento de reservas (apenas no status CREATED)
- ✅ Cálculo automático do valor total no check-out
- ✅ Verificação de conflitos de datas

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL** (banco de dados)
- **Flyway** (migrações de banco)
- **Jakarta Bean Validation** (validações)
- **Maven** (gerenciamento de dependências)

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java 17](https://openjdk.org/projects/jdk/17/)
- [Maven 3.6+](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) (versão 12 ou superior)
- [Git](https://git-scm.com/)

## 🚀 Como Executar o Projeto

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd hotel