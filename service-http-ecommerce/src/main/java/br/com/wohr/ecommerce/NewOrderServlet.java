package br.com.wohr.ecommerce;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
    private final KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void destroy() {
        orderDispatcher.close();
        emailDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //http://localhost:8080/new?email=willian@email.com&amount=5153
        try {
            // We are not caring any security issues, we are only showing how to use http as a starting point
            var email = req.getParameter("email");
            var orderId = UUID.randomUUID().toString();
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            var order = new Order(orderId, email, amount);
            orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);

            var emailCode = new Email("Send email", "Thank you for your order! We are processing your order!");
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);

            System.out.println("New order sent successfully");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("New order sent successfully");
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new ServletException(e);
        }
    }
}
