package com.pashonokk.marketplace.endpoint.dto;

import com.pashonokk.marketplace.endpoint.BaseResponse;
import com.pashonokk.marketplace.endpoint.Status;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationFailedResponse extends BaseResponse {
    private final List<String> errorMessages;

    public ValidationFailedResponse(String message, List<String> errorMessages) {
        super(Status.FAIL, message);
        this.errorMessages = errorMessages;
    }
}
