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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 * FXML Controller class for main application window
 * Contains all action listeners for buttons within main application window and calls all appropriate methods
 *
 * @author AJL5818
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
     * Constructs FileChooser object
     * Allows for users to navigate to image file of encrypted text on their system
     * @param event Select File button is clicked
     */
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
     * Calls OCR method on the image file previously selected
     * @param event Run OCR button is clicked
     */
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
     * This method calls various decryption methods based upon the selected value in the Decryption Selection dropdown.
     * @param event Run Decryption button is clicked
     */
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
    
    /**
     * Method makes a call to Google Translate API based upon the selected value of the translation dropdown.
     * @param event Run Translation button is clicked 
     */
    @FXML
    public void translateButtonAction(ActionEvent event)
    {
        
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
    
    /**
     *
     * @throws IOException
     */
    public AppFXMLController() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    } 
}
