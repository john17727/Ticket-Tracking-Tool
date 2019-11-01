package com.mock;

/************************
 * Mocks query to a server
 */
public class ServerQuery {

    private String[][] data;

    public ServerQuery() {
        data = new String[][] {
                {"Ticket 1", "Open", "10", "Urgent", "Juan Rincon"},
                {"Ticket 2", "Open", "3", "Routine", "Alex Ortega"},
                {"Ticket 3", "Open", "7", "Routine", "Daniel Maynez"},
                {"Ticket 4", "Open", "7", "Routine", "Daniel Villa"},
                {"Ticket 5", "Open", "5", "Critical", "George Juarez"},
                {"Ticket 6", "Open", "4", "Routine", "Miguel Camarillo"},
                {"Ticket 7", "Open", "2", "Urgent", "Paulina Cervantes"},
                {"Ticket 8", "Open", "9", "Critical", "Sam Tinevra"},
        };
    }

    // Gets default data
    public String[][] getDefault() {
        return data;
    }

    // Obtains all results that match a search query
    public String[][] getSearchResults(String query) {
        String[][] searchData = new String[0][];
        for(String[] ticket:data) {
            for(String attribute:ticket) {
                if(attribute.contains(query)) {
                    searchData = appendRow(searchData, ticket);
                }
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
