package java8.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java8.util.r;

/* compiled from: ImmutableCollections.java */
/* loaded from: classes5.dex */
final class h implements Serializable {
    private static final long serialVersionUID = 6309168927139932177L;
    private transient Object[] a;
    private final int tag;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(int i, Object... objArr) {
        this.tag = i;
        this.a = objArr;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        if (readInt >= 0) {
            Object[] objArr = new Object[readInt];
            for (int i = 0; i < readInt; i++) {
                objArr[i] = objectInputStream.readObject();
            }
            this.a = objArr;
            return;
        }
        throw new InvalidObjectException("negative length " + readInt);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.a.length);
        int i = 0;
        while (true) {
            Object[] objArr = this.a;
            if (i < objArr.length) {
                objectOutputStream.writeObject(objArr[i]);
                i++;
            } else {
                return;
            }
        }
    }

    private Object readResolve() throws ObjectStreamException {
        try {
            if (this.a != null) {
                switch (this.tag & 255) {
                    case 1:
                        return Lists.of(this.a);
                    case 2:
                        return Sets.of(this.a);
                    case 3:
                        if (this.a.length == 0) {
                            return r.f;
                        }
                        if (this.a.length == 2) {
                            return new r.h(this.a[0], this.a[1]);
                        }
                        return new r.i(this.a);
                    default:
                        throw new InvalidObjectException(String.format("invalid flags 0x%x", Integer.valueOf(this.tag)));
                }
            } else {
                throw new InvalidObjectException("null array");
            }
        } catch (IllegalArgumentException e) {
            throw a(e);
        } catch (NullPointerException e2) {
            throw a(e2);
        }
    }

    private static InvalidObjectException a(RuntimeException runtimeException) {
        InvalidObjectException invalidObjectException = new InvalidObjectException("invalid object");
        invalidObjectException.initCause(runtimeException);
        return invalidObjectException;
    }
}
