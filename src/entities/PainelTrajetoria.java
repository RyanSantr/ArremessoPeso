package entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

// Painel que desenha a trajetória do arremesso e anima uma bolinha percorrendo-a.

public class PainelTrajetoria extends JPanel {

    // Dados da trajetória
    private double v0 = 0.0;
    private double anguloGraus = 0.0;
    private double alturaInicial = 0.0;
    private double alcance = 0.0;
    private double gravidade = 9.8;
    private boolean dadosValidos = false;

    // Constantes de desenho
    private static final int MARGEM = 50;
    private static final Color COR_TRAJETORIA = new Color(0, 100, 200);
    private static final Color COR_PONTOS = Color.RED;
    private static final Color COR_GRADE = Color.LIGHT_GRAY;
    private static final Stroke STROKE_TRAJETORIA = new BasicStroke(2.5f);
    private static final Stroke STROKE_EIXOS = new BasicStroke(1.5f);

    // Variáveis da animação
    private Timer timerAnimacao;
    private double tempoAtual = 0.0;          // segundos
    private double tempoTotalVoo = 0.0;       // duração total do movimento
    private boolean animacaoAtiva = false;
    private double fatorVelocidade = 1.0;     // multiplicador de velocidade

    private static final Color COR_BOLINHA = new Color(220, 20, 60);
    private static final int TAMANHO_BOLINHA = 12;

    // Construtor: configura o fundo, borda e o Timer da animação.

    public PainelTrajetoria() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Trajetória do Lançamento"));
        setPreferredSize(new Dimension(500, 350));

