/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST440Project;

import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.io.IOException;

/**
 *
 * @author robds
 */
public class AppLogger {
    
        static Logger logger = Logger.getLogger(AppLogger.class.getName());
    
    /**
     *
     * @throws IOException
     */
    public AppLogger() throws IOException{
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        try{
            FileHandler fh = new FileHandler("./logfile.log",true);
            fh.setLevel(Level.WARNING);
            logger.addHandler(fh);
            Formatter MyFormatter = new SimpleFormatter();
            fh.setFormatter(MyFormatter);
        }catch(java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger is not working.", e);
        }
        }

    private static Logger getLogger(){
        if(logger == null){
        try {
            new AppLogger();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    return logger;
    }
    
    /**
     *
     * @param level
     * @param msg
     */
    public static void log(Level level, String msg){
    getLogger().log(level, msg);
    System.out.println(msg);
}
}

