package com.gui;

import com.mock.Authentication;

import javax.swing.*;
import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login extends JFrame{
    private JPanel mainPanel;
    private JTextField userEdit;
    private JPasswordField passwordEdit;
    private JButton loginButton;
    private JLabel errorMsg;

    public Login() {
        add(mainPanel);
        setTitle("Ticket Manager");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        getRootPane().setDefaultButton(loginButton);
        errorMsg.setVisible(false);

        loginButton.addActionListener(actionEvent -> {
            String userName = userEdit.getText();
            char[] charPassword = passwordEdit.getPassword();
            System.out.println(charToString(charPassword));
            String password = hashPassword(charPassword);
            if(!userName.isEmpty() && !password.isEmpty()) {
                int Authenticate = Authentication.login(userName, password);
                //og
                if (Authenticate != -1) {
                    new MainWindow().setVisible(true);
                    dispose();//end of og
                } else {
                    errorMsg.setVisible(true);
                }
            }
        });
    }

    public static String hashPassword(char[] password) {
        String hashedPass = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(toBytes(password));
            hashedPass = bytesToHex(encodedHash);
            System.out.println(hashedPass);
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

    public static String charToString(char[] charArray) {
        String result = "";
        for(char aChar: charArray) {
            result += String.valueOf(aChar);
        }
        return result;
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
