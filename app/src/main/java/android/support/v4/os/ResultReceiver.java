package android.support.v4.os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;
import androidx.annotation.RestrictTo;

@SuppressLint({"BanParcelableUsage"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>() { // from class: android.support.v4.os.ResultReceiver.1
        /* renamed from: a */
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        /* renamed from: a */
        public ResultReceiver[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    final boolean a;
    final Handler b;
    IResultReceiver c;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected void onReceiveResult(int i, Bundle bundle) {
    }

    /* loaded from: classes.dex */
    class b implements Runnable {
        final int a;
        final Bundle b;

        b(int i, Bundle bundle) {
            this.a = i;
            this.b = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            ResultReceiver.this.onReceiveResult(this.a, this.b);
        }
    }

    /* loaded from: classes.dex */
    class a extends IResultReceiver.Stub {
        a() {
        }

        @Override // android.support.v4.os.IResultReceiver
        public void send(int i, Bundle bundle) {
            if (ResultReceiver.this.b != null) {
                ResultReceiver.this.b.post(new b(i, bundle));
            } else {
                ResultReceiver.this.onReceiveResult(i, bundle);
            }
        }
    }

    public ResultReceiver(Handler handler) {
        this.a = true;
        this.b = handler;
    }

    public void send(int i, Bundle bundle) {
        if (this.a) {
            Handler handler = this.b;
            if (handler != null) {
                handler.post(new b(i, bundle));
            } else {
                onReceiveResult(i, bundle);
            }
        } else {
            IResultReceiver iResultReceiver = this.c;
            if (iResultReceiver != null) {
                try {
                    iResultReceiver.send(i, bundle);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.c == null) {
                this.c = new a();
            }
            parcel.writeStrongBinder(this.c.asBinder());
        }
    }

    ResultReceiver(Parcel parcel) {
        this.a = false;
        this.b = null;
        this.c = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }
}
