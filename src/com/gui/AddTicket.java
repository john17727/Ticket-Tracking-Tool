package com.gui;

import com.google.gson.Gson;
import com.mock.Ticket;
import com.mock.TicketManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class AddTicket/* extends JFrame implements ItemListener*/ extends JFrame{
    private JTextField titleEdit;
    private JPanel ticket;
    private JLabel NewTicketLabel;
    private JLabel NameLabel;
    private JTextField requesterName;
    private JLabel AssignedToLabel;
    private JTextField assignedToEdit;
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



    public AddTicket(int accessLevel, TicketManager ticketManager) {
        this.ticketManager = ticketManager;

        this.accessLevel = accessLevel;
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
    }

    private void initListeners() {
        createButton.addActionListener(ActionEvent -> {
            String title = titleEdit.getText();
            String assignTo = assignedToEdit.getText();
            String status = statusDropbox.getSelectedItem().toString();
            int priority = Integer.parseInt(priorityDropbox.getSelectedItem().toString());
            String severity = severityDropbox.getSelectedItem().toString();

            Ticket ticket = new Ticket(title, status, priority, severity, assignTo);

            String jsonTicket = ticketToJson(ticket);

            // Send to database

            if(title.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please insert a title.",
                        "Missing Title",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(assignTo.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please insert someone to assign the ticket to.",
                        "Missing Assignee",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                ticketManager.addTicket(ticket);
                dispose();
            }
        });

        cancelButton.addActionListener(ActionEvent -> {
            dispose();
        });
    }

    private String ticketToJson(Ticket ticket) {
        Gson gson = new Gson();
        return gson.toJson(ticket);
    }
}
