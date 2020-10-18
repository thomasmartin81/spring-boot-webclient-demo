package ch.duerri.frontdemo.core.info;

import ch.duerri.frontdemo.core.ApplicationConstants;
import ch.duerri.frontdemo.core.info.response.FrontInfoDataResponse;
import ch.duerri.frontdemo.core.info.response.MiddleInfoDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class InfoService {
    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    private final WebClient webClient;

    @Resource
    private HttpServletRequest request;

    public InfoService(WebClient webClient) {
        this.webClient = webClient
                .mutate()
                .baseUrl("http://localhost:8090/demo-middle")
                .build();
    }

    public FrontInfoDataResponse getInfoData(String id) {
        String requestId = getRequestId();
        logger.info("getInfoData id={}, requestId={}", id, requestId);

        MiddleInfoDataResponse middleInfoDataResponse = getInfoDataFromMiddleService(id);

        FrontInfoDataResponse frontInfoDataResponse = new FrontInfoDataResponse();
        frontInfoDataResponse.setId(id);
        frontInfoDataResponse.setFrontRequestId(requestId);
        frontInfoDataResponse.setFrontInfo(getInfoById(id));
        frontInfoDataResponse.setMiddleRequestId(middleInfoDataResponse.getMiddleRequestId());
        frontInfoDataResponse.setMiddleInfo(middleInfoDataResponse.getMiddleInfo());
        frontInfoDataResponse.setEndRequestId(middleInfoDataResponse.getEndRequestId());
        frontInfoDataResponse.setEndInfo(middleInfoDataResponse.getEndInfo());

        return frontInfoDataResponse;
    }

    protected String getRequestId() {
        return request.getAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID).toString();
    }

    private MiddleInfoDataResponse getInfoDataFromMiddleService(String id) {
        return webClient
                .get()
                .uri("/info/{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ResponseStatusException(response.statusCode())))
                .bodyToMono(MiddleInfoDataResponse.class)
                .block();
    }

    private String getInfoById(String id) {
        return id + "-front-info";
    }
}
