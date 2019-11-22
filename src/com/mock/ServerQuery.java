package com.mock;

import com.google.gson.Gson;

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

        List<String> jsonInfo = new ArrayList<String>();
        jsonInfo.add("{\"title\":\"Ticket 1\",\"status\":\"New\",\"priority\":10,\"severity\":\"Urgent\",\"assignedTo\":\"Juan Rincon\",\"client\":\"NASA\",\"description\":\"Cool Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 2\",\"status\":\"New\",\"priority\":3,\"severity\":\"Routine\",\"assignedTo\":\"Alex Ortega\",\"client\":\"NASA\",\"description\":\"Awesome Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 3\",\"status\":\"Open\",\"priority\":7,\"severity\":\"Routine\",\"assignedTo\":\"Daniel Maynez\",\"client\":\"NASA\",\"description\":\"Typical Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 4\",\"status\":\"Open\",\"priority\":7,\"severity\":\"Routine\",\"assignedTo\":\"George Juarez\",\"client\":\"NASA\",\"description\":\"Nice Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 5\",\"status\":\"New\",\"priority\":5,\"severity\":\"Critical\",\"assignedTo\":\"Miguel Camarillo\",\"client\":\"NASA\",\"description\":\"Lovely Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 6\",\"status\":\"Open\",\"priority\":4,\"severity\":\"Routine\",\"assignedTo\":\"Daniel Villa\",\"client\":\"NASA\",\"description\":\"Confident Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 7\",\"status\":\"Open\",\"priority\":2,\"severity\":\"Urgent\",\"assignedTo\":\"Paulina Cervantez\",\"client\":\"NASA\",\"description\":\"Joyful Description\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 8\",\"status\":\"Open\",\"priority\":9,\"severity\":\"Critical\",\"assignedTo\":\"Sam Tinevra\",\"client\":\"NASA\",\"description\":\"Super Description\",\"date\":0}");

        data = new ArrayList<Ticket>();
        for(String info:jsonInfo) {
            data.add(toTicket(info));
        }
        /*
        Ticket me = new Ticket("Ticket 1", "Open", 10, "Urgent", "Juan Rincon");
        data.add(me);
        data.add(new Ticket("Ticket 2", "Open", 3, "Routine", "Alex Ortega"));
        data.add(new Ticket("Ticket 3", "Open", 7, "Routine", "Daniel Maynez"));
        data.add(new Ticket("Ticket 4", "Open", 7, "Routine", "Daniel Villa"));
        data.add(new Ticket("Ticket 5", "Open", 5, "Critical", "George Juarez"));
        data.add(new Ticket("Ticket 6", "Open", 4, "Routine", "Miguel Camarillo"));
        data.add(new Ticket("Ticket 7", "Open", 2, "Urgent", "Paulina Cervantes"));
        data.add(new Ticket("Ticket 8", "Open", 9, "Critical", "Sam Tinevra"));

        toJSON(me);
         */
    }

    public Ticket toTicket(String json) {
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(json, Ticket.class);
        return ticket;
    }

    public void toJSON(Ticket ticket) {
        Gson gson = new Gson();

        String jsonString = gson.toJson(ticket);
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
            } else if(Integer.toString(ticket.getPriority()).contains(query)) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

    public List<Ticket> getSearchResults(List<Ticket> data, String query) {
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
            } else if(Integer.toString(ticket.getPriority()).contains(query)) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

    public List<Ticket> removeSeverityFilter(List<Ticket> data, String query) {
        List<Ticket> searchResults = new ArrayList<Ticket>();
        for(Ticket ticket:data) {
            if(!ticket.getSeverity().contains(query)) {
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
