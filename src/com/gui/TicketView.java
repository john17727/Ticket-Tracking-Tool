package com.gui;

import com.mock.Ticket;

import javax.swing.*;
import java.awt.*;

public class TicketView extends JFrame{
    private JTextArea descEdit;
    private JTextField titleEdit;
    private JTextField clientEdit;
    private JComboBox priorityBox;
    private JComboBox severityBox;
    private JTextArea resEdit;
    private JButton closeButton;
    private JButton rejectButton;
    private JButton saveButton;
    private JButton resolvedButton;
    private JComboBox assignBox;
    private JComboBox statusBox;
    private JPanel mainPanel;
    private static int accessLevel;
    private String[] lvl0 = {"New", "Fixed", "Open","Rejected", "Closed"};
    private String[] lvl1 = {"Rejected" , "Closed"};//set to open if new
    private String[] lvl2 = {"New", "Fixed", "Open",};

    public TicketView(Ticket ticket, int accessLevel) {
        this.accessLevel = accessLevel;
        add(mainPanel);
        setTitle("Add New Ticket");
        setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        setInfo(ticket);
    }

    public void setInfo(Ticket ticket) {
        titleEdit.setText(ticket.getTitle());
        descEdit.setText(ticket.getDescription());
        clientEdit.setText(ticket.getClient());
        priorityBox.addItem(ticket.getPriority());
        severityBox.addItem(ticket.getSeverity());
        assignBox.addItem(ticket.getAssignedTo());
        statusBox.addItem(ticket.getStatus());

        if(accessLevel == 0){
            for(int i = 0; i < lvl0.length; i++){

                if(!ticket.getStatus().equals(lvl0[i])){statusBox.addItem(lvl0[i]);}
            }
        }

        if(accessLevel == 1){

            if(ticket.getStatus().equals("New")){statusBox.addItem("Open");}

            for(int i = 0; i < lvl1.length; i++){

                if(!ticket.getStatus().equals(lvl1[i])){statusBox.addItem(lvl1[i]);}
            }

            for(int i = 0; i <= 10; i++){

                if(ticket.getPriority() != i){priorityBox.addItem(i);}
            }
        }

        if(accessLevel == 2){
            for(int i = 0; i < lvl2.length; i++){

                if(!ticket.getStatus().equals(lvl2[i])){statusBox.addItem(lvl2[i]);}
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new TicketView(new Ticket("Default", "Default", 0, "Default", "Default"), accessLevel).setVisible(true);
        });
    }
}
