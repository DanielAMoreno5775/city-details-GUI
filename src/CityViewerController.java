import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.util.Iterator;


public class CityViewerController {
    //create variables for the different buttons, text fields, labels, and combination boxes
    @FXML
    private ComboBox<City> cityCombo;
    @FXML
    private Label fileReadMsg;
    @FXML
    private TextField filenameInput;
    @FXML
    private ComboBox<State> stateCombo;
    @FXML
    private Label timeZoneLbl;
    @FXML
    private Label zipCodeLbl;

    //create local data fields
    private OrderedAddOnce<State> stateList = new OrderedAddOnce <>();
    
    //read in the data from the file based on the user's input
    @FXML
    void $readFileButton(ActionEvent event) {
        String fileName = filenameInput.getText();
        File dataFile = null;
        Scanner inputFileData = null;
        String word = "";
        int numOfLinesRead = 0;
        
        //empty out all of the lists so that the list won't contain multiple copies even if you open the file multiple times
        timeZoneLbl.setText("");
        zipCodeLbl.setText("");
        fileReadMsg.setText("");
        stateList.empty();
        stateCombo.getSelectionModel().clearSelection();
        stateCombo.setValue(null);
        stateCombo.getItems().clear();
        stateCombo.getItems().removeAll();
        stateCombo.setVisibleRowCount(1);
        cityCombo.getSelectionModel().clearSelection();
        cityCombo.setValue(null);
        cityCombo.getItems().clear();
        cityCombo.getItems().removeAll();
        cityCombo.setVisibleRowCount(1);
        
        try {
            //ensure that the user input something
            if (fileName.equals("")) {
                throw new FileNotFoundException("You didn't input anything");
            }
            
            dataFile = new File(fileName);
            //prevent issues involving the file
            if (!(dataFile.exists()) || !(dataFile.canRead()) || (dataFile.isDirectory())) {
                throw new FileNotFoundException("File doesn't exist, can't be read, or is a directory");
            }
            else {
                //read the file
                inputFileData = new Scanner (dataFile);
                while (inputFileData.hasNextLine()) {
                    //store the next line
                    String line = inputFileData.nextLine();
                    Scanner lineScanner = new Scanner(line);
                    lineScanner.useDelimiter(",");
                    numOfLinesRead += 1;
                    //ignore first line of column names
                    if (numOfLinesRead > 1) {
                        //split up the words within the line
                        int zip = lineScanner.nextInt();
                        String cityName = lineScanner.next();
                        String stateName = lineScanner.next();
                        double latitude = lineScanner.nextDouble();
                        double longitude = lineScanner.nextDouble();
                        int timezone = lineScanner.nextInt();
                        String daylightStr = lineScanner.next();
                        boolean yesDaylight = false;
                        if (daylightStr.charAt(0) == '1') {
                            yesDaylight = true;
                        }
                        //store everything in a city and throw it in the group
                        City tempCity = new City(zip,cityName,latitude,longitude,timezone,yesDaylight);
                        State tempState = stateList.addOnce(new State (stateName));
                        tempState.addCity(tempCity);
                    }
                }
                inputFileData.close();
                //set the message color to a shade of green that I can see
                fileReadMsg.setTextFill(Color.web("#4BB543"));
                fileReadMsg.setText("The cities have been read");
                //acquire and store the list of all city names in the combination box
                stateCombo.setVisibleRowCount(10);
                Iterator<State> stateIter = stateList.iterator();
                while (stateIter.hasNext()) {
                    stateCombo.getItems().add(stateIter.next());
                }
            }
        }
        //can be thrown by inputFileData = new Scanner (dataFile);
        catch (FileNotFoundException e){
            cityCombo.setVisibleRowCount(1);
            stateCombo.setVisibleRowCount(1);
            System.out.println("Scanner error opening the file " + fileName);
            System.out.println(e.getMessage());
            //set the message color to a more friendly red
            fileReadMsg.setTextFill(Color.web("#FF003C"));
            fileReadMsg.setText("The file was unsuccessfully read");
        } 
        //can be thrown by dataFile.exists() or dataFile.canRead()
        catch (SecurityException e) {
            cityCombo.setVisibleRowCount(1);
            stateCombo.setVisibleRowCount(1);
            System.out.println("File error opening the file " + fileName);
            System.out.println(e.getMessage());
            //set the message color to a more friendly red
            fileReadMsg.setTextFill(Color.web("#FF003C"));
            fileReadMsg.setText("The file was unsuccessfully read");
        }
    }
    
    @FXML
    void $readCityComboBox(ActionEvent event) {
        if (cityCombo.getValue() != null) {
            timeZoneLbl.setText(cityCombo.getValue().getTimezone());
            zipCodeLbl.setText(String.valueOf(cityCombo.getValue().getZipcode()));
        } else {
            timeZoneLbl.setText("");
            zipCodeLbl.setText("");
        }
    }

    @FXML
    void $readStateComboBox(ActionEvent event) {
        //reset the city combo box
        cityCombo.getSelectionModel().clearSelection();
        cityCombo.setValue(null);
        cityCombo.getItems().clear();
        cityCombo.getItems().removeAll();
        cityCombo.setVisibleRowCount(1);
        
        //fill the city combo box
        if (stateCombo.getValue() != null) {
           //create an iterator object to iterate through it
           Iterator<City> cityIter = stateCombo.getValue().getCityList().iterator();
           
           //alter the size of the dropdown depending on the number of cities for that state
           int numOfCities = stateCombo.getValue().getNumOfCities();
           if (numOfCities < 10 && numOfCities > 0) {
               cityCombo.setVisibleRowCount(numOfCities);
           } else if (numOfCities <= 0 ) {
               cityCombo.setVisibleRowCount(1);
           } else {
               cityCombo.setVisibleRowCount(10);
           }
           
           //populate the city combo box
           while (cityIter.hasNext()) {
                    cityCombo.getItems().add(cityIter.next());
            }
        }
    }
}
