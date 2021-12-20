package extractors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

/**
 * Powerpoint
 */
public class PPTXExtractor implements Extractor {

    private static final Logger logger = LogManager.getLogger(PPTXExtractor.class);

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

        try
        (
            XMLSlideShow ppt = new XMLSlideShow(stream);
        )
        {
            List<String> listString = new ArrayList<String>();

            int pageNumber = 0;
            for (XSLFSlide slide : ppt.getSlides()){
                pageNumber++;

                logger.info("Page: " + pageNumber + " - start extraction");
                StringBuilder text = new StringBuilder();
                
                for (XSLFShape shape : slide.getShapes()) {
                    logger.debug("Page: " + pageNumber + " - embedded shape");
                    if (shape instanceof XSLFTextShape) {
                        logger.debug("Page: " + pageNumber + " - embedded shape is text");
                        XSLFTextShape textShape = (XSLFTextShape) shape;
                        text.append(textShape.getText());
                    }
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
