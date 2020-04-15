/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Encrypted messages may simply have characters out of order. Generating all permutations will break this form of encryption.
 * @author AJL5818
 */
public class WordDescramble 
{
    /**
     * Constructs an object to decrypt scrambled strings through posting all possible permutations of the string
     * Each character of input string is converted into a string and added to ArrayList
     * Nested for loop structure cycles through all characters of input and concatenates all possible variations of characters.
     * Final for loop converts ArrayList of string values into one string delineated by new lines
     * @param scrambledText
     * @return String with all possible values separated by new lines
     */
    public static String WordDescrambler(String scrambledText)
    {
        String returnString ="";
        StringBuffer sb = new StringBuffer();
        List<String> permutations = new ArrayList<>();
        permutations.add(String.valueOf(scrambledText.charAt(0)));
        
        for (int i = 1; i < scrambledText.length(); i++)
        {
            for (int j = permutations.size()-1; j >= 0; j--)
            {
                String current = permutations.remove(j);
                
                for (int k = 0; k <= current.length(); k++)
                {
                    permutations.add(current.substring(0, k) + scrambledText.charAt(i) + current.substring(k));
                }
                
            }
        }
        
        for (String s : permutations)
        {
            sb.append(s);
            sb.append("\n");
        }
        
        returnString = sb.toString();
        return returnString;
    }    

    /**
     *
     * @throws IOException
     */
    public WordDescramble() throws IOException {
        AppLogger.log(Level.INFO, AppLogger.class.getName());
    }
    
}
