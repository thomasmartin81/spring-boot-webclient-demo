package ch.duerri.middledemo.core.info;

import ch.duerri.middledemo.core.ApplicationConstants;
import ch.duerri.middledemo.core.info.response.EndInfoDataResponse;
import ch.duerri.middledemo.core.info.response.MiddleInfoDataResponse;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
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
                .baseUrl("http://localhost:8091/demo-end")
                .build();
    }

    public MiddleInfoDataResponse getInfoData(String id) {
        String requestId = getRequestId();
        logger.info("getInfoData id={}, requestId={}", id, requestId);

        EndInfoDataResponse endInfoDataResponse = getInfoDataFromEndService(id);

        MiddleInfoDataResponse middleInfoDataResponse = new MiddleInfoDataResponse();
        middleInfoDataResponse.setMiddleRequestId(requestId);
        middleInfoDataResponse.setMiddleInfo(getInfoById(id));
        middleInfoDataResponse.setEndRequestId(endInfoDataResponse.getEndRequestId());
        middleInfoDataResponse.setEndInfo(endInfoDataResponse.getEndInfo());

        return middleInfoDataResponse;
    }

    protected String getRequestId() {
        return request.getAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID).toString();
    }

    private EndInfoDataResponse getInfoDataFromEndService(String id) {
        try {
            return webClient
                    .get()
                    .uri("/info/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> Mono.error(new ResponseStatusException(response.statusCode())))
                    .bodyToMono(EndInfoDataResponse.class)
                    .block();
        } catch (ReadTimeoutException e) {
            throw new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    private String getInfoById(String id) {
        return id + "-middle-info";
    }
}
