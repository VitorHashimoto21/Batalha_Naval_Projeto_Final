
# Projeto Batalha Naval - Refatoração POO

Este projeto implementa uma versão do jogo Batalha Naval em Java com interface de terminal, persistência em SQLite e suporte a reprodução de partidas através de seed.

## Tecnologias
- Java 17
- Maven
- SQLite JDBC
- JUnit 5

## Seeds pré-setadas e reprodução
- `game.seed`: define uma seed fixa para reproduzir sempre o mesmo jogo.
- `game.seeds`: lista de seeds pré-definidas, separadas por vírgula. Quando `game.seed` estiver vazio, o jogo escolhe aleatoriamente uma seed dessa lista.
- Se as propriedades `game.seed` e `game.seeds` estiverem vazias ou ausentes, o sistema usa uma seed aleatória a cada execução.

Isso mantém a geração de números aleatórios do jogo inalterada, enquanto oferece seeds pré-configuradas para testes e reprodução.

## Executar

```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.batalhanaval.Main"
```

## Modificar seeds
No arquivo `src/main/resources/game.properties`:

```properties
game.seed=
game.seeds=42,2024,2025,999999999
```

- Para um jogo sempre igual, preencha `game.seed` com um número.
- Para usar seeds pré-setadas, deixe `game.seed` vazio e adicione valores em `game.seeds`.

## Testes

```bash
mvn test
```
