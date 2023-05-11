/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author user
 */

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.HttpClients;
//
//import javax.net.ssl.SSLContext;
//import java.security.NoSuchAlgorithmException;
//
//public class TwilioSMS {
//
//    // Mettez vos informations de compte Twilio ici
//    public static final String ACCOUNT_SID = "AC8769b67cecdd7b923a8f626299e34ed0";
//    public static final String AUTH_TOKEN = "46a5661b8a1c677b52b52f4459f397ae";
//
//    public static void sendSMS() throws NoSuchAlgorithmException {
//        // Initialisez le client Twilio avec vos informations de compte
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        // Mettez votre numéro de téléphone Twilio (numéro source)
//        String fromNumber = "+12706068705";
//
//        // Mettez le numéro de téléphone du destinataire
//        String toNumber = "+21653231808";
//
//        // Mettez le contenu de votre message
//        String messageBody = "Reclamation envoyée!";
//
//        // Créez le message Twilio et envoyez-le
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault(), new String[]{"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
//
//        Message message = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), messageBody).setHttpClient(HttpClients.custom().setSSLSocketFactory(sslsf).build()).create();
//
//        // Vérifiez que le message a été envoyé avec succès
//        if (message.getStatus() == Message.Status.FAILED) {
//            System.out.println("Erreur lors de l'envoi du message: " + message.getErrorCode() + " - " + message.getErrorMessage());
//        } else {
//            System.out.println("Message envoyé avec succès!");
//        }
//    }
//
//}
//
