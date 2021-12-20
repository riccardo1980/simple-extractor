package extractors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtractorFactory {
    private static final Logger logger = LogManager.getLogger(ExtractorFactory.class);

    static final Map<String, Supplier<Extractor>> constructorRefMap = new HashMap<>();

    // mapping mime-types recognized by ContainerAwareDetector
    static {
        constructorRefMap.put("application/pdf", PDFExtractor::new);
        constructorRefMap.put("application/vnd.ms-powerpoint", PPTExtractor::new);
        constructorRefMap.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", PPTXExtractor::new);
        constructorRefMap.put("application/msword", DOCExtractor::new);
        constructorRefMap.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", DOCXExtractor::new);
    }

    public static void register(String mimetype, Supplier<Extractor> constructorRef){
        logger.debug("Registering " + mimetype + " mimetype");
        constructorRefMap.put(mimetype, constructorRef);
    }

    public static Extractor create(String mimetype) throws Exception{
        logger.debug("Creating using " + mimetype + " mimetype");
        try{
            return constructorRefMap.get(mimetype).get();
        }
        catch (NullPointerException e){
            throw new Exception("Extractor for " + mimetype + " is not available");
        }
    }

}
