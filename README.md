# 🎓 MatriculaTec - Sistema de Gestão de Matrículas (CETAM)

O **MatriculaTec** é um sistema desenvolvido em **Java** com foco nos conceitos de **Programação Orientada a Objetos (POO)**. O projeto gerencia cadastros de alunos, matrículas em turmas, relatórios de taxa de ocupação e situação acadêmica.

---

## 🚀 Funcionalidades

- **Gerenciamento de Alunos:**
  - Cadastrar novo aluno
  - Listar alunos cadastrados
  - Atualizar dados de aluno
  - Remover aluno
- **Gestão de Matrículas:**
  - Matricular aluno em turma
  - Alterar situação da matrícula
- **Relatórios e Mapeamentos:**
  - Relatório de turmas e taxa de ocupação
  - Mapeamento de Turmas/Alunos (`Map`)
  - Listagem de alunos aptos a avançar de módulo

---

## 🛠️ Tecnologias e Conceitos Utilizados

- **Linguagem:** Java (JDK 17+)
- **Estruturas de Dados:** `List`, `Map`, Manipulação de Datas (`java.time`)
- **Conceitos de POO:**
  - Abstração
  - Encapsulamento
  - Herança e Polimorfismo
  - Trata exceções personalizadas (`exception`)

---

## 📂 Estrutura do Projeto

```text
poo-java-projeto4/
└── MatriculaTec/
    └── src/
        └── br/edu/cetam/matriculatec/
            ├── main/        # Classe principal com o menu interativo
            ├── model/       # Entidades do sistema (Aluno, Turma, Matricula)
            ├── service/     # Regras de negócio e gerenciamento
            └── exception/   # Tratamento de erros customizados
