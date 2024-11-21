package view;

import control.AuthenticationController;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final AuthenticationController authController;

    public LoginView() {
        authController = new AuthenticationController();

        // Setup frame
        JFrame frame = new JFrame("Login - Student Transcript Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Student Transcript Generator", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);

        // Add action listener for login button
        loginButton.addActionListener(e -> authenticateUser());

        // Add components to frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void authenticateUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (authController.authenticateRegistry(username, password)) {
            JOptionPane.showMessageDialog(null, "Welcome, Registry User!");
            new RegistryView(); // Open RegistryView
        } else if (authController.authenticateStudent(username)) {
            JOptionPane.showMessageDialog(null, "Welcome, Student!");
            new StudentView(); // Open StudentView
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
