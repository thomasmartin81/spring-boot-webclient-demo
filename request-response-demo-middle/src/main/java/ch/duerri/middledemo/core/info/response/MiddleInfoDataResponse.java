package ch.duerri.middledemo.core.info.response;

public class MiddleInfoDataResponse {
    private String middleRequestId;
    private String middleInfo;
    private String endRequestId;
    private String endInfo;

    public String getMiddleRequestId() {
        return middleRequestId;
    }

    public void setMiddleRequestId(String middleRequestId) {
        this.middleRequestId = middleRequestId;
    }

    public String getMiddleInfo() {
        return middleInfo;
    }

    public void setMiddleInfo(String middleInfo) {
        this.middleInfo = middleInfo;
    }

    public String getEndRequestId() {
        return endRequestId;
    }

    public void setEndRequestId(String endRequestId) {
        this.endRequestId = endRequestId;
    }

    public String getEndInfo() {
        return endInfo;
    }

    public void setEndInfo(String endInfo) {
        this.endInfo = endInfo;
    }
}
