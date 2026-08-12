package br.edu.cetam.matriculatec.main;

import br.edu.cetam.matriculatec.model.*;
import br.edu.cetam.matriculatec.service.MatriculaService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static MatriculaService service = new MatriculaService();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        carregarDadosIniciais();

        int opcao = -1;
        do {
            System.out.println("\n==================================================");
            System.out.println("   ? CETAM - MatriculaTec (Sistema de Matrículas)");
            System.out.println("==================================================");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Atualizar Nome do Aluno");
            System.out.println("4. Remover Aluno");
            System.out.println("--------------------------------------------------");
            System.out.println("5. Matricular Aluno em Turma");
            System.out.println("6. Alterar Situação da Matrícula");
            System.out.println("7. Relatório de Turmas e Taxa de Ocupação");
            System.out.println("8. Mapeamento Turmas/Alunos (Map)");
            System.out.println("9. Listar Alunos Aptos a Avançar de Módulo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                System.out.println();
                switch (opcao) {
                    case 1 -> cadastrarAluno();
                    case 2 -> listarAlunos();
                    case 3 -> System.out.println("Funcionalidade: Atualizar Aluno");
                    case 4 -> System.out.println("Funcionalidade: Remover Aluno");
                    case 5 -> System.out.println("Funcionalidade: Matricular em Turma");
                    case 6 -> System.out.println("Funcionalidade: Alterar Situação");
                    case 7 -> System.out.println("Funcionalidade: Relatórios");
                    case 8 -> System.out.println("Funcionalidade: Mapeamento");
                    case 9 -> System.out.println("Funcionalidade: Alunos Aptos");
                    case 0 -> System.out.println("Encerrando o MatriculaTec. Até mais!");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, digite um número válido.");
            } catch (Exception e) {
                System.out.println("⚠️ Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void carregarDadosIniciais() {
        // Inicialização de dados no serviço se necessário
    }

    private static void cadastrarAluno() {
        System.out.println("--- Cadastrar Novo Aluno ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();

        LocalDate dataNasc = null;
        while (dataNasc == null) {
            System.out.print("Data de Nascimento (dd/mm/aaaa): ");
            try {
                dataNasc = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Formato de data inválido! Use dd/mm/aaaa.");
            }
        }

        service.cadastrarAluno(new Aluno(nome, mat, dataNasc));
        System.out.println("✅ Aluno cadastrado com sucesso!");
    }

    private static void listarAlunos() {
        System.out.println("--- Lista de Alunos ---");
        List<Aluno> alunos = service.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            alunos.forEach(a -> System.out.println(a.gerarRelatorio()));
        }
    }
}