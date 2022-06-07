package org.slf4j.helpers;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: NamedLoggerBase.java */
/* loaded from: classes4.dex */
abstract class a implements Serializable, Logger {
    private static final long serialVersionUID = 7535258609338176893L;
    protected String name;

    public String getName() {
        return this.name;
    }

    protected Object readResolve() throws ObjectStreamException {
        return LoggerFactory.getLogger(getName());
    }
}
