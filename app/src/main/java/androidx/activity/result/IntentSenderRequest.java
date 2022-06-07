package androidx.activity.result;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class IntentSenderRequest implements Parcelable {
    @NonNull
    public static final Parcelable.Creator<IntentSenderRequest> CREATOR = new Parcelable.Creator<IntentSenderRequest>() { // from class: androidx.activity.result.IntentSenderRequest.1
        /* renamed from: a */
        public IntentSenderRequest createFromParcel(Parcel parcel) {
            return new IntentSenderRequest(parcel);
        }

        /* renamed from: a */
        public IntentSenderRequest[] newArray(int i) {
            return new IntentSenderRequest[i];
        }
    };
    @NonNull
    private final IntentSender a;
    @Nullable
    private final Intent b;
    private final int c;
    private final int d;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    IntentSenderRequest(@NonNull IntentSender intentSender, @Nullable Intent intent, int i, int i2) {
        this.a = intentSender;
        this.b = intent;
        this.c = i;
        this.d = i2;
    }

    @NonNull
    public IntentSender getIntentSender() {
        return this.a;
    }

    @Nullable
    public Intent getFillInIntent() {
        return this.b;
    }

    public int getFlagsMask() {
        return this.c;
    }

    public int getFlagsValues() {
        return this.d;
    }

    IntentSenderRequest(@NonNull Parcel parcel) {
        this.a = (IntentSender) parcel.readParcelable(IntentSender.class.getClassLoader());
        this.b = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        this.c = parcel.readInt();
        this.d = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(this.a, i);
        parcel.writeParcelable(this.b, i);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private IntentSender a;
        private Intent b;
        private int c;
        private int d;

        public Builder(@NonNull IntentSender intentSender) {
            this.a = intentSender;
        }

        public Builder(@NonNull PendingIntent pendingIntent) {
            this(pendingIntent.getIntentSender());
        }

        @NonNull
        public Builder setFillInIntent(@Nullable Intent intent) {
            this.b = intent;
            return this;
        }

        @NonNull
        public Builder setFlags(int i, int i2) {
            this.d = i;
            this.c = i2;
            return this;
        }

        @NonNull
        public IntentSenderRequest build() {
            return new IntentSenderRequest(this.a, this.b, this.c, this.d);
        }
    }
}
