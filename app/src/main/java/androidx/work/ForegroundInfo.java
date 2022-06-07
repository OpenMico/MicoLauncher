package androidx.work;

import android.app.Notification;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class ForegroundInfo {
    private final int a;
    private final int b;
    private final Notification c;

    public ForegroundInfo(int i, @NonNull Notification notification) {
        this(i, notification, 0);
    }

    public ForegroundInfo(int i, @NonNull Notification notification, int i2) {
        this.a = i;
        this.c = notification;
        this.b = i2;
    }

    public int getNotificationId() {
        return this.a;
    }

    public int getForegroundServiceType() {
        return this.b;
    }

    @NonNull
    public Notification getNotification() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ForegroundInfo foregroundInfo = (ForegroundInfo) obj;
        if (this.a == foregroundInfo.a && this.b == foregroundInfo.b) {
            return this.c.equals(foregroundInfo.c);
        }
        return false;
    }

    public int hashCode() {
        return (((this.a * 31) + this.b) * 31) + this.c.hashCode();
    }

    public String toString() {
        return "ForegroundInfo{mNotificationId=" + this.a + ", mForegroundServiceType=" + this.b + ", mNotification=" + this.c + '}';
    }
}
