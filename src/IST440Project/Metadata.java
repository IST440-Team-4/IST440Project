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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;

/**
 * The Metadata class describes an object representing all the supported 
 * metadata for an associated image file. The Metadata class uses the Apache
 * Commons Imaging API to read the image metadata. Currently the Metadata Class
 * only supports the jpeg, png, and pdf image file format. Not all metadata fields 
 * are currently implemented at this time.
 * 
 * @author Austin J. Lonjin
 * @author William E. Morris
 * @author Joshua Sadecky
 * @author Robert Sanders
 * @author Simon S. Stroh
 */
public class Metadata {
    
    // Class Variables
    private ImageMetadata metadata;
    private JpegImageMetadata jpegMetadata;
    private Image imagePreview;
    private String imageDate;
    private String imageTime;
    private long imageSize;
    private String imageName;
    private String imageType;
    private String imageAuthor;
    
    /**
     * Constructs a new Metadata object, from the specified image file.
     * 
     * @param imageFile a support image file.
     */
    public Metadata (File imageFile) {
        
        // TODO need to assign default values, then extract
        
        // Intialize Class Variables
        this.setImageName(imageFile.getName());
        this.setImageSize(imageFile.length());
        this.setImageType(Metadata.probeFileType(imageFile.getName()));
        this.extractMetadata(imageFile);
        
    } // Metadata (imageFile)    

