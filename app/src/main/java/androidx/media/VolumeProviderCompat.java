package androidx.media;

import android.media.VolumeProvider;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class VolumeProviderCompat {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private final int a;
    private final int b;
    private final String c;
    private int d;
    private Callback e;
    private VolumeProvider f;

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProviderCompat volumeProviderCompat);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface ControlType {
    }

    public void onAdjustVolume(int i) {
    }

    public void onSetVolumeTo(int i) {
    }

    public VolumeProviderCompat(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public VolumeProviderCompat(int i, int i2, int i3, @Nullable String str) {
        this.a = i;
        this.b = i2;
        this.d = i3;
        this.c = str;
    }

    public final int getCurrentVolume() {
        return this.d;
    }

    public final int getVolumeControl() {
        return this.a;
    }

    public final int getMaxVolume() {
        return this.b;
    }

    public final void setCurrentVolume(int i) {
        this.d = i;
        if (Build.VERSION.SDK_INT >= 21) {
            a.a((VolumeProvider) getVolumeProvider(), i);
        }
        Callback callback = this.e;
        if (callback != null) {
            callback.onVolumeChanged(this);
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public final String getVolumeControlId() {
        return this.c;
    }

    public void setCallback(Callback callback) {
        this.e = callback;
    }

    public Object getVolumeProvider() {
        if (this.f == null) {
            if (Build.VERSION.SDK_INT >= 30) {
                this.f = new VolumeProvider(this.a, this.b, this.d, this.c) { // from class: androidx.media.VolumeProviderCompat.1
                    @Override // android.media.VolumeProvider
                    public void onSetVolumeTo(int i) {
                        VolumeProviderCompat.this.onSetVolumeTo(i);
                    }

                    @Override // android.media.VolumeProvider
                    public void onAdjustVolume(int i) {
                        VolumeProviderCompat.this.onAdjustVolume(i);
                    }
                };
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.f = new VolumeProvider(this.a, this.b, this.d) { // from class: androidx.media.VolumeProviderCompat.2
                    @Override // android.media.VolumeProvider
                    public void onSetVolumeTo(int i) {
                        VolumeProviderCompat.this.onSetVolumeTo(i);
                    }

                    @Override // android.media.VolumeProvider
                    public void onAdjustVolume(int i) {
                        VolumeProviderCompat.this.onAdjustVolume(i);
                    }
                };
            }
        }
        return this.f;
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static void a(VolumeProvider volumeProvider, int i) {
            volumeProvider.setCurrentVolume(i);
        }
    }
}
