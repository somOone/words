package com.banerjee.spellingbee.dto;

/**
 * Created by somobanerjee on 2/26/17.
 */
public class ErrorResponseDTO {

    public ErrorResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
