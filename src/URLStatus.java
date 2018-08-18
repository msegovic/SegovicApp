public enum URLStatus {
    HTTP_OK(200, "OK", "SUCCESS"), INTERNAL_SERVER_ERROR(500, "Internal Server Error", "ERROR"),
    MOVED_PERMANENTLY(301, "Moved Permanently", "SUCCESS"), NOT_FOUND(404, "Not Found", "ERROR"),
    NOT_MODIFIED(304, "Not modified", "SUCCESS"), NO_CONTENT(204, "No Content", "SUCCESS"),
    USE_PROXY(305, "Use Proxy", "SUCCESS");

    private int statusCode;
    private String httpMessage;
    private String result;

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param code
     * @param message
     * @param status
     */
    URLStatus(int code, String message, String status) {
        statusCode = code;
        httpMessage = message;
        result = status;
    }

    public static String getStatusMessageForStatusCode(int httpcode) {
        String returnStatusMessage = "Status Not Defined";
        for (URLStatus object : URLStatus.values()) {
            if (object.statusCode == httpcode) {
                returnStatusMessage = object.httpMessage;
            }
        }
        return returnStatusMessage;
    }

    public static String getResultForStatusCode(int code) {
        String returnResultMessage = "Result Not Defined";
        for (URLStatus object : URLStatus.values()) {
            if (object.statusCode == code) {
                returnResultMessage = object.result;
            }
        }
        return returnResultMessage;
    }
}


