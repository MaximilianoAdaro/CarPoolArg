package austral.ing.lab1.repository;


import austral.ing.lab1.service.email.SendEmailService;

public class sendEmailTest {

    public static void main(String[] args) throws Exception {

        SendEmailService.sendConfirmationEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        SendEmailService.sendDenialEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        SendEmailService.sendRequestEmail("numaleoneelizalde@gmail.com","El talar","Numa");
        SendEmailService.sendPaymentEmail("numaleoneelizalde@gmail.com","El talar","Numa",500);
    }

}
