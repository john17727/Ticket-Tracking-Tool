package com.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.data.Ticket;

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

    public ServerQuery() {}

    // Converts json to list of tickets
    private List<Ticket> toTickets(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Ticket>>(){}.getType());
    }

    // Gets default data from server
    public List<Ticket> getDefault() {
        try {
            List<Ticket> tickets = toTickets(connectToServer("https://tinevra.herokuapp.com/ticketsList-status/New"));
            tickets.addAll(toTickets(connectToServer("https://tinevra.herokuapp.com/ticketsList-status/Open")));
            return tickets;
        } catch (IOException e) {
            System.out.println(e);
        }
        return new ArrayList<Ticket>();
    }

    // Returns all closed tickets in server
    public List<Ticket> getClosedTickets() {

        try {
            List<Ticket> tickets = toTickets(connectToServer("https://tinevra.herokuapp.com/ticketsList-status/Closed"));
            return tickets;
        } catch (IOException e) {
            System.out.println(e);
        }
        return new ArrayList<Ticket>();
    }

    // Sends updated ticket to server
    public String updateTicketToServer(Ticket ticket) {
        try {
            return connectToServer("https://tinevra.herokuapp.com/update-tickets/" +
                    ticket.getId() + "/" +
                    ticket.getTitle() + "/" +
                    ticket.getStatus() + "/" +
                    ticket.getPriority() + "/" +
                    ticket.getSeverity() + "/" +
                    ticket.getAssignedTo() + "/" +
                    ticket.getDescription() + "/" +
                    ticket.getSolution() + "/" +
                    ticket.getDate() + "/" +
                    ticket.getDateEnd() + "/" +
                    ticket.getClient());
        } catch (IOException e) {
            System.out.println(e);
            return "-3";
        }
    }

    // Sends new ticket to server
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

    // Returns true if a ticket is in use
    public boolean isTicketInUse(String id) {
        try {
            return Boolean.parseBoolean(connectToServer("https://tinevra.herokuapp.com/ticket-check-if-open/" + id));
        } catch (IOException e) {
            System.out.println(e);
            return true;
        }
    }

    // Locks a ticket in use and unlocks it once done
    public void toggleTicketLock(String id) {
        try {
            connectToServer("https://tinevra.herokuapp.com/ticket-toggle-open/" + id);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Establishes connection to server
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
