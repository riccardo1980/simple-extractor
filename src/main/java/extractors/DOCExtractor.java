package extractors;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.logging.log4j.LogManager;

/**
 * Microsoft Word 97 to 2007 (.doc) extractor
 */
public class DOCExtractor implements Extractor {

    private static final Logger logger = LogManager.getLogger(DOCExtractor.class);

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
        try (
            HWPFDocument doc = new HWPFDocument(stream);
            WordExtractor extr = new WordExtractor(doc);
        )
        {
            List<String> listString = new ArrayList<String>();

            int pageNumber = 1;
            logger.debug("Page: " + pageNumber + " - embedded text");

            StringBuilder text = new StringBuilder();
            text.append(extr.getText());
            logger.info("Page: " + pageNumber + " - end extraction, text: " + text.length());
            listString.add(text.toString());

            return listString;
        }
        catch (Exception e){
            logger.error(e.toString());
            throw e;
        }
    }
}
