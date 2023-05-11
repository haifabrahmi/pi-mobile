/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import com.codename1.io.JSONParser;
import com.codename1.io.Util;
import static com.mycompany.myapp.utils.Statics.REC_URL;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.RequestBuilder;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Callback;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class ServiceReclamation {
     public ArrayList<reclamation> reclamations;
    
    
    public reclamation reclamation;


    public static ServiceReclamation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceReclamation() {
        req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    
    //AJOUUUUT D'UNE RECLAMATIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOON
    

public boolean addReclamation(reclamation r) {
    String url = Statics.BASE_URL;

    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(true);

    // Créer un objet JSON contenant les données de la réclamation
    JSONObject json = new JSONObject();
    try {
        json.put("categorie_reclamation", r.getCategorie_reclamation());
        json.put("objet_reclamation", r.getObjet_reclamation());
        json.put("description_reclamation", r.getDescription_reclamation());
        json.put("etat_reclamation", r.getEtat_reclamation());
        json.put("date_reclamation", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    } catch (JSONException e) {
        e.printStackTrace();
    }

    // Définir le corps de la requête comme étant le JSON
    req.setRequestBody(json.toString());

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}

  
  
  // affichage 
public ArrayList<reclamation> getAllRecs() {
    String url = Statics.REC_URL + "/listJson";
    req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            try {
                JSONParser jsonParser = new JSONParser();
                Map<String, Object> response = jsonParser.parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
                reclamations = new ArrayList<>();
if (Boolean.parseBoolean(response.get("success").toString())) {
                    ArrayList<Map<String, Object>> reclamationsJson = (ArrayList<Map<String, Object>>) response.get("reclamations");
                    for (Map<String, Object> reclamationJson : reclamationsJson) {
                        reclamation reclamation = new reclamation();
                        reclamation.setId_reclamation(((Double) reclamationJson.get("id_reclamation")).intValue());
                        reclamation.setDescription_reclamation((String) reclamationJson.get("description"));
                        reclamation.setEtat_reclamation(((Double) reclamationJson.get("etat_reclamation")).intValue());
                        reclamation.setCategorie_reclamation((String) reclamationJson.get("categorie_reclamation"));
                        reclamation.setObjet_reclamation((String) reclamationJson.get("objet_reclamation"));
                        reclamations.add(reclamation);
                    }
                } else {
                    System.out.println("Une erreur est survenue lors de la récupération des réclamations : " + response.get("message"));
                }
                req.removeResponseListener(this);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                req.removeResponseListener(this);
            }
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return reclamations;
}

//suppression 
public void deleteReclamation(int id_reclamation) {
    String url = REC_URL + "/" + id_reclamation + "/deleteJson";

    ConnectionRequest request = new ConnectionRequest(url, true) {
        @Override
        protected void handleErrorResponseCode(int code, String message) {
            if (code == 404) {
                Dialog.show("Erreur", "La réclamation n'existe pas.", "OK", null);
            } else {
                Dialog.show("Erreur", "Une erreur s'est produite lors de la suppression de la réclamation : " + message, "OK", null);
            }
        }

        protected void handleResponseCode(int code, String message) {
            if (code == 200) {
                Dialog.show("Succès", "La réclamation a été supprimée avec succès.", "OK", null);
            } else {
                Dialog.show("Erreur", "Une erreur s'est produite lors de la suppression de la réclamation : " + message, "OK", null);
            }
        }
    };
    request.setHttpMethod("DELETE");
    request.addRequestHeader("Content-Type", "application/json");
    request.addRequestHeader("Accept", "application/json");
    request.setFailSilently(true);
    NetworkManager.getInstance().addToQueue(request);
}

//
//public boolean editReclamation(int id_reclamation, String objet_reclamation, String description_reclamation, String categorie_reclamation, int etat_reclamation) {
//    String url = "http://localhost:8000/reclamation/editJson";
//
//    final boolean[] resultOK = {false};
//    ConnectionRequest request = new ConnectionRequest(url) {
//        @Override
//        protected void handleErrorResponseCode(int code, String message) {
//            if (code == 404) {
//                Dialog.show("Erreur", "La réclamation n'existe pas.", "OK", null);
//            } else {
//                Dialog.show("Erreur", "Une erreur s'est produite lors de la modification de la réclamation : " + message, "OK", null);
//            }
//        }
//
//        @Override
//        protected void readResponse(InputStream input) throws IOException {
//            String message = new String(Util.readInputStream(input), "UTF-8");
//            Dialog.show("Succès", message, "OK", null);
//            resultOK[0] = true;
//        }
//
//        @Override
//        protected void handleException(Exception err) {
//            Dialog.show("Erreur", "Une erreur s'est produite lors de la modification de la réclamation : " + err.getMessage(), "OK", null);
//        }
//    };
//
//    // paramètres de la requête
//    request.setHttpMethod("PUT");
//    request.addRequestHeader("Content-Type", "application/json");
//    request.addRequestHeader("Accept", "application/json");
//
//    // corps de la requête
//    JSONObject requestBody = new JSONObject();
//    requestBody.put("id_reclamation", id_reclamation);
//    requestBody.put("objet_reclamation", objet_reclamation);
//    requestBody.put("description_reclamation", description_reclamation);
//    requestBody.put("categorie_reclamation", categorie_reclamation);
//    requestBody.put("etat_reclamation", etat_reclamation);
//
//    request.setRequestBody(requestBody.toString());
//
//    NetworkManager.getInstance().addToQueueAndWait(request);
//
//    return resultOK[0];
//}



public static boolean editReclamation(reclamation r) {
    String url = Statics.MDF_URL + "/reclamation/editJson" + r.getId_reclamation();

    // Créer une nouvelle requête de connexion
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setHttpMethod("PUT");
    
    // Ajouter les paramètres de la réclamation dans le corps de la requête
    req.addArgument("objet_reclamation", r.getObjet_reclamation());
    req.addArgument("description_reclamation", r.getDescription_reclamation());
    req.addArgument("categorie_reclamation", r.getCategorie_reclamation());
    req.addArgument("etat_reclamation", String.valueOf(r.getEtat_reclamation()));
    
    // Envoyer la requête de connexion de manière synchrone
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    // Vérifier si la requête a été traitée avec succès
    boolean resultOK = req.getResponseCode() == 200;
    
    // Renvoyer le résultat de la mise à jour de la réclamation
    return resultOK;
}
}


   

















     



