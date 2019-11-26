package com.gui;

import java.time.LocalDateTime;
import com.controllers.ServerQuery;
import com.data.Ticket;
import com.controllers.TicketManager;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.List;

public class AddTicket extends JFrame{
    private JTextField titleEdit;
    private JPanel ticket;
    private JLabel NewTicketLabel;
    private JLabel NameLabel;
    private JTextField requesterName;
    private JLabel AssignedToLabel;
    private JComboBox AssignedToDropbox;
    private JLabel SeverityLabel;
    private JLabel PriorityLabel;
    private JLabel StatusLabel;
    private JLabel IssueLabel;
    private JComboBox severityDropbox;
    private JComboBox priorityDropbox;
    private JComboBox statusDropbox;
    private JLabel DescriptionLabel;
    private JTextArea descriptionText;
    private JTextArea resolutionText;
    private JLabel ResolutionLabel;
    private JButton cancelButton;
    private JButton createButton;
    private JPanel mainPanel;
    private static JFrame frame;
    private int accessLevel;

    private TicketManager ticketManager;
    private ServerQuery serverQuery;

    List<String> UsersListNames;

    public AddTicket(int accessLevel, TicketManager ticketManager, List<String> UsersListNames) {
        this.ticketManager = ticketManager;
        this.accessLevel = accessLevel;
        this.UsersListNames = UsersListNames;
        serverQuery = new ServerQuery();
        add(mainPanel);
        setTitle("Add Ticket");
        setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        initComboBoxes();
        initListeners();
    }

    private void initComboBoxes() {
        for(int i = 0; i < 10; i++) {
            priorityDropbox.addItem(Integer.toString(i+1));
        }

        severityDropbox.addItem("Routine");
        severityDropbox.addItem("Urgent");
        severityDropbox.addItem("Critical");

        statusDropbox.addItem("New");

        for(int i = 0; i < UsersListNames.size(); i++) {

            AssignedToDropbox.addItem(UsersListNames.get(i));
        }
    }

    private void initListeners() {
        createButton.addActionListener(ActionEvent -> {
            String title = titleEdit.getText();
            String status = statusDropbox.getSelectedItem().toString();
            int priority = Integer.parseInt(priorityDropbox.getSelectedItem().toString());
            String severity = severityDropbox.getSelectedItem().toString();
            String assignTo = AssignedToDropbox.getSelectedItem().toString();
            String client = requesterName.getText();
            String description = descriptionText.getText();
            String solution = resolutionText.getText();

            LocalDateTime dateCreated = LocalDateTime.now();
            ZonedDateTime zdt = ZonedDateTime.of(dateCreated, ZoneId.systemDefault());

            long date = zdt.toInstant().toEpochMilli();

            Ticket ticket = new Ticket(title, status, priority, severity, assignTo, client, description, solution, date);

            ticket.replaceSpaces();

            String id = serverQuery.addTicketToServer(ticket);

            if(title.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please insert a title.",
                        "Missing Title",
                        JOptionPane.WARNING_MESSAGE);
            } else if(assignTo.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please insert someone to assign the ticket to.",
                        "Missing Assignee",
                        JOptionPane.WARNING_MESSAGE);
            } else if(id.equals("-1")) {
                JOptionPane.showMessageDialog(frame,
                        "Server failed to save ticket.",
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if(id.equals("-2")) {
                JOptionPane.showMessageDialog(frame,
                        "Connection to server failed.",
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ticket.setId(id);
                ticket.replaceUnderscores();
                ticketManager.addTicket(ticket);
                dispose();
            }
        });

        cancelButton.addActionListener(ActionEvent -> {
            dispose();
        });
    }
}
