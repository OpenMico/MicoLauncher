package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.umeng.analytics.pro.ai;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ParcelUtils {
    private ParcelUtils() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Parcelable toParcelable(VersionedParcelable versionedParcelable) {
        return new ParcelImpl(versionedParcelable);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static <T extends VersionedParcelable> T fromParcelable(Parcelable parcelable) {
        if (parcelable instanceof ParcelImpl) {
            return (T) ((ParcelImpl) parcelable).getVersionedParcel();
        }
        throw new IllegalArgumentException("Invalid parcel");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static void toOutputStream(VersionedParcelable versionedParcelable, OutputStream outputStream) {
        b bVar = new b(null, outputStream);
        bVar.writeVersionedParcelable(versionedParcelable);
        bVar.closeField();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static <T extends VersionedParcelable> T fromInputStream(InputStream inputStream) {
        return (T) new b(inputStream, null).readVersionedParcelable();
    }

    public static void putVersionedParcelable(@NonNull Bundle bundle, @NonNull String str, @Nullable VersionedParcelable versionedParcelable) {
        if (versionedParcelable != null) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(ai.at, toParcelable(versionedParcelable));
            bundle.putParcelable(str, bundle2);
        }
    }

    @Nullable
    public static <T extends VersionedParcelable> T getVersionedParcelable(@NonNull Bundle bundle, @NonNull String str) {
        try {
            Bundle bundle2 = (Bundle) bundle.getParcelable(str);
            if (bundle2 == null) {
                return null;
            }
            bundle2.setClassLoader(ParcelUtils.class.getClassLoader());
            return (T) fromParcelable(bundle2.getParcelable(ai.at));
        } catch (RuntimeException unused) {
            return null;
        }
    }

    public static void putVersionedParcelableList(@NonNull Bundle bundle, @NonNull String str, @NonNull List<? extends VersionedParcelable> list) {
        Bundle bundle2 = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (VersionedParcelable versionedParcelable : list) {
            arrayList.add(toParcelable(versionedParcelable));
        }
        bundle2.putParcelableArrayList(ai.at, arrayList);
        bundle.putParcelable(str, bundle2);
    }

    @Nullable
    public static <T extends VersionedParcelable> List<T> getVersionedParcelableList(Bundle bundle, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            Bundle bundle2 = (Bundle) bundle.getParcelable(str);
            bundle2.setClassLoader(ParcelUtils.class.getClassLoader());
            Iterator it = bundle2.getParcelableArrayList(ai.at).iterator();
            while (it.hasNext()) {
                arrayList.add(fromParcelable((Parcelable) it.next()));
            }
            return arrayList;
        } catch (RuntimeException unused) {
            return null;
        }
    }
}
