/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.utils;

/**
 *
 * @author user
 */
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReclamationStatistics {
    public Map<String, Integer> getCategoryStatistics(ArrayList<reclamation> reclamations) {
        Map<String, Integer> statistics = new HashMap<>();
        int chauffeurCount = 0;
        int voyageurCount = 0;
        int adminCount = 0;

        for (reclamation reclamation : reclamations) {
            String categorie = reclamation.getCategorie_reclamation();

            if (categorie.equalsIgnoreCase("chauffeur")) {
                chauffeurCount++;
            } else if (categorie.equalsIgnoreCase("voyageur")) {
                voyageurCount++;
            } else if (categorie.equalsIgnoreCase("admin")) {
                adminCount++;
            }
        }

        statistics.put("Chauffeur", chauffeurCount);
        statistics.put("Voyageur", voyageurCount);
        statistics.put("Admin", adminCount);

        return statistics;
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
ServiceReclamation reclamationService = ServiceReclamation.getInstance();
        ArrayList<reclamation> reclamations = reclamationService.getAllRecs();

        ReclamationStatistics statisticsCalculator = new ReclamationStatistics();
        Map<String, Integer> categoryStatistics = statisticsCalculator.getCategoryStatistics(reclamations);

        // Affichage des statistiques
        for (Map.Entry<String, Integer> entry : categoryStatistics.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            System.out.println(category + ": " + count);
        }
    }
}
