package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class p extends i<String> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(BaseRealm baseRealm, OsList osList, Class<String> cls) {
        super(baseRealm, osList, cls);
    }

    @Nullable
    /* renamed from: a */
    public String b(int i) {
        return (String) this.b.getValue(i);
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof String)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.String", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addString((String) obj);
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertString(i, (String) obj);
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setString(i, (String) obj);
    }
}
