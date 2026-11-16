package com.joaovitor.pedrapapeltesoura.model;

import java.util.Random;

/**
 * Regras e placar do jogo Pedra, Papel e Tesoura.
 * Esta classe não conhece a tela: só cuida da lógica,
 * o que deixa o código organizado e fácil de testar.
 */
public class JogoPedraPapelTesoura {

    private final Random random;

    private int pontosJogador = 0;
    private int pontosComputador = 0;
    private int empates = 0;

    public JogoPedraPapelTesoura() {
        this(new Random());
    }

    // Construtor que recebe o Random (permite testes com sorteio controlado)
    public JogoPedraPapelTesoura(Random random) {
        this.random = random;
    }

    /**
     * Gera aleatoriamente a jogada do computador.
     */
    public Jogada sortearJogadaComputador() {
        return Jogada.aleatoria(random);
    }

    /**
     * Compara as jogadas usando estrutura condicional e diz quem venceu.
     * Pedra vence Tesoura, Tesoura vence Papel e Papel vence Pedra.
     */
    public static Resultado determinarVencedor(Jogada jogador, Jogada computador) {
        if (jogador == computador) {
            return Resultado.EMPATE;
        } else if ((jogador == Jogada.PEDRA && computador == Jogada.TESOURA)
                || (jogador == Jogada.TESOURA && computador == Jogada.PAPEL)
                || (jogador == Jogada.PAPEL && computador == Jogada.PEDRA)) {
            return Resultado.VITORIA;
        } else {
            return Resultado.DERROTA;
        }
    }

    /**
     * Joga uma rodada: descobre o resultado e atualiza o placar automaticamente.
     */
    public Resultado jogarRodada(Jogada jogador, Jogada computador) {
        Resultado resultado = determinarVencedor(jogador, computador);

        switch (resultado) {
            case VITORIA:
                pontosJogador++;
                break;
            case DERROTA:
                pontosComputador++;
                break;
            case EMPATE:
                empates++;
                break;
        }
        return resultado;
    }

    /**
     * Zera o placar para começar um novo jogo.
     */
    public void reiniciar() {
        pontosJogador = 0;
        pontosComputador = 0;
        empates = 0;
    }

    /**
     * Restaura o placar (usado quando a tela é recriada, por exemplo ao girar o celular).
     */
    public void restaurarPlacar(int pontosJogador, int pontosComputador, int empates) {
        this.pontosJogador = pontosJogador;
        this.pontosComputador = pontosComputador;
        this.empates = empates;
    }

    public int getPontosJogador() {
        return pontosJogador;
    }

    public int getPontosComputador() {
        return pontosComputador;
    }

    public int getEmpates() {
        return empates;
    }

    public int getTotalRodadas() {
        return pontosJogador + pontosComputador + empates;
    }
}
