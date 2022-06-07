package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class MetaDataUtils {
    private MetaDataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getMetaDataInApp(@NonNull String str) {
        if (str != null) {
            try {
                return String.valueOf(Utils.getApp().getPackageManager().getApplicationInfo(Utils.getApp().getPackageName(), 128).metaData.get(str));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInActivity(@NonNull Activity activity, @NonNull String str) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            return getMetaDataInActivity((Class<? extends Activity>) activity.getClass(), str);
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInActivity(@NonNull Class<? extends Activity> cls, @NonNull String str) {
        if (cls == null) {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            try {
                return String.valueOf(Utils.getApp().getPackageManager().getActivityInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInService(@NonNull Service service, @NonNull String str) {
        if (service == null) {
            throw new NullPointerException("Argument 'service' of type Service (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            return getMetaDataInService((Class<? extends Service>) service.getClass(), str);
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInService(@NonNull Class<? extends Service> cls, @NonNull String str) {
        if (cls == null) {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Service> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            try {
                return String.valueOf(Utils.getApp().getPackageManager().getServiceInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull String str) {
        if (broadcastReceiver == null) {
            throw new NullPointerException("Argument 'receiver' of type BroadcastReceiver (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            return getMetaDataInReceiver(broadcastReceiver, str);
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getMetaDataInReceiver(@NonNull Class<? extends BroadcastReceiver> cls, @NonNull String str) {
        if (cls == null) {
            throw new NullPointerException("Argument 'clz' of type Class<? extends BroadcastReceiver> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            try {
                return String.valueOf(Utils.getApp().getPackageManager().getReceiverInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            throw new NullPointerException("Argument 'key' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }
}
