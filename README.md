# 📒 MyContacts

![Java](https://img.shields.io/badge/Java-POO-blue)
![Status](https://img.shields.io/badge/status-acad%C3%AAmico-green)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Sistema de **agenda de contatos em Java (console)** desenvolvido para praticar Programação Orientada a Objetos, manipulação de listas e organização modular em pacotes.

O sistema permite cadastrar, listar, buscar e remover contatos com validação de dados e tratamento de exceções.

## ALUNO: PAULO RICARDSON SILVA COSTA
## E-MAIL: dev.ricardson@gmail.com

---

## 🚀 Funcionalidades

- ➕ Adicionar contato  
- 📋 Listar contatos  
- 🔎 Buscar contato por nome  
- ❌ Remover contato  
- ✅ Validação de email  
- ⚠️ Exceção personalizada para contato não encontrado  
- 🔁 Menu interativo em loop  

---

## 🧠 Conceitos Aplicados

- Programação Orientada a Objetos
- Encapsulamento
- Herança
- Sobrescrita de métodos (`toString`)
- Exceções personalizadas
- Separação em camadas (model, service, controller)
- Coleções (`ArrayList`)
- Entrada de dados com `Scanner`

---

## 🧱 Estrutura do Projeto

mycontacts/
├── app/
│   └── Main.java
│
├── controller/
│   ├── MenuPrincipal.java
│   └── opcoes/
│       ├── AdicionarContatoOption.java
│       ├── ListarContatosOption.java
│       ├── BuscarContatoOption.java
│       └── RemoverContatoOption.java
│
├── service/
│   └── AgendaService.java
│
├── model/
│   ├── Contato.java
│   └── ContatoComercial.java
│
├── exceptions/
│   └── ContatoNaoEncontradoException.java
│
├── utils/
│   ├── ConsoleUI.java
│   └── ValidadorEmail.java

---

## 📊 Modelo de Dados


### Contato
- nome
- telefone
- email

### ContatoComercial (Herança)
- nome
- telefone
- email
- empresa

---

## 🖥️ Exemplo de Execução

===== AGENDA DE CONTATOS =====
1. Adicionar novo contato
2. Listar contatos
3. Buscar por nome
4. Remover contato
5. Sair


O menu permanece em execução até o usuário escolher a opção **Sair**.

---

## 🧠 Regras de Negócio

- Não é permitido cadastrar contato com email inválido  
- A busca por nome ignora maiúsculas/minúsculas  
- Ao tentar buscar ou remover um contato inexistente, uma exceção é lançada  
- Os contatos são armazenados em memória durante a execução
