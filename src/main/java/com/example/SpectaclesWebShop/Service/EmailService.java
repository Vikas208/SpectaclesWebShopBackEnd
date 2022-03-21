package com.example.SpectaclesWebShop.Service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Bean.Order;
import com.example.SpectaclesWebShop.Bean.OrderAddress;
import com.example.SpectaclesWebShop.Bean.OrderPayment;
import com.example.SpectaclesWebShop.Bean.OrderedProducts;
import com.example.SpectaclesWebShop.Bean.ProductDescription;
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
                            + "<br/>Note:- Otp Valid For 5 minutes<br/><br/>with regards<br/>Spectacles web shop team");
            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

            StringBuilder body = new StringBuilder();

            body.append("<html><body>");
            body.append("<h3>Spectacles Web Shop</h3>");
            body.append(
                    "<span>Name:" + userDetails.getName() + "<br />Mail Id: " + userDetails.getMailId() + "</span>");
            body.append("<br /><span>Adress:" + orderAddress.getAddress1() + "," + orderAddress.getAddress2() + ","
                    + orderAddress.getCity() + "," + orderAddress.getCity() + "-" + orderAddress.getPincode()
                    + "</span>");
            body.append("<br /><span>PhoneNumber: " + orderAddress.getPhonenumber() + "</span>");
            body.append("<br /><span>Order Date: " + order.getLocalDateTime() + "</span>");
            body.append("<br /><span>Order Status: " + order.getOrder_status() + "</span>");
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

                body.append("<tr><td style='border: 1px solid;'>"
                        + orderedProducts2.getProducts().getP_name() + productDetails
                        + OrderproductDetails + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getQty() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getProducts().getP_price() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getSale() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getGlassPrice() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getGst() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getOtherTax() + "</td>");
                body.append("<td style='border: 1px solid;'>" + orderedProducts2.getTotalPrice() + "</td></tr>");
            }
            body.append(
                    "<tr><td style='border: 1px solid;' colspan='7'>" + orderPayment.getTotal_amount() + "</td></tr>");
            body.append("</tbody><tr></tr></table>");
            body.append("<span>Payment Type:" + orderPayment.getPayment_type() + "</span>");
            body.append("<span>Payment Status:" + orderPayment.isPayment_status() + "</span>");
            body.append("</body></html>");

            helper.setText(body.toString(), true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
