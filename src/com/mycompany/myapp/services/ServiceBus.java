/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Bus;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;


/**
 *
 * @author haifa
 */
public class ServiceBus {
    private static ServiceBus instance;
      public static boolean resultOk = true;

    private ConnectionRequest req;

    private ServiceBus() {
        req = new ConnectionRequest();
    }

    public static ServiceBus getInstance() {
        if (instance == null) {
            instance = new ServiceBus();
        }
        return instance;
    }
public boolean addBus(Bus bus) {
    ConnectionRequest req = new ConnectionRequest();
    String url = Statics.BASE_URL + "/bus/addBus";

    // Créer un objet JSON contenant les données du bus
    JSONObject jsonBody = new JSONObject();
    try {
        jsonBody.put("modele", bus.getModele());
        jsonBody.put("capacite", bus.getCapacite());
        jsonBody.put("numero_de_plaque", bus.getNumero_de_plaque());
        jsonBody.put("date_depart", formatDate(bus.getDepart()));
        jsonBody.put("date_arrive", formatDate(bus.getArrivee()));
        jsonBody.put("destination", bus.getDestination());
        jsonBody.put("image", bus.getImage());
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Configurer la requête avec le corps JSON
    req.setUrl(url);
    req.setPost(true);
    req.setHttpMethod("POST");
    req.setRequestBody(jsonBody.toString());

    req.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());

    NetworkManager.getInstance().addToQueueAndWait(req);

    return true;
}

// Méthode pour formater une date en chaîne de caractères
private String formatDate(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);
}


//public boolean addBus(Bus bus) {
//    String url = Statics.BASE_URL + "/bus/addBus";
//
//    ConnectionRequest req = new ConnectionRequest();
//    req.setUrl(url);
//    req.setPost(true);
//
//    // Set request body parameters
//    req.addArgument("modele", bus.getModele());
//    req.addArgument("capacite", String.valueOf(bus.getCapacite()));
//    req.addArgument("numero_de_plaque", String.valueOf(bus.getNumero_de_plaque()));
//    req.addArgument("date_depart", bus.getDepart().toString());
//    req.addArgument("date_arrive", bus.getArrivee().toString());
//    req.addArgument("destination", bus.getDestination());
//    req.addArgument("image", bus.getImage());
//
//    // Set response listener
//    req.addResponseListener((NetworkEvent evt) -> {
//        boolean resultOK = req.getResponseCode() == 200;
//        if (resultOK) {
//            String response = new String(req.getResponseData());
//            System.out.println("AddBus response: " + response);
//        } else {
//            System.out.println("AddBus error: " + req.getResponseErrorMessage());
//        }
//    });
//
//    NetworkManager.getInstance().addToQueueAndWait(req);
//
//    return req.getResponseCode() == 200;
//}



    
public ArrayList<Bus> affichageBus() {
    ArrayList<Bus> result = new ArrayList<>();

    String url = Statics.BASE_URL + "/bus/listJson";
    req.setUrl(url);
    req.setPost(false); // Set the request method to GET

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                // Get the JSON response data
                byte[] responseData = req.getResponseData();
                if (responseData != null) {
                    // Convert the JSON response to a string
                    String responseString = new String(responseData, "UTF-8");

                    // Parse the JSON string
                    Map<String, Object> response = jsonp.parseJSON(new CharArrayReader(responseString.toCharArray()));

                    String successString = (String) response.get("success");
                    boolean success = Boolean.parseBoolean(successString);

                    if (success) {
                        List<Map<String, Object>> busList = (List<Map<String, Object>>) response.get("buses");
                        for (Map<String, Object> busData : busList) {
                            Object idBusObj = busData.get("id_bus");
                          int id_bus=0; // Default value
                           if (idBusObj != null) {
                               if (idBusObj instanceof Integer) {
                                   id_bus = (int) idBusObj;
                               } else if (idBusObj instanceof Double) {
                                   id_bus = ((Double) idBusObj).intValue();
                             }
                            }

                            String modele = (String) busData.get("modele");
                            int numero_de_plaque = ((Double) busData.get("numero_de_plaque")).intValue();
                            int capacite = ((Double) busData.get("capacite")).intValue();
                            String destination = (String) busData.get("destination");
                            String image = (String) busData.get("image");
                            String date_depart = (String) busData.get("date_depart");
                            String date_arrive = (String) busData.get("date_arrive");

                            // Convert the dates
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date depart = null;
                            Date arrivee = null;

                            if (date_depart != null) {
                                depart = formatter.parse(date_depart);
                            }

                            if (date_arrive != null) {
                                arrivee = formatter.parse(date_arrive);
                            }

                            // Create the Bus object
                            Bus bus = new Bus();
                            bus.setId_bus(id_bus);
                            bus.setModele(modele);
                            bus.setNumero_de_plaque(numero_de_plaque);
                            bus.setCapacite(capacite);
                            bus.setDepart(depart);
                            bus.setArrivee(arrivee);
                            bus.setDestination(destination);
                            bus.setImage(image);

                            // Add the bus to the result list
                            result.add(bus);
                        }
                    } else {
                        // Handle failure response
                        // ...
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return result;
}



   //Delete 
public boolean deleteBus(int id_bus) {
    req = new ConnectionRequest();
    String url = Statics.BASE_URL + "/deleteBus/" + id_bus;
    req.setUrl(url);
    req.setHttpMethod("DELETE"); // Spécifiez la méthode DELETE

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            int responseCode = req.getResponseCode();
            if (responseCode == 200) {
                // Suppression réussie
                // Effectuer les actions appropriées en cas de succès
            } else {
                // Suppression échouée
                // Effectuer les actions appropriées en cas d'échec
            }
        }
    });

    try {
        req.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        // Gérer les exceptions ou erreurs d'ajout de la requête à la file d'attente ou d'exécution de la requête
        return false;
    }
}

