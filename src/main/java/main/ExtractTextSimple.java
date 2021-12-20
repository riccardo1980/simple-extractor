package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import detectors.ContainerAwareDetector;
import extractors.Extractor;
import extractors.ExtractorFactory;

public final class ExtractTextSimple
{
    private static final String PAGE_SEPARATION = "----------------------------";

    private ExtractTextSimple()
    {
        // example class should not be instantiated
    }

    private static final Logger logger = LogManager.getLogger(ExtractTextSimple.class);
    
    /**
     * This will print the documents text page by page.
     *
     * @param args The command line arguments.
     * @throws IOException If there is an error parsing or extracting the document.
     */
    public static void main(String[] args) throws IOException
    {

        if (args.length != 1)
            usage();
        
        String filename = args[0];
        logger.info("Opening "+filename);
        File file = new File(filename);

        try (
            InputStream stream = new BufferedInputStream(new FileInputStream(file)); 
        )
        {
            // get mime type
            String mimetype = ContainerAwareDetector.detect(stream);
            logger.info("File " + file.getName() + " is: " + mimetype);
            
            // get text
            Extractor extractor = ExtractorFactory.create(mimetype);
            List<String> listString = extractor.extract(stream);

            // print out
            for (String page : listString){
                System.out.println(PAGE_SEPARATION);
                System.out.println(page);
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
    }
    
    /**
     * Prints usage
     */
    private static void usage()
    {
        System.err.println("Usage: java " + ExtractTextSimple.class.getSimpleName() + " <input-file>");
        System.exit(-1);
    }

}