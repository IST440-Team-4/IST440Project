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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;

/*
 *  Metadata
 *
 *  Description:
 *    The metadata class is an object representing all the metadata
 *    for an associated file.
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
public class Metadata {
    
    // Class Variables
    private ImageMetadata metadata;
    private JpegImageMetadata jpegMetadata;
    private String imageDate;
    private String imageTime;
    private long imageSize;
    private String imageName;
    private String imageType;
    private String imageAuthor;
    
    /**
     * Default Constructor for a Metadata Object
     * 
     * @param imageFile file object of image
     */
    public Metadata (File imageFile) {
        
        // TODO need to assign default values, then extract
        // Intialize Class Variables
        this.setImageName(imageFile.getName());
        this.setImageSize(imageFile.length());
        this.setImageType(Metadata.probeFileType(imageFile.getName()));

        // Extract Image Metadata 
        try {
            this.metadata = Imaging.getMetadata (imageFile);
            this.extractMetadata(metadata);
        } catch (ImageReadException | IOException ex) {
            Logger.getLogger(Metadata.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } // Metadata (imageFile)    

    /**
     * Extract Metadata from file and assign values to class variables
     * 
     * @param imageFile file object of image
     */
    private void extractMetadata (ImageMetadata metadata) 
            throws ImageReadException {
        
        // Declare Local Variables
        TagInfo tagInfo;
        TiffField field;
        String[] arrOfDateTime;
        
                
        // Extract File Metadata             
        if (metadata instanceof JpegImageMetadata) {
            
            jpegMetadata = (JpegImageMetadata) metadata;
            
            // Extract Author
            tagInfo = MicrosoftTagConstants.EXIF_TAG_XPAUTHOR;
            field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
            if (field == null) {
                this.setImageAuthor("unknown");
            } else {
                this.setImageAuthor(field.getStringValue());
            }
            
            // Extract Date & Time
            tagInfo = TiffTagConstants.TIFF_TAG_DATE_TIME;
            field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
            if (field == null) {
                this.setImageDate("0000:00:00");
                this.setImageTime("00:00:00");
            } else {
                // TODO This is a hack, need to implement a better way of 
                //      dealing with date/time
                arrOfDateTime = field.getStringValue().split(" ", 2);
                this.setImageDate(arrOfDateTime[0]);
                this.setImageTime(arrOfDateTime[1]);
            }

        }
        
    } // extractMetadata ()
    
    /**
     * Will return a String representing the Author of the image
     * 
     * @return the imageAuthor
     */
    public String getImageAuthor () {
        
        return imageAuthor;
        
    } // getImageAuthor ()

    /**
     * Will return a String representing the Date of the image
     * in the form of MM/DD/YYYY
     * 
     * @return the imageDate
     */
    public String getImageDate () {
        
        return imageDate;
        
    } // getImageDate ()

    /**
     * Will return a String representing the filename of the image
     * 
     * @return the imageName
     */
    public String getImageName () {
        
        return imageName;
        
    } // getImageName ()


    /**
     * Will return a integer representing the size of the image
     * 
     * @return the imageSize
     */
    public long getImageSize () {
        
        return imageSize;
        
    } // getImageSize ();

    /**
     * Will return a String representing the Time of the image
     * in the for of HH:MM:SS
     * 
     * @return the imageTime
     */
    public String getImageTime () {
        
        return imageTime;
        
    } // getImageTime ()

    /**
     * Will return a String representing the image type
     * 
     * @return the imageType
     */
    public String getImageType () {
        
        return imageType;
        
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
     * @return fileType
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
     * @param imageAuthor the imageAuthor to set
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
     * @return string representing the image metadata
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