package program;

import entidades.PainelTrajetoria;
import entidades.CalculadoraFisica;
import entidades.ValidadorEntrada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Classe principal que inicia a aplicação Swing.
 */
public class Main {
 public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
 }
}

/**
 * Janela principal da aplicação.
 */
class JanelaPrincipal extends JFrame implements ActionListener {

 private JTextField txtMassa, txtAltura, txtAngulo, txtAlcance;
 private JTextField txtCompRampa, txtVelInicial, txtGravidade;

 private JLabel lblVelocidadeLancamento, lblAceleracao, lblForcaMedia;
 private PainelTrajetoria painelGrafico;

 private JButton btnCalcular, btnLimpar, btnExemplo;

 private static final NumberFormat FORMATTER = DecimalFormat.getInstance(new Locale("pt", "BR"));

 static {
  FORMATTER.setMinimumFractionDigits(2);
  FORMATTER.setMaximumFractionDigits(2);
 }

 public JanelaPrincipal() {
  setTitle("Simulador de Arremesso de Peso com Trajetória");
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(950, 700);
  setLocationRelativeTo(null);
  setLayout(new BorderLayout(10, 10));

  // Painel superior com entradas e resultados
  JPanel painelTopo = new JPanel(new BorderLayout());
  painelTopo.add(criarPainelEntrada(), BorderLayout.WEST);
  painelTopo.add(criarPainelResultados(), BorderLayout.EAST);

  // Painel central com gráfico
  painelGrafico = new PainelTrajetoria();

  // Painel inferior com botões
  JPanel painelBotoes = criarPainelBotoes();

  add(painelTopo, BorderLayout.NORTH);
  add(painelGrafico, BorderLayout.CENTER);
  add(painelBotoes, BorderLayout.SOUTH);
 }

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

 private void realizarCalculo() {
  try {
   double massa = parseDouble(txtMassa.getText());
   double altura = parseDouble(txtAltura.getText());
   double angulo = parseDouble(txtAngulo.getText());
   double alcance = parseDouble(txtAlcance.getText());
   double compRampa = parseDouble(txtCompRampa.getText());
   double velInicial = parseDouble(txtVelInicial.getText());
   double gravidade = parseDouble(txtGravidade.getText());

   List<String> erros = ValidadorEntrada.validar(massa, altura, angulo, alcance,
           compRampa, velInicial, gravidade);
   if (!erros.isEmpty()) {
    StringBuilder sb = new StringBuilder("Erros encontrados:\n");
    for (String erro : erros) sb.append("• ").append(erro).append("\n");
    JOptionPane.showMessageDialog(this, sb.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
   }

   double v0 = CalculadoraFisica.calcularVelocidadeLancamento(gravidade, alcance, angulo, altura);
   double aceleracao = CalculadoraFisica.calcularAceleracaoRampa(v0, velInicial, compRampa);
   double forca = CalculadoraFisica.calcularForcaMedia(massa, aceleracao, gravidade, angulo);

   lblVelocidadeLancamento.setText(FORMATTER.format(v0) + " m/s");
   lblAceleracao.setText(FORMATTER.format(aceleracao) + " m/s²");
   lblForcaMedia.setText(FORMATTER.format(forca) + " N");

   painelGrafico.atualizarDados(v0, angulo, altura, alcance, gravidade);

  } catch (NumberFormatException ex) {
   JOptionPane.showMessageDialog(this, "Preencha todos os campos com números válidos.",
           "Erro de Formato", JOptionPane.ERROR_MESSAGE);
  } catch (IllegalArgumentException ex) {
   JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro nos Dados", JOptionPane.ERROR_MESSAGE);
  }
 }

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
  painelGrafico.atualizarDados(0, 0, 0, 0, 0);
 }

 private void carregarExemploPadrao() {
  txtMassa.setText("7.260");
  txtAltura.setText("2.110");
  txtAngulo.setText("34.10");
  txtAlcance.setText("15.90");
  txtCompRampa.setText("1.650");
  txtVelInicial.setText("2.500");
  txtGravidade.setText("9.80");
 }

 private double parseDouble(String texto) {
  if (texto == null || texto.trim().isEmpty()) throw new NumberFormatException("Campo vazio");
  return Double.parseDouble(texto.replace(',', '.'));
 }
}