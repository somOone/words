package com.banerjee.spellingbee.rest;

import com.banerjee.spellingbee.dto.ErrorResponseDTO;
import com.banerjee.spellingbee.dto.WordDTO;
import com.banerjee.spellingbee.service.WordReaderService;
import com.banerjee.spellingbee.util.ExceptionUtil;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by somobanerjee on 3/4/17.
 */
@RestController
public class WordGameResource {
    @Autowired
    WordReaderService wordReaderService;
    /*
    * 1. fetch a random word details from the database
    *  -- pronunciation audio
    *  -- origin details
    *  -- prefix, base, suffix info
    *  -- actual spelling*/

    @RequestMapping(method = RequestMethod.GET, value = "/getNextWord")
    public ResponseEntity<?> saveWord() {
        try {
            WordDTO word = wordReaderService.getNextWord();
            return new ResponseEntity<WordDTO>(word, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO(ExceptionUtil.getExceptionAsString(e)),
                HttpStatus.BAD_REQUEST);
        }
    }
}
