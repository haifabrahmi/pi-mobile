/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.ArrayList;


/**
 *
 * @author user
 */
public class ListReclamationsForm extends Form {
    private ArrayList<reclamation> reclamations;

    public ListReclamationsForm(Form previous) {
        setTitle("Liste des réclamations");
        setLayout(BoxLayout.y());

        // Récupérer la liste des réclamations
               ServiceReclamation service = new ServiceReclamation();
        reclamations = service.getAllRecs(); // Modifier cette

        // Ajouter les réclamations dans la liste
        for (reclamation r : reclamations) {
            Container c = new Container(BoxLayout.x()); // conteneur horizontal
            Container cLeft = new Container(BoxLayout.y()); // conteneur vertical pour le texte
            cLeft.add(new Label("Objet: " + r.getObjet_reclamation()));
            cLeft.add(new Label("Description: " + r.getDescription_reclamation()));
            cLeft.add(new Label("Catégorie: " + r.getCategorie_reclamation()));
            cLeft.add(new Label("État: " + r.getEtat_reclamation()));
            c.add(cLeft);
     
            Button btnDetails = new Button("Détails");
            btnDetails.addActionListener(e -> showDetails(r));
            cLeft.setLeadComponent(btnDetails); // mettre le bouton "Détails" en tant que bouton principal du conteneur vertical
            c.setLeadComponent(btnDetails); // mettre le bouton "Détails" en tant que bouton principal du conteneur horizontal
            add(c);
            add(createSeparator());
        }

        // Bouton de retour



        
        
         // Afficher les statistiques sur les catégories de réclamation
        add(new Label("Statistiques sur les catégories de réclamation:"));
        add(new Label("Chauffeur: " + getReclamationCountByCategory("Chauffeur")));
        add(new Label("Voyageur: " + getReclamationCountByCategory("Voyageur")));
        add(new Label("Admin: " + getReclamationCountByCategory("Admin")));

        
        
        
        
        
        
        
        
        
    

    }
private void showDetails(reclamation r) {
    Form detailsForm = new Form(r.getObjet_reclamation());
    detailsForm.setLayout(BoxLayout.y());
    detailsForm.add(new Label("Objet: " + r.getObjet_reclamation()));
    detailsForm.add(new Label("Description: " + r.getDescription_reclamation()));
    detailsForm.add(new Label("Catégorie: " + r.getCategorie_reclamation()));
    detailsForm.add(new Label("État: " + r.getEtat_reclamation()));

    // Bouton de modification
    Button btnModifier = new Button("Modifier");
    btnModifier.addActionListener(e -> {
        editReclamation(r, detailsForm);
    });
    detailsForm.add(btnModifier);

    // Bouton de suppression
    Button btnSupprimer = new Button("Supprimer");
    btnSupprimer.addActionListener(e -> {
        ServiceReclamation service = new ServiceReclamation();
        service.deleteReclamation(r.getId_reclamation());
        ToastBar.showInfoMessage("Réclamation supprimée avec succès.");
        this.show();
    });
    detailsForm.add(btnSupprimer);

    Button btnBack = new Button("Retour");
    btnBack.addActionListener(e -> this.show());
    detailsForm.add(btnBack);

    detailsForm.show();
}

 private int getReclamationCountByCategory(String category) {
     
    int count = 0;
    for (reclamation r : reclamations) {
        if (r.getCategorie_reclamation().equals(category)) {
            count++;
        }
    }
    return count;
}


    private static Label createSeparator() {
        Label separator = new Label();
        separator.setUIID("Separator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
  public static void editReclamation(reclamation r, Form parentForm) {
    Form editForm = new Form("Modifier une réclamation");
    editForm.setLayout(BoxLayout.y());

    // Champ pour saisir l'objet de la réclamation
    TextField objetField = new TextField(r.getObjet_reclamation());
    editForm.add(new Label("Objet:"));
    editForm.add(objetField);

    // Champ pour saisir la description de la réclamation
    TextField descriptionField = new TextField(r.getDescription_reclamation());
    editForm.add(new Label("Description:"));
    editForm.add(descriptionField);

    // ComboBox pour sélectionner la catégorie de la réclamation
    ComboBox<String> categorieComboBox = new ComboBox<>();
    categorieComboBox.addItem("Voyageur");
    categorieComboBox.addItem("Chauffeur");
    categorieComboBox.addItem("Admin");
    categorieComboBox.setSelectedItem(r.getCategorie_reclamation());
    editForm.add(new Label("Catégorie:"));
    editForm.add(categorieComboBox);

    // ComboBox pour sélectionner l'état de la réclamation
    ComboBox<Integer> etatComboBox = new ComboBox<>();
    etatComboBox.addItem(0); // En attente
    etatComboBox.addItem(1); // En cours
    etatComboBox.setSelectedItem(r.getEtat_reclamation());
    editForm.add(new Label("État:"));
    editForm.add(etatComboBox);

    // Bouton de validation
    Button btnValider = new Button("Valider");
    btnValider.addActionListener(e -> {
        // Mettre à jour la réclamation avec les nouvelles valeurs
        r.setObjet_reclamation(objetField.getText());
        r.setDescription_reclamation(descriptionField.getText());
        r.setCategorie_reclamation(categorieComboBox.getSelectedItem());
        r.setEtat_reclamation(etatComboBox.getSelectedItem());

        // Call the editReclamation method and store the result
boolean success = ServiceReclamation.editReclamation(r);

        if (success) {
            // Afficher un message de confirmation
            ToastBar.showInfoMessage("Réclamation modifiée avec succès.");

            // Fermer la vue de modification et revenir à la vue des détails de la réclamation
            parentForm.showBack();
        } else {
            Dialog.show("Erreur", "Une erreur s'est produite lors de la modification de la réclamation.", "OK", null);
        }
    });
    editForm.add(btnValider);

    // Bouton d'annulation
    Button btnAnnuler = new Button("Annuler");
    btnAnnuler.addActionListener(e -> parentForm.showBack());
    editForm.add(btnAnnuler);

    editForm.show();
}

  
  
  

}




    




