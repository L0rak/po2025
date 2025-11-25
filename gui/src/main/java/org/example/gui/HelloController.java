package org.example.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.example.symulator.*; // Import Twoich klas

public class HelloController {

    @FXML private Pane mapaPane;
    @FXML private Circle znacznikCelu;
    @FXML private Circle znacznikSamochodu;
    @FXML private Label wspolrzedneCeluLabel;
    @FXML private Button uruchomButton;
    @FXML private Label obrotyLabel;
    @FXML private ProgressBar obrotyBar;
    @FXML private Label predkoscLabel;
    @FXML private Label biegLabel;
    @FXML private ToggleButton sprzegloButton;

    private Samochod mojSamochod;
    private AnimationTimer pętlaSymulacji; // To będzie "silnik gry"

    @FXML
    public void initialize() {
        stworzSamochod();
        uruchomPetleSymulacji(); // Startujemy odświeżanie
    }

    private void stworzSamochod() {
        Silnik silnikV8 = new Silnik("V8", 200, 50000, 8000, 1000);
        Sprzeglo sprzeglo = new Sprzeglo("Sport", 10, 2000);
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("Man6", 50, 5000, 6, sprzeglo);

        mojSamochod = new Samochod("WA 12345", "Audi", 260, silnikV8, skrzynia);
    }

    private void uruchomPetleSymulacji() {
        pętlaSymulacji = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Obliczamy ile czasu minęło od ostatniej klatki (w sekundach)
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // 1. Logika: Samochód oblicza swoją nową fizykę
                mojSamochod.aktualizujStan(deltaTime);

                // 2. Grafika: Przerysowujemy auto na mapie
                aktualizujGrafike();
            }
        };
        pętlaSymulacji.start();
    }

    private void aktualizujGrafike() {
        // Przesuń kropkę auta na mapie
        Pozycja pos = mojSamochod.getAktPozycja();
        // Uwaga: Zakładam, że masz getX() i getY() w Pozycji!
        znacznikSamochodu.setCenterX(pos.getX()); // lub pos.x jeśli publiczne
        znacznikSamochodu.setCenterY(pos.getY());

        // Zaktualizuj liczniki
        if (mojSamochod.czyWlaczony()) {
            int rpm = mojSamochod.getSilnik().getObroty();
            int maxRpm = mojSamochod.getSilnik().getMaxObroty();
            obrotyLabel.setText(rpm + " RPM");
            obrotyBar.setProgress((double) rpm / maxRpm);
        } else {
            obrotyLabel.setText("0 RPM");
            obrotyBar.setProgress(0.0);
        }

        biegLabel.setText(String.valueOf(mojSamochod.getSkrzynia().getAktBieg()));

        // Sformatuj prędkość do 0 miejsc po przecinku
        predkoscLabel.setText("%.0f km/h, " + mojSamochod.getAktPredkosc());
    }

    // --- OBSŁUGA PRZYCISKÓW (Logika sterowania) ---

    @FXML
    void onUruchomClick() {
        if (mojSamochod.czyWlaczony()) {
            mojSamochod.wylacz();
            uruchomButton.setText("URUCHOM SILNIK");
            uruchomButton.setStyle("-fx-background-color: #4caf50;");
        } else {
            mojSamochod.wlacz();
            uruchomButton.setText("ZATRZYMAJ SILNIK");
            uruchomButton.setStyle("-fx-background-color: #f44336;");
        }
    }

    @FXML
    void onZwiekszObroty() {
        if (mojSamochod.czyWlaczony()) mojSamochod.getSilnik().zwiekszObroty();
    }

    @FXML
    void onZmniejszObroty() {
        if (mojSamochod.czyWlaczony()) mojSamochod.getSilnik().zmniejszObroty();
    }

    @FXML
    void onZwiekszenieBiegu() {
        // Wywołujemy metodę skrzyni (ona sama sprawdzi czy sprzęgło jest wciśnięte)
        mojSamochod.getSkrzynia().zwiekszBieg();
    }

    @FXML
    void onRedukcjaBiegu() {
        mojSamochod.getSkrzynia().zmniejszBieg();
    }

    @FXML
    void onSprzegloClick() {
        if (sprzegloButton.isSelected()) {
            mojSamochod.getSkrzynia().getSprzeglo().wcisnij();
            sprzegloButton.setText("SPRZĘGŁO (WCIŚNIĘTE)");
        } else {
            mojSamochod.getSkrzynia().getSprzeglo().zwolnij();
            sprzegloButton.setText("SPRZĘGŁO");
        }
    }

    @FXML
    void ustawCelNaMapie(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Grafika
        znacznikCelu.setCenterX(x);
        znacznikCelu.setCenterY(y);
        znacznikCelu.setVisible(true);
        wspolrzedneCeluLabel.setText(String.format("[%.1f, %.1f]", x, y));

        // Logika
        Pozycja nowyCel = new Pozycja(); // Tu przydałby się konstruktor Pozycja(x,y)
        nowyCel.aktualizujPozycje(x, y); // Hack: jeśli nie masz konstruktora, użyj metody

        mojSamochod.jedzDo(nowyCel);
    }
}