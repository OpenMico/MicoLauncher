package org.apache.commons.lang3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class SerializationUtils {
    public static <T extends Serializable> T clone(T t) {
        Throwable th;
        ClassNotFoundException e;
        IOException e2;
        a aVar;
        a aVar2 = null;
        if (t == null) {
            return null;
        }
        try {
            try {
                aVar = new a(new ByteArrayInputStream(serialize(t)), t.getClass().getClassLoader());
            } catch (IOException e3) {
                e2 = e3;
            } catch (ClassNotFoundException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            T t2 = (T) ((Serializable) aVar.readObject());
            try {
                aVar.close();
                return t2;
            } catch (IOException e5) {
                throw new SerializationException("IOException on closing cloned object data InputStream.", e5);
            }
        } catch (IOException e6) {
            e2 = e6;
            throw new SerializationException("IOException while reading cloned object data", e2);
        } catch (ClassNotFoundException e7) {
            e = e7;
            throw new SerializationException("ClassNotFoundException while reading cloned object data", e);
        } catch (Throwable th3) {
            th = th3;
            aVar2 = aVar;
            if (aVar2 != null) {
                try {
                    aVar2.close();
                } catch (IOException e8) {
                    throw new SerializationException("IOException on closing cloned object data InputStream.", e8);
                }
            }
            throw th;
        }
    }

    public static <T extends Serializable> T roundtrip(T t) {
        return (T) ((Serializable) deserialize(serialize(t)));
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

    public static <T> T deserialize(InputStream inputStream) {
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
                T t = (T) objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (IOException unused) {
                }
                return t;
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

    public static <T> T deserialize(byte[] bArr) {
        if (bArr != null) {
            return (T) deserialize(new ByteArrayInputStream(bArr));
        }
        throw new IllegalArgumentException("The byte[] must not be null");
    }

    /* loaded from: classes5.dex */
    static class a extends ObjectInputStream {
        private static final Map<String, Class<?>> a = new HashMap();
        private final ClassLoader b;

        static {
            a.put("byte", Byte.TYPE);
            a.put("short", Short.TYPE);
            a.put("int", Integer.TYPE);
            a.put("long", Long.TYPE);
            a.put("float", Float.TYPE);
            a.put("double", Double.TYPE);
            a.put("boolean", Boolean.TYPE);
            a.put("char", Character.TYPE);
            a.put("void", Void.TYPE);
        }

        public a(InputStream inputStream, ClassLoader classLoader) throws IOException {
            super(inputStream);
            this.b = classLoader;
        }

        @Override // java.io.ObjectInputStream
        protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            String name = objectStreamClass.getName();
            try {
                try {
                    return Class.forName(name, false, this.b);
                } catch (ClassNotFoundException e) {
                    Class<?> cls = a.get(name);
                    if (cls != null) {
                        return cls;
                    }
                    throw e;
                }
            } catch (ClassNotFoundException unused) {
                return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
            }
        }
    }
}
