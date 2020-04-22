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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

/*
 *  FXML Controller class
 *
 *  Description:
 *    TODO:
 *
 *  Contributors:
 *    Austin J. Lonjin (AJL)
 *    William E. Morris (WEM)
 *    Joshua Sadecky (JS)
 *    Robert Sanders (RS)
 *    Simon S. Stroh (SSS)
 *
 *  Changes:
 *    02/07/20   Initial Release                             AJL,WEM,JS,RS,SSS
 */

/**
 *
 * @author austi
 */

public class LogInFXMLController implements Initializable {

    @FXML
    private Label LionCipher;

    @FXML
    private Label loginstatus;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;
    @FXML
    private ImageView LionShield;
    @FXML
    private ImageView whiteLock;
    @FXML
    private ImageView personSilhouette;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Establishes connection to server hosted in Google Cloud Services
     * POST command sends Username and Password to the server, which checks for a match and returns a response code
     * Response code is verified and user is granted access to the system, or error message is displayed.
     * @param event
     * @throws Exception 
     */
    @FXML
    public void Login(ActionEvent event) throws Exception
    {
        URL url = new URL("http://35.225.50.251:3000/login");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        String postParams = "email=" + txtUsername.getText() + "&password=" + txtPassword.getText();
        os.write(postParams.getBytes());
        os.flush();
        os.close();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          // Response code 200 equals `HttpURLConnection.HTTP_OK`
          loginstatus.setText("Successful Login");

          ((Node)(event.getSource())).getScene().getWindow().hide(); //Closes log in wind

          //Loads up second FXML page
          Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("AppFXML.fxml"));
          Scene scene = new Scene(root);
          stage.setTitle("Decryption App");
          stage.setScene(scene);
          stage.show();
        } else if (responseCode == 400) {
          // Response code 400 means authentication failed.
          loginstatus.setText("Incorrect Username or Password.");
        }

    }
    
    /**
     *
     * @throws IOException
     */
    public LogInFXMLController() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
}
