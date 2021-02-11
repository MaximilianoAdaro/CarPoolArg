package austral.ing.lab1.repository;


import austral.ing.lab1.service.email.SendEmailService;

public class SendEmailTest {

    public static void main(String[] args) throws Exception {
        String email = "numaleoneelizalde@gmail.com";
        SendEmailService.sendConfirmationEmail(email,"El talar","Numa");
        SendEmailService.sendDenialEmail(email,"El talar","Numa");
        SendEmailService.sendRequestEmail(email,"El talar","Numa");
        SendEmailService.sendPaymentEmail(email,"El talar","Numa",500);
    }

}
