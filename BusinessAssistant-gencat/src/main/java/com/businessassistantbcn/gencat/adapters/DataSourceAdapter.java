package com.businessassistantbcn.gencat.adapters;

import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import reactor.core.publisher.Flux;

public interface DataSourceAdapter {
    Flux<RaiscResponseDto> findAllRaisc();
}
