package com.gui;

import com.mock.ServerQuery;
import com.mock.Ticket;
import com.mock.TicketTableItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private JTextField searchTextField;
    private JTable ticketTable;
    private JButton addTicketButton;
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
    private JComboBox priorityDropdownList;

    private String[] header;
    private TicketTableItem model;
    private int accessLevel;

    public MainWindow(int accessLevel) {
        this.accessLevel = accessLevel;
        add(mainPanel);
        setTitle("Ticket Manager");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        getRootPane().setDefaultButton(searchButton);

        serverQuery = new ServerQuery();

        System.out.println(this.accessLevel);

        initTable();
        initButtons();
        initListeners();
    }

    // Initializes table with default values
    private void initTable() {
        header = new String[]{"Title", "Status", "Priority", "Severity", "Assigned To"};

        //String[][] data = serverQuery.getDefault();
        List<Ticket> data = new ArrayList<Ticket>(serverQuery.getDefault());
        showTable(data);

    }

    // Creates table based on data model
    private void showTable(List<Ticket> dataModel) {
        model = new TicketTableItem(dataModel);
        ticketTable = new JTable(model) {
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
        ticketTable.setAutoCreateRowSorter(true);

        tableScrollPane.setViewportView(ticketTable);
    }

    private void initButtons() {
        //searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(false);
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
                    Ticket ticket = model.getTicketAt(ticketTable.getSelectedRow());
                    new TicketView(ticket.getTitle()).setVisible(true);
                }
            }
        });

        searchButton.addActionListener(actionEvent -> {
            String query = searchTextField.getText();
            if(!query.isEmpty()) {
                List<Ticket> searchData = serverQuery.getSearchResults(query);
                showTable(searchData);
            } else {
                initTable();
            }
        });

        routineCheckBox.addActionListener(actionEvent -> {
            AbstractButton routineButton = (AbstractButton) actionEvent.getSource();

            boolean selected = routineButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSeveritySearchResults("Routine");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        urgentCheckBox.addActionListener(actionEvent -> {
            AbstractButton urgentButton = (AbstractButton) actionEvent.getSource();

            boolean selected = urgentButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSeveritySearchResults("Urgent");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        criticalCheckBox.addActionListener(actionEvent -> {
            AbstractButton criticalButton = (AbstractButton) actionEvent.getSource();

            boolean selected = criticalButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSeveritySearchResults("Critical");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        newCheckBox.addActionListener(actionEvent -> {
            AbstractButton newButton = (AbstractButton) actionEvent.getSource();

            boolean selected = newButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSearchResults("New");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        openCheckBox.addActionListener(actionEvent -> {
            AbstractButton openButton = (AbstractButton) actionEvent.getSource();

            boolean selected = openButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSearchResults("Open");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        closedCheckBox.addActionListener(actionEvent -> {
            AbstractButton closedButton = (AbstractButton) actionEvent.getSource();

            boolean selected = closedButton.getModel().isSelected();
            if(selected) {
                //String[][] searchData = serverQuery.getSearchResults("Closed");
                //showTable(searchData);
            } else {
                initTable();
            }
        });

        priorityDropdownList.addItem("");
        priorityDropdownList.addItem("1");
        priorityDropdownList.addItem("2");
        priorityDropdownList.addItem("3");
        priorityDropdownList.addItem("4");
        priorityDropdownList.addItem("5");
        priorityDropdownList.addItem("6");
        priorityDropdownList.addItem("7");
        priorityDropdownList.addItem("8");
        priorityDropdownList.addItem("9");
        priorityDropdownList.addItem("10");

        priorityDropdownList.addActionListener(actionEvent -> {
            //AbstractButton priorityDropdown = (AbstractButton) actionEvent.getSource();
            String x = priorityDropdownList.getSelectedItem().toString();
            switch (x) {
                case "1":
                    System.out.println("1 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("1");
                    //showTable(searchData);
                    break;
                case "2":
                    System.out.println("2 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("2");
                    //showTable(searchData);
                    break;
                case "3":
                    System.out.println("3 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("3");
                    //showTable(searchData);
                    break;
                case "4":
                    System.out.println("4 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("4");
                    //showTable(searchData);
                    break;
                case "5":
                    System.out.println("5 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("5");
                    //showTable(searchData);
                    break;
                case "6":
                    System.out.println("6 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("6");
                    //showTable(searchData);
                    break;
                case "7":
                    System.out.println("7 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("7");
                    //showTable(searchData);
                    break;
                case "8":
                    System.out.println("8 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("8");
                    //showTable(searchData);
                    break;
                case "9":
                    System.out.println("9 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("9");
                    //showTable(searchData);
                    break;
                case "10":
                    System.out.println("10 selected");
                    //String[][] searchData = serverQuery.getPrioritySearchResults("10");
                    //showTable(searchData);
                    break;
                default:
                    System.out.println("Reset the thing");
                    //initTable();
                    break;
            }
        });
    }
}
