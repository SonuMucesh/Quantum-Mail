package com.QM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.UIManager.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MainEmailScreen {
    public JPanel MainEmailScreen;
    private JButton createNewEmailButton;
    private JButton downloadNewEmailsButton;
    private JButton searchButton;
    private JFormattedTextField QuerySearch;
    private JTable Emails;
    private JButton inboxButton;
    private JButton sentButton;
    private JButton binButton;
    private JButton spamButton;
    private JScrollPane ScrollingTable;
    private JPanel FoldersAndTable;
    private JPanel LiveDateAndTime;
    private JLabel TimeLabel;
    private JButton DeleteEmail;

    public MainEmailScreen() {

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

        createNewEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame3 = new JFrame("Sending Mail....");
                frame3.setContentPane(new SendEmailScreen().SendScreen);
                frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame3.pack();
                frame3.setVisible(true);
            }
        });

        String columns[] = {"From", "Email Subject", "Message"};
        DefaultTableModel tableModel;
        String internalfolder = "";

        // table with 4 columns
        tableModel = new DefaultTableModel(0, 3);
        tableModel.setColumnIdentifiers(columns);
        Emails.setModel(tableModel);
        Emails.scrollRectToVisible(Emails.getCellRect(Emails.getRowCount() - 1, 0, true));
        //new JScrollPane(Emails, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //JScrollPane(Emails);
        final String[] line = new String[1];
        final BufferedReader[] reader = new BufferedReader[1];

        try{
            reader[0] = new BufferedReader(new FileReader("inbox"+"-"+"emails.eml"));

            while((line[0] = reader[0].readLine()) != null) //this gives me an error???
            {
                tableModel.addRow(line[0].split(",")); //this has a comma and a space bc that is how the file is written to
            }
            reader[0].close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
        }

            downloadNewEmailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        tableModel.setRowCount(0);
                        LoginScreen OB1 = new LoginScreen();
                        Emailfetch NE = new Emailfetch("EMAIL ADDRESS", "PASSWORD",internalfolder);
                        System.out.println("1:" + OB1.getMyField() + "2:" + OB1.getMyField2());
                        reader[0] = new BufferedReader(new FileReader("inbox"+"-"+"emails.eml"));

                        while((line[0] = reader[0].readLine()) != null) //this gives me an error???
                        {
                            tableModel.addRow(line[0].split(",")); //this has a comma and a space bc that is how the file is written to
                        }
                        reader[0].close();
                    } catch (IOException xe) {
                        JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
                    }
                    tableModel.fireTableDataChanged();
                }
            });
            ScrollingTable.addComponentListener(new ComponentAdapter() {
            });

        inboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableModel.setRowCount(0);
                    LoginScreen OB1 = new LoginScreen();
                    Emailfetch NE = new Emailfetch("EMAIL ADDRESS", "PASSWORD","inbox");
                    System.out.println("1:" + OB1.getMyField() + "2:" + OB1.getMyField2());
                    reader[0] = new BufferedReader(new FileReader("inbox"+"-"+"emails.eml"));

                    while((line[0] = reader[0].readLine()) != null) //this gives me an error???
                    {
                        tableModel.addRow(line[0].split(",")); //this has a comma and a space bc that is how the file is written to
                    }
                    reader[0].close();
                } catch (IOException xe) {
                    JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
                }
                tableModel.fireTableDataChanged();
            }
        });


        sentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableModel.setRowCount(0);
                    LoginScreen OB1 = new LoginScreen();
                    Emailfetch NE = new Emailfetch("EMAIL ADDRESS", "PASSWORD","[Gmail]/Sent Mail");
                    System.out.println("1:" + OB1.getMyField() + "2:" + OB1.getMyField2());
                    reader[0] = new BufferedReader(new FileReader("xx-emails.eml"));

                    while((line[0] = reader[0].readLine()) != null) //this gives me an error???
                    {
                        tableModel.addRow(line[0].split(",")); //this has a comma and a space bc that is how the file is written to
                    }
                    reader[0].close();
                } catch (IOException xe) {
                    JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
                }
                tableModel.fireTableDataChanged();
            }
        });

        //When the bin button is clicked
        binButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Clears the email table
                    tableModel.setRowCount(0);
                    //Creates a object to access the username and password
                    LoginScreen OB1 = new LoginScreen();
                    Emailfetch NE = new Emailfetch("EMAIL ADDRESS", "PASSWORD","[Gmail]/Trash");
                    System.out.println("1:" + OB1.getMyField() + "2:" + OB1.getMyField2());
                    //reads the newly downloaded file specific to this folder
                    reader[0] = new BufferedReader(new FileReader("[Gmail]/Trash"+"-"+"emails.eml"));

                    while((line[0] = reader[0].readLine()) != null)
                    {
                        //whenever there is a comma that a new peice of data for the table
                        tableModel.addRow(line[0].split(",")); //this has a comma and a space bc that is how the file is written to
                    }
                    reader[0].close();
                    //incase its unable to read the file it outputs an error
                } catch (IOException xe) {
                    JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
                }
                //Adds the data into the table
                tableModel.fireTableDataChanged();
            }

        });

        //When the spam button is clicked
        spamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        //Clears the email table
                        tableModel.setRowCount(0);
                        //Creates a object to access the username and password
                        LoginScreen OB1 = new LoginScreen();
                        //Calls the email downloader function
                        Emailfetch NE = new Emailfetch("EMAIL ADDRESS", "PASSWORD","[Gmail]/Spam");
                        System.out.println("1:" + OB1.getMyField() + "2:" + OB1.getMyField2());
                        //reads the newly downloaded file specific to this folder
                        reader[0] = new BufferedReader(new FileReader("[Gmail]/Spam"+"-"+"emails.eml"));

                        while((line[0] = reader[0].readLine()) != null)
                        {
                            //whenever there is a comma that a new peice of data for the table
                            tableModel.addRow(line[0].split(","));
                        }
                        reader[0].close();
                        //incase its unable to read the file it outputs an error
                    } catch (IOException xe) {
                        JOptionPane.showMessageDialog(null, "Buffered Reader issue.");
                    }
                    //Adds the data into the table
                    tableModel.fireTableDataChanged();
                }
        });
        //Searching Through Table
        QuerySearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String QueryInput = QuerySearch.getText();
                TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(tableModel);
                Emails.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(QueryInput));
            }
        });
        //Displaying Time And Updating it
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeLabel.setText(DateFormat.getDateTimeInstance().format(new Date()));;
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        DeleteEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int SelectedRowIndex = Emails.getSelectedRow();
                try {
                    tableModel.removeRow(SelectedRowIndex);
                    JOptionPane.showMessageDialog(null,"Email Deleted");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Cannot Delete Email");
                }
            }
        });
    }

}





