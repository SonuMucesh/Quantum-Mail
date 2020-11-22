package com.QM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.UIManager.*;
import javax.xml.bind.ValidationException;

public class LoginScreen {
    public JPanel MainForm;
    private JButton loginButton;
    public JFormattedTextField EmailInput;
    public JPasswordField PasswordInput;
    private String Email_Input = "";
    private String Password_Input = "";
    JFrame frame1 = new JFrame("Quantum Mail");
    JFrame frame2 = new JFrame("Main Screen");

    public static void main(String[] args) {


        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        //Creates The Login Screen window and ive called it Quantum Mail
        JFrame frame1 = new JFrame("Quantum Mail");
        //What the window will look like is determined by how the form is made to look like which is a different file
        frame1.setContentPane(new LoginScreen().MainForm);
        //The widow will close when the user presses the close button
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Makes everything required to create the window
        frame1.pack();
        //The window shows up on the screen for the user to use
        frame1.setVisible(true);

    }

    public LoginScreen() {

        //Creates a listener which will retrieve the email that the user has entered
        EmailInput.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                //The variable that will store the users email until login
                Email_Input = EmailInput.getText();
                //Calls the class UsernameClass where the email is checked for validation
                UsernameClass name = new UsernameClass(Email_Input);
                boolean LogicNamerCheckerIf = name.LogicName;
            }
        });

        //Creates a listener which will retrieve the password that the user has entered
        PasswordInput.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                //The variable that will store the users password until login
                Password_Input = PasswordInput.getText();
                //Calls the class PasswordClass where the email is checked for validation such as empty spot
                PasswordClass name = new PasswordClass(Password_Input);
                boolean LogicPasswordCheckerIf = name.PasswordLogic;
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            //What happens when they press the login button
            public void actionPerformed(ActionEvent e) {
                //Creates Username object to get email
                UsernameClass OBJ = new UsernameClass(Email_Input);
                //Creates password object to get password
                PasswordClass OBJ2 = new PasswordClass(Password_Input);
                //Creates JavamailSending object to send a email telling user they have logged in
                //using the JavaMailSending Class
                boolean logicname1 = OBJ.LogicName;
                boolean logicpass2 = OBJ2.PasswordLogic;
                //If it cannot login to email then the credentials are wrong so sends an error message
                if (logicname1 && logicpass2 == false) {
                    JOptionPane.showMessageDialog(null, "Error, Please Re-enter Credentials");
                }
                JavaMailSending NAP = new JavaMailSending(Email_Input, Password_Input);
                boolean authenticatorLS = NAP.authetications;
                //If logged in to email and account then the following happens
                if (authenticatorLS == true) {
                    JOptionPane.showMessageDialog(null, "Welcome " + Email_Input.toUpperCase());
                    Emailfetch PAP = new Emailfetch(Email_Input, Password_Input,"inbox");
                    //closes the login screen
                    frame1.setVisible(false);
                    frame1.dispose();
                    //Creates the main screen window
                    JFrame frame2 = new JFrame("Quantum Mail Main Screen");
                    frame2.setContentPane(new MainEmailScreen().MainEmailScreen);
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame2.pack();
                    frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame2.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Error, Please Re-enter Credentials");
                }
            }
        });

    }

    public String getMyField() {
        return this.Email_Input;

    }

    public String getMyField2() {
        return this.Password_Input;
    }

    public void setMyField(String value, String value2) {
        //include more logic
        this.Email_Input = value;
        this.Password_Input = value2;
    }
}

