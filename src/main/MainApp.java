

/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package main;

import view.LoginView;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> {
            try {
                // Display the login screen
                new LoginView();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while launching the application.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
