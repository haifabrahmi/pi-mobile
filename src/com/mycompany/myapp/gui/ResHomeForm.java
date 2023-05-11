package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 * A form that displays options for managing reservations.
 */
public class ResHomeForm extends Form {

   public ResHomeForm() {
        setTitle("Reservation");
        setLayout(BoxLayout.y());
        
        add(new Label("Welcome to TuniBus"));
        Button btnAddRec = new Button("Ajouter une nouvelle reservation");
        Button btnListRec = new Button("delete A reservation");
        
        btnAddRec.addActionListener(e -> new AddReservationForm(this).show());
        
        btnListRec.addActionListener(e -> new ListReservationsForm(this).show());
        
        addAll(btnAddRec, btnListRec );
    }
}
