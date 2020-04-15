/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Atbash is an encryption method that uses the backwards alphabet as a key
 * @author AJL5818
 */
public class Atbash {
    
    /**
     * Constructs an object to decrypt Atbash encryption by reversing the alphabet
     * Initial for loop iterates through and reverses the alphabet
     * Nested for loop structure iterates through ciphertext and looks for a character match in the reverse alphabet.
     * If a match is found, the corresponding character in the standard alphabet is concatenated to the return value
     * @param cipherText encoded text result from OCR scan
     * @return Characters are concatenated back into a string and returned.
     */
    public static String AtbashDecrypt(String cipherText)
    {
        String alphabet ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String reverseAlphabet ="";
        
        for(int i = alphabet.length()-1; i >-1; i--)
        {
            reverseAlphabet += alphabet.charAt(i);
        }
        
        String decipheredText ="";
        
        for (int i=0; i<cipherText.length(); i++)
        {
            if(cipherText.charAt(i) == (char)32)
            {
                decipheredText +=" ";
            }
            else
            {
                for(int j=0; j<reverseAlphabet.length(); j++)
                {
                    if (cipherText.charAt(i) == reverseAlphabet.charAt(j))
                    {
                        decipheredText += alphabet.charAt(j);
                        break;
                    }
                } //inner for   
            } //else
        } //outer for
        return decipheredText;
    }

    /**
     *
     * @throws IOException
     */
    public Atbash() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
}
