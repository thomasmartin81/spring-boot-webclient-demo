package ch.duerri.middledemo.core.info;

import ch.duerri.middledemo.core.ApplicationConstants;
import ch.duerri.middledemo.core.info.response.EndInfoDataResponse;
import ch.duerri.middledemo.core.info.response.MiddleInfoDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class InfoService {
    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    private final WebClient webClient;

    @Resource
    private HttpServletRequest request;

    public InfoService() {
        webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8091/demo-end")
                .filter((request, next) -> {
                    ClientRequest filtered = ClientRequest.from(request)
                            .header(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID, getRequestId())
                            .build();
                    return next.exchange(filtered);
                })
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
        return webClient
                .get()
                .uri("/info/{id}", id)
                .retrieve()
                .bodyToMono(EndInfoDataResponse.class)
                .block();
    }

    private String getInfoById(String id) {
        return id + "-middle-info";
    }
}
