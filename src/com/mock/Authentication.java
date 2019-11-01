package com.mock;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Authentication{

    static private final String base = "https://tinevra.herokuapp.com/users/";

    static private String[][] users = {
            {"george",  "juan",         "maynez", "miguel", "sammy", "daniel", "paulina","kenneth"},
            {"georgie", "juantwothree", "minus",  "migg",   "Tinev", "villa2", "Pau",    "kennY"},
            {"0",       "1",            "2",      "3",      "0",     "1",      "2",      "3"}};

    static public int login(String user, String password) {
        try {
            return connectToServer(user, password);
        } catch (IOException e) {
            System.out.println(e);
        }
        /*
        for(int i = 0; i < users[0].length; i++){

            if(users[0][i].equals(user.toLowerCase())){return Integer.parseInt(users[2][i]);}
        }
         */
        return -1;
    }

    private static int connectToServer(String username, String password) throws IOException {
        String parameters = username + "/" + password + "/accesslvl";
        String test = ":username/:password/accesslvl";
        String userURL = base + parameters;
        System.out.println(userURL);
        URL url = new URL(userURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String accesslevel = in.readLine();
        in.close();
        System.out.println(accesslevel);
        if(accesslevel.equals("{}")) {
            return -1;
        }
        return Integer.parseInt(accesslevel);
    }
}