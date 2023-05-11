/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.utils.Statics;
import static com.mycompany.myapp.utils.Statics.BASE_URL;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author bhk
 */
public class ServiceReservation {

    public ArrayList<Reservation> reservations;
    public Reservation reservation;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceReservation() {
        req = new ConnectionRequest();
    }

    public static ServiceReservation getInstance() {
        return new ServiceReservation();
    }

   public boolean addReservation(Reservation r) {
    String url = Statics.BASE_URL+"/AddReservation?heure_res="+r.getHeure_res()+"&prix="+r.getPrix()+"&nb_place="+r.getNb_place()+"&prix_totale="+r.getPrix_totale()+"&type_ticket="+r.getType_ticket();
    req.setUrl(url);
     req.setPost(false);
    // Set request body parameters
    req.addResponseListener((NetworkEvent evt) -> {
        boolean resultOK = req.getResponseCode() == 200;
        if (resultOK) {
            String response = new String(req.getResponseData());
            System.out.println("AddReservation response: " + response);
        } else {
            System.out.println("AddReservation error: " + req.getResponseErrorMessage());
        }
    });
   /* req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
            req.removeResponseListener(this);
        }
    });*/
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}
public ArrayList<Reservation> getAllRes() {
    ArrayList<Reservation> reservations = new ArrayList<>();

    String url = Statics.BASE_URL + "/ListReservation";
    ConnectionRequest req = new ConnectionRequest(url, false);
    req.setPost(false);
    req.addResponseListener((NetworkEvent evt) -> {
        JSONParser jsonParser = new JSONParser();
        //Map<String, Object> response = jsonParser.parseJSON(req.getResponseData());
        Map<String, Object> response;
        try {
            response = jsonParser.parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
                    
                   ArrayList<Map<String, Object>> reservationjs = (ArrayList<Map<String, Object>>) response.get("root"); 
                    for (Map<String, Object> ReservationJSON : reservationjs) {
                        Reservation r = new Reservation();
                            

                        r.setId_res(((Double) ReservationJSON.get("id_res")).intValue());
                        r.setHeure_res((String) ReservationJSON.get("heure_res"));
                        r.setDate_res(((String) ReservationJSON.get("date_res")));
                        r.setType_ticket(((String) ReservationJSON.get("type_ticket")));
                        
                        System.out.println("reservation   " + r.getId_res());
                        reservations.add(r);
                        System.out.println(r);
                    }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

        //reservation = parseReservation(response);
    });
    System.out.println("==================================" +reservations);
    NetworkManager.getInstance().addToQueue(req);
    return reservations;
    /*String url = Statics.BASE_URL + "/ListReservation";
    req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            try {
                JSONParser jsonParser = new JSONParser();
                Map<String, Object> response = jsonParser.parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
                reservations = new ArrayList<>();
                if (Boolean.parseBoolean(response.get("success").toString())) {
                    
                    ArrayList<Map<String, Object>> reservationjs = (ArrayList<Map<String, Object>>) response.get("reservation");
                    
                    for (Map<String, Object> ReservationJSON : reservationjs) {
                        Reservation r = new Reservation();
                        r.setId_res(((Double) ReservationJSON.get("id_res")).intValue());
                        r.setHeure_res((String) ReservationJSON.get("description"));
                        r.setDate_res(((Date) ReservationJSON.get("Date")));
                        r.setType_ticket(((String) ReservationJSON.get("Type")));
                        r.setPrix(((Float) ReservationJSON.get("Prix")));
                        r.setPrix_totale(((Float) ReservationJSON.get("Prix Totale")));
                        
                        
                        reservations.add(r);
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
    
    return reservations;*/
}

    public ArrayList<Reservation> parseReservations(String jsonText) {
        
    ArrayList<Reservation> reservations = new ArrayList<>();
    try {
        JSONParser parser = new JSONParser();
        Map<String, Object> records = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) records.get("root");
        for (Map<String, Object> obj : list) {
            Reservation reservation = new Reservation();
            Object idObj = obj.get("id_res");
            if (idObj == null) {
                reservation.setId_res(0);
            } else {
                int id = Integer.parseInt(idObj.toString());
                reservation.setId_res(id);
            }
            Object dateObj = obj.get("date_res");
            if (dateObj == null) {
                reservation.setDate_res(new String());
            } else {
                String dateString = dateObj.toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateString);
                    reservation.setDate_res(dateString);
                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + dateString);
                    reservation.setDate_res(new String());
                }
            }
            Object heureObj = obj.get("heure_res");
            if (heureObj == null) {
                reservation.setHeure_res("");
            } else {
                reservation.setHeure_res(heureObj.toString());
            }
            Object nbPlaceObj = obj.get("nb_place");
            if (nbPlaceObj == null) {
                reservation.setNb_place(0);
            } else {
                int nbPlace = Integer.parseInt(nbPlaceObj.toString());
                reservation.setNb_place(nbPlace);
            }
            Object typeTicketObj = obj.get("type_ticket");
            if (typeTicketObj == null) {
                reservation.setType_ticket("");
            } else {
                reservation.setType_ticket(typeTicketObj.toString());
            }
            Object prixObj = obj.get("prix");
            if (prixObj == null) {
                reservation.setPrix(0);
            } else {
                float prix = Float.parseFloat(prixObj.toString());
                reservation.setPrix(prix);
            }
            Object prixTotalObj = obj.get("prix_totale");
            if (prixTotalObj == null) {
                reservation.setPrix_totale(0);
            } else {
                float prixTotal = Float.parseFloat(prixTotalObj.toString());
                reservation.setPrix_totale(prixTotal);
            }
            reservations.add(reservation);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return reservations;
}


  public List<Reservation> getAllReservations() {
    List<Reservation> reservations = new ArrayList<>();

    String url = Statics.BASE_URL + "/ListReservation";
    ConnectionRequest req = new ConnectionRequest(url, false);
    req.setPost(false);
    req.addResponseListener((NetworkEvent evt) -> {
        String response = new String(req.getResponseData());
        reservation = parseReservation(response);
    });

    NetworkManager.getInstance().addToQueue(req);

    return reservations;
}

    
    
    
//    ________________________ONE RECLAMATION_________________________________________________
    
