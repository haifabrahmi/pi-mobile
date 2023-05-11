/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableModel;
import com.mycompany.myapp.entities.Bus;
import com.mycompany.myapp.services.ServiceBus;

import java.util.ArrayList;

/**
 *
 * @author haifa
 */
public class ListBusForm extends Form {

    public ListBusForm(Form previous) {
        setTitle("Liste des bus");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

        ArrayList<Bus> list = ServiceBus.getInstance().affichageBus();
        for (Bus bus : list) {
            TableModel model = new DefaultTableModel(new String[] {"Modèle", "Capacité", "Numéro de plaque", "Date de départ", "Date d'arrivée", "Destination", "Image"}, new Object[][] {
                {bus.getModele(), bus.getCapacite(), bus.getNumero_de_plaque(), bus.getDepart(), bus.getArrivee(), bus.getDestination(), bus.getImage()}
            }) {
                public boolean isCellEditable(int row, int col) {
                    return col != 0;
                }
            };
            Table tab = new Table(model);
            add(tab);

            // Bouton Supprimer
            Label lSupprimer = new Label(" ");
            lSupprimer.setUIID("NewsTopLine");
            Style supprimerStyle = new Style(lSupprimer.getUnselectedStyle());
            supprimerStyle.setFgColor(0xf21f1f);
        
            FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
            lSupprimer.setIcon(supprimerImage);
            lSupprimer.setTextPosition(RIGHT);
        
            // Clic sur l'icône Supprimer
            lSupprimer.addPointerPressedListener(l -> {
                Dialog dig = new Dialog("Suppression");
            
                if (dig.show("Suppression", "Voulez-vous supprimer ce bus ?", "Oui", "Annuler")) {
                    dig.dispose();
                } else {
                    dig.dispose();
                }

                // Supprimer le bus en utilisant le service Bus
                if (ServiceBus.getInstance().deleteBus(bus.getId_bus())) {
                    new ListBusForm(previous).show();
                }
            });
            add(lSupprimer);

            // Bouton Modifier
            Label lModifier = new Label(" ");
            lModifier.setUIID("NewsTopLine");
            Style modifierStyle = new Style(lModifier.getUnselectedStyle());
            modifierStyle.setFgColor(0xf7ad02);
        
            FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
            lModifier.setIcon(modifierImage);
            lModifier.setTextPosition(LEFT);
        
            // Clic sur l'icône Modifier
            lModifier.addPointerPressedListener(l -> {
                new ModifierBusform(bus, previous).show();
            });
            add(lModifier);
        }
    }
}

