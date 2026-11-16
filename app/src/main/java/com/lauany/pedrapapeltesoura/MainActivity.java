package com.joaovitor.pedrapapeltesoura;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.joaovitor.pedrapapeltesoura.model.Jogada;
import com.joaovitor.pedrapapeltesoura.model.JogoPedraPapelTesoura;
import com.joaovitor.pedrapapeltesoura.model.Resultado;

/**
 * Tela principal do jogo. Responsável apenas pela interface:
 * recebe os cliques, pede o resultado para a classe de lógica e atualiza a tela.
 */
public class MainActivity extends AppCompatActivity {

    // Chaves usadas para salvar o estado quando a tela é recriada (ex.: girar o celular)
    private static final String CHAVE_PONTOS_JOGADOR = "pontos_jogador";
    private static final String CHAVE_PONTOS_COMPUTADOR = "pontos_computador";
    private static final String CHAVE_EMPATES = "empates";
    private static final String CHAVE_JOGADA_JOGADOR = "jogada_jogador";
    private static final String CHAVE_JOGADA_COMPUTADOR = "jogada_computador";

    private final JogoPedraPapelTesoura jogo = new JogoPedraPapelTesoura();

    // Jogadas da rodada atual (null = nenhuma rodada em andamento)
    private Jogada jogadaJogador;
    private Jogada jogadaComputador;

    // Componentes da tela
    private TextView txtPontosJogador;
    private TextView txtPontosComputador;
    private TextView txtInfoRodadas;
    private TextView txtJogadaJogador;
    private TextView txtJogadaComputador;
    private TextView txtResultado;
    private ImageView imgJogadaComputador;
    private ImageButton btnPedra;
    private ImageButton btnPapel;
    private ImageButton btnTesoura;
    private Button btnNovaJogada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Evita que o conteúdo fique por baixo da barra de status/navegação
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        vincularComponentes();
        configurarCliques();

