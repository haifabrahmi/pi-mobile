/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author haifa
 */
public class HomeForm extends Form {
    
    public HomeForm() {
        setTitle("Bus");
        setLayout(BoxLayout.y());
        
        add(new Label("Choisissez une option"));
        Button btnValider= new Button("Ajouter un nouveau bus");
        Button btnAfficher = new Button("Voir les bus");
        
       btnValider.addActionListener(e -> new AddBusForm(this).show());
        
      btnAfficher.addActionListener(e -> {
          ListBusForm listBForm = new ListBusForm(this);
         listBForm.show();     });
        
        addAll(btnValider, btnAfficher);
    }
}