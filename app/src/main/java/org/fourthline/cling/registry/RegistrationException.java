package org.fourthline.cling.registry;

import java.util.List;
import org.fourthline.cling.model.ValidationError;

/* loaded from: classes5.dex */
public class RegistrationException extends RuntimeException {
    public List<ValidationError> errors;

    public RegistrationException(String str) {
        super(str);
    }

    public RegistrationException(String str, Throwable th) {
        super(str, th);
    }

    public RegistrationException(String str, List<ValidationError> list) {
        super(str);
        this.errors = list;
    }

    public List<ValidationError> getErrors() {
        return this.errors;
    }
}
