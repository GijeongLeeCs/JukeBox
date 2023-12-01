/*
 * Gijeong Lee, Kartik
 * This is for creating an account pane including buttons and textField.
 * It plays songs based on what a user presses.
 * There is a 2-second delay between each song played
 *  All songs play completely before the next song, they are not playing on top of one another
 *  Alert the user they have reached the max selections for the day
 */

package controller_view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.JukeboxAccount;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

public class LoginCreateAccountPane extends GridPane {

	private String userName;
	private String password;

	private JukeboxAccount user;

	private TextField accountNameField = new TextField();
	private PasswordField passwordField = new PasswordField();

	private Label accountNameLabel = new Label("Account name");
	private Label passwordLabel = new Label("Password");

	private Label statusLabel = new Label("Login first");

	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");
	private Button newAccountButton = new Button("Create New Account");

	private boolean logStatus = false;

	public int songsPlayed;
	boolean checkPlaying = false;
	
	/*
	 * Constructor It has buttons and their events as lambda it adds all the
	 * elements Alert the user they have reached the max selections for the day All
	 * songs play completely before the next song, they are not playing on top of
	 * one another
	 */
	public LoginCreateAccountPane() {
		this.setPadding(new Insets(20, 0, 25, 0));
		this.setHgap(10);
		this.setAlignment(Pos.TOP_CENTER);
		
		// Account name and password inputs
		this.add(accountNameLabel, 0, 1);
		this.add(accountNameField, 1, 1);
		this.add(passwordLabel, 0, 2);
		this.add(passwordField, 1, 2);

		this.add(loginButton, 2, 1); // Login Button
		this.add(statusLabel, 1, 0); // Songs played label
		this.add(logoutButton, 2, 2); // Logout Button
		this.add(newAccountButton, 1, 3); // Songs played label

		loginButton.setOnAction((ActionEvent event) -> {
			login();
		});

		logoutButton.setOnAction((ActionEvent event) -> {
			logout();
			clearFields();
		});

		newAccountButton.setOnAction((ActionEvent event) -> {
			newAccountButton();
		});

		// Styling
		Insets topMargin = new Insets(10, 0, 0, 0);
		Insets spaceMargin = new Insets(10, 0, 0, 0);
		GridPane.setMargin(accountNameLabel, topMargin);
		GridPane.setMargin(accountNameField, topMargin);
		GridPane.setMargin(passwordLabel, spaceMargin);
		GridPane.setMargin(passwordField, spaceMargin);
		GridPane.setMargin(loginButton, new Insets(5, 0, 0, 5));
		GridPane.setMargin(logoutButton, new Insets(10, 0, 0, 5));
		GridPane.setMargin(newAccountButton, new Insets(15, 0, 0, 0));

	}

	/*
	 * This function is for login It gets user's account name and password. Then, it
	 * checks whether it is correct account name and password.
	 */
	private void login() {
		if (logStatus == false) {
			userName = accountNameField.getText();
			password = passwordField.getText();
			
			if (JukeboxAccount.accountsTable.containsKey(userName)) {
				if (JukeboxAccount.accountsTable.get(userName).get(0).equals(password)) {
					user = new JukeboxAccount(userName, password);
					clearFields();
					if (user.getLogin() == true) {
						logStatus = true;
						updateNumSongs();
						System.out.println("Current table - " + JukeboxAccount.accountsTable + "\n");				
					}
				}
				else {
					setStatus("Invalid Credentials");
				}
			}
			else {
				setStatus("Invalid Credentials");
				clearFields();
			}
		}
		else {
			logout();
			userName = accountNameField.getText();
			password = passwordField.getText();
			
			System.out.println("this... " + userName);
			if (JukeboxAccount.accountsTable.containsKey(userName)) {
				if (JukeboxAccount.accountsTable.get(userName).get(0).equals(password)) {
					user = new JukeboxAccount(userName, password);
					clearFields();
					if (user.getLogin() == true) {
						logStatus = true;
						updateNumSongs();
						System.out.println("Current table - " + JukeboxAccount.accountsTable + "\n");				
					}
				}
				else {
					setStatus("Invalid Credentials");
					System.out.println("1--- " + userName);
				}
			}
			else {
				setStatus("Invalid Credentials");
				clearFields();
				System.out.println("2--- " + userName);
			}
		}
	}
	
	public void updateNumSongs() {
		int songsPlayedToday = (int) user.getAccountsTable().get(userName).get(1);
		setStatus(songsPlayedToday + " songs selected today");
	}

	private void newAccountButton() {
		String userName1 = accountNameField.getText();
	    String userPassword1 = passwordField.getText();
	    
	    if (!userName1.isEmpty() && !userPassword1.isEmpty()) {
	    	user = new JukeboxAccount(userName1, userPassword1);
		    
			clearFields();
			setStatus("Login First");
			System.out.println("Current table - " + JukeboxAccount.accountsTable + "\n");
	    }
	    else {
	    	setStatus("Invalid Credentials");
	    }
	}
	
	public JukeboxAccount getUser() {
		if (logStatus == true) {
			return user;
		}
		return null;
	}

	/**
	 * this will return if a person is currently logged in or not
	 * 
	 * @return true if the user is logged in false if the user is not logged in
	 */
	public boolean loggedInStatus() {
		if (logStatus == true)
			return true;
		return false;
	}
	
	/*
	 * This function is for log out.
	 */
	private void logout() {
		logStatus = false;
		setStatus("Login first");
		System.out.println("Logged out!");
		System.out.println("Current table - " + JukeboxAccount.accountsTable + "\n");
	}

	private void clearFields() {
		accountNameField.clear();
		passwordField.clear();
	}

	/*
	 * This function is for setting status.
	 * 
	 * @param statusText which is a string that whill be shown on the screen.
	 */
	public void setStatus(String statusText) {
		statusLabel.setText(statusText);
	}

}