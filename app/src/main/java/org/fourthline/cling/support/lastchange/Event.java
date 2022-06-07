package org.fourthline.cling.support.lastchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class Event {
    protected List<InstanceID> instanceIDs;

    public Event() {
        this.instanceIDs = new ArrayList();
    }

    public Event(List<InstanceID> list) {
        this.instanceIDs = new ArrayList();
        this.instanceIDs = list;
    }

    public List<InstanceID> getInstanceIDs() {
        return this.instanceIDs;
    }

    public InstanceID getInstanceID(UnsignedIntegerFourBytes unsignedIntegerFourBytes) {
        for (InstanceID instanceID : this.instanceIDs) {
            if (instanceID.getId().equals(unsignedIntegerFourBytes)) {
                return instanceID;
            }
        }
        return null;
    }

    public void clear() {
        this.instanceIDs = new ArrayList();
    }

    public void setEventedValue(UnsignedIntegerFourBytes unsignedIntegerFourBytes, EventedValue eventedValue) {
        InstanceID instanceID = null;
        for (InstanceID instanceID2 : getInstanceIDs()) {
            if (instanceID2.getId().equals(unsignedIntegerFourBytes)) {
                instanceID = instanceID2;
            }
        }
        if (instanceID == null) {
            instanceID = new InstanceID(unsignedIntegerFourBytes);
            getInstanceIDs().add(instanceID);
        }
        Iterator<EventedValue> it = instanceID.getValues().iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(eventedValue.getClass())) {
                it.remove();
            }
        }
        instanceID.getValues().add(eventedValue);
    }

    public <EV extends EventedValue> EV getEventedValue(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Class<EV> cls) {
        for (InstanceID instanceID : getInstanceIDs()) {
            if (instanceID.getId().equals(unsignedIntegerFourBytes)) {
                Iterator<EventedValue> it = instanceID.getValues().iterator();
                while (it.hasNext()) {
                    EV ev = (EV) it.next();
                    if (ev.getClass().equals(cls)) {
                        return ev;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public boolean hasChanges() {
        for (InstanceID instanceID : this.instanceIDs) {
            if (instanceID.getValues().size() > 0) {
                return true;
            }
        }
        return false;
    }
}
