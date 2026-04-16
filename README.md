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

## Exercicio:

Capitulo 5

 Um arremessador de peso lança um peso de 7260 kg 
empurrando- o ao longo de uma linha reta com 1650 m de compri
mento e um ângulo de 34,10° com a horizontal, acelerando o peso 
até a velocidade de lançamento de 2,500 m/s (que se deve ao movi
mento preparatório do atleta).

O peso deixa a mão do aremessador a uma alura de 2,110 me com um ângulo de 34,100 e percorre uma 
distância horizontal de 15,90 m. 

Qual é o módulo da força média que o atleta exerce sobre o peso durante a fase de aceleração? 

(Sugestão: rate o movimento durante a fase de aceleração como se fosse ao longo de uma rampa com o ângulo dado.)
(se eu nao fiz errado é assim)

Parte 1

<img width="2160" height="2626" alt="image" src="https://github.com/user-attachments/assets/2a4d28de-c98b-48fd-9bea-f735a0887e34" />

Parte 2

<img width="2250" height="2160" alt="image" src="https://github.com/user-attachments/assets/9002a77e-69f5-435c-9748-57b5e3d487d7" />

Resultado =~ 335

## Estrutura do Projeto

<img width="327" height="524" alt="image" src="https://github.com/user-attachments/assets/5e93d532-9cfa-495f-94ce-a5bba06e350b" />

## Interface

Esboço da interface 

<img width="2620" height="2160" alt="image" src="https://github.com/user-attachments/assets/3b0b2b31-61d7-4787-9bf2-4ab360c66b7c" />


## GUI simples

INPUTS

MASSA DO PESO
ALTURA INICIAL
ANGULO DE LANÇAMENTO
ALCANCE HORIZONTAL
COMPRIMENTO DA RAMPA
VELOCIDADE INICIAL
GRAVIDADE

<img width="1012" height="777" alt="image" src="https://github.com/user-attachments/assets/087674ed-01c9-488e-adf7-1ca111a44b8d" />

## GRAFICO DE SIMULAÇÃO

Grafico com interface simples e discreta, mostrando a trajetora  via .time

<img width="966" height="503" alt="image" src="https://github.com/user-attachments/assets/ccfc49f2-a002-4e20-8917-f85baf8c553e" />


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
