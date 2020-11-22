package com.QM;
import java.util.Properties;
        import javax.mail.*;
        import javax.mail.internet.AddressException;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;
        import javax.naming.AuthenticationException;
        import javax.swing.*;
        import javax.mail.Authenticator;
        import javax.mail.PasswordAuthentication;


public class JavaMailSending {
    boolean authetications = false;
    public JavaMailSending(String Name, String Password) {
        // The email ID of the recipientis to be mentioned here.
        String to = Name;
        // The email ID of the recipient is to be mentioned here.
        String from = Name;
        final String username = Name; //change as per your setting
        final String password = Password; //change as per your setting
        String host;
        if(username.contains("@gmail.com"))
        {
            host = "smtp.gmail.com";
            System.out.println("This Is For Gmail!");
        }
        else {
            host = "smtp-mail.outlook.com";
            System.out.println("This Is For Outlook!");
        }
        // Taking into con sideration that you are sending the email through
        //String host = "smtp.gmail.com";
        //String host = "smtp-mail.outlook.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Getting the Session object.
        Session sesion = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        try {
            // Creation of a default MimeMessage object.
            Message msge = new MimeMessage(sesion);
            // Setting From: the header field of the header.
            msge.setFrom(new InternetAddress(from));
            // Setting To: the header field of the header.
            msge.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // Setting the Subject: the header field
            msge.setSubject("Quantum Mail Login");
            // Now, we set the actual message to be sent
            msge.setText("Hello! You have logged into Quantum Mail ");
            // Sending of the message
            Transport.send(msge);
            System.out.println("The message has been successfully sent....");
            authetications = true;
        } catch (MessagingException excp) {
            JOptionPane.showMessageDialog(null, "Error, Please Re-enter Credentials");
            throw new RuntimeException(excp);
        }

    }

}
