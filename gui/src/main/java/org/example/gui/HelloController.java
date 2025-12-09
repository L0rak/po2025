package org.example.gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.example.symulator.*;

public class HelloController {

    @FXML private TabPane glownyTabPane; // Jeśli nadasz fx:id="glownyTabPane" dla całego <TabPane> w FXML
    @FXML private Tab kokpitTab;
    @FXML private Tab garazTab;

    @FXML private TextField modelField;
    @FXML private TextField nrRejestField;
    @FXML private TextField silnikNazwaField;
    @FXML private TextField maxObrotyField;
    @FXML private TextField iloscBiegowField;
    @FXML private Label bladLabel;

    @FXML private Pane mapaPane;
    @FXML private Circle znacznikCelu;
    @FXML private ImageView znacznikSamochodu;
    @FXML private Label wspolrzedneCeluLabel;

    @FXML private Button uruchomButton;
    @FXML private Label obrotyLabel;
    @FXML private ProgressBar obrotyBar;

    @FXML private Label predkoscLabel;
    @FXML private Label biegLabel;
    @FXML private ToggleButton sprzegloButton;

    private Samochod mojSamochod;
    private AnimationTimer pętlaSymulacji;

    @FXML
    public void initialize() {
        System.out.println("Garaż otwarty. Czekam na konfigurację auta.");
    }

    @FXML
    void onUruchomClick() {
        if (mojSamochod == null) return;

        if (mojSamochod.czyWlaczony()) {
            mojSamochod.wylacz();
            uruchomButton.setText("URUCHOM SILNIK");
            uruchomButton.setStyle("-fx-background-color: #4caf50;");
        } else {
            mojSamochod.wlacz();
            uruchomButton.setText("ZATRZYMAJ SILNIK");
            uruchomButton.setStyle("-fx-background-color: #f44336;");
        }
        aktualizujGrafike();
    }

    @FXML
    void onZwiekszObroty() {
        if (mojSamochod != null && mojSamochod.czyWlaczony()) {
            mojSamochod.getSilnik().zwiekszObroty();
        }
    }

    @FXML
    void onZmniejszObroty() {
        if (mojSamochod != null && mojSamochod.czyWlaczony()) {
            mojSamochod.getSilnik().zmniejszObroty();
        }
    }

    @FXML
    void onZwiekszenieBiegu() {
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().zwiekszBieg();
            aktualizujGrafike();
        }
    }

    @FXML
    void onRedukcjaBiegu() {
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().zmniejszBieg();
            aktualizujGrafike();
        }
    }

    @FXML
    void onSprzegloClick() {
        if (mojSamochod != null) {
            if (sprzegloButton.isSelected()) {
                mojSamochod.getSkrzynia().getSprzeglo().wcisnij();
                sprzegloButton.setText("SPRZĘGŁO (WCIŚNIĘTE)");
            } else {
                mojSamochod.getSkrzynia().getSprzeglo().zwolnij();
                sprzegloButton.setText("SPRZĘGŁO");
            }
        }
    }

    @FXML
    void ustawCelNaMapie(MouseEvent event) {
        if (mojSamochod == null) return;

        double x = event.getX();
        double y = event.getY();

        znacznikCelu.setCenterX(x);
        znacznikCelu.setCenterY(y);
        znacznikCelu.setVisible(true);
        wspolrzedneCeluLabel.setText(String.format("Cel: [%.0f, %.0f]", x, y));

        Pozycja nowyCel = new Pozycja();
        nowyCel.aktualizujPozycje(x, y);

        mojSamochod.jedzDo(nowyCel);
    }

    private void uruchomPetleSymulacji() {
        pętlaSymulacji = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) { lastTime = now; return; }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (mojSamochod != null) {
                    mojSamochod.aktualizujStan(deltaTime);
                    aktualizujGrafike();
                }
            }
        };
        pętlaSymulacji.start();
    }

    private void aktualizujGrafike() {
        if (mojSamochod == null) return;

        Pozycja pos = mojSamochod.getAktPozycja();
        double width = znacznikSamochodu.getFitWidth();
        double height = znacznikSamochodu.getFitHeight();

        znacznikSamochodu.setLayoutX(pos.getX() - (width / 2));
        znacznikSamochodu.setLayoutY(pos.getY() - (height / 2));

        if (mojSamochod.czyWlaczony()) {
            obrotyLabel.setText(mojSamochod.getSilnik().getObroty() + " RPM");
            obrotyBar.setProgress((double)mojSamochod.getSilnik().getObroty() / mojSamochod.getSilnik().getMaxObroty());
        }

        biegLabel.setText(String.valueOf(mojSamochod.getSkrzynia().getAktBieg()));
        predkoscLabel.setText(String.format("%.0f km/h", mojSamochod.getAktPredkosc()));
    }

    @FXML
    void onZbudujAutoClick() {
        try {
            String model = modelField.getText();
            String nrRejest = nrRejestField.getText();
            String silnikNazwa = silnikNazwaField.getText();
            int maxObroty = Integer.parseInt(maxObrotyField.getText());
            int iloscBiegow = Integer.parseInt(iloscBiegowField.getText());

            if (model.isEmpty() || nrRejest.isEmpty()) {
                bladLabel.setText("Model i Rejestracja są wymagane!");
                return;
            }

            Silnik silnik = new Silnik(silnikNazwa, 200, 5000, maxObroty, 0);
            Sprzeglo sprzeglo = new Sprzeglo("Sport", 10, 2000);
            SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("Manual", 50, 5000, iloscBiegow, sprzeglo);

            mojSamochod = new Samochod(nrRejest, model, 150, silnik, skrzynia);

            bladLabel.setText("");
            kokpitTab.setDisable(false);

            if (glownyTabPane != null) {
                glownyTabPane.getSelectionModel().select(kokpitTab);
            }

            if (pętlaSymulacji == null) {
                uruchomPetleSymulacji();
            }

            System.out.println("Zbudowano: " + model);

        } catch (NumberFormatException e) {
            bladLabel.setText("Błąd! Wpisz poprawne liczby.");
        } catch (Exception e) {
            bladLabel.setText("Błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }
}