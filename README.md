# Simulador de Arremesso de Peso

## Descricao

Este projeto implementa uma aplicacao Java com interface grafica (Swing) para simular o lancamento de um peso de atletismo. A partir de parametros como massa, altura inicial, angulo de lancamento, alcance horizontal, comprimento da fase de aceleracao e velocidade inicial, o programa calcula:

- Velocidade de lancamento (v0) utilizando as equacoes do movimento balistico.
- Aceleracao media do peso durante a fase de impulsao (movimento retilineo uniformemente variado).
- Forca media exercida pelo atleta sobre o peso (2a Lei de Newton aplicada a um plano inclinado).

Adicionalmente, a interface exibe um grafico 2D da trajetoria e uma animacao que mostra a posicao do peso ao longo do tempo.

## Funcionalidades

- Entrada validada de dados com limites realistas.
- Calculos fisicos baseados em formulas classicas da Mecanica.
- Exibicao dos resultados numericos formatados.
- Grafico da trajetoria com eixos escalados automaticamente.
- Animacao interativa com controles de play, pause, reiniciar e ajuste de velocidade.
- Botao para carregar um exemplo padrao (dados do exercicio proposto).
- Suporte a carregamento de imagens (icone da janela e fundo do grafico) a partir da pasta `resources`.

## Tecnologias Utilizadas

- Linguagem: Java (versao 8 ou superior)
- Interface Grafica: Swing
- Gerenciamento de Layout: BorderLayout, GridBagLayout, FlowLayout
- Renderizacao Grafica: Graphics2D (Java 2D API)
- Animacao: javax.swing.Timer

## Estrutura do Projeto

ArremessoPeso/
├── src/
│ ├── program/
│ │ └── Main.java # Classe principal e janela da aplicacao
│ └── entidades/
│ ├── CalculadoraFisica.java # Metodos estaticos para calculos fisicos
│ ├── ValidadorEntrada.java # Validacao de limites das entradas
│ └── PainelTrajetoria.java # Painel que desenha trajetoria e animacao
└── resources/ # Pasta para recursos (imagens)


## Como Executar

### Usando uma IDE (IntelliJ IDEA / Eclipse / NetBeans)

1. Importe o projeto como um projeto Java existente.
2. Certifique-se de que a pasta `src` esta marcada como Source Root.
3. (Opcional) Marque a pasta `resources` como Resources Root para carregar as imagens.
4. Execute a classe `program.Main`.

### Compilando manualmente (linha de comando)

1. Navegue ate o diretorio raiz do projeto.
2. Compile os arquivos:
   
avac -d bin src/program/.java src/entidades/.java
3. Execute:
java -cp bin program.Main

(Se houver imagens em `resources`, copie a pasta para dentro de `bin` ou configure o classpath adequadamente.)
( o código nao quebra se nao tiver as imagens baixadas, pode executar sem b.o, unica imagem que tem é o icone dele que muda)
## Exemplo de Uso

1. Ao abrir o programa, clique no botao **Exemplo Padrao** para preencher os campos com os dados do problema original.
2. Clique em **Calcular** para obter os resultados e visualizar a trajetoria.
3. Utilize os controles de animacao (play, pause, reiniciar) para observar o movimento do peso.
4. Altere os valores manualmente para testar outros cenarios.

### Dados do Exemplo Padrao

| Campo                     | Valor   |
|---------------------------|---------|
| Massa do peso             | 7,260 kg|
| Altura inicial            | 2,110 m |
| Angulo de lancamento      | 34,10°  |
| Alcance horizontal        | 15,90 m |
| Comprimento da rampa      | 1,650 m |
| Velocidade inicial        | 2,500 m/s|
| Gravidade                 | 9,80 m/s²|

Resultados esperados:
- Velocidade de lancamento: ~11,85 m/s
- Aceleracao na rampa: ~40,62 m/s²
- Forca media do atleta: ~335 N

## Observacoes

- O programa aceita numeros com virgula ou ponto como separador decimal.
- Caso as imagens nao sejam encontradas, a aplicacao funciona normalmente com fundo branco e sem icone.
- Os limites de validacao foram definidos com base em valores tipicos do arremesso de peso.

## Autor

Ryan dos Santos - Trabalho academico para a disciplina de Física.

## Licenca

Este projeto e de uso livre para fins educacionais.
Quaisquer um que queira usar, sinta-se livre.
