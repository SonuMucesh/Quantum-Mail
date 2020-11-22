package com.QM;

import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Emailfetch{
    public Emailfetch(String nameF, String passF, String WhichFolder) {
        //Set mail properties and configure accordingly
        String hostval = "pop.gmail.com";
        String mailStrProt = "pop3";
        String uname = nameF;
        String pwd = passF;
        String folder = "";
        switch (WhichFolder)
        {
            case "[Gmail]/Spam":
                folder = "[Gmail]/Spam";
                // Calling checkMail method to check received emails
                checkMail(hostval, mailStrProt, uname, pwd, folder);
                break;
            case "[Gmail]/Trash":
                folder = "[Gmail]/Trash";
                // Calling checkMail method to check received emails
                checkMail(hostval, mailStrProt, uname, pwd, folder);
                break;
            case "[Gmail]/Sent Mail":
                folder = "[Gmail]/Sent Mail";
                // Calling checkMail method to check received emails
                checkMail(hostval, mailStrProt, uname, pwd, folder);
                break;
            case "inbox":
                folder = "inbox";
                // Calling checkMail method to check received emails
                checkMail(hostval, mailStrProt, uname, pwd, folder);
                break;
        }

    }
    public static void checkMail(String hostval, String mailStrProt, String uname, String pwd,String foldertoget)
    {
        System.out.println(foldertoget);
        try {
            //Set property values
            Properties propvals = new Properties();
            propvals.load(new FileInputStream(new File("smtp.properties")));
            //propvals.put("mail.pop3.host", hostval);
            //propvals.put("mail.pop3.port", "995");
            //propvals.put("mail.pop3.starttls.enable", "true");
            Session emailSessionObj = Session.getDefaultInstance(propvals);
            //Create POP3 store object and connect with the server
            Store storeObj = emailSessionObj.getStore("imaps");
            storeObj.connect(hostval, uname, pwd);
            //Create folder object and open it in read-only mode
            Folder emailFolderObj = storeObj.getFolder(foldertoget);
            emailFolderObj.open(Folder.READ_ONLY);

            int messageCount = emailFolderObj.getMessageCount();
            System.out.println("Total Messages:- " + messageCount);

            //Fetch messages from the folder and print in a loop
            Message[] messageobjs = emailFolderObj.getMessages();

            //int i = 0, n = messageobjs.length; i < n; i++
            /* Others GMail folders :
             * [Gmail]/All Mail   This folder contains all of your Gmail messages.
             * [Gmail]/Drafts     Your drafts.
             * [Gmail]/Sent Mail  Messages you sent to other people.
             * [Gmail]/Spam       Messages marked as spam.
             * [Gmail]/Starred    Starred messages.
             * [Gmail]/Trash      Messages deleted from Gmail.
             */

            for (int i = 0; i < 10; i++) {
                Message indvidualmsg = messageobjs[i];

                //PrintWriter writer = new PrintWriter("the-file-name.eml", "UTF-8");
                String newFolderName = foldertoget.substring(0,0)+'x';
                newFolderName = newFolderName.substring(0)+"x";
                File EmailsFile = new File(newFolderName+"-"+"emails.eml");
                PrintWriter writer = new PrintWriter(new FileWriter(EmailsFile ,true));

                //writer.print("No# " + (i + 1)+",");
                //System.out.println("No# " + (i + 1));

                writer.print(indvidualmsg.getFrom()[0]+",");
                //System.out.println("Sender: " + indvidualmsg.getFrom()[0]);

                writer.print(indvidualmsg.getSubject()+",");
                //System.out.println("Email Subject: " + indvidualmsg.getSubject());

                writer.print(indvidualmsg.getContent().toString()+",");
                //System.out.println("Content: " + indvidualmsg.getContent().toString());

                //writer.println("");
                writer.close();

                    //OutputStream out = new FileOutputStream("G:\\Computer Science\\QuantumMail\\message.txt");
                    //indvidualmsg.writeTo(out);
            }

            //Now close all the objects
            emailFolderObj.close(false);
            storeObj.close();
        } catch (NoSuchProviderException exp) {
            exp.printStackTrace();
        } catch (MessagingException exp) {
            exp.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}