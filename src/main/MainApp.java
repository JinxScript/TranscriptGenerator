/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package main;

import view.LoginView;

public class MainApp {

    public static void main(String[] args) {
        try {
            // Directly launch the LoginView
            new LoginView();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while launching the application.");
        }
    }
}

