package detectors;

import java.io.InputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

public class ContainerAwareDetector {
    private static final Logger logger = LogManager.getLogger(ContainerAwareDetector.class);

    /**
     * Detects mime type
     * 
     * @param stream
     * @return String 
     * @throws IOException
     */
    public static String detect(InputStream stream) throws IOException {

        logger.debug("Detect mime type");
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();
       
        TikaInputStream tikaStream = TikaInputStream.get(stream);
       
        Metadata metadata = new Metadata();
        //metadata.add(Metadata.RESOURCE_NAME_KEY, filenameWithExtension);
        MediaType mediaType = detector.detect(tikaStream, metadata);

        return mediaType.toString();
    }
}
