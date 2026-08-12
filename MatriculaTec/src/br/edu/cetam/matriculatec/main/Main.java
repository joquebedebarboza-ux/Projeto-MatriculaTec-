package br.edu.cetam.matriculatec.main;

import br.edu.cetam.matriculatec.exception.*;
import br.edu.cetam.matriculatec.model.*;
import br.edu.cetam.matriculatec.model.enums.SituacaoMatricula;
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
            System.out.println("   🎓 CETAM - MatriculaTec (Sistema de Matrículas)");
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
                    case 3 -> atualizarAluno();
                    case 4 -> removerAluno();
                    case 5 -> matricularAluno();
                    case 6 -> alterarSituacaoMatricula();
                    case 7 -> relatorioTurmas();
                    case 8 -> mapeamentoTurmas();
                    case 9 -> listarAptosAvancar();
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

    private static void atualizarAluno() {
        System.out.print("Digite a matrícula do aluno a atualizar: ");
        String mat = scanner.nextLine();
        try {
            System.out.print("Digite o novo nome completo: ");
            String novoNome = scanner.nextLine();
            service.atualizarNomeAluno(mat, novoNome);
            System.out.println("✅ Nome atualizado com sucesso!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void removerAluno() {
        System.out.print("Digite a matrícula do aluno a ser removido: ");
        String mat = scanner.nextLine();
        try {
            service.removerAluno(mat);
            System.out.println("✅ Aluno removido com sucesso!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void matricularAluno() {
        System.out.print("Matrícula do Aluno: ");
        String mat = scanner.nextLine();
        System.out.print("Código da Turma (ex: INF-M1): ");
        String codTurma = scanner.nextLine();

        try {
            Aluno aluno = service.buscarAlunoPorMatricula(mat);
            Turma turma = service.buscarTurmaPorCodigo(codTurma);
            turma.matricularAluno(aluno);
            System.out.println("✅ Matrícula efetuada com sucesso!");
        } catch (RegistroNaoEncontradoException | VagasEsgotadasException | MatriculaDuplicadaException e) {
            System.out.println("⚠️ [ERRO NA MATRÍCULA] " + e.getMessage());
        }
    }

    private static void alterarSituacaoMatricula() {
        System.out.print("Código da Turma: ");
        String codTurma = scanner.nextLine();
        System.out.print("Matrícula do Aluno: ");
        String mat = scanner.nextLine();

        try {
            Turma turma = service.buscarTurmaPorCodigo(codTurma);
            Matricula m = turma.getListaMatriculas().stream()
                    .filter(matr -> matr.getAluno().getMatricula().equalsIgnoreCase(mat))
                    .findFirst()
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Matrícula não encontrada para esta turma."));

            System.out.println("Escolha a nova situação: 1-ATIVA, 2-TRANCADA, 3-CONCLUIDA, 4-CANCELADA");
            int op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1 -> m.setSituacao(SituacaoMatricula.ATIVA);
                case 2 -> m.setSituacao(SituacaoMatricula.TRANCADA);
                case 3 -> m.setSituacao(SituacaoMatricula.CONCLUIDA);
                case 4 -> m.setSituacao(SituacaoMatricula.CANCELADA);
                default -> System.out.println("Opção inválida.");
            }
            System.out.println("✅ Situação alterada para: " + m.getSituacao().getDescricao());
        } catch (Exception e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void relatorioTurmas() {
        System.out.println("--- Relatório de Turmas e Ocupação ---");
        for (Turma t : service.listarTurmas()) {
            System.out.println(t.gerarRelatorio());
        }
    }

    private static void mapeamentoTurmas() {
        System.out.println("--- Mapeamento de Turmas e Alunos Ativos (Map) ---");
        Map<Turma, List<Aluno>> mapa = service.mapearAlunosPorTurma();
        mapa.forEach((turma, alunos) -> {
            System.out.println("\nTurma: " + turma.getCodigo() + " (" + turma.getModulo().getNome() + ")");
            if (alunos.isEmpty()) {
                System.out.println("  (Nenhum aluno ativo)");
            } else {
                alunos.forEach(a -> System.out.println("  - " + a.getNome() + " [" + a.getMatricula() + "]"));
            }
        });
    }

    private static void listarAptosAvancar() {
        System.out.print("Código da Turma: ");
        String cod = scanner.nextLine();
        try {
            Turma t = service.buscarTurmaPorCodigo(cod);
            List<Aluno> aptos = service.listarAlunosAptosAvancar(t);
            System.out.println("\n🎓 Alunos com status 'CONCLUIDA' aptos a avançar:");
            if (aptos.isEmpty()) {
                System.out.println("Nenhum aluno apto no momento.");
            } else {
                aptos.forEach(a -> System.out.println(" - " + a.getNome()));
            }
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void carregarDadosIniciais() {
        Curso curso = new Curso("INF01", "Informática para Internet");
        Modulo m1 = new Modulo("Módulo I - Introdução");
        m1.adicionarDisciplina(new Disciplina("Lógica de Programação", 80));
        m1.adicionarDisciplina(new Disciplina("Arquitetura de Computadores", 60));
        curso.adicionarModulo(m1);
        service.cadastrarCurso(curso);

        Turma turma1 = new Turma("INF-M1", m1, 5);
        service.cadastrarTurma(turma1);

        service.cadastrarAluno(new Aluno("CAIO FELIPE FREITAS DE JESUS", "202601", LocalDate.of(2004, 5, 12)));
        service.cadastrarAluno(new Aluno("JOQUEBEDE BARBOZA SANTOS", "202602", LocalDate.of(2005, 8, 22)));
        service.cadastrarAluno(new Aluno("SARAH DA SILVA SIQUEIRA", "202603", LocalDate.of(2003, 3, 15)));
        service.cadastrarAluno(new Aluno("SOPHIA ROQUE MARTINS", "202604", LocalDate.of(2004, 11, 30)));
        service.cadastrarAluno(new Aluno("SUELEN TAVARES DOS SANTOS", "202605", LocalDate.of(2005, 1, 10)));
        service.cadastrarAluno(new Aluno("SUELY NASCIMENTO COSTA", "202606", LocalDate.of(2003, 9, 5)));
    }
}