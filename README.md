<h1 align="center"> Gestão Cognitiva de Talentos </h1>
<h2 align="center"> Global Solution 2025 - Domain Driven Design </h2>
Sistema desenvolvido para gerenciar o ciclo de aprendizado e produtividade de colaboradores em um ambiente digital, simulando processos de recrutamento, acompanhamento de desempenho e recomendações inteligentes para desenvolvimento de habilidades. A aplicação foi implementada usando Java orientado a objetos, Domain Driven Design (DDD) e interface gráfica Swing.

## Como Rodar o Projeto
### Pré-Requisitos
- JDK 11 ou superior instalado e configurado no PATH
- Sistema operacional Windows / Linux / macOS
- Terminal (PowerShell, CMD, ou Terminal Unix)
- IDE Java opcional (Eclipse, IntelliJ IDEA, VS Code)

## Como rodar o projeto
### 1. Navegue até a pasta raiz do projeto (onde está a pasta src):
```bash
cd path/to/globalsolution12025
```

### 2. Compile todos os arquivos .java do projeto:
No PowerShell (Windows):
```bash
javac -d out -cp src (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```
No CMD (Windows) ou Linux/macOS (bash):
```bash
find src -name "*.java" > sources.txt
javac -d out -cp src @sources.txt
```
### 3. Execute a aplicação:
```bash
java -cp out br.com.globalsolution.ui.MainWindow
```
# Relatório Técnico

## 1. O domínio escolhido e o problema resolvido
O domínio Gestão Cognitiva de Talentos, é voltado para apoiar o futuro do trabalho por meio de ferramentas que acompanham e desenvolvem competências profissionais de forma estruturada.
Atualmente, muitas organizações enfrentam dificuldade em:
- Identificar lacunas de habilidades entre colaboradores;
- Indicar treinamentos realmente relevantes;
- Acompanhar a evolução de competências ao longo do tempo;
- Consolidar avaliações de desempenho de maneira inteligível.

O sistema desenvolvido aborda esses problemas ao permitir registrar colaboradores, associar competências, cadastrar treinamentos, registrar avaliações e gerar recomendações inteligentes para desenvolvimento contínuo. A solução promove produtividade e aprendizagem contínua, alinhada à human-centric AI.

## 2. Decisões de Modelagem
### Entidades
 - Colaborador: Representa a pessoa acompanhada pelo sistema. Possui nome, identificador e um conjunto de competências. É o agregado principal responsável por manter regras como evolução de níveis, atualizações por avaliação e registro de treinamentos concluídos;
 - Competência: Modelada como Objeto de Valor por ser imutável e representar um conceito (nome + nível). Ela só muda por eventos e decisões dentro do `Colaborador`;
 - Treinamento: Representa cursos ou atividades que desenvolvem competências específicas. Armazena quais competências impacta e em qual intensidade;
 - Avaliação: Evento que altera o estado do Colaborador. Mantém nota, data e impacto nas competências avaliadas.
   
### Agregações e Regras Importantes:
- O Colaborador é o agregado raiz e controla sua lista de competências;
- Treinamentos e avaliações nunca alteram dados diretamente; apenas solicitam ao colaborador aplicar suas regras de negócio;
- Competências são tratadas como Objetos de Valor, garantindo consistência e integridade.

### Serviços de Domínio
- RecomendacaoService: Analisa as competências de cada colaborador, identifica lacunas e sugere treinamentos que podem melhorar seus níveis.
Esse serviço opera no domínio, sem depender de interface gráfica ou arquivos externos.

### Repositórios
Para manter baixo acoplamento, o domínio utiliza interfaces:
- `ColaboradorRepository`
- `TreinamentoRepository`
As implementações usam persistência simples via arquivos texto (ex.: `data.txt`, `treinamentos.txt`), simulando armazenamento local sem expor detalhes ao domínio.

## 3. Justificativa para o Design da Interface Swing
A interface foi desenvolvida utilizando Swing por ser uma tecnologia consolidada, estável e amplamente utilizada em projetos acadêmicos e desktops.

A UI foi estruturada para facilitar o fluxo de trabalho, utilizando:
### Organização por abas (JTabbedPane)
- Colaboradores: cadastro, competências e buscas;
- Treinamentos: listagem e cadastro;
- Recomendações: geração de sugestões de treinamentos com base nas lacunas;
- Relatórios: visão consolidada do desempenho e evolução dos colaboradores.

 ### Decisões de design
 - Componentes padrão (JTable, JTextField, JButton) para clareza e simplicidade;
 - Painéis modulares, mantendo o código organizado e facilitando futuras expansões;
 - Validação básica na própria interface, evitando dados inconsistentes enviados ao domínio.

## 4. Boas Práticas de Orientação a Objetos 
O desenvolvimento seguiu princípios fundamentais da Orientação a Objeto e boas práticas recomendadas em Domain Driven Design.

### Encapsulamento: 
Todas as entidades mantêm seus atributos privados e fornecem apenas métodos essenciais para manipulação, garantindo controle sobre regras de negócio.

### Composição:
Competências fazem parte da estrutura interna do Colaborador, reforçando o conceito de agregado.

### Herança e Polimorfismo:
Eventos de domínio, como Avaliação, são derivados de uma estrutura base (EventoDominio), permitindo extensões futuras.

### Abstrações e Interfaces:
Repositórios e serviços são abstraídos por interfaces, facilitando substituição, testes e evolução do sistema.

### Baixo acoplamento e alta coesão:
- Cada classe possui uma responsabilidade clara.
- Dependências são passadas via construtor, reduzindo dependência de implementações concretas.


## Integrantes:
- **Rafaela Heer Robinson** - 560249
- **Lucas Alves Piereti** - 559533
- **Julia Hadja Kfouri Nunes** - 559410
