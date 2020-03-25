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