        // Timer dispara a cada 30 ms (~33 fps)
        timerAnimacao = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tempoAtual < tempoTotalVoo) {
                    tempoAtual += 0.03 * fatorVelocidade; // incrementa o tempo
                    if (tempoAtual > tempoTotalVoo) tempoAtual = tempoTotalVoo;
                    repaint(); // redesenha a tela
                } else {
                    pararAnimacao();
                }
            }
        });
    }

    // Atualiza os dados da trajetória e reinicia a animação.

    public void atualizarDados(double v0, double anguloGraus, double alturaInicial,
                               double alcance, double gravidade) {
        this.v0 = v0;
        this.anguloGraus = anguloGraus;
        this.alturaInicial = alturaInicial;
        this.alcance = alcance;
        this.gravidade = gravidade;
        this.dadosValidos = (v0 > 0 && alcance > 0);

        if (dadosValidos) {
            // Calcula o tempo total de voo resolvendo a equação quadrática do movimento vertical
            double theta = Math.toRadians(anguloGraus);
            double vy = v0 * Math.sin(theta);
            double a = -gravidade / 2.0;
            double b = vy;
            double c = alturaInicial;
            double delta = b*b - 4*a*c;
            if (delta >= 0) {
                double t1 = (-b + Math.sqrt(delta)) / (2*a);
                double t2 = (-b - Math.sqrt(delta)) / (2*a);
                tempoTotalVoo = Math.max(t1, t2);
            } else {
                tempoTotalVoo = 0.0;
            }
        }
        reiniciarAnimacao();
        repaint();
    }

    // Controles da animação
    public void iniciarAnimacao() {
        if (!animacaoAtiva && dadosValidos) {
            if (tempoAtual >= tempoTotalVoo) tempoAtual = 0.0;
            animacaoAtiva = true;
            timerAnimacao.start();
        }
    }

    public void pausarAnimacao() {
        if (animacaoAtiva) {
            animacaoAtiva = false;
            timerAnimacao.stop();
        }
    }

    public void reiniciarAnimacao() {
        tempoAtual = 0.0;
        animacaoAtiva = false;
        timerAnimacao.stop();
        repaint();
    }

    public void setFatorVelocidade(double fator) {
        this.fatorVelocidade = fator;
    }

    private void pararAnimacao() {
        animacaoAtiva = false;
        timerAnimacao.stop();
    }


    //  Método principal de desenho: desenha grade, eixos, trajetória e a bolinha.

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!dadosValidos) {
            g.setColor(Color.GRAY);
            g.drawString("Clique em 'Calcular' para visualizar a trajetória.",
                    getWidth()/2 - 130, getHeight()/2);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int largura = getWidth();
        int altura = getHeight();
        int areaLargura = largura - 2 * MARGEM;
        int areaAltura = altura - 2 * MARGEM;

        // Determina os limites dos eixos
        double xMax = alcance * 1.05;
        double theta = Math.toRadians(anguloGraus);
        double vy = v0 * Math.sin(theta);
        double hMax = alturaInicial + (vy * vy) / (2 * gravidade);
        double yMax = Math.max(hMax, alturaInicial) * 1.1;

        desenharGrade(g2, largura, altura, areaLargura, areaAltura, xMax, yMax);

        // Desenha a curva da trajetória
        g2.setColor(COR_TRAJETORIA);
        g2.setStroke(STROKE_TRAJETORIA);
        double cosTheta = Math.cos(theta);
        double tanTheta = Math.tan(theta);
        double denominador = 2.0 * v0 * v0 * cosTheta * cosTheta;

        Point2D.Double pontoAnterior = null;
        for (int xPix = MARGEM; xPix <= largura - MARGEM; xPix += 2) {
            double x = ((double) (xPix - MARGEM) / areaLargura) * xMax;
            if (x > alcance * 1.02) break;

            double y = alturaInicial + x * tanTheta - (gravidade * x * x) / denominador;
            if (y < 0) y = 0;

            int yPix = altura - MARGEM - (int) ((y / yMax) * areaAltura);
            yPix = Math.max(MARGEM, Math.min(altura - MARGEM, yPix));

            if (pontoAnterior != null) {
                g2.drawLine((int) pontoAnterior.x, (int) pontoAnterior.y, xPix, yPix);
            }
            pontoAnterior = new Point2D.Double(xPix, yPix);
        }

        // Marca pontos de lançamento e impacto
        g2.setColor(COR_PONTOS);
        int pontoLancX = MARGEM;
        int pontoLancY = altura - MARGEM - (int) ((alturaInicial / yMax) * areaAltura);
        g2.fillOval(pontoLancX - 5, pontoLancY - 5, 10, 10);
        g2.drawString("Lançamento", pontoLancX + 10, pontoLancY - 10);

        int pontoImpactoX = MARGEM + (int) ((alcance / xMax) * areaLargura);
        int pontoImpactoY = altura - MARGEM;
        g2.fillOval(pontoImpactoX - 5, pontoImpactoY - 5, 10, 10);
        g2.drawString("Impacto", pontoImpactoX + 10, pontoImpactoY - 10);

        // Desenha a bolinha da animação na posição atual
        if (tempoTotalVoo > 0) {
            double vx = v0 * Math.cos(theta);
            double xBolinha = vx * tempoAtual;
            double yBolinha = alturaInicial + vy * tempoAtual - 0.5 * gravidade * tempoAtual * tempoAtual;
            if (yBolinha < 0) yBolinha = 0;
            if (xBolinha > alcance) xBolinha = alcance;

            int xPixBolinha = MARGEM + (int)((xBolinha / xMax) * areaLargura);
            int yPixBolinha = altura - MARGEM - (int)((yBolinha / yMax) * areaAltura);

            g2.setColor(COR_BOLINHA);
            g2.fillOval(xPixBolinha - TAMANHO_BOLINHA/2, yPixBolinha - TAMANHO_BOLINHA/2,
                    TAMANHO_BOLINHA, TAMANHO_BOLINHA);
            g2.setColor(Color.BLACK);
            g2.drawOval(xPixBolinha - TAMANHO_BOLINHA/2, yPixBolinha - TAMANHO_BOLINHA/2,
                    TAMANHO_BOLINHA, TAMANHO_BOLINHA);
        }
    }


    //  Desenha a grade de fundo e os eixos.

    private void desenharGrade(Graphics2D g2, int largura, int altura,
                               int areaLargura, int areaAltura, double xMax, double yMax) {
        g2.setColor(COR_GRADE);
        g2.setStroke(new BasicStroke(0.5f));
        // Linhas horizontais a cada 1 metro
        for (double y = 0; y <= yMax; y += 1.0) {
            int yPix = altura - MARGEM - (int) ((y / yMax) * areaAltura);
            g2.drawLine(MARGEM, yPix, largura - MARGEM, yPix);
        }
        // Linhas verticais a cada 2 metros
        for (double x = 0; x <= xMax; x += 2.0) {
            int xPix = MARGEM + (int) ((x / xMax) * areaLargura);
            g2.drawLine(xPix, MARGEM, xPix, altura - MARGEM);
        }

        // Eixos principais
        g2.setColor(Color.BLACK);
        g2.setStroke(STROKE_EIXOS);
        g2.drawLine(MARGEM, altura - MARGEM, largura - MARGEM, altura - MARGEM);
        g2.drawLine(MARGEM, MARGEM, MARGEM, altura - MARGEM);

        // Rótulos
        g2.drawString("Distância (m)", largura/2 - 30, altura - 10);
        g2.drawString("Altura (m)", 10, altura/2);
        g2.drawString("0", MARGEM - 15, altura - MARGEM + 15);
        g2.drawString(String.format("%.1f", xMax), largura - MARGEM - 25, altura - MARGEM + 15);
        g2.drawString(String.format("%.1f", yMax), MARGEM + 5, MARGEM - 5);
    }
}
