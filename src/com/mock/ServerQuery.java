package com.mock;

import java.util.ArrayList;
import java.util.List;

/************************
 * Mocks query to a server
 */
public class ServerQuery {

    private String[][] data1;
    private List<Ticket> data;

    public ServerQuery() {
        data1 = new String[][] {
                {"Ticket 1", "Open", "10", "Urgent", "Juan Rincon"},
                {"Ticket 2", "Open", "3", "Routine", "Alex Ortega"},
                {"Ticket 3", "Open", "7", "Routine", "Daniel Maynez"},
                {"Ticket 4", "Open", "7", "Routine", "Daniel Villa"},
                {"Ticket 5", "Open", "5", "Critical", "George Juarez"},
                {"Ticket 6", "Open", "4", "Routine", "Miguel Camarillo"},
                {"Ticket 7", "Open", "2", "Urgent", "Paulina Cervantes"},
                {"Ticket 8", "Open", "9", "Critical", "Sam Tinevra"},
        };

        data = new ArrayList<Ticket>();
        data.add(new Ticket("Ticket 1", "Open", 10, "Urgent", "Juan Rincon"));
        data.add(new Ticket("Ticket 2", "Open", 3, "Routine", "Alex Ortega"));
        data.add(new Ticket("Ticket 3", "Open", 7, "Routine", "Daniel Maynez"));
        data.add(new Ticket("Ticket 4", "Open", 7, "Routine", "Daniel Villa"));
        data.add(new Ticket("Ticket 5", "Open", 5, "Critical", "George Juarez"));
        data.add(new Ticket("Ticket 6", "Open", 4, "Routine", "Miguel Camarillo"));
        data.add(new Ticket("Ticket 7", "Open", 2, "Urgent", "Paulina Cervantes"));
        data.add(new Ticket("Ticket 8", "Open", 9, "Critical", "Sam Tinevra"));
    }

    // Gets default data
    public List<Ticket> getDefault() {
        return data;
    }

    // Obtains all results that match a search query
    public List<Ticket> getSearchResults(String query) {
        List<Ticket> searchResults = new ArrayList<Ticket>();
        for(Ticket ticket: data) {
            if(ticket.getTitle().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getStatus().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getSeverity().contains(query)) {
                searchResults.add(ticket);
            } else if(ticket.getAssignedTo().contains(query)) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

    /*
    public String[][] getSearchResults(String query) {
        String[][] searchData = new String[0][];
        for(String[] ticket: data1) {
            for(String attribute:ticket) {
                if(attribute.contains(query)) {
                    searchData = appendRow(searchData, ticket);
                }
            }
        }
        return searchData;
    }
    */

    public String[][] getPrioritySearchResults(String query) {
        String[][] searchData = new String[0][];
        for(String[] ticket: data1) {
            if(ticket[2].equals(query)) {
                searchData = appendRow(searchData, ticket);
            }
        }
        return searchData;
    }

    public String[][] getSeveritySearchResults(String query) {
        String[][] searchData = new String[0][];
        for (String[] ticket : data1) {
            if (ticket[3].equals(query)) {
                searchData = appendRow(searchData, ticket);
            }
        }
        return searchData;
    }

    // Appends row to data model
    private String[][] appendRow(String[][] array, String[] row) {
        String[][] newArray = new String[array.length + 1][];

        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[newArray.length - 1] = row;
        return newArray;
    }
}
