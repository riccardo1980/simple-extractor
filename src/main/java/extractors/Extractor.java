package extractors;

import java.io.InputStream;
import java.util.List;

/**
 * Extractor Interface
 */
public interface Extractor {

    /**
     * Extraction Method
     * @param stream
     * @return
     * @throws Exception
     */
    public List<String> extract(InputStream stream) throws Exception;

}
