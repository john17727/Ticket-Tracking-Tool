package com.mock;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TicketTableItem extends AbstractTableModel {

    private List<Ticket> tickets;

    public TicketTableItem(List<Ticket> tickets) {
        this.tickets = new ArrayList<Ticket>(tickets);
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object value = "";
        Ticket ticket = tickets.get(row);
        switch(column) {
            case 0:
                value = ticket.getTitle();
                break;
            case 1:
                value = ticket.getStatus();
                break;
            case 2:
                value = ticket.getPriority();
                break;
            case 3:
                value = ticket.getSeverity();
                break;
            case 4:
                value = ticket.getAssignedTo();
                break;
        }
        return value;
    }

    @Override
    public String getColumnName(int column) {
        String name = "";
        switch (column) {
            case 0:
                name = "Title";
                break;
            case 1:
                name = "Status";
                break;
            case 2:
                name = "Priority";
                break;
            case 3:
                name = "Severity";
                break;
            case 4:
                name = "Assigned To";
                break;
        }
        return name;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (tickets.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    public Ticket getTicketAt(int row) {
        return tickets.get(row);
    }

    public void printTickets() {
        tickets.forEach(ticket -> System.out.println(ticket.getTitle()));
    }
}
