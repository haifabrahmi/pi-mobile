/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingHint;
import static com.codename1.push.PushContent.setTitle;
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
public class ModifierBusform extends Form {
    // déclaration de l'objet Bus
    private Bus bus;
    private Form previous;

    public ModifierBusform(Bus bus, Form previous) {
        setTitle("Modifier Bus");

        TextField modeleBus = new TextField(bus.getModele(), "Modèle du bus", 20, TextField.ANY);
        TextField capaciteBus = new TextField(String.valueOf(bus.getCapacite()), "Capacité du bus", 20, TextField.ANY);
        TextField numPlaqueBus = new TextField(String.valueOf(bus.getNumero_de_plaque()), "Numéro de plaque du bus", 20, TextField.ANY);
        Picker dateDepart = new Picker();
        dateDepart.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateDepart.setDate(bus.getDepart());
        Picker dateArrivee = new Picker();
        dateArrivee.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateArrivee.setDate(bus.getArrivee());
        TextField destination = new TextField(bus.getDestination(), "Destination du bus", 20, TextField.ANY);
        TextField imageBus = new TextField(bus.getImage(), "Image du bus", 20, TextField.ANY);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btnModifer
        btnModifier.addActionListener(l -> {

            bus.setModele(modeleBus.getText());
            bus.setCapacite(Integer.parseInt(capaciteBus.getText()));
            bus.setNumero_de_plaque(Integer.parseInt(numPlaqueBus.getText()));
            bus.setDepart(dateDepart.getDate());
            bus.setArrivee(dateArrivee.getDate());
            bus.setDestination(destination.getText());
            bus.setImage(imageBus.getText());

            //appel fonction modifierBus du service
            if (ServiceBus.getInstance().modifierBus(bus)) { // if true
                new ListBusForm(previous).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListBusForm(previous).show();
        });

        Container content = BoxLayout.encloseY(
                new FloatingHint(modeleBus),
                new FloatingHint(capaciteBus),
                new FloatingHint(numPlaqueBus),
                new Label("Date de départ"),
                dateDepart,
                new Label("Date d'arrivée"),
                dateArrivee,
                new FloatingHint(destination),
                new FloatingHint(imageBus),
                btnModifier,
                btnAnnuler
        );

        add(content);
    }
}


