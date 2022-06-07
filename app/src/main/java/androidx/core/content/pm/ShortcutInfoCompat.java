package androidx.core.content.pm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.net.UriCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ShortcutInfoCompat {
    Context a;
    String b;
    String c;
    Intent[] d;
    ComponentName e;
    CharSequence f;
    CharSequence g;
    CharSequence h;
    IconCompat i;
    boolean j;
    Person[] k;
    Set<String> l;
    @Nullable
    LocusIdCompat m;
    boolean n;
    int o;
    PersistableBundle p;
    long q;
    UserHandle r;
    boolean s;
    boolean t;
    boolean u;
    boolean v;
    boolean w;
    boolean x = true;
    boolean y;
    int z;

    ShortcutInfoCompat() {
    }

    @RequiresApi(25)
    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder intents = new ShortcutInfo.Builder(this.a, this.b).setShortLabel(this.f).setIntents(this.d);
        IconCompat iconCompat = this.i;
        if (iconCompat != null) {
            intents.setIcon(iconCompat.toIcon(this.a));
        }
        if (!TextUtils.isEmpty(this.g)) {
            intents.setLongLabel(this.g);
        }
        if (!TextUtils.isEmpty(this.h)) {
            intents.setDisabledMessage(this.h);
        }
        ComponentName componentName = this.e;
        if (componentName != null) {
            intents.setActivity(componentName);
        }
        Set<String> set = this.l;
        if (set != null) {
            intents.setCategories(set);
        }
        intents.setRank(this.o);
        PersistableBundle persistableBundle = this.p;
        if (persistableBundle != null) {
            intents.setExtras(persistableBundle);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            Person[] personArr = this.k;
            if (personArr != null && personArr.length > 0) {
                android.app.Person[] personArr2 = new android.app.Person[personArr.length];
                for (int i = 0; i < personArr2.length; i++) {
                    personArr2[i] = this.k[i].toAndroidPerson();
                }
                intents.setPersons(personArr2);
            }
            LocusIdCompat locusIdCompat = this.m;
            if (locusIdCompat != null) {
                intents.setLocusId(locusIdCompat.toLocusId());
            }
            intents.setLongLived(this.n);
        } else {
            intents.setExtras(a());
        }
        return intents.build();
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private PersistableBundle a() {
        if (this.p == null) {
            this.p = new PersistableBundle();
        }
        Person[] personArr = this.k;
        if (personArr != null && personArr.length > 0) {
            this.p.putInt("extraPersonCount", personArr.length);
            int i = 0;
            while (i < this.k.length) {
                PersistableBundle persistableBundle = this.p;
                StringBuilder sb = new StringBuilder();
                sb.append("extraPerson_");
                int i2 = i + 1;
                sb.append(i2);
                persistableBundle.putPersistableBundle(sb.toString(), this.k[i].toPersistableBundle());
                i = i2;
            }
        }
        LocusIdCompat locusIdCompat = this.m;
        if (locusIdCompat != null) {
            this.p.putString("extraLocusId", locusIdCompat.getId());
        }
        this.p.putBoolean("extraLongLived", this.n);
        return this.p;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Intent a(Intent intent) {
        Intent[] intentArr = this.d;
        intent.putExtra("android.intent.extra.shortcut.INTENT", intentArr[intentArr.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.f.toString());
        if (this.i != null) {
            Drawable drawable = null;
            if (this.j) {
                PackageManager packageManager = this.a.getPackageManager();
                ComponentName componentName = this.e;
                if (componentName != null) {
                    try {
                        drawable = packageManager.getActivityIcon(componentName);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                if (drawable == null) {
                    drawable = this.a.getApplicationInfo().loadIcon(packageManager);
                }
            }
            this.i.addToShortcutIntent(intent, drawable, this.a);
        }
        return intent;
    }

    @NonNull
    public String getId() {
        return this.b;
    }

    @NonNull
    public String getPackage() {
        return this.c;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.e;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.f;
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.g;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.h;
    }

    public int getDisabledReason() {
        return this.z;
    }

    @NonNull
    public Intent getIntent() {
        Intent[] intentArr = this.d;
        return intentArr[intentArr.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        Intent[] intentArr = this.d;
        return (Intent[]) Arrays.copyOf(intentArr, intentArr.length);
    }

    @Nullable
    public Set<String> getCategories() {
        return this.l;
    }

    @Nullable
    public LocusIdCompat getLocusId() {
        return this.m;
    }

    public int getRank() {
        return this.o;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public IconCompat getIcon() {
        return this.i;
    }

    @VisibleForTesting
    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static Person[] a(@NonNull PersistableBundle persistableBundle) {
        if (persistableBundle == null || !persistableBundle.containsKey("extraPersonCount")) {
            return null;
        }
        int i = persistableBundle.getInt("extraPersonCount");
        Person[] personArr = new Person[i];
        int i2 = 0;
        while (i2 < i) {
            StringBuilder sb = new StringBuilder();
            sb.append("extraPerson_");
            int i3 = i2 + 1;
            sb.append(i3);
            personArr[i2] = Person.fromPersistableBundle(persistableBundle.getPersistableBundle(sb.toString()));
            i2 = i3;
        }
        return personArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static List<ShortcutInfoCompat> a(@NonNull Context context, @NonNull List<ShortcutInfo> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (ShortcutInfo shortcutInfo : list) {
            arrayList.add(new Builder(context, shortcutInfo).build());
        }
        return arrayList;
    }

    @Nullable
    public PersistableBundle getExtras() {
        return this.p;
    }

    @Nullable
    public UserHandle getUserHandle() {
        return this.r;
    }

    public long getLastChangedTimestamp() {
        return this.q;
    }

    public boolean isCached() {
        return this.s;
    }

    public boolean isDynamic() {
        return this.t;
    }

    public boolean isPinned() {
        return this.u;
    }

    public boolean isDeclaredInManifest() {
        return this.v;
    }

    public boolean isImmutable() {
        return this.w;
    }

    public boolean isEnabled() {
        return this.x;
    }

    public boolean hasKeyFieldsOnly() {
        return this.y;
    }

    @Nullable
    @RequiresApi(25)
    static LocusIdCompat a(@NonNull ShortcutInfo shortcutInfo) {
        if (Build.VERSION.SDK_INT < 29) {
            return b(shortcutInfo.getExtras());
        }
        if (shortcutInfo.getLocusId() == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(shortcutInfo.getLocusId());
    }

    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private static LocusIdCompat b(@Nullable PersistableBundle persistableBundle) {
        String string;
        if (persistableBundle == null || (string = persistableBundle.getString("extraLocusId")) == null) {
            return null;
        }
        return new LocusIdCompat(string);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private final ShortcutInfoCompat a = new ShortcutInfoCompat();
        private boolean b;
        private Set<String> c;
        private Map<String, Map<String, List<String>>> d;
        private Uri e;

        public Builder(@NonNull Context context, @NonNull String str) {
            ShortcutInfoCompat shortcutInfoCompat = this.a;
            shortcutInfoCompat.a = context;
            shortcutInfoCompat.b = str;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull ShortcutInfoCompat shortcutInfoCompat) {
            this.a.a = shortcutInfoCompat.a;
            this.a.b = shortcutInfoCompat.b;
            this.a.c = shortcutInfoCompat.c;
            this.a.d = (Intent[]) Arrays.copyOf(shortcutInfoCompat.d, shortcutInfoCompat.d.length);
            this.a.e = shortcutInfoCompat.e;
            this.a.f = shortcutInfoCompat.f;
            this.a.g = shortcutInfoCompat.g;
            this.a.h = shortcutInfoCompat.h;
            this.a.z = shortcutInfoCompat.z;
            this.a.i = shortcutInfoCompat.i;
            this.a.j = shortcutInfoCompat.j;
            this.a.r = shortcutInfoCompat.r;
            this.a.q = shortcutInfoCompat.q;
            this.a.s = shortcutInfoCompat.s;
            this.a.t = shortcutInfoCompat.t;
            this.a.u = shortcutInfoCompat.u;
            this.a.v = shortcutInfoCompat.v;
            this.a.w = shortcutInfoCompat.w;
            this.a.x = shortcutInfoCompat.x;
            this.a.m = shortcutInfoCompat.m;
            this.a.n = shortcutInfoCompat.n;
            this.a.y = shortcutInfoCompat.y;
            this.a.o = shortcutInfoCompat.o;
            if (shortcutInfoCompat.k != null) {
                this.a.k = (Person[]) Arrays.copyOf(shortcutInfoCompat.k, shortcutInfoCompat.k.length);
            }
            if (shortcutInfoCompat.l != null) {
                this.a.l = new HashSet(shortcutInfoCompat.l);
            }
            if (shortcutInfoCompat.p != null) {
                this.a.p = shortcutInfoCompat.p;
            }
        }

        @RequiresApi(25)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull Context context, @NonNull ShortcutInfo shortcutInfo) {
            ShortcutInfoCompat shortcutInfoCompat = this.a;
            shortcutInfoCompat.a = context;
            shortcutInfoCompat.b = shortcutInfo.getId();
            this.a.c = shortcutInfo.getPackage();
            Intent[] intents = shortcutInfo.getIntents();
            this.a.d = (Intent[]) Arrays.copyOf(intents, intents.length);
            this.a.e = shortcutInfo.getActivity();
            this.a.f = shortcutInfo.getShortLabel();
            this.a.g = shortcutInfo.getLongLabel();
            this.a.h = shortcutInfo.getDisabledMessage();
            if (Build.VERSION.SDK_INT >= 28) {
                this.a.z = shortcutInfo.getDisabledReason();
            } else {
                this.a.z = shortcutInfo.isEnabled() ? 0 : 3;
            }
            this.a.l = shortcutInfo.getCategories();
            this.a.k = ShortcutInfoCompat.a(shortcutInfo.getExtras());
            this.a.r = shortcutInfo.getUserHandle();
            this.a.q = shortcutInfo.getLastChangedTimestamp();
            if (Build.VERSION.SDK_INT >= 30) {
                this.a.s = shortcutInfo.isCached();
            }
            this.a.t = shortcutInfo.isDynamic();
            this.a.u = shortcutInfo.isPinned();
            this.a.v = shortcutInfo.isDeclaredInManifest();
            this.a.w = shortcutInfo.isImmutable();
            this.a.x = shortcutInfo.isEnabled();
            this.a.y = shortcutInfo.hasKeyFieldsOnly();
            this.a.m = ShortcutInfoCompat.a(shortcutInfo);
            this.a.o = shortcutInfo.getRank();
            this.a.p = shortcutInfo.getExtras();
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence charSequence) {
            this.a.f = charSequence;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence charSequence) {
            this.a.g = charSequence;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence charSequence) {
            this.a.h = charSequence;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] intentArr) {
            this.a.d = intentArr;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat iconCompat) {
            this.a.i = iconCompat;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusIdCompat) {
            this.a.m = locusIdCompat;
            return this;
        }

        @NonNull
        public Builder setIsConversation() {
            this.b = true;
            return this;
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName componentName) {
            this.a.e = componentName;
            return this;
        }

        @NonNull
        public Builder setAlwaysBadged() {
            this.a.j = true;
            return this;
        }

        @NonNull
        public Builder setPerson(@NonNull Person person) {
            return setPersons(new Person[]{person});
        }

        @NonNull
        public Builder setPersons(@NonNull Person[] personArr) {
            this.a.k = personArr;
            return this;
        }

        @NonNull
        public Builder setCategories(@NonNull Set<String> set) {
            this.a.l = set;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setLongLived() {
            this.a.n = true;
            return this;
        }

        @NonNull
        public Builder setLongLived(boolean z) {
            this.a.n = z;
            return this;
        }

        @NonNull
        public Builder setRank(int i) {
            this.a.o = i;
            return this;
        }

        @NonNull
        public Builder setExtras(@NonNull PersistableBundle persistableBundle) {
            this.a.p = persistableBundle;
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String str) {
            if (this.c == null) {
                this.c = new HashSet();
            }
            this.c.add(str);
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String str, @NonNull String str2, @NonNull List<String> list) {
            addCapabilityBinding(str);
            if (!list.isEmpty()) {
                if (this.d == null) {
                    this.d = new HashMap();
                }
                if (this.d.get(str) == null) {
                    this.d.put(str, new HashMap());
                }
                this.d.get(str).put(str2, list);
            }
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder setSliceUri(@NonNull Uri uri) {
            this.e = uri;
            return this;
        }

        @NonNull
        @SuppressLint({"UnsafeNewApiCall"})
        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty(this.a.f)) {
                throw new IllegalArgumentException("Shortcut must have a non-empty label");
            } else if (this.a.d == null || this.a.d.length == 0) {
                throw new IllegalArgumentException("Shortcut must have an intent");
            } else {
                if (this.b) {
                    if (this.a.m == null) {
                        ShortcutInfoCompat shortcutInfoCompat = this.a;
                        shortcutInfoCompat.m = new LocusIdCompat(shortcutInfoCompat.b);
                    }
                    this.a.n = true;
                }
                if (this.c != null) {
                    if (this.a.l == null) {
                        this.a.l = new HashSet();
                    }
                    this.a.l.addAll(this.c);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    if (this.d != null) {
                        if (this.a.p == null) {
                            this.a.p = new PersistableBundle();
                        }
                        for (String str : this.d.keySet()) {
                            Map<String, List<String>> map = this.d.get(str);
                            this.a.p.putStringArray(str, (String[]) map.keySet().toArray(new String[0]));
                            for (String str2 : map.keySet()) {
                                List<String> list = map.get(str2);
                                PersistableBundle persistableBundle = this.a.p;
                                persistableBundle.putStringArray(str + "/" + str2, list == null ? new String[0] : (String[]) list.toArray(new String[0]));
                            }
                        }
                    }
                    if (this.e != null) {
                        if (this.a.p == null) {
                            this.a.p = new PersistableBundle();
                        }
                        this.a.p.putString("extraSliceUri", UriCompat.toSafeString(this.e));
                    }
                }
                return this.a;
            }
        }
    }
}
