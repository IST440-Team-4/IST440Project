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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 *  MainApp
 *
 *  Description:
 *    The IST440Project Application will decrypt a previously handwritten 
 *    encrypted message. The MainApp will lauch the JavaFX GUI.
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

public class MainApp extends Application {

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        
//      Parent root = FXMLLoader.load(getClass().getResource("LogInFXML.fxml"));
      Parent root = FXMLLoader.load(getClass().getResource("AppFXML.fxml"));
      Scene scene = new Scene(root);
      stage.setTitle("Decryption App");
      stage.setScene(scene);
      stage.show();
    }
    
    /**
     *
     * @throws IOException
     */
    public MainApp() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) 
    {   
        launch(args);
    }
    
} // MainApp ()
