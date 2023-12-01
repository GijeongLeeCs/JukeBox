package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

//import javafx.collections.FXCollections;
//import javafx.scene.control.ListView;
//
import java.util.HashMap;

public class JukeboxAccount implements Serializable {
	private String name;
	private LocalDate lastSelectionDate;
	private int songsSelectedToday;

	private LocalDate today;
	private int numSongsPlayed;
	private boolean login = false;
	private String userName;

	// Data: String is the name, ArrayList: Password, songsPlayed, Day
	public static Hashtable<String, ArrayList<Object>> accountsTable = new Hashtable<>();
	static {
		if (accountsTable.isEmpty()) {
			JukeboxAccount.accountsTable.put("Chris", new ArrayList<>() {
				{
					add("1");
					add(0);
					add(LocalDate.now());
				}
			});
			JukeboxAccount.accountsTable.put("Devon", new ArrayList<>() {
				{
					add("22");
					add(0);
					add(LocalDate.now());
				}
			});
			JukeboxAccount.accountsTable.put("River", new ArrayList<>() {
				{
					add("333");
					add(0);
					add(LocalDate.now());
				}
			});
			JukeboxAccount.accountsTable.put("Ryan", new ArrayList<>() {
				{
					add("4444");
					add(0);
					add(LocalDate.now());
				}
			});
		}
	}

	public static HashMap<String, String> userDictionary = new HashMap<>();

	public JukeboxAccount(String name) {
		this.name = name;
		this.lastSelectionDate = LocalDate.now();
		this.songsSelectedToday = 0;
	}

	public JukeboxAccount(String name, String password) {
		today = LocalDate.now();
		numSongsPlayed = 0;
		
		this.userName = name;

		// New account
		if (!accountsTable.containsKey(name)) {
			accountsTable.put(name, new ArrayList<>() {
				{
					add(password);
					add(0);
					add(LocalDate.now());
				}
			});
			
			this.userName = name;
			this.login = true;
			System.out.println("Account created success for " + name);
			
		} else {
			// Invalid credentials
			if (!accountsTable.get(name).get(0).equals(password)) {
				this.login = false;
			}

			// Correct login info
			if (accountsTable.get(name).get(0).equals(password)) {
				this.login = true;
				System.out.println("Login done");
			}
		}
		
	}

	public boolean getLogin() {
		return login;
	}
    
	public Hashtable<String, ArrayList<Object>> getAccountsTable() {
		return accountsTable;
	}
	
	public String getUserName() {
		return userName;
	}

	public boolean canPlaySong() {
		ArrayList<Object> curUser = accountsTable.get(userName);
		if (!curUser.get(2).equals(LocalDate.now())) {
			curUser.set(1, 1);
			curUser.set(2, LocalDate.now());
			System.out.println("Writing 1...");
			return true;
		} else 
			if (curUser.get(2).equals(LocalDate.now())) {
			numSongsPlayed = (int) curUser.get(1);
			if (numSongsPlayed < 3) {
				numSongsPlayed += 1;
				curUser.set(1, numSongsPlayed);
				return true;
			} else {
				return false;
			}
		}
		
		// Resets the date and number of songs played if day is not the same
		return false;

	}
	
	public void saveNewAccounts() {
        try {
            FileOutputStream bytesToDisk = new FileOutputStream("accounts.ser");
            ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
            outFile.writeObject(accountsTable);
            outFile.close();
        } catch (IOException ioe) {
            System.out.println("Write failed... " + ioe);
        }
    }
	
	@SuppressWarnings("unchecked")
	public void readSavedAccounts() {
        try {
            FileInputStream disk = new FileInputStream("accounts.ser");
            ObjectInputStream inputStreamIn = new ObjectInputStream(disk);
            
            accountsTable = (Hashtable<String, ArrayList<Object>>) inputStreamIn.readObject();
            inputStreamIn.close();
        } catch (ClassNotFoundException | IOException a) {
            System.out.println("Something went wrong! " + a);
        }
    }
	
	public void pretendItsTomorrow() {
		lastSelectionDate = LocalDate.now().plusDays(1);
		songsSelectedToday = 0;
	}

	public int songsSelectedToday() {
		return songsSelectedToday;
	}

	public void recordOneSelection() {
		if (canSelect()) {
			songsSelectedToday++;
		}
	}

	public boolean canSelect() {
		return songsSelectedToday < 3;
	}
}
