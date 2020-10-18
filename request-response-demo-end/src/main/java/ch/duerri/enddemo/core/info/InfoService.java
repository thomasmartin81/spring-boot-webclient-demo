package ch.duerri.enddemo.core.info;

import ch.duerri.enddemo.core.ApplicationConstants;
import ch.duerri.enddemo.core.info.response.EndInfoDataResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class InfoService {

    @Resource
    private HttpServletRequest request;

    protected String getRequestId() {
        return request.getAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID).toString();
    }

    public EndInfoDataResponse getInfoData(String id) {
        EndInfoDataResponse endInfoDataResponse = new EndInfoDataResponse();
        endInfoDataResponse.setEndRequestId(getRequestId());
        endInfoDataResponse.setEndInfo(getInfoById(id));

        return endInfoDataResponse;
    }

    private String getInfoById(String id) {
        return id + "-end-info";
    }
}
