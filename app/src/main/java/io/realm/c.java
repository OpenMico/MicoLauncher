package io.realm;

import io.realm.internal.OsList;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class c extends i<Date> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(BaseRealm baseRealm, OsList osList, Class<Date> cls) {
        super(baseRealm, osList, cls);
    }

    @Nullable
    /* renamed from: a */
    public Date b(int i) {
        return (Date) this.b.getValue(i);
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Date)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.util.Date", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addDate((Date) obj);
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertDate(i, (Date) obj);
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setDate(i, (Date) obj);
    }
}