//    __________________________________________________________________________________________
    
    
    
public Reservation parseReservation(String jsonText) {
    try {
        JSONParser j = new JSONParser();
        Map<String, Object> resJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        Reservation r = new Reservation();

        // Parse reservation properties
        Object idObj = resJson.get("id_res");
        if (idObj == null) {
            r.setId_res(0);
        } else {
            int id_res = ((Double) idObj).intValue(); // convert to int
            r.setId_res(id_res);
        }
        if (resJson.get("date_res") == null) {
            r.setDate_res(new String());
        } else {
            String dateString = resJson.get("date_res").toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date_res = dateFormat.parse(dateString);
                r.setDate_res(dateString);
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + dateString);
                r.setDate_res(new String());
            }
        }
        if (resJson.get("heure_res") == null) {
            r.setHeure_res("");
        } else {
            r.setHeure_res(resJson.get("heure_res").toString());
        }
        if (resJson.get("Type_ticket") == null) {
            r.setType_ticket("");
        } else {
            r.setType_ticket(resJson.get("Type_ticket").toString());
        }
        Object prixObj = resJson.get("prix");
        if (prixObj == null) {
            r.setPrix(0);
        } else {
            float prix = Float.parseFloat(prixObj.toString());
            r.setPrix(prix);
        }
        Object nbPlaceObj = resJson.get("nb_place");
        if (nbPlaceObj == null) {
            r.setNb_place(0);
        } else {
            int nb_place = ((Double) nbPlaceObj).intValue(); // convert to int
            r.setNb_place(nb_place);
        }
        Object prixTotaleObj = resJson.get("prix_totale");
        if (prixTotaleObj == null) {
            r.setPrix_totale(0);
        } else {
            float prix_totale = Float.parseFloat(prixTotaleObj.toString());
            r.setPrix_totale(prix_totale);
        }

        return r;
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}

public boolean updateReservation(int reservationId, Date dateRes, String heureRes, double prix, int nbPlace, double prixTotale, String typeTicket) {
    String url = Statics.BASE_URL + "/UpdateReservation/" + reservationId;
    req.setUrl(url);
    req.setHttpMethod("PUT");

    // Set request body parameters
    req.setRequestBody("date_res=" + dateRes +
                       "&heure_res=" + heureRes +
                       "&prix=" + prix +
                       "&nb_place=" + nbPlace +
                       "&prix_totale=" + prixTotale +
                       "&type_ticket=" + typeTicket);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
            req.removeResponseListener(this);
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}


    public Reservation getResByResId(int recId) {
    String url = Statics.BASE_URL + "/ShowReservation/" ;
    req.setUrl(url);
    req.setPost(false);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            String jsonText = new String(req.getResponseData());
            reservation = parseReservation(jsonText);
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return reservation;
    
   
}
    
    //suppression 
public void deleteReservation(int res_id) {
    String url = BASE_URL + "/DeleteReservation" + "/" + res_id;

    ConnectionRequest request = new ConnectionRequest(url, true) {
        @Override
        protected void handleErrorResponseCode(int code, String message) {
            if (code == 404) {
                Dialog.show("Erreur", "La reservation n'existe pas.", "OK", null);
            } else {
                Dialog.show("Erreur", "Une erreur s'est produite lors de la suppression de la reservation : " + message, "OK", null);
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
   
    
    
    //    _______________________________REPONSES_____________________________________
//    ________________________________________________________________________________

    public boolean cancelReservation(Reservation reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
    



    
    
    
    

}
