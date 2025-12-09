package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.symulator.Samochod;
import org.example.symulator.Silnik;
import org.example.symulator.SkrzyniaBiegow;
import org.example.symulator.Sprzeglo;

public class NewCarController {

    // Samochód
    @FXML private TextField modelField;
    @FXML private TextField nrRejestField;
    @FXML private TextField predkoscMaxField;

    // Silnik
    @FXML private TextField silnikNazwaField;
    @FXML private TextField silnikObrotyField;
    @FXML private TextField silnikWagaField;
    @FXML private TextField silnikCenaField;

    // Skrzynia
    @FXML private TextField skrzyniaNazwaField;
    @FXML private TextField skrzyniaBiegiField;
    @FXML private TextField skrzyniaWagaField;
    @FXML private TextField skrzyniaCenaField;

    // Sprzęgło
    @FXML private TextField sprzegloNazwaField;
    @FXML private TextField sprzegloWagaField;
    @FXML private TextField sprzegloCenaField;

    @FXML private Label errorLabel;

    private Samochod utworzonySamochod = null;

    @FXML
    void zapisz() {
        try {
            String model = modelField.getText();
            String nrRejest = nrRejestField.getText();

            if (model.isEmpty() || nrRejest.isEmpty()) {
                errorLabel.setText("Model i Rejestracja są wymagane!");
                return;
            }

            int vMax = Integer.parseInt(predkoscMaxField.getText());

            // Silnik
            String sNazwa = silnikNazwaField.getText();
            int sMaxObroty = Integer.parseInt(silnikObrotyField.getText());
            float sWaga = Float.parseFloat(silnikWagaField.getText());
            float sCena = Float.parseFloat(silnikCenaField.getText());

            // Skrzynia
            String skNazwa = skrzyniaNazwaField.getText();
            int skBiegi = Integer.parseInt(skrzyniaBiegiField.getText());
            float skWaga = Float.parseFloat(skrzyniaWagaField.getText());
            float skCena = Float.parseFloat(skrzyniaCenaField.getText());

            // Sprzęgło
            String spNazwa = sprzegloNazwaField.getText();
            float spWaga = Float.parseFloat(sprzegloWagaField.getText());
            float spCena = Float.parseFloat(sprzegloCenaField.getText());

            Silnik silnik = new Silnik(sNazwa, sWaga, sCena, sMaxObroty, 0);

            Sprzeglo sprzeglo = new Sprzeglo(spNazwa, spWaga, spCena);

            SkrzyniaBiegow skrzynia = new SkrzyniaBiegow(skNazwa, skWaga, skCena, skBiegi, sprzeglo);

            utworzonySamochod = new Samochod(nrRejest, model, vMax, silnik, skrzynia);

            zamknijOkno();

        } catch (NumberFormatException e) {
            errorLabel.setText("Błąd: Sprawdź czy w polach liczbowych (waga, cena, obroty) wpisałeś poprawne liczby.");
        } catch (Exception e) {
            errorLabel.setText("Błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void anuluj() {
        utworzonySamochod = null;
        zamknijOkno();
    }

    private void zamknijOkno() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    public Samochod getUtworzonySamochod() {
        return utworzonySamochod;
    }
}