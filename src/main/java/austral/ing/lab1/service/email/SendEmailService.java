package austral.ing.lab1.service.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailService {

    public static void sendMail(String recipient, String messageText) throws Exception {

        System.out.println("preparing to send mail");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myAccount = "carpoolarg2020@gmail.com";
        String password = "carpoolarg2020";


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareMessage(session, myAccount, recipient, messageText);

        assert message != null;
        Transport.send(message);
        System.out.println("Message sent successfully");

    }

    private static Message prepareMessage(Session session, String account, String recipient, String messageText) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Carpool Notification");
            message.setText(messageText);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendConfirmationEmail(String recipient, String place, String driver) throws Exception {
        String text = "You have been accepted by " + driver + " to a trip to " + place + ".";
        sendMail(recipient, text);
    }

    public static void sendDenialEmail(String recipient, String place, String driver) throws Exception {
        String text = "You have been rejected by " + driver + " to a trip to " + place + ".";
        sendMail(recipient, text);
    }

    public static void sendPaymentEmail(String recipient, String place, String passenger, int money) throws Exception {
        String text = "You have been payed $" + money + " by " + passenger + " from your trip to " + place + ".";
        sendMail(recipient, text);
    }

    public static void sendRequestEmail(String recipient, String place, String passenger) throws Exception {
        String text = passenger + " has requested to join your trip to " + place + ".";
        sendMail(recipient, text);
    }
}




