package org.example.symulator;

public class Pozycja {
    private double x;
    private double y;

    public void aktualizujPozycje(double delta_x, double delta_y) {
        this.x += delta_x;
        this.y += delta_y;
    }

    public String getPozycje() {
        return x + ", " + y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

}
