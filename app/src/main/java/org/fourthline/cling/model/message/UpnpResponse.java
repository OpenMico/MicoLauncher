package org.fourthline.cling.model.message;

import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class UpnpResponse extends UpnpOperation {
    private int statusCode;
    private String statusMessage;

    /* loaded from: classes5.dex */
    public enum Status {
        OK(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        METHOD_NOT_SUPPORTED(405, "Method Not Supported"),
        PRECONDITION_FAILED(412, "Precondition Failed"),
        UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        NOT_IMPLEMENTED(501, "Not Implemented");
        
        private int statusCode;
        private String statusMsg;

        Status(int i, String str) {
            this.statusCode = i;
            this.statusMsg = str;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getStatusMsg() {
            return this.statusMsg;
        }

        public static Status getByStatusCode(int i) {
            Status[] values = values();
            for (Status status : values) {
                if (status.getStatusCode() == i) {
                    return status;
                }
            }
            return null;
        }
    }

    public UpnpResponse(int i, String str) {
        this.statusCode = i;
        this.statusMessage = str;
    }

    public UpnpResponse(Status status) {
        this.statusCode = status.getStatusCode();
        this.statusMessage = status.getStatusMsg();
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public boolean isFailed() {
        return this.statusCode >= 300;
    }

    public String getResponseDetails() {
        return getStatusCode() + StringUtils.SPACE + getStatusMessage();
    }

    public String toString() {
        return getResponseDetails();
    }
}
