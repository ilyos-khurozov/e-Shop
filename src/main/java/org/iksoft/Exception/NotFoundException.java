package org.iksoft.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * @author IK
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException implements Supplier <NotFoundException>{

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public NotFoundException get() {
        return this;
    }
}
