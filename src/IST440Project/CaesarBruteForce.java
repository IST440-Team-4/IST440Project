/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author austi
 */
public class CaesarBruteForce 
{
    public static String Caesar (String ciphertext)
    {
        String text="";
        String returnString="";
        String c = ciphertext;
        char charCipherText [] = c.toCharArray();
        int index =0;
        char chr;
        
        for (int key=1; key<26;key++)
        {
            text="";
            
            for(int i=0; i<charCipherText.length;i++)
            {
                index = charCipherText[i]-97;
                index = (index-key +26)%26;
                chr = (char) (index+97);
                text += chr;
            }
            returnString += "\n" + text;
        }
        return returnString;
    }
    public CaesarBruteForce() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
}
