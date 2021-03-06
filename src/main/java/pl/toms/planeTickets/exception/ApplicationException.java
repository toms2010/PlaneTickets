package pl.toms.planeTickets.exception;

import org.springframework.http.HttpStatus;

/**
 * Błąd ogólny aplikacji do którego przekazać możemy status HTTP
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    /**
     * Błąd w trakcie działania aplikacji.
     * 
     * @param message komunikat o błędzie
     */
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException() {
        super();
    }

    /**
     * Błąd w trakcie działania aplikacji.
     * 
     * @param message komunikat o błędzie
     * @param status status HTTP błędu
     */
    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}