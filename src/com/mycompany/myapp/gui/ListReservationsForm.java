package com.mycompany.myapp.gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.services.ServiceReservation;
import java.util.ArrayList;

public class ListReservationsForm extends Form {

    public ListReservationsForm(Form previous) {
        setTitle("Liste des réservations");
        setLayout(BoxLayout.y());
        add(new Label("Welcome to TuniBus"));

        // Récupérer la liste des réservations
        ServiceReservation service = new ServiceReservation();
                ArrayList<Reservation>reservationList = ServiceReservation.getInstance().getAllRes();


        // Créer un conteneur pour la liste des réservations
        Container reservationContainer = new Container(BoxLayout.y());
                    Container reservationDetailsContainer = new Container(BoxLayout.y());

        // Ajouter chaque réservation dans la liste
        for (Reservation r : reservationList) {
           
           /* reservationDetailsContainer.add(new Label("id: " + r.getId_res()));
           reservationDetailsContainer.add(new Label("Date: " + r.getDate_res().toString()));
            reservationDetailsContainer.add(new Label("Heure: " + r.getHeure_res()));
            reservationDetailsContainer.add(new Label("Type de ticket: " + r.getType_ticket()));
            reservationDetailsContainer.add(new Label("Prix: " + r.getPrix()));
            reservationDetailsContainer.add(new Label("Nombre de places: " + r.getNb_place()));
            reservationDetailsContainer.add(new Label("Prix total: " + r.getPrix_totale()));
            
            Button deleteButton = new Button("Delete");
            deleteButton.addActionListener(e -> {
                if (Dialog.show("Confirmation", "Are you sure you want to delete this reservation?", "Yes", "No")) {
                    // Supprimer la réservation
                    service.deleteReservation(r.getId_res());
                    reservationDetailsContainer.remove();
                    ToastBar.showInfoMessage("Reservation deleted successfully");
                }
            });
            reservationDetailsContainer.add(deleteButton);

            // Ajouter les détails de la réservation dans le conteneur de la liste
            reservationContainer.add(reservationDetailsContainer);
       
          // reservationContainer.add(createSeparator());*/
           
                   Container cnt = new Container();

//kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label objetTxt = new Label("Date : "+r.getDate_res(),"NewsTopLine2");
        Label dateTxt = new Label("Titre : "+r.getType_ticket(),"NewsTopLine2");
        Label etatTxt = new Label("Contenu : "+r.getHeure_res(),"NewsTopLine2" );
        
        createSeparator();
     
        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
            Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
            FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce reclamation ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
              /* if(ServiceReservation.getInstance().deleteReservation(r.getId_res())) {
                    new ListReservationsForm(res).show();
                }*/
           
        });
        
        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);
        
        
        lModifier.addPointerPressedListener(l -> {
            //System.out.println("hello update");
         //   new ModifierPublicationFrom(res,rec).show();
        });
        
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                
                BoxLayout.encloseX(objetTxt),
                BoxLayout.encloseX(dateTxt),
                BoxLayout.encloseX(etatTxt,lModifier,lSupprimer)));
        
        
        
        add(cnt);
                
        }
     
        
       TextField id = new TextField("", "id to delete");
        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(e -> {
            if (Dialog.show("Confirmation", "Are you sure you want to delete this reservation?", "Yes", "No")) {
                // Supprimer la réservation avec l'ID spécifié
                service.deleteReservation(Integer.parseInt(id.getText()));
                ToastBar.showInfoMessage("Reservation deleted successfully");
            }
        });

       add(id);
       add(deleteButton);
    }

    private static Label createSeparator() {
        Label separator = new Label();
        separator.setUIID("Separator");
        separator.setShowEvenIfBlank(true);
        return separator;
}
}
