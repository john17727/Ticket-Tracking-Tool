package com.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.imageio.IIOException;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        jsonInfo.add("{\"title\":\"Ticket 1\",\"status\":\"New\",\"priority\":10,\"severity\":\"Urgent\",\"assignedTo\":\"Juan Rincon\",\"client\":\"NASA\",\"description\":\"Cool Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 2\",\"status\":\"New\",\"priority\":3,\"severity\":\"Routine\",\"assignedTo\":\"Alex Ortega\",\"client\":\"NASA\",\"description\":\"Awesome Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 3\",\"status\":\"Open\",\"priority\":7,\"severity\":\"Routine\",\"assignedTo\":\"Daniel Maynez\",\"client\":\"NASA\",\"description\":\"Typical Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 4\",\"status\":\"Open\",\"priority\":7,\"severity\":\"Routine\",\"assignedTo\":\"George Juarez\",\"client\":\"NASA\",\"description\":\"Nice Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 5\",\"status\":\"New\",\"priority\":5,\"severity\":\"Critical\",\"assignedTo\":\"Miguel Camarillo\",\"client\":\"NASA\",\"description\":\"Lovely Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 6\",\"status\":\"Open\",\"priority\":4,\"severity\":\"Routine\",\"assignedTo\":\"Daniel Villa\",\"client\":\"NASA\",\"description\":\"Confident Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 7\",\"status\":\"Open\",\"priority\":2,\"severity\":\"Urgent\",\"assignedTo\":\"Paulina Cervantez\",\"client\":\"NASA\",\"description\":\"Joyful Description\",\"solution\":\"Solution\",\"date\":0}");
        jsonInfo.add("{\"title\":\"Ticket 8\",\"status\":\"Open\",\"priority\":9,\"severity\":\"Critical\",\"assignedTo\":\"Sam Tinevra\",\"client\":\"NASA\",\"description\":\"Super Description\",\"solution\":\"Solution\",\"date\":0}");

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

    private Ticket toTicket(String json) {
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(json, Ticket.class);
        return ticket;
    }

    private List<Ticket> toTickets(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Ticket>>(){}.getType());
    }

    // Gets default data
    public List<Ticket> getDefault() {
        try {
            return toTickets(connectToServer("https://tinevra.herokuapp.com/ticketsList"));

        } catch (IOException e) {
            System.out.println(e);
            return data;
        }
        //return new ArrayList<Ticket>();
    }

    public String addTicketToServer(Ticket ticket) {
        try {
            return connectToServer("https://tinevra.herokuapp.com/tickets/" +
                    ticket.getTitle() + "/" +
                    ticket.getStatus() + "/" +
                    ticket.getPriority() + "/" +
                    ticket.getSeverity() + "/" +
                    ticket.getAssignedTo() + "/" +
                    ticket.getDescription() + "/" +
                    ticket.getSolution() + "/" +
                    ticket.getDate() + "/" +
                    ticket.getClient());
        } catch (IOException e) {
            System.out.println(e);
            return "-2";
        }
    }

    private String connectToServer(String inputURL) throws IOException {
        URL url = new URL(inputURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String result = in.readLine();
        in.close();

        return result;
    }
}
