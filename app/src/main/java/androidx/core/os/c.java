package androidx.core.os;

import android.os.LocaleList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.Locale;

/* compiled from: LocaleListPlatformWrapper.java */
@RequiresApi(24)
/* loaded from: classes.dex */
final class c implements b {
    private final LocaleList a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(LocaleList localeList) {
        this.a = localeList;
    }

    @Override // androidx.core.os.b
    public Object a() {
        return this.a;
    }

    @Override // androidx.core.os.b
    public Locale a(int i) {
        return this.a.get(i);
    }

    @Override // androidx.core.os.b
    public boolean b() {
        return this.a.isEmpty();
    }

    @Override // androidx.core.os.b
    public int c() {
        return this.a.size();
    }

    @Override // androidx.core.os.b
    public int a(Locale locale) {
        return this.a.indexOf(locale);
    }

    public boolean equals(Object obj) {
        return this.a.equals(((b) obj).a());
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }

    @Override // androidx.core.os.b
    public String d() {
        return this.a.toLanguageTags();
    }

    @Override // androidx.core.os.b
    @Nullable
    public Locale a(@NonNull String[] strArr) {
        return this.a.getFirstMatch(strArr);
    }
}
