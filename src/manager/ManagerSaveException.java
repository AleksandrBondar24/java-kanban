package manager;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String exception, IOException e) {
        super(exception, e);
    }
}
