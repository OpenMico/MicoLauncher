package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import androidx.annotation.NonNull;
import com.xiaomi.idm.api.IDMServer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class ServiceUtils {
    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Set<String> getAllRunningServices() {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) Utils.getApp().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningServices(Integer.MAX_VALUE);
        HashSet hashSet = new HashSet();
        if (runningServices == null || runningServices.size() == 0) {
            return null;
        }
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            hashSet.add(runningServiceInfo.service.getClassName());
        }
        return hashSet;
    }

    public static void startService(@NonNull String str) {
        if (str != null) {
            try {
                startService(Class.forName(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Argument 'className' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startService(@NonNull Class<?> cls) {
        if (cls != null) {
            startService(new Intent(Utils.getApp(), cls));
            return;
        }
        throw new NullPointerException("Argument 'cls' of type Class<?> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startService(Intent intent) {
        try {
            intent.setFlags(32);
            if (Build.VERSION.SDK_INT >= 26) {
                Utils.getApp().startForegroundService(intent);
            } else {
                Utils.getApp().startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean stopService(@NonNull String str) {
        if (str != null) {
            try {
                return stopService(Class.forName(str));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new NullPointerException("Argument 'className' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean stopService(@NonNull Class<?> cls) {
        if (cls != null) {
            return stopService(new Intent(Utils.getApp(), cls));
        }
        throw new NullPointerException("Argument 'cls' of type Class<?> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean stopService(@NonNull Intent intent) {
        if (intent != null) {
            try {
                return Utils.getApp().stopService(intent);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void bindService(@NonNull String str, @NonNull ServiceConnection serviceConnection, int i) {
        if (str == null) {
            throw new NullPointerException("Argument 'className' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (serviceConnection != null) {
            try {
                bindService(Class.forName(str), serviceConnection, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Argument 'conn' of type ServiceConnection (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void bindService(@NonNull Class<?> cls, @NonNull ServiceConnection serviceConnection, int i) {
        if (cls == null) {
            throw new NullPointerException("Argument 'cls' of type Class<?> (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (serviceConnection != null) {
            bindService(new Intent(Utils.getApp(), cls), serviceConnection, i);
        } else {
            throw new NullPointerException("Argument 'conn' of type ServiceConnection (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void bindService(@NonNull Intent intent, @NonNull ServiceConnection serviceConnection, int i) {
        if (intent == null) {
            throw new NullPointerException("Argument 'intent' of type Intent (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (serviceConnection != null) {
            try {
                Utils.getApp().bindService(intent, serviceConnection, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Argument 'conn' of type ServiceConnection (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void unbindService(@NonNull ServiceConnection serviceConnection) {
        if (serviceConnection != null) {
            Utils.getApp().unbindService(serviceConnection);
            return;
        }
        throw new NullPointerException("Argument 'conn' of type ServiceConnection (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isServiceRunning(@NonNull Class<?> cls) {
        if (cls != null) {
            return isServiceRunning(cls.getName());
        }
        throw new NullPointerException("Argument 'cls' of type Class<?> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isServiceRunning(@NonNull String str) {
        if (str != null) {
            try {
                List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) Utils.getApp().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningServices(Integer.MAX_VALUE);
                if (!(runningServices == null || runningServices.size() == 0)) {
                    for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                        if (str.equals(runningServiceInfo.service.getClassName())) {
                            return true;
                        }
                    }
                    return false;
                }
                return false;
            } catch (Exception unused) {
                return false;
            }
        } else {
            throw new NullPointerException("Argument 'className' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }
}
