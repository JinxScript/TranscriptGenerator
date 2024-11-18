
package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JFrame {
    public LandingPage() {
        setTitle("Student Transcript System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Logo and title
        JLabel titleLabel = new JLabel("Student Transcript System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Graduation cap icon (replace "icon.png" with your icon path)
        ImageIcon icon = new ImageIcon("icon.png");  // Make sure to have the icon in your project directory
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Student Transcript System.", JLabel.CENTER);
        JLabel welcomeLabel2 = new JLabel("Access and manage academic records with ease.", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login options
        JLabel loginAsLabel = new JLabel("Login as", JLabel.CENTER);
        loginAsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginAsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAsLabel.setForeground(new Color(40, 167, 69));

        // Student button
        JButton studentButton = new JButton("Student");
        studentButton.setFont(new Font("Arial", Font.BOLD, 16));
        studentButton.setForeground(new Color(40, 167, 69));
        studentButton.setBackground(Color.WHITE);
        studentButton.setBorderPainted(false);
        studentButton.setFocusPainted(false);
        studentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Registry button
        JButton registryButton = new JButton("Registry");
        registryButton.setFont(new Font("Arial", Font.BOLD, 16));
        registryButton.setForeground(new Color(40, 167, 69));
        registryButton.setBackground(Color.WHITE);
        registryButton.setBorderPainted(false);
        registryButton.setFocusPainted(false);
        registryButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Footer label
        JLabel footerLabel = new JLabel("© 2024 Student Transcript System. All rights reserved", JLabel.CENTER);
        JLabel creatorLabel = new JLabel("by Omolemo Tshwaolesele 22001043.", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        creatorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        creatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to main panel
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(welcomeLabel);
        mainPanel.add(welcomeLabel2);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginAsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(studentButton);
        mainPanel.add(registryButton);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(footerLabel);
        mainPanel.add(creatorLabel);

        // Add main panel to frame
        add(mainPanel);

        // Action Listeners for buttons (you can define actions here)
        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Student Login");
                // Open Student login window or redirect here
            }
        });

        registryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Registry Login");
                // Open Registry login window or redirect here
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LandingPage frame = new LandingPage();
            frame.setVisible(true);
        });
    }
}
