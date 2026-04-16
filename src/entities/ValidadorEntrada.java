package entities;

import java.util.ArrayList;
import java.util.List;

// Classe que valida os valores de entrada dentro de limites realistas.

public class ValidadorEntrada {


      // Verifica cada campo e retorna uma lista de mensagens de erro.

      //Se a lista estiver vazia, os dados são válidos.

    public static List<String> validar(double massa, double altura, double angulo,
                                       double alcance, double compRampa,
                                       double velInicial, double gravidade) {
        List<String> erros = new ArrayList<>();

        if (massa <= 0 || massa > 50.0) erros.add("Massa deve ser > 0 e ≤ 50 kg.");
        if (altura < 0 || altura > 3.0) erros.add("Altura deve estar entre 0 e 3 m.");
        if (angulo <= 0 || angulo >= 90) erros.add("Ângulo deve estar entre 0° e 90°.");
        if (alcance <= 0 || alcance > 25.0) erros.add("Alcance deve ser > 0 e ≤ 25 m.");
        if (compRampa <= 0 || compRampa > 3.0) erros.add("Comprimento da rampa deve ser > 0 e ≤ 3 m.");
        if (velInicial < 0 || velInicial > 5.0) erros.add("Velocidade inicial deve estar entre 0 e 5 m/s.");
        if (gravidade < 9.7 || gravidade > 9.9) erros.add("Gravidade deve estar entre 9.7 e 9.9 m/s².");

        return erros;
    }
}
