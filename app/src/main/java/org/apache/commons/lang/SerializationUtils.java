package org.apache.commons.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class SerializationUtils {
    public static Object clone(Serializable serializable) {
        return deserialize(serialize(serializable));
    }

    public static void serialize(Serializable serializable, OutputStream outputStream) {
        Throwable th;
        IOException e;
        ObjectOutputStream objectOutputStream;
        if (outputStream != null) {
            ObjectOutputStream objectOutputStream2 = null;
            try {
                try {
                    objectOutputStream = new ObjectOutputStream(outputStream);
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                objectOutputStream.writeObject(serializable);
                try {
                    objectOutputStream.close();
                } catch (IOException unused) {
                }
            } catch (IOException e3) {
                e = e3;
                objectOutputStream2 = objectOutputStream;
                throw new SerializationException(e);
            } catch (Throwable th3) {
                th = th3;
                objectOutputStream2 = objectOutputStream;
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
    }

    public static byte[] serialize(Serializable serializable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(serializable, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(InputStream inputStream) {
        Throwable th;
        ClassNotFoundException e;
        IOException e2;
        ObjectInputStream objectInputStream;
        if (inputStream != null) {
            try {
                try {
                    objectInputStream = new ObjectInputStream(inputStream);
                } catch (IOException e3) {
                    e2 = e3;
                } catch (ClassNotFoundException e4) {
                    e = e4;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                Object readObject = objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (IOException unused) {
                }
                return readObject;
            } catch (IOException e5) {
                e2 = e5;
                throw new SerializationException(e2);
            } catch (ClassNotFoundException e6) {
                e = e6;
                throw new SerializationException(e);
            } catch (Throwable th3) {
                th = th3;
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
    }

    public static Object deserialize(byte[] bArr) {
        if (bArr != null) {
            return deserialize(new ByteArrayInputStream(bArr));
        }
        throw new IllegalArgumentException("The byte[] must not be null");
    }
}
