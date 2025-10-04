package nl.novi.cannoliworld.exeptions;
import java.io.Serial;

public class UsernameAlreadyExistException extends RuntimeException  {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistException(String message) {

        super(message);
    }
}
