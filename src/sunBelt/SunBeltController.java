/**
 * This is the controller class for the GUI in the SunBelt Controller project. 
 * This file Maintains the functionality of the Gui while useing the MainApp file 
 * for processing. 
 * 
 */
package sunBelt;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sunBelt.model.Game;

public class SunBeltController {
	// Creating an instance of the MainApp to access its methods.
	MainApp prog = new MainApp();
	
	
	 // Fields to control the Table  on the Display
	 
	@FXML
    private TableView<Game> Table;
	@FXML
	private TableColumn<Game, Integer> gameId;
	@FXML
	private TableColumn<Game, String> date;
	@FXML
	private TableColumn<Game, String> hTeam;
	@FXML
	private TableColumn<Game, String> vTeam;
	@FXML
	private TableColumn<Game, Integer> hScore;
	@FXML
	private TableColumn<Game, Integer> vScore;
    
	//Field for the Selection List
	@FXML
	private ListView<String> teamList;
	
	//TODO field for the Overall Game record That is shown under the selector list. Needs to be populated.
	@FXML
	private TextField recordText;
	
	
	
	// Field variables for the add game Function
	@FXML
	private TextField idate;
	@FXML
	private ComboBox<String> iHome;
	@FXML
	private ComboBox<String> iVisiting;
	@FXML
	private TextField iHomeScore;
	@FXML
	private TextField iVisitingScore;
	@FXML
	private Button submitBTN;
	
	@FXML
	private Label CurrentTeam;
	
	
	
	// Field variables that control the Menu Actions
	 
	@FXML
	private MenuItem close;
	
	@FXML
	private MenuItem help; 
	
	//Table Popup Menu controller
	@FXML
	private MenuItem removeGame;
	
    
	
	
	/**
	 * This is the Main Constructor for the Sunbelt Controller. When invoked it
	 * Initializeses the Game Table and Team List. Along with the Help Dialog and 
	 * All the Event Listeners for the GUI.
	 */
    public SunBeltController(){
    	
    }
    
    /**
     * 
     */
    public void displayContext(){
    	 
     }
    
    
    
    /**
     * Initialize is called when the constructor is called for the object. 
     * it initializes the Displays and event Listeners. and Describes the actions to take on 
     * events.
     * 
     */
    @FXML
    private void initialize() {
    	//initiate the team list on the left side selecting all teams to be Selected
    	teamList.setItems(prog.teams);
    	iHome.setItems(prog.sTeams);
    	iVisiting.setItems(prog.sTeams);
    	teamList.getSelectionModel().clearAndSelect(0);
    	
    	
      // Initialize the game table
        gameId.setCellValueFactory(new PropertyValueFactory<Game, Integer>("gameID"));
        date.setCellValueFactory(new PropertyValueFactory<Game, String>("gameDate"));
        hTeam.setCellValueFactory(new PropertyValueFactory<Game, String>("gameHName"));
        vTeam.setCellValueFactory(new PropertyValueFactory<Game, String>("gameVName"));
        hScore.setCellValueFactory(new PropertyValueFactory<Game, Integer>("gameHScore"));
        vScore.setCellValueFactory(new PropertyValueFactory<Game, Integer>("gameVScore"));
        
        //Listener checks for Changes in the Selection on the ListView.
        teamList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        	
        	@Override //When Changed it updates the displayGamesList that is the auto populator for the table.
        	public void changed(ObservableValue<? extends String> observable, 
        			String oldTeam, String newTeam){
        		prog.setCurrentTeam(prog.getTeamID(newTeam));
        		prog.findRecord();
        		if(!(prog.getTeamID(newTeam)=="All Teams")){
        			recordText.setText( ""+ prog.getCurrentTeamWins() + " / " +prog.getCurrentTeamLosses());
        			}else {
        				recordText.setText("N/A");
        			}
        		prog.updateDisplayGamesList();
        	}
        	});
        //Listener for the Submit Button for Insert
        submitBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
                if(prog.vailidateInput(idate.getText(),iHome.getValue(),iVisiting.getValue(),iHomeScore.getText(),iVisitingScore.getText())){
                	Game g = new Game(idate.getText(),prog.getTeamID(iHome.getValue()),prog.getTeamID(iVisiting.getValue()),iHomeScore.getText(),iVisitingScore.getText());
                	//System.err.println(g.toString());
                	prog.insertData(g);
                }else{
                	System.err.println("Validation FAIL");
            }}}
        );
        
        //For when Remove Game is Clicked.
        removeGame.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent t){
        		prog.removeGame(Table.getSelectionModel().getSelectedItem());
        	}
        });
        
        // For when exit is clicked in the Menu
        close.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent t){
        		System.exit(0);
        	}
        });
        //when help-about is clicked call MainApp.about() to display the dialog box
        help.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent t){
        		prog.about();
        	}
        });
    }
     
     /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
     public void setMainApp(MainApp main) {
         prog = main;
         // Add observable list data to the table
         Table.setItems(prog.getDisplayGames());
     }
     
}
