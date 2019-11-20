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

    public TicketView(Ticket ticket) {
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
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new TicketView(new Ticket("Default", "Default", 0, "Default", "Default")).setVisible(true);
        });
    }
}
