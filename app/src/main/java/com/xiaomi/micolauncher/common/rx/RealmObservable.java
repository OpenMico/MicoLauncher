package com.xiaomi.micolauncher.common.rx;

import com.xiaomi.micolauncher.common.L;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmObject;

/* loaded from: classes3.dex */
public class RealmObservable {
    public static <T extends RealmObject, P> Observable<T> object(final Function<P, Realm> function, final Function<Realm, T> function2, boolean z) {
        return Observable.create(new OnSubscribeRealm<T>(z) { // from class: com.xiaomi.micolauncher.common.rx.RealmObservable.1
            @Override // com.xiaomi.micolauncher.common.rx.OnSubscribeRealm
            public Realm getRealm() {
                try {
                    return (Realm) function.apply(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    L.base.e("getRealm errror %s", e.getMessage());
                    return null;
                }
            }

            /* JADX WARN: Incorrect return type in method signature: (Lio/realm/Realm;)TT; */
            /* renamed from: a */
            public RealmObject operate(Realm realm) {
                try {
                    return (RealmObject) function2.apply(realm);
                } catch (Exception e) {
                    e.printStackTrace();
                    L.base.e("realm operate errror %s", e.getMessage());
                    return null;
                }
            }
        });
    }
}
