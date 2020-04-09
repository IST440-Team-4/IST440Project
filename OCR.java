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
import java.util.logging.Level;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/*
 *  OCR
 *
 *  Description:
 *    TODO
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
public class OCR {
    
  public static String Tesseract (File ocrInput) throws IOException {
      
      String result="";
      File imageFile = ocrInput;
          
      ITesseract OCR = new Tesseract();
      OCR.setDatapath("assets/tessdata");
      
      try 
      {
        result = OCR.doOCR(imageFile);
      } 
      catch (TesseractException e) 
      {
          System.err.println(e.getMessage());
      }
      return result;
    
    } // Tesseract ()
  
      public OCR() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
  
 } // OCR Class
