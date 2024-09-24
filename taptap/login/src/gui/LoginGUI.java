package gui;

import constants.CommonConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginGUI extends JFrame {
    public LoginGUI(){
        super(CommonConstants.APP_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(CommonConstants.FRAME_SIZE[0], CommonConstants.FRAME_SIZE[1]);
        setLocationRelativeTo(null);

        addGUIComponents();
    }

    public void addGUIComponents(){
        SpringLayout springLayout = new SpringLayout();
        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(springLayout);

        //username
        JLabel userNameLabel = new JLabel("Username: ");
        userNameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        JTextField userNameField = new JTextField(CommonConstants.TEXT_SIZE);
        userNameField.setFont(new Font("Dialog", Font.PLAIN, 18));

        springLayout.putConstraint(SpringLayout.WEST, userNameLabel, 35, SpringLayout.WEST, layoutPanel);
        springLayout.putConstraint(SpringLayout.NORTH, userNameLabel, 85, SpringLayout.NORTH, layoutPanel);
        springLayout.putConstraint(SpringLayout.WEST, userNameField, 135, SpringLayout.WEST, layoutPanel);
        springLayout.putConstraint(SpringLayout.NORTH, userNameField, 85, SpringLayout.NORTH, layoutPanel);

        layoutPanel.add(userNameLabel);
        layoutPanel.add(userNameField);

        //password
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        JPasswordField passwordField = new JPasswordField(CommonConstants.TEXT_SIZE);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 18));

        springLayout.putConstraint(SpringLayout.WEST, passwordLabel,35, SpringLayout.WEST, layoutPanel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 135, SpringLayout.NORTH, layoutPanel);
        springLayout.putConstraint(SpringLayout.WEST, passwordField, 135, SpringLayout.WEST, layoutPanel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 135, SpringLayout.NORTH,layoutPanel);

        layoutPanel.add(passwordLabel);
        layoutPanel.add(passwordField);

        //button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        loginButton.setFocusPainted(false);
        springLayout.putConstraint(SpringLayout.WEST, loginButton, 150, SpringLayout.WEST, layoutPanel);
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 250, SpringLayout.NORTH, layoutPanel);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = passwordField.getText();

                if (username.equals("username") && password.equals("password")){
                    JOptionPane.showMessageDialog(new LoginGUI(),"LOGIN SUCCESSFUL!","Information",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(new JOptionPane(),"LOGIN FAILED...","Information",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Login Failed....");
                }
            }
        });

        layoutPanel.add(loginButton);

        this.getContentPane().add(layoutPanel);
    }
}
