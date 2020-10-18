package ch.duerri.frontdemo.core.info.response;

public class FrontInfoDataResponse {
    private String id;
    private String frontRequestId;
    private String frontInfo;
    private String middleRequestId;
    private String middleInfo;
    private String endRequestId;
    private String endInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrontRequestId() {
        return frontRequestId;
    }

    public void setFrontRequestId(String frontRequestId) {
        this.frontRequestId = frontRequestId;
    }

    public String getFrontInfo() {
        return frontInfo;
    }

    public void setFrontInfo(String frontInfo) {
        this.frontInfo = frontInfo;
    }

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
