package com.controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Authentication{

    static private final String base = "https://tinevra.herokuapp.com/users/";

    static private String[][] users = {
            {"george",  "juan",         "maynez", "miguel", "sammy", "daniel", "paulina","kenneth"},
            {"georgie", "juantwothree", "minus",  "migg",   "Tinev", "villa2", "Pau",    "kennY"},
            {"0",       "1",            "2",      "3",      "0",     "1",      "2",      "3"}};

    public static int login(String user, String password) {
        try {
            return connectToServer(user, password);
        } catch (IOException e) {
            System.out.println(e);
        }
        return -1;
    }

    private static int connectToServer(String username, String password) throws IOException {
        String parameters = username + "/" + password + "/accesslvl";
        String userURL = base + parameters;

        URL url = new URL(userURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String accesslevel = in.readLine();
        in.close();

        if(accesslevel.equals("{}")) {
            return -1;
        }
        return Integer.parseInt(accesslevel);
    }
}