        if (savedInstanceState != null) {
            restaurarEstado(savedInstanceState);
        } else {
            limparRodada();
        }
        atualizarPlacar();
    }

    private void vincularComponentes() {
        txtPontosJogador = findViewById(R.id.txtPontosJogador);
        txtPontosComputador = findViewById(R.id.txtPontosComputador);
        txtInfoRodadas = findViewById(R.id.txtInfoRodadas);
        txtJogadaJogador = findViewById(R.id.txtJogadaJogador);
        txtJogadaComputador = findViewById(R.id.txtJogadaComputador);
        txtResultado = findViewById(R.id.txtResultado);
        imgJogadaComputador = findViewById(R.id.imgJogadaComputador);
        btnPedra = findViewById(R.id.btnPedra);
        btnPapel = findViewById(R.id.btnPapel);
        btnTesoura = findViewById(R.id.btnTesoura);
        btnNovaJogada = findViewById(R.id.btnNovaJogada);
    }

    private void configurarCliques() {
        btnPedra.setOnClickListener(v -> jogar(Jogada.PEDRA));
        btnPapel.setOnClickListener(v -> jogar(Jogada.PAPEL));
        btnTesoura.setOnClickListener(v -> jogar(Jogada.TESOURA));
        btnNovaJogada.setOnClickListener(v -> novaJogada());
        findViewById(R.id.btnFinalizar).setOnClickListener(v -> finalizarJogo());
    }

    /**
     * Fluxo de uma rodada: o usuário escolhe, o computador sorteia,
     * a lógica decide o vencedor e a tela é atualizada.
     */
    private void jogar(Jogada escolhaJogador) {
        jogadaJogador = escolhaJogador;
        jogadaComputador = jogo.sortearJogadaComputador();

        Resultado resultado = jogo.jogarRodada(jogadaJogador, jogadaComputador);

        mostrarRodada(resultado);
        atualizarPlacar();
    }

    private void mostrarRodada(Resultado resultado) {
        txtJogadaJogador.setText(getString(R.string.voce_escolheu, getString(jogadaJogador.getNomeRes())));
        txtJogadaComputador.setText(getString(R.string.computador_escolheu, getString(jogadaComputador.getNomeRes())));
        imgJogadaComputador.setImageResource(jogadaComputador.getIconeRes());

        if (resultado == Resultado.VITORIA) {
            txtResultado.setText(R.string.voce_venceu);
            txtResultado.setTextColor(ContextCompat.getColor(this, R.color.vitoria));
        } else if (resultado == Resultado.DERROTA) {
            txtResultado.setText(R.string.voce_perdeu);
            txtResultado.setTextColor(ContextCompat.getColor(this, R.color.derrota));
        } else {
            txtResultado.setText(R.string.empate);
            txtResultado.setTextColor(ContextCompat.getColor(this, R.color.empate));
        }

        // Rodada concluída: trava as opções até o usuário pedir uma nova jogada
        habilitarEscolhas(false);
    }

    private void atualizarPlacar() {
        txtPontosJogador.setText(String.valueOf(jogo.getPontosJogador()));
        txtPontosComputador.setText(String.valueOf(jogo.getPontosComputador()));
        txtInfoRodadas.setText(getString(R.string.info_rodadas, jogo.getTotalRodadas(), jogo.getEmpates()));
    }

    /**
     * Limpa a rodada atual (mantém o placar) e libera os botões.
     */
    private void novaJogada() {
        limparRodada();
    }

    private void limparRodada() {
        jogadaJogador = null;
        jogadaComputador = null;
        txtJogadaJogador.setText(R.string.faca_sua_jogada);
        txtJogadaComputador.setText(R.string.computador_aguardando);
        imgJogadaComputador.setImageResource(R.drawable.ic_computador);
        txtResultado.setText(R.string.escolha_para_comecar);
        txtResultado.setTextColor(ContextCompat.getColor(this, R.color.texto_principal));
        habilitarEscolhas(true);
    }

    private void habilitarEscolhas(boolean habilitar) {
        ImageButton[] botoes = {btnPedra, btnPapel, btnTesoura};
        for (ImageButton botao : botoes) {
            botao.setEnabled(habilitar);
            botao.setAlpha(habilitar ? 1f : 0.4f);
        }
        btnNovaJogada.setVisibility(habilitar ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * Mostra o placar final e pergunta se o usuário quer jogar de novo ou sair.
     */
    private void finalizarJogo() {
        String vencedor;
        if (jogo.getPontosJogador() > jogo.getPontosComputador()) {
            vencedor = getString(R.string.fim_voce_venceu);
        } else if (jogo.getPontosJogador() < jogo.getPontosComputador()) {
            vencedor = getString(R.string.fim_computador_venceu);
        } else {
            vencedor = getString(R.string.fim_empate);
        }

        String mensagem = getString(R.string.fim_mensagem,
                jogo.getPontosJogador(), jogo.getPontosComputador(), jogo.getEmpates(), vencedor);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.fim_de_jogo)
                .setMessage(mensagem)
                .setPositiveButton(R.string.jogar_novamente, (dialog, which) -> {
                    jogo.reiniciar();
                    limparRodada();
                    atualizarPlacar();
                })
                .setNegativeButton(R.string.sair, (dialog, which) -> finish())
                .setNeutralButton(R.string.cancelar, null)
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAVE_PONTOS_JOGADOR, jogo.getPontosJogador());
        outState.putInt(CHAVE_PONTOS_COMPUTADOR, jogo.getPontosComputador());
        outState.putInt(CHAVE_EMPATES, jogo.getEmpates());
        if (jogadaJogador != null && jogadaComputador != null) {
            outState.putString(CHAVE_JOGADA_JOGADOR, jogadaJogador.name());
            outState.putString(CHAVE_JOGADA_COMPUTADOR, jogadaComputador.name());
        }
    }

    private void restaurarEstado(Bundle estado) {
        jogo.restaurarPlacar(
                estado.getInt(CHAVE_PONTOS_JOGADOR),
                estado.getInt(CHAVE_PONTOS_COMPUTADOR),
                estado.getInt(CHAVE_EMPATES));

        String jogador = estado.getString(CHAVE_JOGADA_JOGADOR);
        String computador = estado.getString(CHAVE_JOGADA_COMPUTADOR);
        if (jogador != null && computador != null) {
            jogadaJogador = Jogada.valueOf(jogador);
            jogadaComputador = Jogada.valueOf(computador);
            mostrarRodada(JogoPedraPapelTesoura.determinarVencedor(jogadaJogador, jogadaComputador));
        } else {
            limparRodada();
        }
    }
}
