package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.endpoint.PageResponse;
import org.springframework.data.domain.Page;

public interface PageMapper{
    <T> PageResponse<T> toPageResponse(Page<T> page);
}
