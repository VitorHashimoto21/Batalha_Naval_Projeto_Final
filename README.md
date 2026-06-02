# Batalha_Naval_Projeto_Final

Este repositório agrupa o projeto `BatalhaNavalProjetoFinal`, uma implementação em Java do jogo Batalha Naval.

## O que foi produzido

O projeto entrega:

- Um jogo de Batalha Naval com interface de terminal.
- Configuração de frota automática e posicionamento manual de navios.
- Lógica de ataque, verificação de tiros e término de partida.
- Persistência em banco SQLite para armazenar partidas jogadas.
- Histórico de partidas com replay de jogadas.
- Controle de seeds para reproduzir partidas ou escolher seeds pré-definidas.

## Estrutura do projeto

A raiz do repositório abriga o subprojeto `BatalhaNavalProjetoFinal`, que contém o código-fonte, recursos e testes.

```text
Batalha_Naval_Projeto_Final/
├── BatalhaNavalProjetoFinal/
│   ├── pom.xml
│   ├── README.md
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/com/batalhanaval/
│   │   │   │   ├── Config.java
│   │   │   │   ├── Jogo.java
│   │   │   │   ├── Main.java
│   │   │   │   ├── TerminalApp.java
│   │   │   │   ├── PartidaRepository.java
│   │   │   │   ├── Partida.java
│   │   │   │   ├── Jogador.java
│   │   │   │   ├── JogadorCPU.java
│   │   │   │   ├── JogadorHumano.java
│   │   │   │   ├── Tabuleiro.java
│   │   │   │   ├── Navio.java
│   │   │   │   ├── Coordenada.java
│   │   │   │   ├── ResultadoTiro.java
│   │   │   │   ├── Jogada.java
│   │   │   │   ├── ResultadoValidacao.java
│   │   │   │   ├── ValidadorDeFrota.java
│   │   │   │   ├── InterfaceTerminal.java
│   │   │   │   └── Database.java
│   │   └── resources/
│   │       └── game.properties
│   └── test/
│       └── java/br/com/batalhanaval/
│           ├── CoordenadaTest.java
│           ├── TabuleiroTest.java
│           └── ValidadorDeFrotaTest.java
└── README.md
```

### O que cada parte faz

- `pom.xml`: configura o build Maven, dependências e execução.
- `src/main/java/br/com/batalhanaval/`: contém toda a lógica do jogo, incluindo:
  - `Config.java`: carregamento de propriedades de configuração.
  - `Jogo.java`: inicia e gerencia a partida, usando seed e `Random`.
  - `TerminalApp.java`: menu, jogo, histórico, replay e interface de terminal.
  - `PartidaRepository.java`: grava e lê partidas/jogadas no SQLite.
  - `Partida.java`: objeto de domínio que representa uma partida.
  - `Jogador*` e `Tabuleiro.java`: tratam de jogadores, frota, tabuleiro e regras.
  - `ResultadoTiro.java`: enumera resultados de ataque.
  - `InterfaceTerminal.java`: leitura de entrada e exibição do tabuleiro no terminal.
  - `Database.java`: cria tabela e gerencia conexão com SQLite.
- `src/main/resources/game.properties`: define configurações do jogo, como tamanho do tabuleiro, seeds, modo de jogo e persistência.
- `src/test/java/br/com/batalhanaval/`: testes unitários em JUnit 5 para validar coordenadas, tabuleiro e frota.

## Como rodar

No diretório `BatalhaNavalProjetoFinal`:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.batalhanaval.Main"
```

## Como testar

```bash
mvn test
```

## Configuração de seed

O projeto agora suporta dois modos de seed:

- `game.seed`: seed fixa para reproduzir sempre o mesmo jogo.
- `game.seeds`: lista de seeds predefinidas. Quando `game.seed` estiver vazio, o jogo escolhe aleatoriamente uma seed dessa lista.

Exemplo em `BatalhaNavalProjetoFinal/src/main/resources/game.properties`:

```properties
# seed fixa para reproduzir sempre a mesma partida
game.seed=

# seeds pré-definidas; usadas de forma aleatória quando game.seed estiver vazio
game.seeds=42,2024,2025,999999999
```

Se ambas estiverem vazias, o jogo gera uma seed randômica por execução.

## Observações

- O pacote principal está em `BatalhaNavalProjetoFinal/`.
- O arquivo `README.md` dessa pasta contém instruções específicas de execução e configuração.
- A persistência SQLite é usada apenas se `db.enabled=true` em `game.properties`.
