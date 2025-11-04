# po2025
Repozytorium dla przedmiotu Programowanie Obiektowe.



Projekt laboratoryjny Samochod (symulacja) - GUI
===============================================

Diagramy UML
------------

### Diagram klas

```plantuml
@startuml

package Samochod {
    class Samochod {
        - String nazwa
        - String nrRejestracyjny
        - Silnik silnik
        - SkrzyniaBiegow skrzyniaBiegow
        - Pozycja Pozycja
        - boolean running
        + void wlacz()
        + void wylacz()
        + double getWaga()
        + String getNrRejestracyjny()
        + double getPredkosc()
        + SkrzyniaBiegow getSkrzyniaBiegow()
        + Pozycja getPozycja()
        + String getNazwa()
        + void notifyObserwator()
    }

    class Silnik {
        - int moc
        - int obroty
        + void wlacz()
        + void wylacz()
        + int getObroty()
    }

    class SkrzyniaBiegow {
        - int bieg
        + void zwiekszBieg()
        + void zmniejszBieg()
        + int getBieg()
    }

    class Pozycja {
        - double x
        - double y
    }
    
    class Komponent {
        
    }
    
    class Sprzeglo {
        
    }

    interface Obserwator {
        + void aktualizuj()
    }
    
    class SamochodException {
       
    }
}

package samochodgui {
    class SamochodApplication {
        - Stage primaryStage
        - SamochodController samochodController
        + void start(Stage primaryStage)
    }

    class SamochodController {
        - ComboBox<Samochod> samochodComboBox
        - ObservableList<Samochod> samochody
        - Samochod Samochod
        - TextField wagaTextField
        - TextField nrRejestracyjnyTextField
        - TextField predkoscTextField
        - TextField nazwaTextField
        - TextField biegTextField
        - Button wlaczButton
        - ImageView carImageView
        - HBox mapa
        - Label welcomeText
        + void initialize()
        + void onDodajSamochod(ActionEvent event)
        + void onUsunSamochod(ActionEvent event)
        + void dodajSamochod(Samochod nowySamochod)
        + void refresh()
        + void onWlacz(ActionEvent actionEvent)
        + void onWylacz(ActionEvent actionEvent)
        + void onZmniejszBieg(ActionEvent actionEvent)
        + void onUjmijGazu(ActionEvent actionEvent)
        + void onZwiekszBieg(ActionEvent actionEvent)
        + void onNacisnijSprzeglo(ActionEvent actionEvent)
        + void onZwolnij(ActionEvent actionEvent)
        + void aktualizuj()
    }

    class NowySamochodController {
        - TextField nazwaTextField
        - TextField nrRejestracyjnyTextField
        - TextField mocSilnikaTextField
        - TextField obrotySilnikaTextField
        - TextField biegiSkrzyniTextField
        - SamochodController samochodController
        + void setSamochodController(SamochodController samochodController)
        + void onDodaj(ActionEvent event)
        + void onAnuluj(ActionEvent event)
    }
}

Samochod.Samochod --> Samochod.Silnik
Samochod.Samochod --> Samochod.SkrzyniaBiegow
Samochod.Samochod --> Samochod.Pozycja
Samochod.Samochod --|> Thread
Samochod.Samochod *-- Samochod.Obserwator
Samochod.Silnik --|> Samochod.Komponent
Samochod.SkrzyniaBiegow --|> Samochod.Komponent
Samochod.SkrzyniaBiegow --> Samochod.Sprzeglo
Samochod.Sprzeglo --|> Samochod.Komponent


samochodgui.SamochodController -> Samochod.Samochod
samochodgui.SamochodController ..|> Samochod.Obserwator

samochodgui.SamochodController <-- samochodgui.NowySamochodController
samochodgui.SamochodApplication --> samochodgui.SamochodController

Samochod.SamochodException --|> Exception
Samochod.SkrzyniaBiegow -- Samochod.SamochodException

hide methods
hide members
@enduml
```

### Diagram sekwencji

```plantuml
@startuml
actor User
participant SamochodApplication
participant SamochodController
participant Samochod
participant Silnik
participant SkrzyniaBiegow

User -> SamochodApplication: start(primaryStage)
SamochodApplication -> SamochodController: initialize()
SamochodController -> Samochod: new Samochod(...)
Samochod -> Silnik: new Silnik(...)
Samochod -> SkrzyniaBiegow: new SkrzyniaBiegow(...)
SamochodController -> Samochod: subscribeObserwator(this)
SamochodController -> Samochod: setItems(samochody)
SamochodController -> Samochod: selectFirst()
@enduml
```

### Diagram sekwencji aktualizacji

```plantuml
@startuml
participant Samochod
participant Obserwator
participant SamochodController
participant Platform

Samochod -> Samochod: notifyObserwator()
Samochod -> Platform: runLater()
Platform -> Obserwator: aktualizuj()
Obserwator -> SamochodController: aktualizuj()
SamochodController -> SamochodController: refresh()
@enduml
```

### Diagram sekwencji dodania samochodu

```plantuml
@startuml
actor User
participant SamochodController
participant NowySamochodController
participant Samochod

User -> SamochodController: onDodajSamochod(event)
SamochodController -> NowySamochodController: setSamochodController(this)
SamochodController -> NowySamochodController: show()

User -> NowySamochodController: onDodaj(event)
NowySamochodController -> Samochod: new Samochod(...)
NowySamochodController -> SamochodController: dodajSamochod(nowySamochod)
SamochodController -> SamochodController: samochody.add(nowySamochod)
SamochodController -> SamochodController: samochodComboBox.select(nowySamochod)
@enduml
```

### Diagram sekwencji usuwania samochodu

```plantuml
@startuml
actor User
participant SamochodController

User -> SamochodController: onUsunSamochod(event)
SamochodController -> SamochodController: samochody.remove(Samochod)
SamochodController -> SamochodController: samochodComboBox.selectFirst()
@enduml
