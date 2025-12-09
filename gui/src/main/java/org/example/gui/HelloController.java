package org.example.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.symulator.Listener;
import org.example.symulator.Pozycja;
import org.example.symulator.Samochod;

import java.io.IOException;

public class HelloController implements Listener {

    @FXML private ComboBox<Samochod> wyborSamochoduBox;
    @FXML private Label statusLabel;

    private ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();

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

    @FXML
    public void initialize() {
        wyborSamochoduBox.setItems(listaSamochodow);

        wyborSamochoduBox.getSelectionModel().selectedItemProperty().addListener((obs, stareAuto, noweAuto) -> {
            if (stareAuto != null) {
                stareAuto.removeListener(this); // Przestajemy słuchać starego auta
            }
            if (noweAuto != null) {
                przelaczSamochod(noweAuto);
            }
        });

        System.out.println("System gotowy. Wątki aktywne.");
    }

    private void przelaczSamochod(Samochod auto) {
        this.mojSamochod = auto;
        this.mojSamochod.addListener(this);

        if (mojSamochod.czyWlaczony()) {
            uruchomButton.setText("ZATRZYMAJ SILNIK");
            uruchomButton.setStyle("-fx-background-color: #f44336;");
        } else {
            uruchomButton.setText("URUCHOM SILNIK");
            uruchomButton.setStyle("-fx-background-color: #4caf50;");
        }

        if (sprzegloButton.isSelected() != mojSamochod.getSkrzynia().getSprzeglo().czyWcisniete()) {
            sprzegloButton.setSelected(false);
            sprzegloButton.setText("SPRZĘGŁO");
        }

        aktualizujGrafike(); // Pierwsze odświeżenie
        System.out.println("Przełączono na: " + auto.getModel());
    }

    private void aktualizujGrafike() {
        if (mojSamochod == null) return;

        Pozycja pos = mojSamochod.getAktPozycja();
        double w = znacznikSamochodu.getFitWidth();
        double h = znacznikSamochodu.getFitHeight();

        // Ustawienie pozycji ikony [cite: 99-100]
        znacznikSamochodu.setLayoutX(pos.getX() - w/2);
        znacznikSamochodu.setLayoutY(pos.getY() - h/2);

        if (mojSamochod.czyWlaczony()) {
            int rpm = mojSamochod.getSilnik().getObroty();
            int max = mojSamochod.getSilnik().getMaxObroty();
            obrotyLabel.setText(rpm + " RPM");
            obrotyBar.setProgress((double)rpm / max);
        } else {
            obrotyLabel.setText("0 RPM");
            obrotyBar.setProgress(0.0);
        }
        biegLabel.setText(String.valueOf(mojSamochod.getSkrzynia().getAktBieg()));
        predkoscLabel.setText(String.format("%.0f km/h", mojSamochod.getAktPredkosc()));
    }

    public void pokazBlad(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
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
    }

    @FXML
    void onZwiekszObroty() {
        if (mojSamochod != null && mojSamochod.czyWlaczony()) {
            mojSamochod.getSilnik().zwiekszObroty();
            update();
        }
    }

    @FXML
    void onZmniejszObroty() {
        if (mojSamochod != null && mojSamochod.czyWlaczony()) {
            mojSamochod.getSilnik().zmniejszObroty();
            update();
        }
    }

    @FXML
    void onZwiekszenieBiegu() {
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().zwiekszBieg();
            update();
        }
    }

    @FXML
    void onRedukcjaBiegu() {
        if (mojSamochod != null) {
            mojSamochod.getSkrzynia().zmniejszBieg();
            update();
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
            update();
        }
    }

    @FXML
    void onDodajNowyClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-car-view.fxml"));
            Parent root = loader.load();
            NewCarController nowyCtrl = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Dodaj Nowy Samochód");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            Samochod noweAuto = nowyCtrl.getUtworzonySamochod();
            if (noweAuto != null) {
                // Wątek startuje w konstruktorze Samochodu, więc tu już działa [cite: 65]
                listaSamochodow.add(noweAuto);
                wyborSamochoduBox.getSelectionModel().select(noweAuto);
                statusLabel.setText("Dodano: " + noweAuto.getModel());
            }

        } catch (IOException e) {
            e.printStackTrace();
            pokazBlad("Błąd otwierania okna: " + e.getMessage());
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


    @Override
    public void update() {
        Platform.runLater(this::aktualizujGrafike);
    }
}