package com.gui;

import javax.swing.*;
import java.awt.*;

public class TicketView extends JFrame{
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextArea textArea2;
    private JButton closeButton;
    private JButton rejectButton;
    private JButton saveButton;
    private JButton resolvedButton;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JPanel mainPanel;

    public TicketView() {
        add(mainPanel);
        setTitle("Add New Ticket");
        setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new TicketView().setVisible(true);
        });
    }
}
