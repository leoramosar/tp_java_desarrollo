package logistica;

public class Movimiento {
    private String quePaso;
    private String donde;

    public Movimiento(String quePaso, String donde) {
        this.quePaso = quePaso;
        this.donde = donde;
    }

    public String getQuePaso() {
        return quePaso;
    }

    public String getDonde() {
        return donde;
    }

    public String toString() {
        return quePaso + " | lugar: " + donde;
    }
}
