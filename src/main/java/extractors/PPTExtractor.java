package extractors;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.logging.log4j.LogManager;

/**
 * PowerPoint 2007 OOXML (.xlsx) extractor
 */
public class PPTExtractor implements Extractor {
    
    private static final Logger logger = LogManager.getLogger(PPTExtractor.class);

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
            HSLFSlideShow ppt = new HSLFSlideShow(stream);
        )
        {
            
            List<String> listString = new ArrayList<String>();

            int pageNumber = 0;
            for (HSLFSlide slide : ppt.getSlides()) {
                pageNumber++;

                logger.info("Page: " + pageNumber + " - start extraction");
                StringBuilder text = new StringBuilder();
                
                for(List<HSLFTextParagraph> paragraphs : slide.getTextParagraphs()){
                    logger.debug("Page: " + pageNumber + " - embedded paragraph");
                    text.append(HSLFTextParagraph.getText(paragraphs)); 
                }
                logger.info("Page: " + pageNumber + " - end extraction, text: " + text.length());    
                listString.add(text.toString());
            }
            return listString;
        }
        catch (Exception e){
            logger.error(e.toString());
            throw e;
        }

    }
}
