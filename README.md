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
(Obs:Caso o programa não abra a janela, verifique se a pasta out realmente existe e se não houve erros de compilação.)

---
## Tutorial exemplo 

1. Em **Colaboradores**, cadastre:
```
Rafaela
Comunicação: 3; Liderança: 2; Java: 4;
```

2. Em **Treinamentos**, cadastre:
```
Comunicação Avançada
Comunicação: Melhora comunicação;
```

3. Em **Recomendações**, selecione *Rafaela* → clique em **Gerar Recomendações** → selecione o treinamento → **Completar Treinamento**.

(OBS: Se o nome não aparecer na lista, feche o programa e rode novamente no terminal que atualiza)

4. Em **Relatórios**, clique em **Gerar Relatório** e verá Comunicação aumentar de 3 para 4.


---
---
# Relatório Técnico
Implementado em Java + Swing + Domain Driven Design (DDD)

## 1. Domínio escolhido e problema resolvido

O sistema foi desenvolvido no domínio de Gestão Cognitiva de Talentos, alinhado às demandas do futuro do trabalho e ao uso de tecnologia para apoiar o desenvolvimento profissional contínuo.

O problema central envolve a dificuldade das organizações em:

- Acompanhar competências dos colaboradores;
- Identificar lacunas de habilidades;
- Recomendar treinamentos adequados;
- Monitorar a evolução das competências ao longo do tempo;
- Consolidar informações de avaliações de desempenho.

A solução implementada permite o cadastro de colaboradores, gerenciamento de competências, criação e consulta de treinamentos, detecção automática de lacunas e recomendações inteligentes com base no perfil de cada colaborador. A arquitetura foi construída segundo princípios de DDD, garantindo coesão, clareza e flexibilidade na evolução do sistema.

---

## 2. Decisões de Modelagem (Entidades, Agregados e Serviços)

A modelagem foi organizada nos seguintes pacotes:
```
br.com.globalsolution
├── application.services
├── entities
├── infrastructure.repositories
├── repositories
├── services
└── ui
```


### Entidades do domínio (`entities`)

#### Colaborador
- Entidade raiz do agregado.
- Armazena ID, nome e uma lista de competências com seus níveis.
- Implementa regras internas como adicionar competências, atualizar níveis e aplicar evoluções.
- Possui a classe interna `CompetenciaNivel`, representando a composição entre competência e nível.

#### Competencia
- Modelada como Objeto de Valor (Value Object).
- Imutável e definida por nome e descrição.
- Igualdade baseada exclusivamente no nome, por meio de `equals` e `hashCode`.

#### Treinamento
- Entidade representando um curso ou atividade.
- Contém ID, nome, descrição e lista de competências desenvolvidas.
- Permite conexão direta com lacunas identificadas no colaborador.

#### Avaliacao
- Evento de domínio que representa uma avaliação formal do colaborador.
- Ao ser aplicada, aumenta proporcionalmente os níveis das competências com base na nota.

#### EventoDominio
- Classe abstrata base para eventos, contendo apenas a data.
- Projetada para futuras extensões, como novas modalidades de avaliação ou eventos de carreira.

---

### Serviços de Domínio (`services`)

#### RecomendacaoService
- Serviço responsável por identificar lacunas de competências (nível inferior ao recomendado).
- Filtra treinamentos que desenvolvem essas competências.
- Opera exclusivamente no domínio, utilizando repositórios injetados e sem dependências de interface gráfica ou infraestrutura.

---

### Serviços de Aplicação (`application.services`)

#### GestaoService
- Camada intermediária entre UI e domínio.
- Centraliza operações como:
  - cadastro de colaboradores;
  - cadastro de treinamentos;
  - conclusão de treinamentos;
  - obtenção de recomendações;
  - busca de colaboradores e treinamentos.
- Mantém a interface desacoplada das regras de negócio.

---

### Repositórios

Interfaces do domínio:

- `ColaboradorRepository`
- `TreinamentoRepository`

Implementações concretas:

- `InMemoryColaboradorRepository`
- `InMemoryTreinamentoRepository`

Ambas utilizam armazenamento em memória com persistência adicional em arquivos texto (`data.txt` e `treinamentos.txt`).  
O carregamento e a gravação são automáticos, funcionando como um banco de dados local simplificado sem expor detalhes técnicos ao domínio.

---

## 3. Justificativa para o design da interface Swing

A interface foi desenvolvida em Swing por ser uma tecnologia consolidada, multiplataforma e adequada ao escopo acadêmico do projeto.

Toda a interface gráfica está no pacote `ui`, dividida em painéis independentes organizados por abas:

### MainWindow
- Janela principal da aplicação.
- Carrega os repositórios, inicializa o serviço de gestão e organiza o sistema por meio de `JTabbedPane`.

### ColaboradorPainel
- Permite cadastrar colaboradores e suas competências.
- Realiza validação da entrada e apresenta mensagens de feedback ao usuário.

### TreinamentoPainel
- Responsável pelo cadastro e exibição de treinamentos.
- Utiliza `JTable` para melhorar a navegação e visualização.

### RecomendacaoPainel
- Exibe colaboradores cadastrados.
- Gera recomendações personalizadas.
- Permite concluir treinamentos e atualizar automaticamente os níveis de competência.

### RelatorioPainel
- Gera um relatório textual consolidado com colaboradores e suas competências.

### Decisões de design
- Uso de `GridBagLayout` e `BorderLayout` para disposição organizada dos elementos.
- Componentes nativos do Swing garantindo simplicidade e compatibilidade.
- Painéis independentes facilitam manutenção, testes e expansão futura.
  
---

## 4. Boas práticas de Orientação a Objetos aplicadas

### Encapsulamento
- Entidades mantêm atributos privados e expõem apenas métodos essenciais.
- Regras de negócio permanecem no domínio, não na interface.

### Composição
- O `Colaborador` agrega `CompetenciaNivel`, reforçando o conceito de agregado.
- Treinamentos agregam competências desenvolvidas.

### Herança e Polimorfismo
- `Avaliacao` herda de `EventoDominio`, facilitando a criação de novos eventos.

### Abstração e Interfaces
- Repositórios e serviços seguem interfaces, permitindo testes, troca de implementações e redução de acoplamento.

### Baixo acoplamento e alta coesão
- A interface utiliza somente o `GestaoService`.
- Domínio não depende de infraestrutura.
- Cada classe tem responsabilidade única.

### Persistência isolada
- A leitura e gravação de arquivos de texto estão encapsuladas nos repositórios da camada de infraestrutura.

## Integrantes:
- **Rafaela Heer Robinson** - 560249
- **Lucas Alves Piereti** - 559533
- **Julia Hadja Kfouri Nunes** - 559410
