package austral.ing.lab1.repository;


import austral.ing.lab1.service.email.SendEmailService;

class SendEmailTest {

    public static void main(String[] args) throws Exception {
        String email = "maxiadaro1999@gmail.com";
        SendEmailService.sendConfirmationEmail(email,"El talar","Numa");
        SendEmailService.sendDenialEmail(email,"El talar","Numa");
        SendEmailService.sendRequestEmail(email,"El talar","Numa");
        SendEmailService.sendPaymentEmail(email,"El talar","Numa",500);
    }

}
