package de.codecentric.resilient.dto;

/**
 * @author Benjamin Wilms
 */
public class FallbackAbstractDTO extends AbstractDTO {


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
