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
    
    /**
     * 
     * @param event
     */
    @FXML
    private void handleMenuAbout (ActionEvent event) {
        
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
     * the File Open Menu, or selects the Open Image button.
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