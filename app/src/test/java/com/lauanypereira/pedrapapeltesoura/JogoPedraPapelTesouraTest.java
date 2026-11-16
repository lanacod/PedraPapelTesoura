package com.lauanypereira.pedrapapeltesoura;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.lauanypereira.pedrapapeltesoura.model.Jogada;
import com.lauanypereira.pedrapapeltesoura.model.JogoPedraPapelTesoura;
import com.lauanypereira.pedrapapeltesoura.model.Resultado;

import org.junit.Test;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

/**
 * Testes unitários da lógica do jogo (todas as 9 combinações da tabela do enunciado).
 */
public class JogoPedraPapelTesouraTest {

    @Test
    public void todasAsCombinacoesSeguemATabelaDoEnunciado() {
        assertEquals(Resultado.EMPATE, JogoPedraPapelTesoura.determinarVencedor(Jogada.PEDRA, Jogada.PEDRA));
        assertEquals(Resultado.DERROTA, JogoPedraPapelTesoura.determinarVencedor(Jogada.PEDRA, Jogada.PAPEL));
        assertEquals(Resultado.VITORIA, JogoPedraPapelTesoura.determinarVencedor(Jogada.PEDRA, Jogada.TESOURA));

        assertEquals(Resultado.VITORIA, JogoPedraPapelTesoura.determinarVencedor(Jogada.PAPEL, Jogada.PEDRA));
        assertEquals(Resultado.EMPATE, JogoPedraPapelTesoura.determinarVencedor(Jogada.PAPEL, Jogada.PAPEL));
        assertEquals(Resultado.DERROTA, JogoPedraPapelTesoura.determinarVencedor(Jogada.PAPEL, Jogada.TESOURA));

        assertEquals(Resultado.DERROTA, JogoPedraPapelTesoura.determinarVencedor(Jogada.TESOURA, Jogada.PEDRA));
        assertEquals(Resultado.VITORIA, JogoPedraPapelTesoura.determinarVencedor(Jogada.TESOURA, Jogada.PAPEL));
        assertEquals(Resultado.EMPATE, JogoPedraPapelTesoura.determinarVencedor(Jogada.TESOURA, Jogada.TESOURA));
    }

    @Test
    public void placarEAtualizadoACadaRodada() {
        JogoPedraPapelTesoura jogo = new JogoPedraPapelTesoura();

        jogo.jogarRodada(Jogada.PEDRA, Jogada.TESOURA);   // vitória
        jogo.jogarRodada(Jogada.PEDRA, Jogada.PAPEL);     // derrota
        jogo.jogarRodada(Jogada.PAPEL, Jogada.PAPEL);     // empate
        jogo.jogarRodada(Jogada.TESOURA, Jogada.PAPEL);   // vitória

        assertEquals(2, jogo.getPontosJogador());
        assertEquals(1, jogo.getPontosComputador());
        assertEquals(1, jogo.getEmpates());
        assertEquals(4, jogo.getTotalRodadas());
    }

    @Test
    public void reiniciarZeraOPlacar() {
        JogoPedraPapelTesoura jogo = new JogoPedraPapelTesoura();
        jogo.jogarRodada(Jogada.PEDRA, Jogada.TESOURA);
        jogo.reiniciar();

        assertEquals(0, jogo.getPontosJogador());
        assertEquals(0, jogo.getPontosComputador());
        assertEquals(0, jogo.getEmpates());
    }

    @Test
    public void computadorSorteiaTodasAsOpcoes() {
        JogoPedraPapelTesoura jogo = new JogoPedraPapelTesoura(new Random(42));
        Set<Jogada> sorteadas = EnumSet.noneOf(Jogada.class);
        for (int i = 0; i < 100; i++) {
            Jogada jogada = jogo.sortearJogadaComputador();
            assertNotNull(jogada);
            sorteadas.add(jogada);
        }
        assertEquals(3, sorteadas.size());
    }
}
