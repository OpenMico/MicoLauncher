package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NotificationCompatJellybean.java */
@RequiresApi(16)
/* loaded from: classes.dex */
public class c {
    private static Field b;
    private static boolean c;
    private static Field e;
    private static Field f;
    private static Field g;
    private static Field h;
    private static boolean i;
    private static final Object a = new Object();
    private static final Object d = new Object();

    public static SparseArray<Bundle> a(List<Bundle> list) {
        int size = list.size();
        SparseArray<Bundle> sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = list.get(i2);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                sparseArray.put(i2, bundle);
            }
        }
        return sparseArray;
    }

    public static Bundle a(Notification notification) {
        synchronized (a) {
            if (c) {
                return null;
            }
            try {
                if (b == null) {
                    Field declaredField = Notification.class.getDeclaredField("extras");
                    if (!Bundle.class.isAssignableFrom(declaredField.getType())) {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        c = true;
                        return null;
                    }
                    declaredField.setAccessible(true);
                    b = declaredField;
                }
                Bundle bundle = (Bundle) b.get(notification);
                if (bundle == null) {
                    bundle = new Bundle();
                    b.set(notification, bundle);
                }
                return bundle;
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                c = true;
                return null;
            } catch (NoSuchFieldException e3) {
                Log.e("NotificationCompat", "Unable to access notification extras", e3);
                c = true;
                return null;
            }
        }
    }

    public static NotificationCompat.Action a(int i2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        boolean z;
        RemoteInput[] remoteInputArr;
        RemoteInput[] remoteInputArr2;
        if (bundle != null) {
            remoteInputArr2 = a(a(bundle, NotificationCompatExtras.EXTRA_REMOTE_INPUTS));
            remoteInputArr = a(a(bundle, "android.support.dataRemoteInputs"));
            z = bundle.getBoolean("android.support.allowGeneratedReplies");
        } else {
            z = false;
            remoteInputArr2 = null;
            remoteInputArr = null;
        }
        return new NotificationCompat.Action(i2, charSequence, pendingIntent, bundle, remoteInputArr2, remoteInputArr, z, 0, true, false);
    }

    public static Bundle a(Notification.Builder builder, NotificationCompat.Action action) {
        IconCompat iconCompat = action.getIconCompat();
        builder.addAction(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
        Bundle bundle = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            bundle.putParcelableArray(NotificationCompatExtras.EXTRA_REMOTE_INPUTS, a(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            bundle.putParcelableArray("android.support.dataRemoteInputs", a(action.getDataOnlyRemoteInputs()));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        return bundle;
    }

    public static int b(Notification notification) {
        int length;
        synchronized (d) {
            Object[] c2 = c(notification);
            length = c2 != null ? c2.length : 0;
        }
        return length;
    }

    public static NotificationCompat.Action a(Notification notification, int i2) {
        SparseArray sparseParcelableArray;
        synchronized (d) {
            try {
                try {
                    Object[] c2 = c(notification);
                    if (c2 != null) {
                        Object obj = c2[i2];
                        Bundle a2 = a(notification);
                        return a(f.getInt(obj), (CharSequence) g.get(obj), (PendingIntent) h.get(obj), (a2 == null || (sparseParcelableArray = a2.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS)) == null) ? null : (Bundle) sparseParcelableArray.get(i2));
                    }
                } catch (IllegalAccessException e2) {
                    Log.e("NotificationCompat", "Unable to access notification actions", e2);
                    i = true;
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Object[] c(Notification notification) {
        synchronized (d) {
            if (!a()) {
                return null;
            }
            try {
                return (Object[]) e.get(notification);
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification actions", e2);
                i = true;
                return null;
            }
        }
    }

    private static boolean a() {
        if (i) {
            return false;
        }
        try {
            if (e == null) {
                Class<?> cls = Class.forName("android.app.Notification$Action");
                f = cls.getDeclaredField("icon");
                g = cls.getDeclaredField("title");
                h = cls.getDeclaredField("actionIntent");
                e = Notification.class.getDeclaredField("actions");
                e.setAccessible(true);
            }
        } catch (ClassNotFoundException e2) {
            Log.e("NotificationCompat", "Unable to access notification actions", e2);
            i = true;
        } catch (NoSuchFieldException e3) {
            Log.e("NotificationCompat", "Unable to access notification actions", e3);
            i = true;
        }
        return true ^ i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static NotificationCompat.Action a(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("extras");
        return new NotificationCompat.Action(bundle.getInt("icon"), bundle.getCharSequence("title"), (PendingIntent) bundle.getParcelable("actionIntent"), bundle.getBundle("extras"), a(a(bundle, "remoteInputs")), a(a(bundle, "dataOnlyRemoteInputs")), bundle2 != null ? bundle2.getBoolean("android.support.allowGeneratedReplies", false) : false, bundle.getInt("semanticAction"), bundle.getBoolean("showsUserInterface"), false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Bundle a(NotificationCompat.Action action) {
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        IconCompat iconCompat = action.getIconCompat();
        bundle2.putInt("icon", iconCompat != null ? iconCompat.getResId() : 0);
        bundle2.putCharSequence("title", action.getTitle());
        bundle2.putParcelable("actionIntent", action.getActionIntent());
        if (action.getExtras() != null) {
            bundle = new Bundle(action.getExtras());
        } else {
            bundle = new Bundle();
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        bundle2.putBundle("extras", bundle);
        bundle2.putParcelableArray("remoteInputs", a(action.getRemoteInputs()));
        bundle2.putBoolean("showsUserInterface", action.getShowsUserInterface());
        bundle2.putInt("semanticAction", action.getSemanticAction());
        return bundle2;
    }

    private static RemoteInput b(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("allowedDataTypes");
        HashSet hashSet = new HashSet();
        if (stringArrayList != null) {
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next());
            }
        }
        return new RemoteInput(bundle.getString("resultKey"), bundle.getCharSequence("label"), bundle.getCharSequenceArray("choices"), bundle.getBoolean("allowFreeFormInput"), 0, bundle.getBundle("extras"), hashSet);
    }

    private static Bundle a(RemoteInput remoteInput) {
        Bundle bundle = new Bundle();
        bundle.putString("resultKey", remoteInput.getResultKey());
        bundle.putCharSequence("label", remoteInput.getLabel());
        bundle.putCharSequenceArray("choices", remoteInput.getChoices());
        bundle.putBoolean("allowFreeFormInput", remoteInput.getAllowFreeFormInput());
        bundle.putBundle("extras", remoteInput.getExtras());
        Set<String> allowedDataTypes = remoteInput.getAllowedDataTypes();
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>(allowedDataTypes.size());
            for (String str : allowedDataTypes) {
                arrayList.add(str);
            }
            bundle.putStringArrayList("allowedDataTypes", arrayList);
        }
        return bundle;
    }

    private static RemoteInput[] a(Bundle[] bundleArr) {
        if (bundleArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[bundleArr.length];
        for (int i2 = 0; i2 < bundleArr.length; i2++) {
            remoteInputArr[i2] = b(bundleArr[i2]);
        }
        return remoteInputArr;
    }

    private static Bundle[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[remoteInputArr.length];
        for (int i2 = 0; i2 < remoteInputArr.length; i2++) {
            bundleArr[i2] = a(remoteInputArr[i2]);
        }
        return bundleArr;
    }

    private static Bundle[] a(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Bundle[]) || parcelableArray == null) {
            return (Bundle[]) parcelableArray;
        }
        Bundle[] bundleArr = (Bundle[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
        bundle.putParcelableArray(str, bundleArr);
        return bundleArr;
    }
}
