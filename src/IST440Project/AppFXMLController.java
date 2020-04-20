/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author austi
 */
        
public class AppFXMLController implements Initializable {
    
    //Initialize JavaFX buttons
    @FXML
    private Button FileButton;
    @FXML
    private Button ocrButton;
    @FXML
    private Button decryptButton;
    @FXML
    private Button translateButton;
    
    //Initialize label to show user file chosen
    @FXML
    private Label FilePath;
    
    //Initialize Dropdown menus
    @FXML
    private ChoiceBox decryptionChooser;
    @FXML
    private ChoiceBox languageChooser;
    
    //Initialize Text Areas
    @FXML
    private TextArea decryptionOutput;
    @FXML
    private TextArea translationOutput;
    @FXML
    private TextArea ocrOutput;

    File ocrInput;
    String ocrResult;
    
    /**
     * Will perform the actions required when the user selects
     * the Help -> About menu item.
     */
    @FXML
    private void handleMenuAbout () {
        
        // Local Variables
        Alert alert;                         // About Dialog Box
        
        // Initialize Local Variables
        alert = new Alert (AlertType.INFORMATION, "", ButtonType.CLOSE);
        
        // Set parameters for About Dialog Box
        alert.setTitle("About");
        alert.setHeaderText("About");
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 600);
                        
        // Set text for the About text dialog
        alert.setContentText("LionCipher is an application developed by Team 4"
                + "\nas part of the IST440 Capstone project. The LionCipher"
                + "\napplication will decrypt a previously handwritten encrypted"
                + "\nmessage. The LionCipher application also has the ability to"
                + "\ntranslate messages written in foreign languages."
                + "\n\nContributors:"
                + "\n    Austin J. Lonjin"
                + "\n    William E. Morris (WEM)"
                + "\n    Joshua Sadecky (JS)"
                + "\n    Robert Sanders (RS)"
                + "\n    Simon S. Stroh (SSS)"
                + "\n\nLicensed under the Apache License, Version 2.0 "
                + "(the \"License\");"
                + "\nyou may not use this  except in compliance with "
                + "the License."
                + "\nYou may obtain a copy of the License at"
                + "\n\n          http://www.apache.org/licenses/LICENSE-2.0"
                + "\n\nUnless required by applicable law or agreed to in "
                + "writing, software"
                + "\ndistributed under the License is distributed on "
                + "an \"AS IS\" BASIS,"
                + "\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either "
                + "\nexpress or implied. See the License for the specific "
                + "language governing "
                + "\npermissions and limitations under the License.\n"); 
       
        // Display About Dialog Box and wait for close
        alert.showAndWait();
        
    } // handleMenuAbout ()
      
    /**
     * 
     * @param event 
     */
    @FXML
    private void handleMenuExit (ActionEvent event) {  
        
    } // handleMenuExit ()
    
    /**
     * Will perform the actions required when the user selects
     * the File -> Open menu item, or selects the Open Image button.
     */
    @FXML
    private void handleMenuOpen () {
        
        openImageFile ();
        
    } // handleMenuOpen ()
    
    /**
     * 
     * @param event 
     */
    @FXML
    private void handleMenuSave (ActionEvent event) {
        
    } // handleMenuSave ()
       
    //Allows user to browse for file to pass into OCR scanner
    @FXML
    public void FileButtonAction(ActionEvent event)
    {
        FileChooser fc = new FileChooser(); //Create Filechooser object
        ocrInput = fc.showOpenDialog(null); //Sets ocrInput as whatever the selected file is
        
        if (ocrInput != null)
        {
            FilePath.setText(ocrInput.getName());
        }
        else
        {
            FilePath.setText("Error");
        }
        
    }
    
    /**
     * Will verify if the passed file object is a valid image file
     * 
     * @param imageFile the file object to validate
     * @return if the passed file object is valid
     */
    private boolean isImageFileValid (File imageFile) {
        
        // Local Variables
        boolean returnValue;        
       
        // Initialize Local Variables
        returnValue = true;
        
        // Test if passed file is null
        if (imageFile == null) {
            returnValue = false;
        }
        
        // TODO: Add file type check
        
        return (returnValue);
        
    } // isImageFileValid ()
    
    //Takes File Input from FileButtonAction Method and runs OCR
    @FXML
    public void ocrButtonAction(ActionEvent event)
    {         
        try 
        {
            ocrResult = OCR.Tesseract(ocrInput);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        ocrOutput.setText(ocrResult);
    }
    
    /**
     * Will display a open File dialog for the user to select an image file. 
     * After the user selects the file, will verify the file is a valid image
     * file.
     */
    private void openImageFile () {
        
        // Local Variables
        FileChooser fc;
        
        // Initialize Local Variables
        fc = new FileChooser();
        
        // Display File Chooser Dialog and store results
        ocrInput = fc.showOpenDialog(null);
        
        if (isImageFileValid (ocrInput)) {
            FilePath.setText(ocrInput.getName());
        } else {
            // TODO: Add error dialog box, and logging
            FilePath.setText("Error");
        }
    
    } // openImageFile ()
    
    //Runs selected decryption method on OCR output string
    @FXML
    public void decryptionButtonaction(ActionEvent event)
    {
        String decryptionChoice = decryptionChooser.getValue().toString();
        String encryptedText = ocrResult.trim();
        if (decryptionChoice.equals("Caesar"))
        {
            decryptionOutput.setText(CaesarBruteForce.Caesar(encryptedText));
        }
        
        else if (decryptionChoice.equals("Atbash"))
        {
            decryptionOutput.setText(Atbash.AtbashDecrypt(encryptedText.toUpperCase()));
        }
        
        else if (decryptionChoice.equals("Word Scramble"))
        {
            decryptionOutput.setText(WordDescramble.WordDescrambler(encryptedText));
        }
    }
    
    //Run selected translation operation on decryption output
    @FXML
    public void translateButtonAction(ActionEvent event)
    {
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add options to decryption dropdown
        decryptionChooser.getItems().add("Atbash");
        decryptionChooser.getItems().add("Caesar");
        decryptionChooser.getItems().add("Word Scramble");
        
        //Add options to translation dropdown
        languageChooser.getItems().add("Spanish to English");
        languageChooser.getItems().add("French to English");
        languageChooser.getItems().add("German to English");
    }    
    
    
}