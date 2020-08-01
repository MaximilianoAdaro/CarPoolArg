package austral.ing.lab1.service.mercadoPago;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment-procedure")
public class MercadoPagoPayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            MercadoPago.SDK.setAccessToken("TEST-2308253301754618-080117-868f1a045e71b55d64963f455d1cb9bf-619140218");

            Float amount = Float.parseFloat(req.getParameter("amountToPay"));
            String tripID = req.getParameter("tripID");

            // Crea un objeto de preferencia
            Preference preference = new Preference();

            // Crea un ítem en la preferencia
            Item item = new Item().setTitle("Pay to driver +")
                    .setQuantity(1).setUnitPrice(amount);

            preference.appendItem(item);
            preference.setPayer(new Payer());
            preference.setAutoReturn(Preference.AutoReturn.approved);

            BackUrls backUrls = new BackUrls(
                    "http://localhost:8080/secure/success-buy-event?tripID=" + tripID + "&amount=" + amount,
                    "http://localhost:8080/secure/success-buy-event?tripID=" + tripID + "&amount=" + amount,
                    "http://localhost:8080/secure/home.do");

            preference.setBackUrls(backUrls);

            resp.sendRedirect(preference.save().getInitPoint());

        } catch (MPException e) {
            e.printStackTrace();
        }
    }
}
