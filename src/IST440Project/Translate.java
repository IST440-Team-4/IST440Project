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

import java.io.*;
import com.google.gson.*;
import com.squareup.okhttp.*;

/**
 * The Translate class is a static wrapper class for Microsoft Azure 
 * Cognitive Language Translation servers.
 * 
 * @author Austin J. Lonjin
 * @author William E. Morris
 * @author Joshua Sadecky
 * @author Robert Sanders
 * @author Simon S. Stroh
 */
public class Translate {
    
    private static final String SUB_KEY = "b93ad148f1564aafa188ad4ec50d924b";
    private static final String END_POINT = 
            "https://api.cognitive.microsofttranslator.com";

    /**
     * Will send a translation request to Microsoft Azure Cognitive
     * language service, requesting a translation of the passed text.
     * The response will be formatted and returned.
     * 
     * @param text to be translated
     * @return String representing the response, including the translation.
     * @throws IOException 
     */
    public static String translateText (String text) throws IOException {
        
        // Local Variables
        String translation;
        String url;
        OkHttpClient client;
    
        // Initialize Local Variables
        url = END_POINT + "/translate?api-version=3.0&to=en";
        client = new OkHttpClient();
        
        // Perform a POST request to Service
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\n\t\"Text\": \"" + text + "\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", SUB_KEY)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        translation = response.body().string();
        
        // Return translation
        return (prettify(translation));
        
    } // translateText ()
   
    /**
     * 
     * @param json_text
     * @return 
     */
    private static String prettify (String json_text) {
       
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);       
                       
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
            
    } // prettify ()
    
} // Translate Class