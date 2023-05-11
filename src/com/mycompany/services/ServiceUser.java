/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.gui.ProfilForm;
import com.mycompany.gui.SessionManager;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author msi
 */
public class ServiceUser {
    
      public static ServiceUser instance=null;
    public static boolean resultOk = true;
   private ConnectionRequest req;
   String json;
   int jsons;
   int id = 0;
   int x=0;
   int y=0;
   int z=0;
   private String response;
    public static ServiceUser getInstance(){
       if(instance==null) 
           instance=new ServiceUser();
       
       return instance;
    }
   
     public ServiceUser(){
        req=new ConnectionRequest();
    }
       public boolean signin(TextField nomPers,TextField prenomPers,TextField email,TextField mdp, ComboBox<String> role,TextField numTel, Resources res ) {
        
     
        String url = Statics.BASE_URL+"/signin?nom="+nomPers.getText().toString()+"&prenom="+prenomPers.getText().toString()+"&num="+numTel.getText().toString()+"&email="+email.getText().toString()+"&password="+mdp.getText().toString()+"&role="+role.getSelectedItem().toString(); 
       
        req.setUrl(url);
        
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
          return true;  
    
    }
       //login
   public boolean login(TextField username,TextField password, Resources rs ) {
        
        
        String url = Statics.BASE_URL+"/login?email="+username.getText().toString()+"&password="+password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            try {
            
            if(json.equals("failed")) {
               // Dialog.show("Echec d'authentification","Email ou mot de passe incorrecte","OK",null);
            }
            else {
                System.out.println("data =="+json);
                
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
               /* Set<String> keys = user.keySet();
for (String key : keys) {
    System.out.println("Clé : " + key);
}*/
               String ide=user.get("iduser").toString();
             
                System.out.println(ide);
                //Session 
              

                
                float idUser = Float.parseFloat(user.get("iduser").toString());
                float id= Float.parseFloat(user.get("iduser").toString());
              System.out.println("2");
                SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
             System.out.println("3");
                SessionManager.setEmail(user.get("email").toString());
                System.out.println("4"); 
                SessionManager.setMdp(user.get("password").toString());
                
               
                if(user.size() >0 ) 
                {
                     System.out.println("utilsateur connecté: "+SessionManager.getEmail()+", "+SessionManager.getMdp());
                  //  Toolbar.setGlobalToolbar(false);
                   //new ProfilForm(rs).show();
            //Toolbar.setGlobalToolbar(true);
            
                }
                    
                    }
            
            }catch(Exception ex) {
                Dialog.show("Echec d'authentification","Email ou mot de passe incorrecte","OK",null);
              }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    } 
   
   
   public User find(int id) {
        
     User p=new User();
        String url = Statics.BASE_URL+"/Student/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                p.setNom(obj.get("nom").toString());
                p.setPrenom(obj.get("prenom").toString());
                
                
                p.setNum(obj.get("num").toString());
                p.setEmail(obj.get("email").toString());
                p.setPassword(obj.get("password").toString());
                p.setRole(obj.get("role").toString());
              
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return p;
        
        
    }
   
   
   //send mail
    public boolean sendMail(String email) {

        String url = Statics.BASE_URL+"/sendMail?email=" + email;
        ConnectionRequest con = new ConnectionRequest();
    con.setUrl(url);

    NetworkManager.getInstance().addToQueueAndWait(con);
    byte[] data = con.getResponseData();

    if (data == null) {
        // Une erreur s'est produite, retourne false
        return false;
    }

    String responseData = new String(data);
    if (responseData.contains("\"error\":")) {
        // Une erreur s'est produite, retourne false
        return false;
    } else {
        // L'e-mail a été envoyé avec succès
        return true;
    }
    }
    
    
     public boolean modifierUser(User p,int id) {
        String url = Statics.BASE_URL +"/updateStudentJSON/"+p.getIdUser()+"?nom="+p.getNom()+"&prenom="+p.getPrenom()+"&num="+p.getNum()+"&email="+p.getEmail()+"&password="+p.getPassword()+"&role="+p.getRole(); 
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
     
     
     public boolean verifcode(String code,String email) {

        String url = Statics.BASE_URL+"/verifcode?email=" + email+"&token="+code;
         ConnectionRequest con = new ConnectionRequest();
    con.setUrl(url);

    NetworkManager.getInstance().addToQueueAndWait(con);
    byte[] data = con.getResponseData();

    if (data == null) {
        // Une erreur s'est produite, retourne false
        return false;
    }

    String responseData = new String(data);
    if (responseData.contains("\"error\":")) {
        // Une erreur s'est produite, retourne false
        return false;
    } else {
        // L'e-mail a été envoyé avec succès
        return true;
    }  
        
        
        
        
        
        
    }
      public boolean changer(String mdp,String email) {

        String url = Statics.BASE_URL+"/changer?email=" + email+"&password="+mdp;
        ConnectionRequest con = new ConnectionRequest();
         con.setUrl(url);

    NetworkManager.getInstance().addToQueueAndWait(con);
    byte[] data = con.getResponseData();

    if (data == null) {
        // Une erreur s'est produite, retourne false
        return false;
    }

    String responseData = new String(data);
    if (responseData.contains("\"error\":")) {
        // Une erreur s'est produite, retourne false
        return false;
    } else {
        // L'e-mail a été envoyé avec succès
        return true;
    }  }
   
}
