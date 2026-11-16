package com.lauanypereira.pedrapapeltesoura.model;

import com.lauanypereira.pedrapapeltesoura.R;

import java.util.Random;

/**
 * Representa as três jogadas possíveis do jogo.
 * Cada jogada guarda o texto (string) e a imagem (drawable) que a representam na tela.
 */
public enum Jogada {

    PEDRA(R.string.pedra, R.drawable.ic_pedra),
    PAPEL(R.string.papel, R.drawable.ic_papel),
    TESOURA(R.string.tesoura, R.drawable.ic_tesoura);

    private final int nomeRes;
    private final int iconeRes;

    Jogada(int nomeRes, int iconeRes) {
        this.nomeRes = nomeRes;
        this.iconeRes = iconeRes;
    }

    public int getNomeRes() {
        return nomeRes;
    }

    public int getIconeRes() {
        return iconeRes;
    }

    /**
     * Sorteia uma jogada aleatória (usada pelo computador).
     */
    public static Jogada aleatoria(Random random) {
        Jogada[] opcoes = values();
        return opcoes[random.nextInt(opcoes.length)];
    }
}
