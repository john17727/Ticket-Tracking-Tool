package com.gui;

import javax.swing.*;
import java.awt.*;

public class AddTicket/* extends JFrame implements ItemListener*/ extends JFrame{
    private JTextField Issue;
    private JPanel ticket;
    private JLabel NewTicketLabel;
    private JLabel NameLabel;
    private JTextField RequesterName;
    private JLabel AssignedToLabel;
    private JTextField AssignedTo;
    private JLabel SeverityLabel;
    private JLabel PriorityLabel;
    private JLabel StatusLabel;
    private JLabel IssueLabel;
    private JComboBox severityDropbox;
    private JComboBox priorityDropbox;
    private JComboBox statusDropbox;
    private JLabel DescriptionLabel;
    private JTextArea DescriptionText;
    private JTextArea ResolutionText;
    private JLabel ResolutionLabel;
    private JButton CancelButton;
    private JButton CreateButton;
    private JPanel mainPanel;
    private static JFrame frame;

    public AddTicket() {
        add(mainPanel);
        setTitle("Add Ticket");
        setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        initComboBoxes();
    }

    private void initComboBoxes() {
        for(int i = 0; i < 10; i++) {
            priorityDropbox.addItem(Integer.toString(i+1));
        }

        severityDropbox.addItem("Routine");
        severityDropbox.addItem("Urgent");
        severityDropbox.addItem("Critical");
    }
}
