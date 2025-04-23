package ma.salman.sbschoolassojet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur avec un message d'erreur personnalisé.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     *
     * @param message le message détaillant la raison de l'exception
     * @param cause la cause initiale de cette exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
