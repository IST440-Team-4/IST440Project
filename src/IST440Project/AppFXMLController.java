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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author austi
 */
        
public class AppFXMLController implements Initializable {
    
    @FXML
    private Button FileButton;
    @FXML
    private Button ocrButton;
    @FXML
    private TextArea ocrOutput;
    @FXML
    private Label FilePath;
    File ocrInput;
    
    //Allows user to browse for file to pass into OCR scanner
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
    public void ocrButtonAction(ActionEvent event)
    {
        String result="";
                
        try 
        {
            result = OCR.Tesseract(ocrInput);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        ocrOutput.setText(result);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
