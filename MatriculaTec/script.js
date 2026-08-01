// Alternar abas
function showSection(sectionId) {
  const sections = document.querySelectorAll('.section');
  sections.forEach(sec => sec.classList.remove('active'));

  const buttons = document.querySelectorAll('.nav-btn');
  buttons.forEach(btn => btn.classList.remove('active'));

  const activeSection = document.getElementById(sectionId);
  if (activeSection) {
    activeSection.classList.add('active');
  }

  if (event && event.currentTarget) {
    event.currentTarget.classList.add('active');
  }

  if (sectionId === 'alunos') {
    renderizarTabela();
  }
}

// Dados locais
let alunos = JSON.parse(localStorage.getItem('matriculatec_alunos')) || [
  { matricula: "2026001", nome: "Maria Silva", curso: "Técnico em Informática", situacao: "Ativa" },
  { matricula: "2026002", nome: "João Souza", curso: "Técnico em Administração", situacao: "Pendente" },
  { matricula: "2026003", nome: "suelen", curso: "Técnico em Informática", situacao: "Ativa" }
];

function renderizarTabela() {
  const tbody = document.querySelector('#tabelaAlunos tbody');
  if (!tbody) return;

  tbody.innerHTML = "";

  alunos.forEach((aluno, index) => {
    const badgeClass = aluno.situacao === "Ativa" ? "ativada" : "pendente";
    
    tbody.innerHTML += `
      <tr>
        <td><strong>${aluno.matricula}</strong></td>
        <td>${aluno.nome}</td>
        <td>${aluno.curso}</td>
        <td><span class="badge ${badgeClass}">${aluno.situacao}</span></td>
        <td>
          <button class="btn-action btn-edit" onclick="editarNome(${index})">✏️ Editar</button>
          <button class="btn-action btn-status" onclick="alterarStatus(${index})">🔄 Status</button>
          <button class="btn-action btn-delete" onclick="removerAluno(${index})">🗑️ Excluir</button>
        </td>
      </tr>
    `;
  });
}

function editarNome(index) {
  const novoNome = prompt("Digite o novo nome do aluno:", alunos[index].nome);
  if (novoNome && novoNome.trim() !== "") {
    alunos[index].nome = novoNome.trim();
    salvarEAtualizar();
  }
}

function alterarStatus(index) {
  alunos[index].situacao = alunos[index].situacao === "Ativa" ? "Pendente" : "Ativa";
  salvarEAtualizar();
}

function removerAluno(index) {
  if (confirm(`Deseja realmente remover o aluno ${alunos[index].nome}?`)) {
    alunos.splice(index, 1);
    salvarEAtualizar();
  }
}

function salvarEAtualizar() {
  localStorage.setItem('matriculatec_alunos', JSON.stringify(alunos));
  renderizarTabela();
}

function realizarMatricula(event) {
  event.preventDefault();

  const nomeInput = document.getElementById('nome').value;
  const cursoInput = document.getElementById('cursoSelect').value;

  const novoAluno = {
    matricula: "202600" + (alunos.length + 1),
    nome: nomeInput,
    curso: cursoInput === 'Informática' ? 'Técnico em Informática' : 
           cursoInput === 'Enfermagem' ? 'Técnico em Enfermagem' : 'Técnico em Administração',
    situacao: "Ativa"
  };

  alunos.push(novoAluno);
  salvarEAtualizar();

  alert("✅ Aluno matriculado com sucesso!");
  document.getElementById('formMatricula').reset();
  showSection('alunos');
}

document.addEventListener("DOMContentLoaded", renderizarTabela);