package com.gui;

import com.mock.ServerQuery;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainWindow extends JFrame {

    private JTextField searchTextField;
    private JTable ticketTable;
    private JCheckBox a1CheckBox;
    private JCheckBox a2CheckBox;
    private JCheckBox a3CheckBox;
    private JButton addTicketButton;
    private JCheckBox a4CheckBox;
    private JCheckBox a5CheckBox;
    private JCheckBox a6CheckBox;
    private JCheckBox a7CheckBox;
    private JCheckBox a8CheckBox;
    private JCheckBox a9CheckBox;
    private JCheckBox a10CheckBox;
    private JCheckBox criticalCheckBox;
    private JCheckBox newCheckBox;
    private JCheckBox openCheckBox;
    private JCheckBox closedCheckBox;
    private JCheckBox routineCheckBox;
    private JCheckBox urgentCheckBox;
    private JPanel mainPanel;
    private JScrollPane tableScrollPane;
    private JPanel tablePanel;
    private JSplitPane splitPane;
    private JLabel priorityLabel;
    private JButton searchButton;
    private ServerQuery serverQuery;

    private String[] header;

    public MainWindow() {
        add(mainPanel);
        setTitle("Ticket Manager");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        serverQuery = new ServerQuery();

        initTable();
        initListeners();
    }

    // Initializes table with default values
    private void initTable() {
        header = new String[]{"Title", "Status", "Priority", "Severity", "Assigned To"};

        String[][] data = serverQuery.getDefault();
        showTable(data);

    }

    // Creates table based on data model
    private void showTable(String[][] dataModel) {
        ticketTable = new JTable(dataModel, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
        ticketTable.setFillsViewportHeight(true);
        ticketTable.setRowHeight(25);
        ticketTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        ticketTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableScrollPane.setViewportView(ticketTable);
    }

    // Initializes all listeners of the window
    private void initListeners() {
        addTicketButton.addActionListener(actionEvent -> {
            new AddTicket().setVisible(true);
        });

        ticketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    new TicketView().setVisible(true);
                }
            }
        });

        searchButton.addActionListener(actionEvent -> {
            String query = searchTextField.getText();
            if(!query.isEmpty()) {
                String[][] searchData = serverQuery.getSearchResults(query);
                showTable(searchData);
            } else {
                initTable();
            }
        });
    }
}
