package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import java.lang.reflect.Method;

/* compiled from: VersionedParcelParcel.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class a extends VersionedParcel {
    private final SparseIntArray a;
    private final Parcel b;
    private final int c;
    private final int d;
    private final String e;
    private int f;
    private int g;
    private int h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new ArrayMap(), new ArrayMap(), new ArrayMap());
    }

    private a(Parcel parcel, int i, int i2, String str, ArrayMap<String, Method> arrayMap, ArrayMap<String, Method> arrayMap2, ArrayMap<String, Class> arrayMap3) {
        super(arrayMap, arrayMap2, arrayMap3);
        this.a = new SparseIntArray();
        this.f = -1;
        this.g = 0;
        this.h = -1;
        this.b = parcel;
        this.c = i;
        this.d = i2;
        this.g = this.c;
        this.e = str;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean readField(int i) {
        while (this.g < this.d) {
            int i2 = this.h;
            if (i2 == i) {
                return true;
            }
            if (String.valueOf(i2).compareTo(String.valueOf(i)) > 0) {
                return false;
            }
            this.b.setDataPosition(this.g);
            int readInt = this.b.readInt();
            this.h = this.b.readInt();
            this.g += readInt;
        }
        return this.h == i;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void setOutputField(int i) {
        closeField();
        this.f = i;
        this.a.put(i, this.b.dataPosition());
        writeInt(0);
        writeInt(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void closeField() {
        int i = this.f;
        if (i >= 0) {
            int i2 = this.a.get(i);
            int dataPosition = this.b.dataPosition();
            this.b.setDataPosition(i2);
            this.b.writeInt(dataPosition - i2);
            this.b.setDataPosition(dataPosition);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected VersionedParcel createSubParcel() {
        Parcel parcel = this.b;
        int dataPosition = parcel.dataPosition();
        int i = this.g;
        if (i == this.c) {
            i = this.d;
        }
        return new a(parcel, dataPosition, i, this.e + "  ", this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr) {
        if (bArr != null) {
            this.b.writeInt(bArr.length);
            this.b.writeByteArray(bArr);
            return;
        }
        this.b.writeInt(-1);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            this.b.writeInt(bArr.length);
            this.b.writeByteArray(bArr, i, i2);
            return;
        }
        this.b.writeInt(-1);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeInt(int i) {
        this.b.writeInt(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeLong(long j) {
        this.b.writeLong(j);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeFloat(float f) {
        this.b.writeFloat(f);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeDouble(double d) {
        this.b.writeDouble(d);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeString(String str) {
        this.b.writeString(str);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongBinder(IBinder iBinder) {
        this.b.writeStrongBinder(iBinder);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeParcelable(Parcelable parcelable) {
        this.b.writeParcelable(parcelable, 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBoolean(boolean z) {
        this.b.writeInt(z ? 1 : 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongInterface(IInterface iInterface) {
        this.b.writeStrongInterface(iInterface);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBundle(Bundle bundle) {
        this.b.writeBundle(bundle);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected void writeCharSequence(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this.b, 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected CharSequence readCharSequence() {
        return (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.b);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public int readInt() {
        return this.b.readInt();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public long readLong() {
        return this.b.readLong();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public float readFloat() {
        return this.b.readFloat();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public double readDouble() {
        return this.b.readDouble();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public String readString() {
        return this.b.readString();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public IBinder readStrongBinder() {
        return this.b.readStrongBinder();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public byte[] readByteArray() {
        int readInt = this.b.readInt();
        if (readInt < 0) {
            return null;
        }
        byte[] bArr = new byte[readInt];
        this.b.readByteArray(bArr);
        return bArr;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public <T extends Parcelable> T readParcelable() {
        return (T) this.b.readParcelable(getClass().getClassLoader());
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public Bundle readBundle() {
        return this.b.readBundle(getClass().getClassLoader());
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean readBoolean() {
        return this.b.readInt() != 0;
    }
}
