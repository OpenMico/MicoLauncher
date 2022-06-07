package io.netty.handler.codec.serialization;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

/* compiled from: CompactObjectInputStream.java */
/* loaded from: classes4.dex */
class c extends ObjectInputStream {
    private final ClassResolver a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(InputStream inputStream, ClassResolver classResolver) throws IOException {
        super(inputStream);
        this.a = classResolver;
    }

    @Override // java.io.ObjectInputStream
    protected void readStreamHeader() throws IOException {
        int readByte = readByte() & 255;
        if (readByte != 5) {
            throw new StreamCorruptedException("Unsupported version: " + readByte);
        }
    }

    @Override // java.io.ObjectInputStream
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        int read = read();
        if (read >= 0) {
            switch (read) {
                case 0:
                    return super.readClassDescriptor();
                case 1:
                    return ObjectStreamClass.lookupAny(this.a.resolve(readUTF()));
                default:
                    throw new StreamCorruptedException("Unexpected class descriptor type: " + read);
            }
        } else {
            throw new EOFException();
        }
    }

    @Override // java.io.ObjectInputStream
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        try {
            return this.a.resolve(objectStreamClass.getName());
        } catch (ClassNotFoundException unused) {
            return super.resolveClass(objectStreamClass);
        }
    }
}
