package com.searchmedicine.demo.job;
import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.email.EmailSender;
import com.searchmedicine.demo.services.ListWaiterService;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class SendNotification {

  private final ListWaiterService listWaiterService;
  private final EmailSender emailSender;


  public void sendNotification( PharmacyMedicine pharmacyMedicine){
    List<ListWaiter> listWaiters = listWaiterService.getNotification(pharmacyMedicine.getCompanyMedicine().getMedicine().getId());
    String setSubject = "Notification about medicine";
    System.out.println("*******************************************");
    System.out.println("Send notification");
    emailSender.send("kh.diana0@gmail.com",buildEmail("Diana", pharmacyMedicine.getCompanyMedicine().getMedicine(), pharmacyMedicine.getPharmacy() ), setSubject);
//    for(ListWaiter lw: listWaiters){
//      emailSender.send( lw.getUsers().getEmail(),
//          buildEmail(lw.getUsers().getFullName(), pharmacyMedicine.getCompanyMedicine().getMedicine(), pharmacyMedicine.getPharmacy()),
//          setSubject);
//    }

  }

    private String buildEmail(String name,Medicine medicine, Pharmacy pharmacy){
      return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
          "\n" +
          "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
          "\n" +
          "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
          "    <tbody><tr>\n" +
          "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
          "        \n" +
          "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
          "          <tbody><tr>\n" +
          "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
          "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
          "                  <tbody><tr>\n" +
          "                    <td style=\"padding-left:10px\">\n" +
          "                  \n" +
          "                    </td>\n" +
          "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
          "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
          "                    </td>\n" +
          "                  </tr>\n" +
          "                </tbody></table>\n" +
          "              </a>\n" +
          "            </td>\n" +
          "          </tr>\n" +
          "        </tbody></table>\n" +
          "        \n" +
          "      </td>\n" +
          "    </tr>\n" +
          "  </tbody></table>\n" +
          "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
          "    <tbody><tr>\n" +
          "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
          "      <td>\n" +
          "        \n" +
          "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
          "                  <tbody><tr>\n" +
          "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
          "                  </tr>\n" +
          "                </tbody></table>\n" +
          "        \n" +
          "      </td>\n" +
          "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
          "    </tr>\n" +
          "  </tbody></table>\n" +
          "\n" +
          "\n" +
          "\n" +
          "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
          "    <tbody><tr>\n" +
          "      <td height=\"30\"><br></td>\n" +
          "    </tr>\n" +
          "    <tr>\n" +
          "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
          "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
          "        \n" +
          "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for clicking notifications. I want to please you, <b>"+ medicine.getName() +" </b> drugs that you wanted to buy came to the <b> "+pharmacy.getName()+"</b> </p>\n <p>Address: "+pharmacy.getAddress().getRegion().getCity().getName()+", " + pharmacy.getAddress().getRegion().getName()+ ", "+ pharmacy.getAddress().getName()  + " "+ pharmacy.getAddress().getNumber() +
                        " \n Phone number: " + pharmacy.getPhoneNumber() +
                        "\n Whatsapp number: " + pharmacy.getWhatsappNumber() +
                        "\n Work time: "+ pharmacy.getWorkStartTime() + " - " + pharmacy.getWorkEndTime() +
                        "</p> \n<p>See you soon</p>" +
          "        \n" +
          "      </td>\n" +
          "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
          "    </tr>\n" +
          "    <tr>\n" +
          "      <td height=\"30\"><br></td>\n" +
          "    </tr>\n" +
          "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
          "\n" +
          "</div></div>";
    }
//    final String username = "searchimedicine@gmail.com";
//    final String password = "Botikakoz14!";
//
//    Properties prop = new Properties();
//    prop.put("mail.smtp.host", "smtp.gmail.com");
//    prop.put("mail.smtp.port", "465");
//    prop.put("mail.smtp.auth", "true");
//    prop.put("mail.smtp.socketFactory.port", "465");
//    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//    Session session = Session.getInstance(prop,
//        new javax.mail.Authenticator() {
//          protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(username, password);
//          }
//        });
//
//    try {
//
//      Message message = new MimeMessage(session);
//      message.setFrom(new InternetAddress("botaskander@gmail.com"));
//      message.setRecipients(
//          Message.RecipientType.TO,
//          InternetAddress.parse("kh.diana0@gmail.com, botaskander@gmail.com")
//      );
//      message.setSubject("Testing Gmail SSL");
//      message.setText("Dear Mail Crawler,"
//          + "\n\n Please do not spam my email!");
//
//      Transport.send(message);
//
//      System.out.println("Done");
//
//    } catch (MessagingException e) {
//      e.printStackTrace();
//    }
}

