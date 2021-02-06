package austral.ing.lab1.repository;


import austral.ing.lab1.service.email.sendEmail;

public class sendEmailTest {

    public static void main(String[] args) throws Exception {

        sendEmail.sendConfirmationEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        sendEmail.sendDenialEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        sendEmail.sendRequestEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        sendEmail.sendPaymentEmail("numaleoneelizalde@gmail.com","El talar","Numa",500);
    }

}
