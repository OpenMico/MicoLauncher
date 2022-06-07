package com.google.protobuf;

import java.io.IOException;

/* loaded from: classes2.dex */
public class LazyFieldLite {
    private static final ExtensionRegistryLite a = ExtensionRegistryLite.getEmptyRegistry();
    private ByteString b;
    private ExtensionRegistryLite c;
    private volatile ByteString d;
    protected volatile MessageLite value;

    public int hashCode() {
        return 1;
    }

    public LazyFieldLite(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        a(extensionRegistryLite, byteString);
        this.c = extensionRegistryLite;
        this.b = byteString;
    }

    public LazyFieldLite() {
    }

    public static LazyFieldLite fromValue(MessageLite messageLite) {
        LazyFieldLite lazyFieldLite = new LazyFieldLite();
        lazyFieldLite.setValue(messageLite);
        return lazyFieldLite;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LazyFieldLite)) {
            return false;
        }
        LazyFieldLite lazyFieldLite = (LazyFieldLite) obj;
        MessageLite messageLite = this.value;
        MessageLite messageLite2 = lazyFieldLite.value;
        if (messageLite == null && messageLite2 == null) {
            return toByteString().equals(lazyFieldLite.toByteString());
        }
        if (messageLite != null && messageLite2 != null) {
            return messageLite.equals(messageLite2);
        }
        if (messageLite != null) {
            return messageLite.equals(lazyFieldLite.getValue(messageLite.getDefaultInstanceForType()));
        }
        return getValue(messageLite2.getDefaultInstanceForType()).equals(messageLite2);
    }

    public boolean containsDefaultInstance() {
        ByteString byteString;
        return this.d == ByteString.EMPTY || (this.value == null && ((byteString = this.b) == null || byteString == ByteString.EMPTY));
    }

    public void clear() {
        this.b = null;
        this.value = null;
        this.d = null;
    }

    public void set(LazyFieldLite lazyFieldLite) {
        this.b = lazyFieldLite.b;
        this.value = lazyFieldLite.value;
        this.d = lazyFieldLite.d;
        ExtensionRegistryLite extensionRegistryLite = lazyFieldLite.c;
        if (extensionRegistryLite != null) {
            this.c = extensionRegistryLite;
        }
    }

    public MessageLite getValue(MessageLite messageLite) {
        ensureInitialized(messageLite);
        return this.value;
    }

    public MessageLite setValue(MessageLite messageLite) {
        MessageLite messageLite2 = this.value;
        this.b = null;
        this.d = null;
        this.value = messageLite;
        return messageLite2;
    }

    public void merge(LazyFieldLite lazyFieldLite) {
        ByteString byteString;
        if (!lazyFieldLite.containsDefaultInstance()) {
            if (containsDefaultInstance()) {
                set(lazyFieldLite);
                return;
            }
            if (this.c == null) {
                this.c = lazyFieldLite.c;
            }
            ByteString byteString2 = this.b;
            if (byteString2 != null && (byteString = lazyFieldLite.b) != null) {
                this.b = byteString2.concat(byteString);
            } else if (this.value == null && lazyFieldLite.value != null) {
                setValue(a(lazyFieldLite.value, this.b, this.c));
            } else if (this.value == null || lazyFieldLite.value != null) {
                setValue(this.value.toBuilder().mergeFrom(lazyFieldLite.value).build());
            } else {
                setValue(a(this.value, lazyFieldLite.b, lazyFieldLite.c));
            }
        }
    }

    public void mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        if (containsDefaultInstance()) {
            setByteString(codedInputStream.readBytes(), extensionRegistryLite);
            return;
        }
        if (this.c == null) {
            this.c = extensionRegistryLite;
        }
        ByteString byteString = this.b;
        if (byteString != null) {
            setByteString(byteString.concat(codedInputStream.readBytes()), this.c);
            return;
        }
        try {
            setValue(this.value.toBuilder().mergeFrom(codedInputStream, extensionRegistryLite).build());
        } catch (InvalidProtocolBufferException unused) {
        }
    }

    private static MessageLite a(MessageLite messageLite, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        try {
            return messageLite.toBuilder().mergeFrom(byteString, extensionRegistryLite).build();
        } catch (InvalidProtocolBufferException unused) {
            return messageLite;
        }
    }

    public void setByteString(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        a(extensionRegistryLite, byteString);
        this.b = byteString;
        this.c = extensionRegistryLite;
        this.value = null;
        this.d = null;
    }

    public int getSerializedSize() {
        if (this.d != null) {
            return this.d.size();
        }
        ByteString byteString = this.b;
        if (byteString != null) {
            return byteString.size();
        }
        if (this.value != null) {
            return this.value.getSerializedSize();
        }
        return 0;
    }

    public ByteString toByteString() {
        if (this.d != null) {
            return this.d;
        }
        ByteString byteString = this.b;
        if (byteString != null) {
            return byteString;
        }
        synchronized (this) {
            if (this.d != null) {
                return this.d;
            }
            if (this.value == null) {
                this.d = ByteString.EMPTY;
            } else {
                this.d = this.value.toByteString();
            }
            return this.d;
        }
    }

    protected void ensureInitialized(MessageLite messageLite) {
        if (this.value == null) {
            synchronized (this) {
                if (this.value == null) {
                    try {
                        if (this.b != null) {
                            this.value = (MessageLite) messageLite.getParserForType().parseFrom(this.b, this.c);
                            this.d = this.b;
                        } else {
                            this.value = messageLite;
                            this.d = ByteString.EMPTY;
                        }
                    } catch (InvalidProtocolBufferException unused) {
                        this.value = messageLite;
                        this.d = ByteString.EMPTY;
                    }
                }
            }
        }
    }

    private static void a(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        if (extensionRegistryLite == null) {
            throw new NullPointerException("found null ExtensionRegistry");
        } else if (byteString == null) {
            throw new NullPointerException("found null ByteString");
        }
    }
}