//public boolean deleteBus(int id_bus) {
//    
//    String url = Statics.BASE_URL + "/deleteBus/" + id_bus;
//
//    req.setUrl(url);
//    req.setHttpMethod("DELETE");
//
//    req.addResponseListener(new ActionListener<NetworkEvent>() {
//        @Override
//        public void actionPerformed(NetworkEvent evt) {
//            String response = new String(req.getResponseData());
//            System.out.println("DeleteBus response: " + response);
//            // Vérifier si la suppression a réussi
//            boolean resultOK = req.getResponseCode() == 200;
//            if (resultOK) {
//                // Traitement en cas de succès
//            } else {
//                // Traitement en cas d'échec
//            }
//            req.removeResponseCodeListener(this);
//        }
//    });
//
//    try {
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return true;
//    } catch (Exception e) {
//        e.printStackTrace();
//        // Gérer les erreurs d'ajout de la requête à la file d'attente
//        return false;
//    }
//}





       //Update 
public boolean modifierBus(Bus bus) {
    String url = Statics.BASE_URL + "/updateBus/" + bus.getId_bus();

    // Create the JSON object with the bus data
    JSONObject jsonBody = new JSONObject();
    try {
        jsonBody.put("modele", bus.getModele());
        jsonBody.put("capacite", bus.getCapacite());
        jsonBody.put("numero_de_plaque", bus.getNumero_de_plaque());
        jsonBody.put("date_depart", formatDate(bus.getDepart()));
        jsonBody.put("date_arrive", formatDate(bus.getArrivee()));
        jsonBody.put("destination", bus.getDestination());
        jsonBody.put("image", bus.getImage());
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Create the request
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setHttpMethod("PUT");
    req.setRequestBody(jsonBody.toString());

    req.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());

    NetworkManager.getInstance().addToQueueAndWait(req);

    return true;
}

// Méthode pour formater une date en chaîne de caractères
  private String formatDate1(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);
}





}


