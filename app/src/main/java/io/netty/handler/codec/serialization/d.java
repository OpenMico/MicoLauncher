package io.netty.handler.codec.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/* compiled from: CompactObjectOutputStream.java */
/* loaded from: classes4.dex */
class d extends ObjectOutputStream {
    /* JADX INFO: Access modifiers changed from: package-private */
    public d(OutputStream outputStream) throws IOException {
        super(outputStream);
    }

    @Override // java.io.ObjectOutputStream
    protected void writeStreamHeader() throws IOException {
        writeByte(5);
    }

    @Override // java.io.ObjectOutputStream
    protected void writeClassDescriptor(ObjectStreamClass objectStreamClass) throws IOException {
        Class<?> forClass = objectStreamClass.forClass();
        if (forClass.isPrimitive() || forClass.isArray() || forClass.isInterface() || objectStreamClass.getSerialVersionUID() == 0) {
            write(0);
            super.writeClassDescriptor(objectStreamClass);
            return;
        }
        write(1);
        writeUTF(objectStreamClass.getName());
    }
}
