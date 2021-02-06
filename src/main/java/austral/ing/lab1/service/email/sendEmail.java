package austral.ing.lab1.service.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class sendEmail {
    public static void sendMail(String recepient) throws Exception{

        System.out.println("preparing to send mail");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myAccount = "carpoolarg2020@gmail.com";//change accordingly
        String password = "carpoolarg2020";//change accordingly


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareMessage(session, myAccount, recepient);

        System.out.println("prepara mensaje 2");

        Transport.send(message);
        System.out.println("message sent successfully");

    }
    private static Message prepareMessage (Session session, String account, String recepient) {
        System.out.println("mandando mensaje");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Testing");
            message.setText("Hola broder");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}




