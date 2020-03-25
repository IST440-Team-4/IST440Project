/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

/**
 *
 * @author austi
 */
public class Atbash {
    
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
    
}
