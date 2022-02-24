package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class EconomicActivitiesCensusService {

	private static final Logger log = LoggerFactory.getLogger(EconomicActivitiesCensusService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private GenericResultDto<EconomicActivitiesCensusDto> genericResultDto;


	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getEconomicActivitiesCensusDefaultPage")
	public Mono<GenericResultDto<EconomicActivitiesCensusDto>>getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy
				.getRequestData(new URL(config.getDs_economicactivitiescensus()), EconomicActivitiesCensusDto[].class)
				.flatMap(economicActivitiesCensusDtos -> {
					EconomicActivitiesCensusDto[] pagedDto = JsonHelper.filterDto(economicActivitiesCensusDtos, offset, limit);
					genericResultDto.setInfo(offset, limit, economicActivitiesCensusDtos.length, pagedDto);
					return Mono.just(genericResultDto);
				});
	}

	public Mono<GenericResultDto<EconomicActivitiesCensusDto>> getEconomicActivitiesCensusDefaultPage(
			int offset, int limit, Throwable exception
	) {
		genericResultDto.setInfo(0, 0, 0, new EconomicActivitiesCensusDto[0]);
		return Mono.just(genericResultDto);
	}

}
