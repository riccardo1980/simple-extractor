package extractors;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;

public class PDFExtractor implements Extractor {
    private static final Logger logger = LogManager.getLogger(PDFExtractor.class);

    @Override
    public List<String> extract(InputStream stream) throws Exception
    {
        logger.info("Document open");
        try (
            PDDocument document = PDDocument.load(stream);
        )
        {
            
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(false);
            List<String> listString = new ArrayList<String>();

            for (int pageNumber = 1; pageNumber <= document.getNumberOfPages(); ++pageNumber)
            {
                logger.info("Page: " + pageNumber + " - start extraction");
                
                stripper.setStartPage(pageNumber);
                stripper.setEndPage(pageNumber);

                // TEXT
                logger.debug("Page: " + pageNumber + " - embedded text");
                StringBuilder text = new StringBuilder();
                text.append(stripper.getText(document));

                // embedded images
                logger.debug("Page: " + pageNumber + " - embedded images");
                PDPage p = document.getPage(pageNumber-1);
                PDResources resources = p.getResources();
                for (COSName xObjectName : resources.getXObjectNames())
                {
                    try{
                        PDXObject xObject = resources.getXObject(xObjectName);
                        if (xObject instanceof PDImageXObject){
                            final BufferedImage img = ((PDImageXObject) xObject).getImage();
                            logger.debug("Page: " + pageNumber + " - embedded image size: " + img.getWidth() + "x" + img.getHeight());
                        }
                    }
                    catch (Exception e) {
                        logger.error("ReadPdfPage - Error retrieving pdf image -> Page: " + pageNumber +
                            " - trace: " + e);
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
