/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package IST440Project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class for main application window
 * Contains all action listeners for buttons within main 
 * application window and calls all appropriate methods.
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
    
    //Initialize Metadata Labels
    @FXML
    private Label imageNameLabel;
    @FXML
    private Label imageTypeLabel;
    @FXML
    private Label imageAuthorLabel;
    @FXML
    private Label imageDateLabel;
    @FXML
    private Label imageSizeLabel;
    @FXML
    private ImageView imageView;

    File ocrInput;
    String ocrResult;
    
    /**
     * Will display an alert dialog with the passed Title, Header, and
     * Message. 
     * 
     * @param String of the title to display in the alert dialog box.
     * @param String of the header to display in the alert dialog box.
     * @param String of the message to display in the alert dialog box.
     */
    private void displayAlert (String alertTitle, String alertHeader, 
            String alertMessage) {
        
        // Local Variables
        Alert alert;                        // Alert Dialog Box
        int defaultHeight;                  // Default Height of Alert Dialog Box
        int defaultWidth;                   // Default Width of Alert Dialog Box
        
        // Initialize Local Variables
        alert = new Alert (AlertType.ERROR, alertMessage);
        defaultHeight = 200;
        defaultWidth = 400;
        
        // Set parameters for Alert Dialog Box
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(defaultWidth, defaultHeight);
                  
        // Display Alert Dialog Box
        alert.showAndWait();
        
    } // displayAlert ()
    
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
     * Will perform the actions required when the user selects
     * the File -> Exit menu item.
     */
    @FXML
    private void handleMenuExit () {
        
        Platform.exit();
        System.exit(0);
        
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
     * Will reset all fields to the default when the user selects
     * the File -> Reset menu item.
     */
    @FXML
    private void handleMenuReset () {
        
        ocrOutput.setText("");
        decryptionOutput.setText("");
        translationOutput.setText("");
        decryptionChooser.getSelectionModel().clearSelection();
        languageChooser.getSelectionModel().clearSelection();
        FilePath.setText("");
        imageNameLabel.setText("");
        imageTypeLabel.setText("");
        imageAuthorLabel.setText("");
        imageDateLabel.setText("");
        imageSizeLabel.setText("");
        imageView.imageProperty().set(null);
        
    } // handleMenuReset ()
    
    /**
     * Will perform the actions required when the user selects
     * the File -> Save menu item.
     */
    @FXML
    private void handleMenuSave () {
        
        // Local Variables
        FileChooser fc;
        File file;
        
        // Initialize Local Variables
        fc = new FileChooser();
        fc.setTitle("Save Image File");
        
        // If source file is not null then display Save Dialog
        if (ocrInput != null) {
            
            // Set file defaults
            fc.setInitialDirectory(ocrInput.getParentFile());
            fc.setInitialFileName(ocrInput.getName());
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
                        
            // Display File Chooser Dialog and store results
            file = fc.showSaveDialog(null);
          
            // If destination file is not null then save               
            if (file != null) {
                try {
                    Files.copy(ocrInput.toPath(), file.toPath());
                } catch (IOException ex) {
                    displayAlert ("Save Image", "ERROR", "An unexpected error "
                            + "has occured.");
                    Logger.getLogger(AppFXMLController.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
            
        }  else {
            
            displayAlert ("Save Image", "ERROR", "An image file is not "
                    + "currently open! Please open and image file first.");
            
        } // if (ocrInput != null)
        
    } // handleMenuSave ()

    /** 
     * Constructs FileChooser object
     * Allows for users to navigate to image file of encrypted 
     * text on their system
     * 
     * @param event Select File button is clicked
     */
    @FXML
    public void FileButtonAction (ActionEvent event) {
        
        openImageFile ();
        
    } // FileButtonAction ()
    
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
    
    /**
     * Calls OCR method on the image file previously selected
     * @param event Run OCR button is clicked
     */
    @FXML
    public void ocrButtonAction (ActionEvent event) {        
        
        if (ocrInput != null) {
            
            try {
                ocrResult = OCR.Tesseract(ocrInput);
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            
            ocrOutput.setText(ocrResult);
        
        } else {
            
            displayAlert ("Save Image", "ERROR", "An image file is not "
                    + "currently open! Please open and image file first.");
            
        }
        
    } // ocrButtonAction ()
    
    /**
     * Will display a open File dialog for the user to select an image file. 
     * After the user selects the file, will verify the file is a valid image
     * file.
     */
    private void openImageFile () {
        
        // Local Variables
        FileChooser fc;
        Metadata imageMetadata;
        
        // Initialize Local Variables
        fc = new FileChooser();
        
        // Set FileChooser parameters
        fc.setTitle("Open Image File");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG Files", "*.png"),
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        // Display File Chooser Dialog and store results
        ocrInput = fc.showOpenDialog(null);
        
        if (isImageFileValid (ocrInput)) {
            
            FilePath.setText(ocrInput.getName());
            
            // Retrieve Metadata from file
            imageMetadata = new Metadata (ocrInput);
            
            // Set Metadata Labels
            imageNameLabel.setText(imageMetadata.getImageName());
            imageTypeLabel.setText(imageMetadata.getImageType());
            imageAuthorLabel.setText(imageMetadata.getImageAuthor());
            imageDateLabel.setText(imageMetadata.getImageDate());
            imageSizeLabel.setText(String.format("%,d", 
                    imageMetadata.getImageSize()));
            imageView.setImage(imageMetadata.getImagePreview());
                        
        } else {
            
            FilePath.setText("Error");
            
        }
    
    } // openImageFile ()
    
    /**
     * This method calls various decryption methods based upon the selected 
     * value in the Decryption Selection dropdown.
     * 
     * @param event Run Decryption button is clicked
     */
    @FXML
    public void decryptionButtonaction (ActionEvent event) {
        
        // Local Variables
        String decryptionChoice;             // Decryption Type Selected
        String encryptedText;                // Text for OCR Textarea
        
        // Initialize Local Variables
        decryptionChoice = "";
        encryptedText = "";
        
        // Validate that decryption choice selected. 
        if (decryptionChooser.getValue() == null) {
            displayAlert ("Run Decryption", "ERROR", "Please select a "
                    + "Decryption method, and try again.");
            return;
        } else {
            decryptionChoice = decryptionChooser.getValue().toString();
        }
        
        // Validated that the ocrOutput textarea has text
        if (ocrOutput.getText().trim().length() == 0) {
            displayAlert ("Run Decryption", "ERROR", "No OCR data. Please "
                    + "perform an OCR Scan on an image file first, and then "
                    + "try again.");
            return;
        } else {
            encryptedText = ocrOutput.getText().trim();
        }
               
        // Perform decryption 
        switch (decryptionChoice) {
            case "Caesar":
                decryptionOutput.setText(CaesarBruteForce.
                        Caesar(encryptedText));
                break;
            case "Atbash":
                decryptionOutput.setText(Atbash.
                        AtbashDecrypt(encryptedText.toUpperCase()));
                break;
            case "Word Scramble":
                decryptionOutput.setText(WordDescramble.
                        WordDescrambler(encryptedText));
                break;
            default:
                break;
        }

    } // decryptionButtonaction ()
    
    /**
     * Method makes a call to Google Translate API based upon 
     * the selected value of the translation dropdown.
     * 
     * @param event Run Translation button is clicked 
     */
    @FXML
    public void translateButtonAction (ActionEvent event) {
        
        // Local Variables
        String translationChoice;            // Translation Type Selected
        String decryptedText;                // Decrypted Text
                
        // Initialize Local Variables
        translationChoice = "";
        decryptedText = "";
        
        // Validate that translation type is selected. 
        if (languageChooser.getValue() == null) {
            displayAlert ("Run Translation", "ERROR", "Please select the "
                    + "language of the message to translate.");
            return;
        } else {
            translationChoice = languageChooser.getValue().toString();
        }
        
        // Validated that the ocrOutput textarea has text
        if (decryptionOutput.getSelectedText().trim().length() == 0) {
            displayAlert ("Run Translation", "ERROR", "No message text. Please "
                    + "select the decryption text to translate, and then "
                    + "try again.");
            return;
        } else {
            decryptedText = decryptionOutput.getSelectedText().trim();
        }
        
        // Translate Text, and display results
        try {
            translationOutput.setText(Translate.translateText(decryptedText));
        } catch (Exception e) {
            System.out.println(e);
        }
                        
    } // translateButtonAction ()
    
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
        languageChooser.getItems().add("French");
        languageChooser.getItems().add("German");
        languageChooser.getItems().add("Spanish");
    }    
    
    /**
     *
     * @throws IOException
     */
    public AppFXMLController() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    } 
    
} // Class AppFXMLController 