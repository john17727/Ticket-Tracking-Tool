package com.mock;

import java.util.ArrayList;
import java.util.List;

public class TicketManager {

    private List<Ticket> tickets;
    private ServerQuery serverQuery;

    public TicketManager() {
        serverQuery = new ServerQuery();
        tickets = serverQuery.getDefault();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Ticket> getTicketsFromServer() {
        return tickets;
    }

    public List<Ticket> search(String query) {
        List<Ticket> searchResults = new ArrayList<Ticket>();
        for(Ticket ticket:tickets) {
            if(ticket.getTitle().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getStatus().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getSeverity().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getAssignedTo().contains(query)) {
                searchResults.add(ticket);
            } else if(Integer.toString(ticket.getPriority()).contains(query)) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

    public List<Ticket> filter(List<String> severity, List<String> status, List<Integer> priority) {
        List<Ticket> filtered = new ArrayList<Ticket>();
        List<Ticket> filtSeverity = new ArrayList<Ticket>(tickets);
        List<Ticket> filtStatus;
        List<Ticket> filtPriority;

        if(severity.size() != 0) {
            severity.forEach((query) -> {
                filtSeverity.forEach((ticket) -> {
                    if(ticket.getSeverity().equals(query)) {
                        filtered.add(ticket);
                    }
                });
            });
            filtStatus = new ArrayList<Ticket>(filtered);
        } else {
            filtStatus = new ArrayList<Ticket>(tickets);
        }

        if(status.size() != 0) {
            filtered.clear();
            status.forEach((query) -> {
                filtStatus.forEach((ticket) -> {
                    if(ticket.getStatus().equals(query)) {
                        filtered.add(ticket);
                    }
                });
            });
            filtPriority = new ArrayList<Ticket>(filtered);
        } else {
            filtPriority = new ArrayList<Ticket>(tickets);
        }

        if(priority.size() != 0) {
            filtered.clear();
            priority.forEach((query) -> {
                filtPriority.forEach((ticket) -> {
                    if(ticket.getPriority() == query) {
                        filtered.add(ticket);
                    }
                });
            });
        }

        if(filtered.size() == 0) {
            return tickets;
        }

        return filtered;
    }
}
