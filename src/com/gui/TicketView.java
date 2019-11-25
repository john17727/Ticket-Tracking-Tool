package com.gui;

import com.mock.ServerQuery;
import com.mock.Ticket;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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
    private JLabel openDateLabel;
    private JLabel daysOpenLabel;
    private JLabel closedDateLabel;

    private static int accessLevel;
    private Ticket ticket;
    private ServerQuery serverQuery;
    private String[] lvl0 = {"New", "Fixed", "Open","Rejected", "Closed"};
    private String[] lvl1 = {"Rejected" , "Closed"};//set to open if new
    private String[] lvl2 = {"New", "Fixed", "Open",};

    public TicketView(Ticket ticket, int accessLevel) {
        this.ticket = ticket;
        this.accessLevel = accessLevel;
        serverQuery = new ServerQuery();
        add(mainPanel);
        setTitle(ticket.getTitle());
        setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);


        initListeners();
        setInfo(ticket);
    }

    public void setInfo(Ticket ticket) {
        titleEdit.setText(ticket.getTitle());
        descEdit.setText(ticket.getDescription());
        resEdit.setText(ticket.getSolution());
        clientEdit.setText(ticket.getClient());
        priorityBox.addItem(ticket.getPriority());
        severityBox.addItem(ticket.getSeverity());
        assignBox.addItem(ticket.getAssignedTo());
        statusBox.addItem(ticket.getStatus());

        long dateLong = ticket.getDate();
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateLong), ZoneId.systemDefault());

        openDateLabel.setText(date.toLocalDate().toString());
        LocalDateTime now = LocalDateTime.now();
        daysOpenLabel.setText(String.valueOf(ChronoUnit.DAYS.between(date.toLocalDate(), now.toLocalDate())));

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

    private void initListeners() {
        saveButton.addActionListener(ActiveEvent -> {
            String title = titleEdit.getText();
            String status = statusBox.getSelectedItem().toString();
            int priority = Integer.parseInt(priorityBox.getSelectedItem().toString());
            String severity = severityBox.getSelectedItem().toString();
            String assignTo = assignBox.getSelectedItem().toString();
            String client = clientEdit.getText();
            String description = descEdit.getText();
            String solution = resEdit.getText();

            ticket.setTitle(title);
            ticket.setStatus(status);
            ticket.setPriority(priority);
            ticket.setSeverity(severity);
            ticket.setAssignedTo(assignTo);
            ticket.setClient(client);
            ticket.setDescription(description);
            ticket.setSolution(solution);

            ticket.replaceSpaces();

            String result = serverQuery.updateTicketToServer(ticket);

            ticket.replaceUnderscores();

            dispose();
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new TicketView(new Ticket("Default", "Default", 0, "Default", "Default", "Default", "Default", "Default", 0), accessLevel).setVisible(true);
        });
    }
}
