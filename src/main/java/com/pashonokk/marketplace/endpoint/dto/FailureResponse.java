package com.pashonokk.marketplace.endpoint.dto;

import com.pashonokk.marketplace.endpoint.BaseResponse;
import com.pashonokk.marketplace.endpoint.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureResponse extends BaseResponse {
    public FailureResponse(String message) {
        super(Status.FAIL, message);
    }

}