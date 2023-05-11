/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Bus;
import com.mycompany.myapp.services.ServiceBus;


/**
 *
 * @author haifa
 */
public class ModifierBus extends com.codename1.ui.Form {

    Form current;

    public ModifierBus(Bus b, Form previous) {
        setTitle("Modifier Bus");

        TextField modele = new TextField(b.getModele(), "Modele", 20, TextField.ANY);
        TextField numero_de_plaque = new TextField(String.valueOf(b.getNumero_de_plaque()), "Numero de plaque", 20, TextField.ANY);
        TextField capacite = new TextField(String.valueOf(b.getCapacite()), "Capacite", 20, TextField.ANY);
        Picker dateDepart = new Picker();
        dateDepart.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateDepart.setDate(b.getDepart());
        Picker dateArrivee = new Picker();
        dateArrivee.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateArrivee.setDate(b.getArrivee());
        TextField destination = new TextField(b.getDestination(), "Destination", 20, TextField.ANY);
        TextField image = new TextField(b.getImage(), "Image", 20, TextField.ANY);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        // Event onclick btnModifier
        btnModifier.addPointerPressedListener(l -> {
            b.setModele(modele.getText());
            b.setNumero_de_plaque(Integer.parseInt(numero_de_plaque.getText()));
            b.setCapacite(Integer.parseInt(capacite.getText()));
            b.setDestination(destination.getText());
            b.setDepart(dateDepart.getDate());
            b.setArrivee(dateArrivee.getDate());
            b.setImage(image.getText());

            // appel fonction modifier bus du service
            if (ServiceBus.getInstance().modifierBus(b)) {
                new ListBusForm(previous).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListBusForm(previous).show();
        });

        Container content = BoxLayout.encloseY(
                new FloatingHint(modele),
                new FloatingHint(numero_de_plaque),
                new FloatingHint(capacite),
                new FloatingHint(destination),
                   new Label("Date de départ"),
                dateDepart,
                new Label("Date d'arrivée"),
                dateArrivee,
                new FloatingHint(image),
                btnModifier,
                btnAnnuler
        );

        add(content);
    }
}

