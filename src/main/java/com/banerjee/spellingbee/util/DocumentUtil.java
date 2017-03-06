package com.banerjee.spellingbee.util;

import com.banerjee.spellingbee.dto.WordDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

/**
 * Created by somobanerjee on 3/4/17.
 */
public class DocumentUtil {
    public static WordDTO convertDocumentToPojo(Document wordDTODoc) throws Exception {
        final String json = wordDTODoc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, WordDTO.class);
    }

    public static Document convertPojoToDocument(WordDTO wordDTO) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final String wordJson = mapper.writeValueAsString(wordDTO);
        return Document.parse(wordJson);
    }
}
