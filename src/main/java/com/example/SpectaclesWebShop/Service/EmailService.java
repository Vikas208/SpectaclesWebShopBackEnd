package com.example.SpectaclesWebShop.Service;

import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import com.example.SpectaclesWebShop.Bean.*;
import com.example.SpectaclesWebShop.Info.TableName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String cancelationReason;

    public String getAdminMailId() {
        try {
            String query = "SELECT MAILID FROM " + TableName.LOGIN_TABLE + " WHERE HASROLE='admin'";
            return jdbcTemplate.queryForObject(query, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shreechasamaghar263@gmail.com";
    }

    public boolean sendMail(String mail, int otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String adminMailId = getAdminMailId();
            message.setFrom(adminMailId);
            message.setTo(mail);
            message.setSubject("Spectacles Web Shop");
            message.setText(
                    "Otp is : " + otp
                            + "Note:- Otp Valid For 5 minutes\n\nwith regards\nSpectacles web shop team");
            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public StringBuilder getOrderDetailsBody(Order order, OrderAddress orderAddress, OrderPayment orderPayment,
            List<OrderedProducts> orderedProducts, Login userDetails) {
        try {
            StringBuilder body = new StringBuilder();

            body.append("<html><body>");
            body.append("<h3>Spectacles Web Shop</h3>");
            body.append("<span>Name:").append(userDetails.getName()).append("<br />Mail Id: ")
                    .append(userDetails.getMailId()).append("</span>");
            body.append("<br /><span>Adress:").append(orderAddress.getAddress1()).append(",")
                    .append(orderAddress.getAddress2()).append(",").append(orderAddress.getCity()).append(",")
                    .append(orderAddress.getCity()).append("-").append(orderAddress.getPincode()).append("</span>");
            body.append("<br /><span>PhoneNumber: ").append(orderAddress.getPhonenumber()).append("</span>");
            body.append("<br /><span>Order Date: ").append(order.getLocalDateTime()).append("</span>");
            body.append("<br /><span>Order Status: ").append(order.getOrder_status()).append("</span>");
            body.append(
                    "<table style='border:2px solid black;border-collapse: collapse;'><thead style='  background-color: black;color: white; border: 1px solid;'><td style=' border: 1px solid;'>Product</td><td style=' border: 1px solid;'>Qty</td><td style=' border: 1px solid;'>Price</td><td style=' border: 1px solid;'>Sale</td><td style=' border: 1px solid;'>GlassPrice</td><td style='border: 1px solid;'>gst</td><td style=' border: 1px solid;'>Other Tax</td><td style='border: 1px solid;'>Total Price</td></thead><tbody>");
            for (int i = 0; i < orderedProducts.size(); ++i) {
                OrderedProducts orderedProducts2 = orderedProducts.get(i);
                String onlyframe = orderedProducts2.isOnlyframe() ? "YES" : "NO";
                String OrderproductDetails = "<br/>OnlyFrame: " + onlyframe + "<br/>Left_eye_no: "
                        + orderedProducts2.getLeft_eye_no() + " Right_eye_no: " + orderedProducts2.getRight_eye_no()
                        + "<br/>GlassType: " + orderedProducts2.getGlassType();

                ProductDescription productDescription = orderedProducts2.getProducts().getProductDescription();
                String productDetails = "<br/>Company Name: " + productDescription.getCompany_name() + "<br/>Category: "
                        + productDescription.getP_category() + " Group: " + productDescription.getP_group()
                        + "<br/>FrameStyle: " + productDescription.getP_frameStyle() + " FrameSize: "
                        + productDescription.getP_frameSize() + "<br/>Color: " + productDescription.getColor();

                body.append("<tr><td style='border: 1px solid;'>").append(orderedProducts2.getProducts().getP_name())
                        .append(productDetails).append(OrderproductDetails).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getQty()).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getProducts().getP_price())
                        .append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getSale()).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getGlassPrice()).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getGst()).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getOtherTax()).append("</td>");
                body.append("<td style='border: 1px solid;'>").append(orderedProducts2.getTotalPrice())
                        .append("</td></tr>");
            }
            body.append("<tr><td style='border: 1px solid;' colspan='7'>").append(orderPayment.getTotal_amount())
                    .append("</td></tr>");
            body.append("</tbody><tr></tr></table>");
            body.append("<span>Payment Type:").append(orderPayment.getPayment_type()).append("</span>")
                    .append("<br />");
            body.append("<span>Payment Status:").append(orderPayment.isPayment_status()).append("</span>")
                    .append("<br />");

            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }

    public boolean sendInvoiceMail(Order order, OrderAddress orderAddress, OrderPayment orderPayment,
            List<OrderedProducts> orderedProducts, Login userDetails) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String adminMailId = getAdminMailId();

            helper.setFrom(adminMailId);
            helper.setTo(userDetails.getMailId());
            helper.setSubject("#Order Invoice");
            StringBuilder body = getOrderDetailsBody(order, orderAddress, orderPayment, orderedProducts, userDetails);
            body.append("</body></html>");
            helper.setText(body.toString(), true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendCancelOrderMail(Order order, OrderAddress orderAddress, OrderPayment orderPayment,
                                       List<OrderedProducts> orderedProducts, Login userDetails, String cancelationReason) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String adminMailId = getAdminMailId();

            helper.setFrom(adminMailId);
            helper.setTo(userDetails.getMailId());
            helper.setSubject("#Order Cancel");
            StringBuilder body = getOrderDetailsBody(order, orderAddress, orderPayment, orderedProducts, userDetails);
            body.append("<br />").append("<span style='color:red;'>CancellationReason: ").append(cancelationReason)
                    .append("</span>");
            body.append("</body></html>");
            helper.setText(body.toString(), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendDeleteReviews(HashMap<String, Object> feedBack, String reason) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String adminMailId = getAdminMailId();

            helper.setFrom(adminMailId);
            FeedBack feedBack1 = (FeedBack) feedBack.get("feedBack");
            Products products = (Products) feedBack.get("product");
            helper.setTo(feedBack1.getUser());
            helper.setSubject("#Review Delete");
            StringBuilder body = new StringBuilder();
            body.append("<html><body>").append("<h3>Spectacles Web Shop</h3>").append("<p> Your ReView is Deleted From Our Site on Product ").append(products.getP_name()).append("</p>").append("<p>Your Review was").append(feedBack1.getFeedBack()).append("On Date: ").append(feedBack1.getTime()).append(".This was Delete For Following Reason: ").append(reason).append("</p>").append("</body></html>");
            helper.setText(body.toString());
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
