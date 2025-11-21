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
