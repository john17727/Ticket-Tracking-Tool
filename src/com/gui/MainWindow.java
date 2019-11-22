package com.gui;

import com.mock.ServerQuery;
import com.mock.Ticket;
import com.mock.TicketManager;
import com.mock.TicketTableItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private TicketManager ticketManager;

    private TicketTableItem model;
    private int accessLevel;
    private List<Ticket> data;
    private List<String> severity = new ArrayList<String>();
    private List<String> status = new ArrayList<String>();
    private List<Integer> priority = new ArrayList<Integer>();

    public MainWindow(int accessLevel) {
        this.accessLevel = accessLevel;
        add(mainPanel);
        setTitle("Ticket Manager");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        getRootPane().setDefaultButton(searchButton);

        ticketManager = new TicketManager();

        System.out.println(this.accessLevel);

        initTable();
        initButtons();
        initListeners();
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
                    new TicketView(ticket).setVisible(true);
                }
            }
        });
    }

    // Initializes all listeners of the window
    private void initListeners() {
        addTicketButton.addActionListener(actionEvent -> {
            new AddTicket().setVisible(true);
        });

        searchButton.addActionListener(actionEvent -> {
            String query = searchTextField.getText();
            if(!query.isEmpty()) {
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
            if(selected) {
                severityToggleOn("Routine");
            } else {
                severityToggleOff("Routine");
            }
        });

        urgentCheckBox.addActionListener(actionEvent -> {
            AbstractButton urgentButton = (AbstractButton) actionEvent.getSource();

            boolean selected = urgentButton.getModel().isSelected();
            if(selected) {
                severityToggleOn("Urgent");
            } else {
                severityToggleOff("Urgent");
            }
        });

        criticalCheckBox.addActionListener(actionEvent -> {
            AbstractButton criticalButton = (AbstractButton) actionEvent.getSource();

            boolean selected = criticalButton.getModel().isSelected();
            if(selected) {
                severityToggleOn("Critical");
            } else {
                severityToggleOff("Critical");
            }
        });

        newCheckBox.addActionListener(actionEvent -> {
            AbstractButton newButton = (AbstractButton) actionEvent.getSource();

            boolean selected = newButton.getModel().isSelected();
            if(selected) {
                statusToggleOn("New");
            } else {
                statusToggleOff("New");
            }
        });

        openCheckBox.addActionListener(actionEvent -> {
            AbstractButton openButton = (AbstractButton) actionEvent.getSource();

            boolean selected = openButton.getModel().isSelected();
            if(selected) {
                statusToggleOn("Open");
            } else {
                statusToggleOff("Open");
            }
        });

        closedCheckBox.addActionListener(actionEvent -> {
            AbstractButton closedButton = (AbstractButton) actionEvent.getSource();

            boolean selected = closedButton.getModel().isSelected();
            if(selected) {
                statusToggleOn("Closed");
            } else {
                statusToggleOff("Closed");
            }
        });

        a1CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(1);
            } else {
                priorityToggleOff(1);
            }
        });

        a2CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(2);
            } else {
                priorityToggleOff(2);
            }
        });

        a3CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(3);
            } else {
                priorityToggleOff(3);
            }
        });

        a4CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(4);
            } else {
                priorityToggleOff(4);
            }
        });

        a5CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(5);
            } else {
                priorityToggleOff(5);
            }
        });

        a6CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(6);
            } else {
                priorityToggleOff(6);
            }
        });

        a7CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(7);
            } else {
                priorityToggleOff(7);
            }
        });

        a8CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(8);
            } else {
                priorityToggleOff(8);
            }
        });

        a9CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(9);
            } else {
                priorityToggleOff(9);
            }
        });

        a10CheckBox.addActionListener(actionEvent -> {
            AbstractButton priorityButton = (AbstractButton) actionEvent.getSource();

            boolean selected = priorityButton.getModel().isSelected();
            if(selected) {
                priorityToggleOn(10);
            } else {
                priorityToggleOff(10);
            }
        });
    }

    public void severityToggleOn(String query) {
        severity.add(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void severityToggleOff(String query) {
        severity.remove(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void statusToggleOn(String query) {
        status.add(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void statusToggleOff(String query) {
        status.remove(query);
        data = ticketManager.filter(severity, status, priority);
        showTable(data);
    }

    public void priorityToggleOn(int query) {
        priority.add(query);
        data = new ArrayList<Ticket>(ticketManager.filter(severity, status, priority));
        showTable(data);
    }

    public void priorityToggleOff(int query) {
        priority.remove(Integer.valueOf(query));
        data = new ArrayList<Ticket>(ticketManager.filter(severity, status, priority));
        showTable(data);
    }
}
