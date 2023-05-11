/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author user
 */
public class HomeRecForm extends Form {
    public HomeRecForm() {
        setTitle("Reclamations");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0xEEEEEE);

        Label titleLabel = new Label("Bienvenue sur TuniBus");
        titleLabel.getUnselectedStyle().setFgColor(0x333333);
        titleLabel.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
        titleLabel.setAlignment(Component.CENTER);

        Button btnAddRec = new Button("Ajouter une nouvelle réclamation");
        btnAddRec.getUnselectedStyle().setBgColor(0xFFC107);
        btnAddRec.getUnselectedStyle().setFgColor(0x333333);
        btnAddRec.getAllStyles().setMarginTop(10);
        btnAddRec.getAllStyles().setMarginBottom(10);
        btnAddRec.addActionListener(e -> new AddRecForm(this).show());

        Button btnListRec = new Button("Voir vos réclamations");
        btnListRec.getUnselectedStyle().setBgColor(0xFFC107);
        btnListRec.getUnselectedStyle().setFgColor(0x333333);
        btnListRec.getAllStyles().setMarginBottom(10);
        btnListRec.addActionListener(e -> new ListReclamationsForm(this).show());

        addAll(titleLabel, btnAddRec, btnListRec);
    }
}

