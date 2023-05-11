/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.Properties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import com.mycompany.myapp.gui.Mailling;

/**
 *
 * @author user
 */
public class AddRecForm extends Form {

    private ComboBox<String> cbCategorie;
    private TextArea taDescription;
    private TextField tfEtat;
    private TextField tfObjet;
Mailling m = new Mailling();
    public AddRecForm(Form previous) {
        setTitle("Ajouter une réclamation");
        setLayout(BoxLayout.y());

        // Champs de saisie
        cbCategorie = new ComboBox<>("Catégorie de la réclamation", "Chauffeur", "Voyageur", "Admin");
        tfObjet = new TextField("", "Objet");
        taDescription = new TextArea(5, 20);
        taDescription.setHint("Description");
        tfEtat = new TextField("", "État");

        // Bouton d'ajout
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.addActionListener(e -> {
            
            
            
            // Récupération des valeurs saisies
            String categorie = cbCategorie.getSelectedItem();
            String description = taDescription.getText();
            String objet = tfObjet.getText();
            
            
            
            
            

            // Validation des champs obligatoires
            if (categorie.isEmpty() || description.isEmpty() || objet.isEmpty()) {
                Dialog.show("Erreur", "Veuillez remplir tous les champs.", "OK", null);
                return;
            }

            // Parsing de l'état
            int etat;
            try {
                etat = Integer.parseInt(tfEtat.getText());
            } catch (NumberFormatException ex) {
                Dialog.show("Erreur", "L'état doit être un entier.", "OK", null);
                return;
            }

            // Création de la réclamation
            reclamation r = new reclamation();
            r.setCategorie_reclamation(categorie);
            r.setDescription_reclamation(description);
            r.setEtat_reclamation(etat);
            r.setObjet_reclamation(objet);
            
         

            // Envoi de la réclamation au serveur
            ServiceReclamation service = new ServiceReclamation();
            //TWILIO
            //  TwilioSMS.sendSMS();

            
            if (service.addReclamation(r)) {
             TextComponent email= new TextComponent().label("Email :");
      add(email);
 
        TextComponent description1= new TextComponent().label("Description :");
        add(description1);
                Dialog.show("Succès", "Réclamation ajoutée avec succès.", "OK", null);
            m.sendReponse(email.getText(),description1.getText());
                
                previous.showBack();
            } else {
                Dialog.show("Erreur", "Une erreur est survenue lors de l'ajout de la réclamation.", "OK", null);
            }
        });
        // Ajout des composants au formulaire
        add(cbCategorie).add(taDescription).add(tfEtat).add(tfObjet).add(btnAjouter);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
