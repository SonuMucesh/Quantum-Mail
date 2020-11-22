package com.QM;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.UIManager.*;

import java.io.FileNotFoundException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.mail.internet.AddressException;
import javax.naming.AuthenticationException;
import javax.mail.Authenticator;

import static com.sun.deploy.util.BufferUtil.MB;


public class SendEmailScreen {
    private boolean screen;
    public JPanel SendScreen;
    private JFormattedTextField CC;
    private JButton sendButton;
    private JFormattedTextField EmailAddress;
    private JTextPane Message;
    private JFormattedTextField Subject;
    private JButton attachmentsButton;
    private String EmailAddressSendTo = "";
    private String CCText = "";
    private String SubjectText = "";
    private String EmailText = "";
    private String attachementLocation = "";


    public SendEmailScreen() {

        try {//This is makes the window look more attractive
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        //For the person to send to field.
        EmailAddress.addFocusListener(new FocusAdapter() {
            //Takes The What the user put for the email
            //address that the email needs to be sent to store it in a variable
            @Override
            public void focusLost(FocusEvent e) {
                EmailAddressSendTo = EmailAddress.getText();
            }
        });
        //For the CC to send to field.
        CC.addFocusListener(new FocusAdapter() {
            //Takes The What the user put for the CC
            //address that the email needs to be sent to store it in a variable
            @Override
            public void focusLost(FocusEvent e) {
                CCText = CC.getText();
            }
        });
        //For the subject to use in the email field.
        Subject.addFocusListener(new FocusAdapter() {
            //Takes The What the user put for the subject
            //address that the email needs to be sent to store it in a variable
            @Override
            public void focusLost(FocusEvent e) {
                SubjectText = Subject.getText();
            }
        });
        //The actual message the user types into the email
        Message.addFocusListener(new FocusAdapter() {
            //Takes The What the user put for the email
            //address that the email needs to be sent to store it in a message
            @Override
            public void focusLost(FocusEvent e) {
                EmailText = Message.getText();
            }
        });
        //file browser opens and user chooses file this selects it.
        attachmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //opens file browser with home directory
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    attachementLocation = selectedFile.getAbsolutePath();
                    //just for debug
                    System.out.println(selectedFile.getAbsolutePath());
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recipient's email ID needs to be mentioned.
                String to = EmailAddressSendTo;
                // Sender's email ID needs to be mentioned
                LoginScreen OB = new LoginScreen();
                String from = OB.EmailInput.getText();
                String password = OB.PasswordInput.getText();
                // Assuming you are sending email from localhost
                String host;
                if(EmailAddressSendTo.contains("@gmail.com"))
                {
                    host = "smtp.gmail.com";
                    System.out.println("This Is For Gmail!");
                }
                else {
                    host = "smtp-mail.outlook.com";
                    System.out.println("This Is For Outlook!");
                    System.out.println(from);
                    System.out.println(password);
                }
                // Get system properties
                Properties properties = System.getProperties();
                // Setup mail server
                properties.setProperty("smtp.gmail.com", host);
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(from, password);
                            }
                        });

                try {
                    // Create a default MimeMessage object.
                    Message message = new MimeMessage(session);
                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));
                    // Set To: header field of the header.
                    message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
                    // Set Subject: header field
                    message.setSubject(SubjectText);
                    // Create the message part
                    BodyPart messageBodyPart = new MimeBodyPart();
                    // Now set the actual message
                    messageBodyPart.setText(EmailText);
                    // Create a multipart message
                    Multipart multipart = new MimeMultipart();
                    // Set text message part
                    multipart.addBodyPart(messageBodyPart);
                    // attachment
                    messageBodyPart = new MimeBodyPart();
                    String filename = attachementLocation;
                    if(filename == null)
                    {
                        JOptionPane.showMessageDialog(null, "Email Was Not Sent " + " File Not Found!");
                    }else {
                        filename = attachementLocation;
                    }
                    if(attachementLocation.length()>=10000000){
                        JOptionPane.showMessageDialog(null,"Attachment Too Big");
                    }else {
                        attachementLocation = attachementLocation;
                    }
                    DataSource source = new FileDataSource(filename);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(filename);
                    multipart.addBodyPart(messageBodyPart);

                    // Send the complete message parts
                    message.setContent(multipart);

                    // Send message
                    Transport.send(message);
                    JOptionPane.showMessageDialog(null,"Email Was Sent");
                    System.out.println("Sent message successfully....");


                } catch (SendFailedException e1) {
                    JOptionPane.showMessageDialog(null, "Email Was Not Sent " +
                            "Please Try again!");
                } catch (MessagingException excp) {
                    throw new RuntimeException(excp);
                }
            }
        });

    }
}