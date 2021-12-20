package extractors;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DOCXExtractor implements Extractor {
    
    private static final Logger logger = LogManager.getLogger(DOCXExtractor.class);

    /**
     * Extraction Method
     * @param stream
     * @return
     * @throws Exception
     */
    @Override
    public List<String> extract(InputStream stream) throws Exception
    {
        logger.info("Document open");
        logger.warn("Not implemented yet!");
        try
        {
            
            List<String> listString = new ArrayList<String>();
            return listString;
        }
        catch (Exception e){
            logger.error(e.toString());
            throw e;
        }
    }
}