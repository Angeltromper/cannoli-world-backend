package nl.novi.cannoliworld.exeptions;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);

    }

}
