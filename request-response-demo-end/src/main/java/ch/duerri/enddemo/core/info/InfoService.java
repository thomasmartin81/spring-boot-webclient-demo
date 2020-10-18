package ch.duerri.enddemo.core.info;

import ch.duerri.enddemo.core.ApplicationConstants;
import ch.duerri.enddemo.core.info.response.EndInfoDataResponse;
import ch.duerri.enddemo.web.exceptions.MyConflictException;
import ch.duerri.enddemo.web.exceptions.MyNotFoundException;
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
        if (id.equals("2")) {
            waitTime(5000l);
        } else if (id.equals("3")) {
            waitTime(15000l);
        } else if (id.equals("4")) {
            throw new MyNotFoundException();
        } else if (id.equals("5")) {
            throw new MyConflictException();
        }

        return id + "-end-info";
    }

    private void waitTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
