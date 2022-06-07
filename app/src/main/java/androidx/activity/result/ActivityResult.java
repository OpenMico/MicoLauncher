package androidx.activity.result;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class ActivityResult implements Parcelable {
    @NonNull
    public static final Parcelable.Creator<ActivityResult> CREATOR = new Parcelable.Creator<ActivityResult>() { // from class: androidx.activity.result.ActivityResult.1
        /* renamed from: a */
        public ActivityResult createFromParcel(@NonNull Parcel parcel) {
            return new ActivityResult(parcel);
        }

        /* renamed from: a */
        public ActivityResult[] newArray(int i) {
            return new ActivityResult[i];
        }
    };
    private final int a;
    @Nullable
    private final Intent b;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityResult(int i, @Nullable Intent intent) {
        this.a = i;
        this.b = intent;
    }

    ActivityResult(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt() == 0 ? null : (Intent) Intent.CREATOR.createFromParcel(parcel);
    }

    public int getResultCode() {
        return this.a;
    }

    @Nullable
    public Intent getData() {
        return this.b;
    }

    public String toString() {
        return "ActivityResult{resultCode=" + resultCodeToString(this.a) + ", data=" + this.b + '}';
    }

    @NonNull
    public static String resultCodeToString(int i) {
        switch (i) {
            case -1:
                return "RESULT_OK";
            case 0:
                return "RESULT_CANCELED";
            default:
                return String.valueOf(i);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b == null ? 0 : 1);
        Intent intent = this.b;
        if (intent != null) {
            intent.writeToParcel(parcel, i);
        }
    }
}
