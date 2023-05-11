/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Publication;
import com.mycompany.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
/**
 *
 * @author asus
 */
public class ServicePublicaton {
    public static ServicePublicaton instance = null;
    public static boolean resultOk = true;

    private ConnectionRequest req ;
    
    public static ServicePublicaton getInstance(){
        if(instance == null)
            instance = new  ServicePublicaton();
        return instance;
    }
    
    public ServicePublicaton(){
     req = new ConnectionRequest();
    }
    //ajouter
    public void ajouterPublication(Publication p){
        String url = Statics.BASE_URL+"/AddPublication?user="+p.getUser().getIdUser()+"&titre="+p.getTitre()+"&text="+p.getTexte();
        req.setUrl(url);
        req.addResponseListener((e)->{
            String str = new String(req.getResponseData());
            System.out.println("dataa == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    //afficher
    public ArrayList<Publication> afficherPublication(){
            ArrayList<Publication> result= new ArrayList<>();

        String url = Statics.BASE_URL+"/AllPub";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
            jsonp = new JSONParser();
            
            try {
                Map<String,Object> mapPublications = jsonp.parseJSON(new CharArrayReader((new String(req.getResponseData()).toCharArray())));
                List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapPublications.get("root");
                for(Map<String,Object> obj : listOfMaps){
                    Publication p = new Publication();
                    
                    float id = Float.parseFloat(obj.get("idPub").toString());
                     String text = obj.get("texte").toString();
                     String titre = obj.get("titre").toString();
                     p.setIdPub((int) id);
                     p.setTexte(text);
                     p.setTitre(titre);
                   /*   String DateConverter = obj.get("datePub").toString().substring(obj.get("datePub").toString().indexOf("timestamp")+10,obj.get("datePub").toString().lastIndexOf("}"));
                    Date CurentDate = new Date(Double.valueOf(DateConverter).longValue()+1000);
                    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
               String dateString = formater.format(CurentDate);
               p.setDatePub(dateString);*/
        
                   
               result.add(p);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result ;
    }
    //details
    public Publication DetailPublication (int id , Publication p){
         String url = Statics.BASE_URL+"/ShowPublication/"+id;
        req.setUrl(url);
        String str = new String(req.getResponseData());
        req.addResponseListener((e)->{
            JSONParser jsonp = new JSONParser();
            try{
            
               Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader((new String(req.getResponseData()).toCharArray()))); 
               p.setTexte(obj.get("text").toString());
                p.setTitre(obj.get("titre").toString());

            }catch(Exception ex){
                System.out.println("erreur"+ex.getMessage()); 
            }
            System.out.println("data === "+str);
        });
                NetworkManager.getInstance().addToQueueAndWait(req);
return p;
    }
//supprimer
    public boolean deletePublication(int id ) {
        String url = Statics.BASE_URL +"/DeletePublication/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
//update
  public boolean modifierPublication(Publication p) {
        String url = Statics.BASE_URL +"/UpdatePublication/"+p.getIdPub()+"?titre="+p.getTitre()+"&text="+p.getTexte();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
        
    }    

}
