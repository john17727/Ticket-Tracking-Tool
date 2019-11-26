package com.gui;

import com.controllers.ServerQuery;
import com.controllers.TicketManager;
import com.data.Ticket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.adapters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private JComboBox priorityDropdownList;
    private JButton addUserButton;
    private static JFrame frame;

    private TicketManager ticketManager;
    private ServerQuery serverQuery;
    private String currentTicketID;

    private TicketTableItem model;
    private int accessLevel;
    private List<Ticket> data;
    private List<String> severity = new ArrayList<String>();
    private List<String> status = new ArrayList<String>();
    private List<Integer> priority = new ArrayList<Integer>();

    //private String[] UsersListNames;
    private List<String> UsersListNames;

    public MainWindow(int accessLevel) throws IOException {
        this.accessLevel = accessLevel;
        add(mainPanel);
        setTitle("Ticket Manager");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        getRootPane().setDefaultButton(searchButton);

        ticketManager = new TicketManager();
        serverQuery = new ServerQuery();

        if(accessLevel != 0){

            addUserButton.setEnabled(false);
            addUserButton.setVisible(false);
        }

        if(accessLevel == 1){

            addTicketButton.setEnabled(false);
            addTicketButton.setVisible(false);
        }

        initTable();
        initButtons();
        initListeners();
        userList();
        setTimer();
    }

    // Refreshes tickets at an interval
    public void setTimer() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                data = ticketManager.getTicketsFromServer();
                showTable(data);
            }
        };
        Timer t = new Timer();
        t.scheduleAtFixedRate(repeatedTask, 5000, 5000); // 2 minutes = 120000 milli
    }

    // Converts the json to list of users
    public static List<String> toArray(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
    }

    // Gets the list of users
    public void userList()throws IOException {

        URL url = new URL("https://tinevra.herokuapp.com/usersList");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String result = in.readLine();
        in.close();

        this.UsersListNames = toArray(result);
    }

    // Initializes table with default values
    private void initTable() {
        data = new ArrayList<Ticket>(ticketManager.getTicketsFromServer());
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
        ticketTable.setIntercellSpacing(new Dimension(8, 2));

        tableScrollPane.setViewportView(ticketTable);
        initTableListener();
    }

    private void initButtons() {
        //searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(false);
    }

    private void initTableListener() {

        ticketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Ticket ticket = model.getTicketAt(ticketTable.convertRowIndexToModel(ticketTable.getSelectedRow()));
                    TicketView ticketView = new TicketView(ticket, accessLevel, UsersListNames);
                    ticketView.setVisible(true);
                    ticketView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            super.windowOpened(e);
                            if(serverQuery.isTicketInUse(ticket.getId())) {
                                ticketView.setVisible(false);
                                JOptionPane.showMessageDialog(frame,
                                        "Another user is currently working on this ticket.",
                                        "Locked Ticket",
                                        JOptionPane.WARNING_MESSAGE);
                            } else {
                                currentTicketID = ticket.getId();
                                serverQuery.toggleTicketLock(ticket.getId());
                            }
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            currentTicketID = "";
                            serverQuery.toggleTicketLock(ticket.getId());
                            showTable(data);
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            currentTicketID = "";
                            serverQuery.toggleTicketLock(ticket.getId());
                            showTable(data);
                        }
                    });
                }
            }
        });

        ticketTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                ticketTable.getSelectionModel().clearSelection();
            }
        });
    }

    // Initializes all listeners of the window
    private void initListeners() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(!currentTicketID.isEmpty()) {
                    serverQuery.toggleTicketLock(currentTicketID);
                }
            }
        });

        addUserButton.addActionListener(ActionEvent -> {
            AddUser newUser = new AddUser(accessLevel);
            newUser.setVisible(true);
            newUser.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    data = ticketManager.getTickets();
                    showTable(data);
                }
            });
        });

        addTicketButton.addActionListener(actionEvent -> {
            AddTicket newTicket = new AddTicket(accessLevel, ticketManager, UsersListNames);
            newTicket.setVisible(true);
            newTicket.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    data = ticketManager.getTickets();
                    showTable(data);
                }
            });
        });

        searchButton.addActionListener(actionEvent -> {
            String query = searchTextField.getText();
            if (!query.isEmpty()) {
                data = ticketManager.search(query);
                showTable(data);
            } else {
                data = ticketManager.getTickets();
                showTable(data);
            }
        });

        routineCheckBox.addActionListener(actionEvent -> {
            AbstractButton routineButton = (AbstractButton) actionEvent.getSource();

            boolean selected = routineButton.getModel().isSelected();
            if (selected) {
                severityToggleOn("Routine");
            } else {
                severityToggleOff("Routine");
            }
        });

        urgentCheckBox.addActionListener(actionEvent -> {
            AbstractButton urgentButton = (AbstractButton) actionEvent.getSource();

            boolean selected = urgentButton.getModel().isSelected();
            if (selected) {
                severityToggleOn("Urgent");
            } else {
                severityToggleOff("Urgent");
            }
        });

        criticalCheckBox.addActionListener(actionEvent -> {
            AbstractButton criticalButton = (AbstractButton) actionEvent.getSource();

            boolean selected = criticalButton.getModel().isSelected();
            if (selected) {
                severityToggleOn("Critical");
            } else {
                severityToggleOff("Critical");
            }
        });

        newCheckBox.addActionListener(actionEvent -> {
            AbstractButton newButton = (AbstractButton) actionEvent.getSource();

            boolean selected = newButton.getModel().isSelected();
            if (selected) {
                statusToggleOn("New");
            } else {
                statusToggleOff("New");
            }
        });

        openCheckBox.addActionListener(actionEvent -> {
            AbstractButton openButton = (AbstractButton) actionEvent.getSource();

            boolean selected = openButton.getModel().isSelected();
            if (selected) {
                statusToggleOn("Open");
            } else {
                statusToggleOff("Open");
            }
        });

        closedCheckBox.addActionListener(actionEvent -> {
            AbstractButton closedButton = (AbstractButton) actionEvent.getSource();

            boolean selected = closedButton.getModel().isSelected();
            if (selected) {
                statusToggleOn("Closed");
            } else {
                statusToggleOff("Closed");
            }
        });

        priorityDropdownList.addItem("None");
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
                    priorityClear();
                    priorityToggleOn(1);
                    break;
                case "2":
                    priorityClear();
                    priorityToggleOn(2);
                    break;
                case "3":
                    priorityClear();
                    priorityToggleOn(3);
                    break;
                case "4":
                    priorityClear();
                    priorityToggleOn(4);
                    break;
                case "5":
                    priorityClear();
                    priorityToggleOn(5);
                    break;
                case "6":
                    priorityClear();
                    priorityToggleOn(6);
                    break;
                case "7":
                    priorityClear();
                    priorityToggleOn(7);
                    break;
                case "8":
                    priorityClear();
                    priorityToggleOn(8);
                    break;
                case "9":
                    priorityClear();
                    priorityToggleOn(9);
                    break;
                case "10":
                    priorityClear();
                    priorityToggleOn(10);
                    break;
                default:
                    System.out.println("None selected");
                    priorityToggleOff();
                    break;
            }
        });
    }

    public void severityToggleOn (String query){
        severity.add(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void severityToggleOff (String query){
        severity.remove(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void statusToggleOn (String query){
        if(query.equals("Closed")) {
            data = ticketManager.getClosedTickets();
        } else {
            status.add(query);
            data = ticketManager.filter(severity, status, priority);
        }
        showTable(data);
    }

    public void statusToggleOff (String query){
        if(query.equals("Closed")) {
            data = ticketManager.getTicketsFromServer();
        } else {
            status.remove(query);
            data = ticketManager.filter(severity, status, priority);
        }
        showTable(data);
    }

    public void priorityToggleOn ( int query){
        priority.add(query);
        data = new ArrayList<Ticket>(ticketManager.filter(severity, status, priority));
        showTable(data);
    }

    public void priorityToggleOff (){
        priority.clear();
        data = new ArrayList<Ticket>(ticketManager.filter(severity, status, priority));
        showTable(data);
    }
    public void priorityClear() {
        priority.clear();
    }
}
