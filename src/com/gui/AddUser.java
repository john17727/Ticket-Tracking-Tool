package com.gui;

import com.controllers.Authentication;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AddUser extends JFrame{

    private JLabel AddUserLabel;
    private JLabel NameLabel;
    private JLabel UsernameLabel;
    private JLabel EmailLabel;
    private JLabel PasswordLabel;
    private JLabel AccessLvlLabel;
    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JTextField accessLvlTextField;
    private JPasswordField passwordLvlTextField;
    private JButton saveButton;
    private int accessLevel;
    private JPanel mainPanel;
    private JLabel errorMsg;

    public AddUser(int accessLevel){

        this.accessLevel = accessLevel;
        add(mainPanel);
        setTitle("AddUser");
        setSize(500, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        errorMsg.setVisible(false);

        initListeners();
    }

    private void initListeners() {

        saveButton.addActionListener(actionEvent -> {

            String name = nameTextField.getText();
            String username = usernameTextField.getText();
            String email = emailTextField.getText();
            char[] password = passwordLvlTextField.getPassword();
            String temp = accessLvlTextField.getText();

            if(!name.equals("") && !username.equals("") && !email.equals("") && !password.equals("") && !temp.equals("")){

                try {

                    int newAccessLvl = Integer.parseInt(temp);
                    int test = Authentication.addNewUser(name, username, email, hashPassword(password), newAccessLvl);

                    if(test == 1){dispose();}

                    else{errorMsg.setVisible(true);}
                }

                catch (IOException e) {

                    e.printStackTrace();
                    errorMsg.setVisible(true);
                }

            }

            else{errorMsg.setVisible(true);}
        });
    }

    private static String hashPassword(char[] password) {
        String hashedPass = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(toBytes(password));
            hashedPass = bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPass;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }
}
