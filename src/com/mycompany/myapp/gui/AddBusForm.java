/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Bus;
import com.mycompany.myapp.services.ServiceBus;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author haifa
 */
public class AddBusForm extends Form {

    public AddBusForm(Form previous) {
        setTitle("Add a new Bus");
        setLayout(BoxLayout.y());

        TextField modele = new TextField("", "Modele");
        TextField capacite = new TextField("", "Capacite");
        TextField numero_de_plaque = new TextField("", "Numero de plaque");
        TextField date_depart = new TextField("", "Date Depart");
        TextField date_arrive = new TextField("", "Date Arrive");
        TextField destination = new TextField("", "Destination");
        TextField image = new TextField("", "Image");
        Button btnValider = new Button("Add Bus");
        Button btnAfficher = new Button("Affiche Bus");

        btnAfficher.addActionListener(evt -> new ListBusForm(previous).show());

        btnValider.addActionListener(evt -> {
            if (modele.getText().isEmpty() || capacite.getText().isEmpty() || numero_de_plaque.getText().isEmpty()
                    || date_depart.getText().isEmpty() || date_arrive.getText().isEmpty()
                    || destination.getText().isEmpty() || image.getText().isEmpty()) {
                Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
            } else {
                try {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    Bus b = new Bus();
                    b.setModele(modele.getText());
                    b.setCapacite(Integer.parseInt(capacite.getText()));
                    b.setNumero_de_plaque(Integer.parseInt(numero_de_plaque.getText()));

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date depart = formatter.parse(date_depart.getText());
                    Date arrivee = formatter.parse(date_arrive.getText());

                    b.setDepart(depart);
                    b.setArrivee(arrivee);
                    b.setDestination(destination.getText());
                    b.setImage(image.getText());
                    System.out.println("data Bus == " + b);

                    ServiceBus.getInstance().addBus(b);

                    iDialog.dispose();

                    refreshTheme();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        addAll(modele, capacite, numero_de_plaque, date_depart, date_arrive, destination, image, btnValider, btnAfficher);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
