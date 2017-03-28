package de.codecentric.resilient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Benjamin Wilms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FallbackAbstractDTO {


    private boolean fallback;

    private String errorMsg;

    public boolean isFallback() {
        return fallback;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
