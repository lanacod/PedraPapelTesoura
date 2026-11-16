# Pedra, Papel e Tesoura (Android / Java)

**ATPS – Desenvolvimento Mobile (M2 2026)**
**Aluna:** Lauany Pereira
**Professora:** Joseneuza Julita Pimenta de Aguiar

Aplicativo Android desenvolvido em **Java** no **Android Studio**, onde o usuário joga Pedra, Papel e Tesoura contra o computador.

## Funcionalidades

- Três botões grandes com imagem (**ImageButton**) para escolher Pedra, Papel ou Tesoura
- Jogada do computador gerada **aleatoriamente** (`java.util.Random`)
- Comparação das jogadas com **estrutura condicional** (`if / else if / else`)
- Mensagem do resultado da rodada: **"Você venceu!"**, **"Você perdeu!"** ou **"Empate!"** (com cor verde, vermelha e laranja)
- **Placar** do Jogador e do Computador atualizado automaticamente, com total de rodadas e empates
- Imagem da jogada do computador (**ImageView**)
- Botão **Nova jogada** (libera uma nova rodada mantendo o placar)
- Botão **Finalizar o jogo** (mostra o placar final e o vencedor, com opção de jogar novamente ou sair)
- Layout **responsivo**: rolagem, largura máxima centralizada e tamanhos maiores em tablets (`values-sw600dp`)
- O placar **não se perde ao girar o celular** (`onSaveInstanceState`)

## Regras (tabela do enunciado)

| Jogador | Computador | Resultado |
|---|---|---|
| Pedra | Pedra | Empate |
| Pedra | Papel | Computador vence |
| Pedra | Tesoura | Jogador vence |
| Papel | Pedra | Jogador vence |
| Papel | Papel | Empate |
| Papel | Tesoura | Computador vence |
| Tesoura | Pedra | Computador vence |
| Tesoura | Papel | Jogador vence |
| Tesoura | Tesoura | Empate |

## Organização do código

```
app/src/main/
├── AndroidManifest.xml
├── java/com/lauanypereira/pedrapapeltesoura/
│   ├── MainActivity.java              → tela: cliques, textos, imagens e placar
│   └── model/
│       ├── Jogada.java                → enum PEDRA, PAPEL, TESOURA (+ sorteio aleatório)
│       ├── Resultado.java             → enum VITORIA, DERROTA, EMPATE
│       └── JogoPedraPapelTesoura.java → regras do jogo e placar
└── res/
    ├── layout/activity_main.xml       → interface (TextView, ImageButton, ImageView, Button)
    ├── drawable/                      → ícones vetoriais e fundos
    ├── values/                        → textos, cores, tema e dimensões
    └── values-sw600dp/                → dimensões para tablets

app/src/test/.../JogoPedraPapelTesouraTest.java → testes unitários das 9 combinações
```

A lógica fica separada da tela: a `MainActivity` só cuida da interface e a classe `JogoPedraPapelTesoura` cuida das regras e do placar.

## Como executar

1. Abra a pasta do projeto no **Android Studio** (File > Open).
2. Aguarde o Gradle sincronizar.
3. Escolha um emulador ou celular e clique em **Run ▶**.

Requisitos: Android 8.0 (API 26) ou superior.