    /**
     * Will parse the image metadata into separate fields, and store
     * in class variables.
     * 
     * @param metadata image file metadata 
     * @throws ImageReadException 
     */
    private void extractMetadata (File imageFile) {
        
        // Declare Local Variables
        TagInfo tagInfo;
        TiffField field;
        String[] arrOfDateTime;
        String author;
        String date;
        String time;
        Image preview;
 
        // Initialize Local Variables
        author = "unknown";
        date = "0000:00:00";
        time = "00:00:00";
                
        // Initialize Default Preview
        preview = new Image ("file:./assets/images/preview.jpg", 
                293, 171, false, true);
        
        // Based on file type extract format specific metadata        
        if (this.getImageType().equals("image/jpeg")) {
            
            try {
                
                // Extract Image Metadata 
                this.metadata = Imaging.getMetadata (imageFile);

                // Extract File Metadata             
                if (metadata instanceof JpegImageMetadata) {

                    jpegMetadata = (JpegImageMetadata) metadata;

                    // Extract Author
                    tagInfo = MicrosoftTagConstants.EXIF_TAG_XPAUTHOR;
                    field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                    if (field != null) {
                        author = field.getStringValue();
                    }

                    // Extract Date & Time
                    tagInfo = TiffTagConstants.TIFF_TAG_DATE_TIME;
                    field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                    if (field != null) {
                        // TODO This is a hack, need to implement a better way of 
                        //      dealing with date/time
                        arrOfDateTime = field.getStringValue().split(" ", 2);
                        date = arrOfDateTime[0];
                        time = arrOfDateTime[1];
                    }

                } // if (metadata)
                
                // Extract Image Preview
                preview = new Image (imageFile.toURI().toURL().toString(), 
                        293, 171, false, true);
                
            } catch (ImageReadException | IOException ex) {
                Logger.getLogger(Metadata.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        } // if (getImageType())

        // Set Metadata Fields
        this.setImageAuthor(author);
        this.setImageDate(date);
        this.setImageTime(time);
        this.setImagePreview(preview);
        
        // Create Image Preview
        
    } // extractMetadata ()
    
    /**
     * Will return a String representing the Author of the image
     * 
     * @return String of the image author.
     */
    public String getImageAuthor () {
        
        return this.imageAuthor;
        
    } // getImageAuthor ()

    /**
     * Will return a String representing the Date of the image
     * in the form of MM/DD/YYYY
     * 
     * @return String of the image date "MM/DD/YYYY".
     */
    public String getImageDate () {
        
        return this.imageDate;
        
    } // getImageDate ()

    /**
     * Will return a String representing the filename of the image
     * 
     * @return String of the image name.
     */
    public String getImageName () {
        
        return this.imageName;
        
    } // getImageName ()
    
    /**
     * Will return a Image representing a preview of the image file.
     * 
     * @return Image which representing a preview of the image file.
     */
    public Image getImagePreview () {
        
        return this.imagePreview;
        
    } // getImagePreview ()

    /**
     * Will return a integer representing the size of the image
     * 
     * @return long of the image size.
     */
    public long getImageSize () {
        
        return this.imageSize;
        
    } // getImageSize ();

    /**
     * Will return a String representing the Time of the image
     * in the for of HH:MM:SS
     * 
     * @return String of the image time in formated as "HH:MM:SS".
     */
    public String getImageTime () {
        
        return this.imageTime;
        
    } // getImageTime ()

    /**
     * Will return a String representing the image type
     * 
     * @return String of the image type.
     */
    public String getImageType () {
        
        return this.imageType;
        
    } // getImageType ()
    
    /**
     * Will print the image file metadata to the console in a human 
     * readable format.
     */
    public void printMetadata () {
        
        System.out.println ("File Name = " + this.getImageName());
        System.out.println ("File Size = " + this.getImageSize() + " bytes");
        System.out.println ("File Type = " + this.getImageType());
        System.out.println ("Image Author = " + this.getImageAuthor());
        System.out.println ("Image Date = " + this.getImageDate());
        System.out.println ("Image Time = " + this.getImageTime());
        
    } // printMetadata ()
    
    /**
     * Will probe the file to determine the file's mime type.
     * 
     * @param parFileName filename of file to probe
     * @return String of the file type returned by probeContentType.
     */
    private static String probeFileType (String parFileName) {

        // Declare Local Variables
        String fileName;
        Path filePath;
        String fileType;

        // Initialize Local Variables
        fileName = parFileName;
        filePath = Paths.get(fileName);
        fileType = "Undetermined";

        // Determine what type of file
        try {
            fileType = Files.probeContentType(filePath);
        } catch (IOException e) {
            // TODO Add to application logging
            e.printStackTrace();
        }

        return (fileType);

    } // probeFileType ()

    /**
     * Will assign the passed String to imageAuthor
     * 
     * @param imageAuthor the imageAuther to set
     */
    private void setImageAuthor (String imageAuthor) {
        
        this.imageAuthor = imageAuthor;
        
    } // setImageAuthor ()

    /**
     * Will assign the passed String to imageDate
     * 
     * @param imageDate the imageDate to set
     */
    private void setImageDate (String imageDate) {
        
        this.imageDate = imageDate;
        
    } // setImageDate ()

    /**
     * Will assign the passed String to imageName
     * 
     * @param imageName the imageName to set
     */
    private void setImageName (String imageName) {
        
        this.imageName = imageName;
        
    } // setImaageName ()
    
    /**
     * Will assign the passed Image to imagePreview
     * 
     * @param imagePreview the Image to set 
     */
    private void setImagePreview (Image imagePreview) {
        
        this.imagePreview = imagePreview;
        
    } // setImagePreview ()

    /**
     * Will assign the passed long to imageSize
     * 
     * @param imageSize the imageSize to set
     */
    private void setImageSize (long imageSize) {
        
        this.imageSize = imageSize;
        
    } // setImageSize ()

    /**
     * Will assign the passed String to imageTime
     * 
     * @param imageTime the imageTime to set
     */
    private void setImageTime (String imageTime) {
        
        this.imageTime = imageTime;
        
    } // setImageTime ()

    /**
     * Will assign the passed String to imageType 
     * 
     * @param imageType the imageType to set
     */
    private void setImageType (String imageType) {
        
        this.imageType = imageType;
        
    } // setImageType ()

    /**
     * Will return a string representing the image metadata
     * 
     * @return String representing the image file metadata
     */
    @Override
    public String toString () {

        return ("Author: " + this.getImageAuthor() + ";" + 
                "Date: " + this.getImageDate() + ";" + 
                "Time: " + this.getImageTime() + ";" +
                "Name: " + this.getImageName() + ";" +
                "Size: " + this.getImageSize() + ";" +
                "Type: " + this.getImageType());

    } // toString ()

} // Metadata Class