package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class d extends i<Double> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(BaseRealm baseRealm, OsList osList, Class<Double> cls) {
        super(baseRealm, osList, cls);
    }

    @Nullable
    /* renamed from: a */
    public Double b(int i) {
        return (Double) this.b.getValue(i);
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Number)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.Number", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addDouble(((Number) obj).doubleValue());
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertDouble(i, ((Number) obj).doubleValue());
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setDouble(i, ((Number) obj).doubleValue());
    }
}
