package com.xiaomi.passport.LocalFeatures;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.accounts.ILocalFeatureManagerResponse;

/* loaded from: classes4.dex */
public class LocalFeaturesManagerResponse implements Parcelable {
    public static final Parcelable.Creator<LocalFeaturesManagerResponse> CREATOR = new Parcelable.Creator<LocalFeaturesManagerResponse>() { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesManagerResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalFeaturesManagerResponse createFromParcel(Parcel parcel) {
            return new LocalFeaturesManagerResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalFeaturesManagerResponse[] newArray(int i) {
            return new LocalFeaturesManagerResponse[i];
        }
    };
    private static final String TAG = "LocalFeaturesManagerRes";
    private ILocalFeatureManagerResponse mLocalFeatureManagerResponse;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocalFeaturesManagerResponse(ILocalFeatureManagerResponse iLocalFeatureManagerResponse) {
        this.mLocalFeatureManagerResponse = iLocalFeatureManagerResponse;
    }

    public LocalFeaturesManagerResponse(Parcel parcel) {
        this.mLocalFeatureManagerResponse = ILocalFeatureManagerResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle bundle) {
        try {
            this.mLocalFeatureManagerResponse.onResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public void onRequestContinued() {
        try {
            this.mLocalFeatureManagerResponse.onRequestContinued();
        } catch (RemoteException unused) {
        }
    }

    public void onError(int i, String str) {
        try {
            this.mLocalFeatureManagerResponse.onError(i, str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mLocalFeatureManagerResponse.asBinder());
    }
}
