package program;

// Importações necessárias para interface gráfica e uso das classes do pacote entidades
import entities.PainelTrajetoria;
import entities.CalculadoraFisica;
import entities.ValidadorEntrada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// Classe principal que inicia a aplicação.

public class Main {
     static void main(String[] args) {

      // Garante que a interface gráfica seja criada na thread correta do Swing
      // se ela nao for criada da ruim...

      SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
 }
}

// Janela principal da aplicação, contém todos os componentes visuais.

class JanelaPrincipal extends JFrame implements ActionListener {

 // Campos de texto para entrada de dados
 private JTextField txtMassa, txtAltura, txtAngulo, txtAlcance;
 private JTextField txtCompRampa, txtVelInicial, txtGravidade;

 // Rótulos para exibir os resultados calculados
 private JLabel lblVelocidadeLancamento, lblAceleracao, lblForcaMedia;

 // Painel que desenha a trajetória e a animação
 private final PainelTrajetoria painelGrafico;

 // Botões de ação
 private JButton btnCalcular, btnLimpar, btnExemplo;

 // Formatador para exibir números com duas casas decimais
 private static final NumberFormat FORMATTER = DecimalFormat.getInstance(new Locale("pt", "BR"));
 static {
  FORMATTER.setMinimumFractionDigits(2);
  FORMATTER.setMaximumFractionDigits(2);
 }

 // Construtor: configura a janela e organiza os componentes.

 public JanelaPrincipal() {
  setTitle("Simulador de Arremesso de Peso com Trajetória");
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(1000, 750);
  setLocationRelativeTo(null); // centraliza na tela
  setLayout(new BorderLayout(10, 10));

  // Painel superior: entradas  na (esquerda) e resultados na (direita)
  JPanel painelTopo = new JPanel(new BorderLayout());
  painelTopo.add(criarPainelEntrada(), BorderLayout.WEST);
  painelTopo.add(criarPainelResultados(), BorderLayout.EAST);

  // gráfico central da trajetória
  painelGrafico = new PainelTrajetoria();

  // Painel inferior: botões principais + controles de animação
  JPanel painelSul = new JPanel(new BorderLayout());
  painelSul.add(criarPainelBotoes(), BorderLayout.NORTH);
  painelSul.add(criarPainelControleAnimacao(), BorderLayout.SOUTH);

  add(painelTopo, BorderLayout.NORTH);
  add(painelGrafico, BorderLayout.CENTER);
  add(painelSul, BorderLayout.SOUTH);

// validação pro icon nao ser invalido e nao quebrar o código.
  try{

   java.net.URL urlIcon = getClass().getResource("/icon.jpg");

   if(urlIcon != null){
    ImageIcon icon = new ImageIcon(urlIcon);
    setIconImage((icon.getImage()));
   }else{
    System.err.println("Arquivo do icon nao encotrado");
   }
  }catch (Exception e){
   System.err.println("Erro ao carregar Icone" + e.getMessage());
  }
 }

 //Cria o painel com os campos de entrada de dados.

