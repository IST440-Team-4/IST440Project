/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Caesar Cipher is an encryption method that shifts characters down the alphabet by a set value
 * @author AJL5818
 */
public class CaesarBruteForce 
{
    /**
     * Constructs an object to brute force Caesar Cipher shifts
     * String input is converted into an array of characters
     * Shifting is achieved through the use of loops and performing mathematical calculations on the ASCII values of the characters
     * @param ciphertext encoded text result from OCR scan
     * @return Characters are concatenated back into strings and the method returns one string with all possible values separated by new lines.
     */
    public static String Caesar (String ciphertext)
    {
        String text="";
        String returnString="";
        String c = ciphertext.toLowerCase();
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

    /**
     *
     * @throws IOException
     */
    public CaesarBruteForce() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
}
