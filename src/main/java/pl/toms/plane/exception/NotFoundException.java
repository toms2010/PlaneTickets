package pl.toms.plane.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Błąd, nie odnaleziono. Zwraca status http 404 not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    /**
     * Błąd w trakcie działania aplikacji.
     * 
     * @param message komunikat o błędzie
     */
    public NotFoundException(String message) {
        super(message);
    }
}