 private JPanel criarPainelEntrada() {
  JPanel painel = new JPanel(new GridBagLayout());
  painel.setBorder(BorderFactory.createTitledBorder("Dados de Entrada"));

  GridBagConstraints gbc = new GridBagConstraints();
  gbc.insets = new Insets(5, 5, 5, 5);
  gbc.fill = GridBagConstraints.HORIZONTAL;

  txtMassa = new JTextField(10);
  txtAltura = new JTextField(10);
  txtAngulo = new JTextField(10);
  txtAlcance = new JTextField(10);
  txtCompRampa = new JTextField(10);
  txtVelInicial = new JTextField(10);
  txtGravidade = new JTextField("9.80", 10);

  // Adiciona os componentes linha a linha
  gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("Massa do peso (kg):"), gbc);
  gbc.gridx = 1; painel.add(txtMassa, gbc);
  gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("Altura inicial (m):"), gbc);
  gbc.gridx = 1; painel.add(txtAltura, gbc);
  gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("Ângulo de lançamento (°):"), gbc);
  gbc.gridx = 1; painel.add(txtAngulo, gbc);
  gbc.gridx = 0; gbc.gridy = 3; painel.add(new JLabel("Alcance horizontal (m):"), gbc);
  gbc.gridx = 1; painel.add(txtAlcance, gbc);
  gbc.gridx = 0; gbc.gridy = 4; painel.add(new JLabel("Comprimento da rampa (m):"), gbc);
  gbc.gridx = 1; painel.add(txtCompRampa, gbc);
  gbc.gridx = 0; gbc.gridy = 5; painel.add(new JLabel("Velocidade inicial (m/s):"), gbc);
  gbc.gridx = 1; painel.add(txtVelInicial, gbc);
  gbc.gridx = 0; gbc.gridy = 6; painel.add(new JLabel("Gravidade (m/s²):"), gbc);
  gbc.gridx = 1; painel.add(txtGravidade, gbc);

  return painel;
 }


  // Cria o painel que exibe os resultados numéricos.

 private JPanel criarPainelResultados() {
  JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
  painel.setBorder(BorderFactory.createTitledBorder("Resultados Numéricos"));

  painel.add(new JLabel("Velocidade de lançamento:"));
  lblVelocidadeLancamento = new JLabel("--- m/s");
  painel.add(lblVelocidadeLancamento);

  painel.add(new JLabel("Aceleração na rampa:"));
  lblAceleracao = new JLabel("--- m/s²");
  painel.add(lblAceleracao);

  painel.add(new JLabel("Força média do atleta:"));
  lblForcaMedia = new JLabel("--- N");
  painel.add(lblForcaMedia);

  return painel;
 }


  // Cria o painel com os botões principais (Calcular, Limpar, Exemplo).

 private JPanel criarPainelBotoes() {
  JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
  btnCalcular = new JButton("Calcular");
  btnLimpar = new JButton("Limpar");
  btnExemplo = new JButton("Exemplo Padrão");

  btnCalcular.addActionListener(this);
  btnLimpar.addActionListener(this);
  btnExemplo.addActionListener(this);

  painel.add(btnCalcular);
  painel.add(btnLimpar);
  painel.add(btnExemplo);
  return painel;
 }

 // Cria o painel de controle da animação (play, pause, reiniciar, velocidade).
 private JPanel criarPainelControleAnimacao() {
  JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
  painel.setBorder(BorderFactory.createTitledBorder("Controle da Animação"));

  JButton btnPlay = new JButton("▶");
  JButton btnPause = new JButton("⏸");
  JButton btnReiniciar = new JButton("↺");

  JSlider sliderVelocidade = new JSlider(1, 10, 5); // valores de 1 a 10
  sliderVelocidade.setToolTipText("Velocidade da animação");
  sliderVelocidade.setMajorTickSpacing(2);
  sliderVelocidade.setPaintTicks(true);
  sliderVelocidade.setPaintLabels(true);

  JLabel lblVelocidade = new JLabel("Velocidade: 1.0x");

  // Ações dos botões
  btnPlay.addActionListener(e -> painelGrafico.iniciarAnimacao());
  btnPause.addActionListener(e -> painelGrafico.pausarAnimacao());
  btnReiniciar.addActionListener(e -> painelGrafico.reiniciarAnimacao());

  // Slider ajusta o fator de velocidade (0.2x a 2.0x)
  sliderVelocidade.addChangeListener(e -> {
   double valor = sliderVelocidade.getValue() / 5.0;
   painelGrafico.setFatorVelocidade(valor);
   lblVelocidade.setText(String.format("Velocidade: %.1fx", valor));
  });

  painel.add(btnPlay);
  painel.add(btnPause);
  painel.add(btnReiniciar);
  painel.add(new JLabel("|"));
  painel.add(sliderVelocidade);
  painel.add(lblVelocidade);

  return painel;
 }

 // Método chamado quando um botão é pressionado.

 @Override
 public void actionPerformed(ActionEvent e) {
  if (e.getSource() == btnCalcular) {
   realizarCalculo();
  } else if (e.getSource() == btnLimpar) {
   limparCampos();
  } else if (e.getSource() == btnExemplo) {
   carregarExemploPadrao();
  }
 }

 // Lê os valores dos campos, valida, calcula e atualiza os resultados e o gráfico.

 private void realizarCalculo() {
  try {
   // Converte os textos para double (aceita vírgula)
   double massa = parseDouble(txtMassa.getText());
   double altura = parseDouble(txtAltura.getText());
   double angulo = parseDouble(txtAngulo.getText());
   double alcance = parseDouble(txtAlcance.getText());
   double compRampa = parseDouble(txtCompRampa.getText());
   double velInicial = parseDouble(txtVelInicial.getText());
   double gravidade = parseDouble(txtGravidade.getText());

   // Valida os limites que coloquei
   List<String> erros = ValidadorEntrada.validar(massa, altura, angulo, alcance,
           compRampa, velInicial, gravidade);
   if (!erros.isEmpty()) {
    StringBuilder sb = new StringBuilder("Erros encontrados:\n");
    for (String erro : erros) sb.append("• ").append(erro).append("\n");
    JOptionPane.showMessageDialog(this, sb.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
   }

   // Cálculos físicos
   double v0 = CalculadoraFisica.calcularVelocidadeLancamento(gravidade, alcance, angulo, altura);
   double aceleracao = CalculadoraFisica.calcularAceleracaoRampa(v0, velInicial, compRampa);
   double forca = CalculadoraFisica.calcularForcaMedia(massa, aceleracao, gravidade, angulo);

   // Exibe resultados formatados
   lblVelocidadeLancamento.setText(FORMATTER.format(v0) + " m/s");
   lblAceleracao.setText(FORMATTER.format(aceleracao) + " m/s²");
   lblForcaMedia.setText(FORMATTER.format(forca) + " N");

   // Atualiza o gráfico com os novos dados
   painelGrafico.atualizarDados(v0, angulo, altura, alcance, gravidade);

  } catch (NumberFormatException ex) {
   JOptionPane.showMessageDialog(this, "Preencha todos os campos com números válidos.",
           "Erro de Formato", JOptionPane.ERROR_MESSAGE);
  } catch (IllegalArgumentException ex) {
   JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro nos Dados", JOptionPane.ERROR_MESSAGE);
  }
 }

 // Limpa todos os campos e reseta os resultados.

 private void limparCampos() {
  txtMassa.setText("");
  txtAltura.setText("");
  txtAngulo.setText("");
  txtAlcance.setText("");
  txtCompRampa.setText("");
  txtVelInicial.setText("");
  txtGravidade.setText("9.80");
  lblVelocidadeLancamento.setText("--- m/s");
  lblAceleracao.setText("--- m/s²");
  lblForcaMedia.setText("--- N");
  painelGrafico.atualizarDados(0, 0, 0, 0, 0); // limpa o gráfico
 }

 // Preenche os campos com os valores do exercício original.

 private void carregarExemploPadrao() {
  txtMassa.setText("7.260");
  txtAltura.setText("2.110");
  txtAngulo.setText("34.10");
  txtAlcance.setText("15.90");
  txtCompRampa.setText("1.650");
  txtVelInicial.setText("2.500");
  txtGravidade.setText("9.80");
 }

 //Converte uma string para double, substituindo vírgula por ponto

 private double parseDouble(String texto) {
  if (texto == null || texto.trim().isEmpty()) throw new NumberFormatException("Campo vazio");
  return Double.parseDouble(texto.replace(',', '.'));
 }
}
