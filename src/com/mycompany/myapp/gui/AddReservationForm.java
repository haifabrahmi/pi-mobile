package com.mycompany.myapp.gui;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.services.ServiceReservation;

import java.util.Date;

public class AddReservationForm extends Form {

    public AddReservationForm(Form previous) {
        setTitle("Ajouter une nouvelle réservation");
        setLayout(BoxLayout.y());

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(today);
        TextField date_res = new TextField(formattedDate, "Date (yyyy-mm-dd)");
        
        Picker heure_res = new Picker();
        heure_res.setType(Display.PICKER_TYPE_TIME);
        heure_res.setStrings(
            "09:00",
            "09:30",
            "10:00"
        );

        ComboBox<String> Type_ticket = new ComboBox<>("2 stations", "Normal");
        TextField searchField = new TextField();
searchField.addDataChangeListener((i1, i2) -> {
    // Perform the refresh action here
    // You can retrieve the text using searchField.getText()
});

        TextField prix = new TextField("", "Prix");
        prix.setEditable(false);
        TextField nb_place = new TextField("", "Nombre de places");
        TextField prix_totale = new TextField("", "Prix total");
        Button btnValider = new Button("Ajouter une nouvelle réservation");
        btnValider.getUnselectedStyle().setBgColor(0xFFA500);


        // Add an ActionListener to the Type_ticket ComboBox
        Type_ticket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String selectedItem = Type_ticket.getSelectedItem();
                if (selectedItem.equals("2 stations")) {
                    prix.setText("1"); // set the value of prix to 0.5
                } else {
                    prix.setText("0.5"); // set the value of prix to 1
                }
            }
        });
        nb_place.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    float prixFloat = Float.parseFloat(prix.getText());
                    int nbPlaceInt = Integer.parseInt(nb_place.getText());
                    prix_totale.setText(Float.toString(prixFloat * nbPlaceInt));
                            prix_totale.setEditable(false);

                } catch (NumberFormatException e) {
                    Dialog.show("Erreur", "Les champs 'Prix', 'Nombre de places' et 'Prix total' doivent être des nombres", new Command("OK"));
                }
            }
        });
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (date_res.getText().length() == 0 || heure_res.getText().length() == 0 ||
                        prix.getText().length() == 0 || nb_place.getText().length() == 0 || 
                        prix_totale.getText().length() == 0) {
                    Dialog.show("Alerte", "Veuillez remplir tous les champs", new Command("OK"));
                } else {
                    try {
                        Reservation r = new Reservation();

                        r.setId_res(0); // auto-generated id
                        r.setDate_res(date_res.getText()); // parse the input string as a date
                        r.setHeure_res(heure_res.getText());
                        r.setType_ticket(Type_ticket.getSelectedItem());
                        r.setPrix(Float.parseFloat(prix.getText()));
                        r.setNb_place(Integer.parseInt(nb_place.getText()));
                        r.setPrix_totale(Float.parseFloat(prix_totale.getText()));
                        ServiceReservation.getInstance().addReservation(r);
                        Dialog.show("Succès", "Votre réservation a été ajoutée", new Command("OK"));

                    } catch (NumberFormatException e) {
                        Dialog.show("Erreur", "Les champs 'Prix', 'Nombre de places' et 'Prix total' doivent être des nombres", new Command("OK"));
                    } 
                }
            }
        });

        addAll(date_res, heure_res, Type_ticket, prix, nb_place, prix_totale, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    AddReservationForm(ListReservationsForm aThis, int id_res) {
    }
}
