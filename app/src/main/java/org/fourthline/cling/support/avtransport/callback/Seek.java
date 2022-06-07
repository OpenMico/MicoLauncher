package org.fourthline.cling.support.avtransport.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.SeekMode;

/* loaded from: classes5.dex */
public abstract class Seek extends ActionCallback {
    private static Logger log = Logger.getLogger(Seek.class.getName());

    public Seek(Service service, String str) {
        this(new UnsignedIntegerFourBytes(0L), service, SeekMode.REL_TIME, str);
    }

    public Seek(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service, String str) {
        this(unsignedIntegerFourBytes, service, SeekMode.REL_TIME, str);
    }

    public Seek(Service service, SeekMode seekMode, String str) {
        this(new UnsignedIntegerFourBytes(0L), service, seekMode, str);
    }

    public Seek(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service, SeekMode seekMode, String str) {
        super(new ActionInvocation(service.getAction("Seek")));
        getActionInvocation().setInput("InstanceID", unsignedIntegerFourBytes);
        getActionInvocation().setInput("Unit", seekMode.name());
        getActionInvocation().setInput("Target", str);
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        log.fine("Execution successful");
    }
}
