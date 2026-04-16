package entities;

//Classe com os métodos estáticos que realizam os cálculos de física.

public class CalculadoraFisica {

    //Calcula a velocidade de lançamento (v0) usando a fórmula do alcance.

    public static double calcularVelocidadeLancamento(double g, double R, double thetaGraus, double y0) {
        double thetaRad = Math.toRadians(thetaGraus);
        double tanTheta = Math.tan(thetaRad);
        double cosTheta = Math.cos(thetaRad);
        double cos2Theta = cosTheta * cosTheta;

        double denominador = 2.0 * cos2Theta * (y0 + R * tanTheta);
        double numerador = g * R * R;
        double v0Quadrado = numerador / denominador;

        if (v0Quadrado < 0) {
            throw new IllegalArgumentException("Dados inconsistentes: velocidade ao quadrado negativa.");
        }
        return Math.sqrt(v0Quadrado);
    }

    //Calcula a aceleração média na rampa usando Torricelli.

    public static double calcularAceleracaoRampa(double vf, double vi, double d) {
        return (vf * vf - vi * vi) / (2.0 * d);
    }

    // Calcula a força média que o atleta aplica (2ª Lei de Newton no plano inclinado).

    public static double calcularForcaMedia(double m, double a, double g, double thetaGraus) {
        double thetaRad = Math.toRadians(thetaGraus);
        return m * (a + g * Math.sin(thetaRad));
    }
}
