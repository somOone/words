package com.banerjee.spellingbee.rest;

import com.banerjee.spellingbee.dto.ErrorResponseDTO;
import com.banerjee.spellingbee.dto.SuccessfulResponseDTO;
import com.banerjee.spellingbee.integration.WebsterApiClient;
import com.banerjee.spellingbee.service.WordWriterService;
import com.banerjee.spellingbee.util.ExceptionUtil;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by somobanerjee on 2/26/17.
 */
@RestController
public class WordInResource {

    @Autowired
    WordWriterService wordWriterService;

    @Autowired
    WebsterApiClient websterApiClient;
    @RequestMapping(method = RequestMethod.GET, value = "/saveWord/word/{word}")
    public ResponseEntity<?> saveWord(
        @Valid @PathVariable("word") String word) {
        if(StringUtils.isBlank(word)) {
            throw new RuntimeException("Word is not provided");
        }
        /*Map<String, String> wordMap = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map =
            mapper.convertValue(wordDTO, new TypeReference<Map<String, Object>>() {});*/
        try {
            String wordXml = websterApiClient.getWordDataFromWebster(word);
            JSONObject wordJson = wordWriterService.convertWordXMLToJSON(wordXml);
            wordWriterService.saveWordToMongo(Document.parse( wordJson.toString() ));
            SuccessfulResponseDTO  successfulResponseDTO = new SuccessfulResponseDTO();
            successfulResponseDTO.setMessage("successfully saved word " + word);
            return new ResponseEntity<>(successfulResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ExceptionUtil.getExceptionAsString(e));
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
