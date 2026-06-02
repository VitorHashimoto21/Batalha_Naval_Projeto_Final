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

- `BatalhaNavalProjetoFinal/src/main/java/br/com/batalhanaval/`
  - Código-fonte Java do jogo.
  - `Config.java`: leitura de propriedades do jogo, incluindo `game.seed` e `game.seeds`.
  - `Jogo.java`: lógica principal da partida, gerenciamento de turnos e uso do gerador aleatório.
  - `TerminalApp.java`: interface de texto, menus, histórico e replay.
  - `PartidaRepository.java`: persistência de partidas e jogadas no banco de dados.
  - `ResultadoTiro.java`: enumeração dos resultados possíveis de um ataque.
- `BatalhaNavalProjetoFinal/src/main/resources/game.properties`
  - Arquivo de configuração do jogo.
  - Permite definir seed fixa, seeds pré-setadas e outros parâmetros do jogo.
- `BatalhaNavalProjetoFinal/src/test/java/br/com/batalhanaval/`
  - Testes automatizados em JUnit 5.

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
