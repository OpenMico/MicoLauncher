package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.versionedparcelable.VersionedParcel;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Set;

/* compiled from: VersionedParcelStream.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class b extends VersionedParcel {
    private static final Charset c = Charset.forName("UTF-16");
    int a;
    int b;
    private final DataInputStream d;
    private final DataOutputStream e;
    private DataInputStream f;
    private DataOutputStream g;
    private a h;
    private boolean i;
    private int j;

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean isStream() {
        return true;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected CharSequence readCharSequence() {
        return null;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public <T extends Parcelable> T readParcelable() {
        return null;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public IBinder readStrongBinder() {
        return null;
    }

    public b(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, outputStream, new ArrayMap(), new ArrayMap(), new ArrayMap());
    }

    private b(InputStream inputStream, OutputStream outputStream, ArrayMap<String, Method> arrayMap, ArrayMap<String, Method> arrayMap2, ArrayMap<String, Class> arrayMap3) {
        super(arrayMap, arrayMap2, arrayMap3);
        this.a = 0;
        this.j = -1;
        this.b = -1;
        DataOutputStream dataOutputStream = null;
        this.d = inputStream != null ? new DataInputStream(new FilterInputStream(inputStream) { // from class: androidx.versionedparcelable.b.1
            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read() throws IOException {
                if (b.this.b == -1 || b.this.a < b.this.b) {
                    int read = super.read();
                    b.this.a++;
                    return read;
                }
                throw new IOException();
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                if (b.this.b == -1 || b.this.a < b.this.b) {
                    int read = super.read(bArr, i, i2);
                    if (read > 0) {
                        b.this.a += read;
                    }
                    return read;
                }
                throw new IOException();
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public long skip(long j) throws IOException {
                if (b.this.b == -1 || b.this.a < b.this.b) {
                    long skip = super.skip(j);
                    if (skip > 0) {
                        b.this.a += (int) skip;
                    }
                    return skip;
                }
                throw new IOException();
            }
        }) : null;
        this.e = outputStream != null ? new DataOutputStream(outputStream) : dataOutputStream;
        this.f = this.d;
        this.g = this.e;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void setSerializationFlags(boolean z, boolean z2) {
        if (z) {
            this.i = z2;
            return;
        }
        throw new RuntimeException("Serialization of this object is not allowed");
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void closeField() {
        a aVar = this.h;
        if (aVar != null) {
            try {
                if (aVar.a.size() != 0) {
                    this.h.a();
                }
                this.h = null;
            } catch (IOException e) {
                throw new VersionedParcel.ParcelException(e);
            }
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected VersionedParcel createSubParcel() {
        return new b(this.f, this.g, this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean readField(int i) {
        while (this.j != i) {
            try {
                if (String.valueOf(this.j).compareTo(String.valueOf(i)) > 0) {
                    return false;
                }
                if (this.a < this.b) {
                    this.d.skip(this.b - this.a);
                }
                this.b = -1;
                int readInt = this.d.readInt();
                this.a = 0;
                int i2 = readInt & 65535;
                if (i2 == 65535) {
                    i2 = this.d.readInt();
                }
                this.j = (readInt >> 16) & 65535;
                this.b = i2;
            } catch (IOException unused) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void setOutputField(int i) {
        closeField();
        this.h = new a(i, this.e);
        this.g = this.h.b;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr) {
        try {
            if (bArr != null) {
                this.g.writeInt(bArr.length);
                this.g.write(bArr);
                return;
            }
            this.g.writeInt(-1);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr, int i, int i2) {
        try {
            if (bArr != null) {
                this.g.writeInt(i2);
                this.g.write(bArr, i, i2);
                return;
            }
            this.g.writeInt(-1);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected void writeCharSequence(CharSequence charSequence) {
        if (!this.i) {
            throw new RuntimeException("CharSequence cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeInt(int i) {
        try {
            this.g.writeInt(i);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeLong(long j) {
        try {
            this.g.writeLong(j);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeFloat(float f) {
        try {
            this.g.writeFloat(f);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeDouble(double d) {
        try {
            this.g.writeDouble(d);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeString(String str) {
        try {
            if (str != null) {
                byte[] bytes = str.getBytes(c);
                this.g.writeInt(bytes.length);
                this.g.write(bytes);
                return;
            }
            this.g.writeInt(-1);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBoolean(boolean z) {
        try {
            this.g.writeBoolean(z);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongBinder(IBinder iBinder) {
        if (!this.i) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeParcelable(Parcelable parcelable) {
        if (!this.i) {
            throw new RuntimeException("Parcelables cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongInterface(IInterface iInterface) {
        if (!this.i) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public int readInt() {
        try {
            return this.f.readInt();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public long readLong() {
        try {
            return this.f.readLong();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public float readFloat() {
        try {
            return this.f.readFloat();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public double readDouble() {
        try {
            return this.f.readDouble();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public String readString() {
        try {
            int readInt = this.f.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.f.readFully(bArr);
            return new String(bArr, c);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public byte[] readByteArray() {
        try {
            int readInt = this.f.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.f.readFully(bArr);
            return bArr;
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean readBoolean() {
        try {
            return this.f.readBoolean();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBundle(Bundle bundle) {
        try {
            if (bundle != null) {
                Set<String> keySet = bundle.keySet();
                this.g.writeInt(keySet.size());
                for (String str : keySet) {
                    writeString(str);
                    a(bundle.get(str));
                }
                return;
            }
            this.g.writeInt(-1);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public Bundle readBundle() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (int i = 0; i < readInt; i++) {
            a(readInt(), readString(), bundle);
        }
        return bundle;
    }

    private void a(Object obj) {
        if (obj == null) {
            writeInt(0);
        } else if (obj instanceof Bundle) {
            writeInt(1);
            writeBundle((Bundle) obj);
        } else if (obj instanceof String) {
            writeInt(3);
            writeString((String) obj);
        } else if (obj instanceof String[]) {
            writeInt(4);
            writeArray((String[]) obj);
        } else if (obj instanceof Boolean) {
            writeInt(5);
            writeBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof boolean[]) {
            writeInt(6);
            writeBooleanArray((boolean[]) obj);
        } else if (obj instanceof Double) {
            writeInt(7);
            writeDouble(((Double) obj).doubleValue());
        } else if (obj instanceof double[]) {
            writeInt(8);
            writeDoubleArray((double[]) obj);
        } else if (obj instanceof Integer) {
            writeInt(9);
            writeInt(((Integer) obj).intValue());
        } else if (obj instanceof int[]) {
            writeInt(10);
            writeIntArray((int[]) obj);
        } else if (obj instanceof Long) {
            writeInt(11);
            writeLong(((Long) obj).longValue());
        } else if (obj instanceof long[]) {
            writeInt(12);
            writeLongArray((long[]) obj);
        } else if (obj instanceof Float) {
            writeInt(13);
            writeFloat(((Float) obj).floatValue());
        } else if (obj instanceof float[]) {
            writeInt(14);
            writeFloatArray((float[]) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    private void a(int i, String str, Bundle bundle) {
        switch (i) {
            case 0:
                bundle.putParcelable(str, null);
                return;
            case 1:
                bundle.putBundle(str, readBundle());
                return;
            case 2:
                bundle.putBundle(str, readBundle());
                return;
            case 3:
                bundle.putString(str, readString());
                return;
            case 4:
                bundle.putStringArray(str, (String[]) readArray(new String[0]));
                return;
            case 5:
                bundle.putBoolean(str, readBoolean());
                return;
            case 6:
                bundle.putBooleanArray(str, readBooleanArray());
                return;
            case 7:
                bundle.putDouble(str, readDouble());
                return;
            case 8:
                bundle.putDoubleArray(str, readDoubleArray());
                return;
            case 9:
                bundle.putInt(str, readInt());
                return;
            case 10:
                bundle.putIntArray(str, readIntArray());
                return;
            case 11:
                bundle.putLong(str, readLong());
                return;
            case 12:
                bundle.putLongArray(str, readLongArray());
                return;
            case 13:
                bundle.putFloat(str, readFloat());
                return;
            case 14:
                bundle.putFloatArray(str, readFloatArray());
                return;
            default:
                throw new RuntimeException("Unknown type " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: VersionedParcelStream.java */
    /* loaded from: classes.dex */
    public static class a {
        final ByteArrayOutputStream a = new ByteArrayOutputStream();
        final DataOutputStream b = new DataOutputStream(this.a);
        private final int c;
        private final DataOutputStream d;

        a(int i, DataOutputStream dataOutputStream) {
            this.c = i;
            this.d = dataOutputStream;
        }

        void a() throws IOException {
            this.b.flush();
            int size = this.a.size();
            this.d.writeInt((this.c << 16) | (size >= 65535 ? 65535 : size));
            if (size >= 65535) {
                this.d.writeInt(size);
            }
            this.a.writeTo(this.d);
        }
    }
}
