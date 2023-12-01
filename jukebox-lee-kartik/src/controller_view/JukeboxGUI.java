/**
 * @author Kartik, LEE
 *	This is the main GUI file with all the JavaFX Components
 */

package controller_view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.JukeboxAccount;
import model.PlayList;
import model.Song;
import java.io.Serializable;

@SuppressWarnings("serial")
public class JukeboxGUI extends Application implements Serializable {

	public static void main(String[] args) {
		launch(args);
	}

	transient LoginCreateAccountPane loginPane;
	private transient BorderPane everything;
	private transient PlayList playlist;
	private transient ListView<Song> listview;
	private transient Button playButton;
	private transient TableView<Song> table;
	private transient ObservableList<Song> observableSongs;
	
	private transient Label songList;
	private transient Label songQueue;
	
	private transient HBox topView;
		
	private transient JukeboxAccount user;

	@Override
	public void start(Stage primaryStage) throws Exception {
		gettingSongDetails();
	    LayoutGUI();
	    getPlayList();
	    tableView();
		
		Scene scene = new Scene(everything, 850, 650);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Jukebox");
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			closingAlertSavingChanges();
		});
	}

	/**
	 * All the layout of the main GUI
	 */
	private void LayoutGUI() {
		everything = new BorderPane();
		table = new TableView<Song>();

		user = new JukeboxAccount("Chris", "1");
		
		loginPane = new LoginCreateAccountPane();
		everything.setPadding(new Insets(10, 10, 10, 10));

		playlist = new PlayList();

		topView(); 
		everything.setTop(topView);

		playButton = new Button("Play");
	    everything.setCenter(playButton);
		
	    listView();
		everything.setRight(listview);
		everything.setBottom(loginPane);
		everything.setLeft(table);
		
		registerHandlers();

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void listView() {
		listview = new ListView(playlist.getPlayList());
	    listview.setMaxHeight(450);
	    listview.setMinWidth(300);
	    listview.setMouseTransparent(true);
	    listview.setFocusTraversable(false);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void tableView() {
		table.setItems(observableSongs);

		TableColumn<Song, String> titleCol = new TableColumn<Song, String>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory("name"));

		TableColumn<Song, String> artistCol = new TableColumn<Song, String>("Artist");
		artistCol.setCellValueFactory(new PropertyValueFactory("artist"));

		TableColumn<Song, String> timeCol = new TableColumn<Song, String>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory("songTime"));

		titleCol.setMinWidth(175);
		artistCol.setMinWidth(175);
		timeCol.setMinWidth(20);

		table.getColumns().setAll(titleCol, artistCol, timeCol);

		table.setMinWidth(400);
		table.setMaxSize(400, 450);
	}

	private void topView() {
		songList = new Label("Song List");
		songList.setStyle("-fx-font-size: 22;");
		
		songQueue = new Label("Song Queue");
		songQueue.setStyle("-fx-font-size: 20;");
		
		topView = new HBox(430, songList, songQueue);
		topView.setPadding(new Insets(10, 0, 0, 15));
	}
	
	/**
	 * This will add song objects to an observable list of songs so that the table
	 * view can access them
	 */
	private void gettingSongDetails() {
		observableSongs = FXCollections.observableArrayList();

		Song capture = new Song("Capture", "Pikachu", "0:05");
		Song danse = new Song("Danse Macabre Violin Hook", "Kevin MacLeod", "0:37");
		Song determined = new Song("Determined Tumbao", "FreePlay Music", "0:20");
		Song longing = new Song("Longing In Their Hearts", "Michael O'Keefe/Bonnie Raitt", "4:48");
		Song loop = new Song("Loping Sting", "Kevin MacLeod", "0:05");
		Song swing = new Song("Swing Cheese", "FreePlay Music", "0:15");
		Song curtain = new Song("The Curtain Rises", "Kevin MacLeod", "0:28");
		Song fire = new Song("Untameable Fire", "Pierre Langer", "4:42");

		observableSongs.add(capture);
		observableSongs.add(danse);
		observableSongs.add(determined);
		observableSongs.add(longing);
		observableSongs.add(loop);
		observableSongs.add(swing);
		observableSongs.add(curtain);
		observableSongs.add(fire);

	}

	
	/**
	 * This will play the song if songsPlayed is less than 3, and add in 
	 * queue if song is being played right now
	 */
	private void registerHandlers() {	
		playButton.setOnAction(event -> {
			if (loginPane.loggedInStatus() == true) {
				JukeboxAccount user = loginPane.getUser();

				// If played <= 3 songs
				if (user.canPlaySong() == true) {
					int currentIndex = table.getSelectionModel().getSelectedIndex();
					String song = observableSongs.get(currentIndex).getName();

					playlist.queueUpNextSong(String.join("", song.split(" ")) + ".mp3");
					loginPane.updateNumSongs();
					
					if (!playlist.getIsPlaying()) {
						listview.getSelectionModel().selectFirst();
						playlist.playSongs(user);
						loginPane.updateNumSongs();
					}
				}

				else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Your play limit maxed out, try tomorrow");

					@SuppressWarnings("unused")
					Optional<ButtonType> result = alert.showAndWait();

				}
			}

		});

	}

	/**
	 * If user choose to get the old playlist back, then the songs will
	 * start playing according to the old playlist, otherwise it just get
	 * a new list
	 */
	private void getPlayList() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Start up Option");
		alert.setHeaderText("Read saved data?");
		alert.setContentText("Press cancel while system testing.");

		Optional<ButtonType> result = alert.showAndWait();
		
		// It will also get all the accounts data back as well
		if (result.get() == ButtonType.OK) {
			playlist.readPlayList();
			user.readSavedAccounts();
			
			listView();
			everything.setRight(listview);
			
			if (!playlist.getIsPlaying()) {
				listview.getSelectionModel().selectFirst();
				playlist.playSongs(user);
			}
		} 
		else {
			newJukeboxPlaylist();
		}
	}
	
	/**
	 * This alert will be shown at the time of closing the application
	 * and if the user clicks OK, then the program will save the playlist 
	 * sequence and accounts data. And if the user clicks no, then it
	 * start from a clean slate from the next time. 
	 */
	private void closingAlertSavingChanges() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Shut down Option");
		alert.setHeaderText("Save data?");
		alert.setContentText("Press cancel while system testing.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			playlist.savePlayList();
			user.saveNewAccounts();
		} else {
			newJukeboxPlaylist();
		}
	}
	
	
	private void newJukeboxPlaylist() {
		JukeboxAccount.accountsTable.clear();
		
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
		
		System.out.println("Current table - " + JukeboxAccount.accountsTable);
	}


}