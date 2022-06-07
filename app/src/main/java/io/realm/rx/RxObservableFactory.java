package io.realm.rx;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/* loaded from: classes5.dex */
public interface RxObservableFactory {
    Observable<ObjectChange<DynamicRealmObject>> changesetsFrom(DynamicRealm dynamicRealm, DynamicRealmObject dynamicRealmObject);

    <E> Observable<CollectionChange<RealmList<E>>> changesetsFrom(DynamicRealm dynamicRealm, RealmList<E> realmList);

    <E> Observable<CollectionChange<RealmResults<E>>> changesetsFrom(DynamicRealm dynamicRealm, RealmResults<E> realmResults);

    <E> Observable<CollectionChange<RealmList<E>>> changesetsFrom(Realm realm, RealmList<E> realmList);

    <E extends RealmModel> Observable<ObjectChange<E>> changesetsFrom(Realm realm, E e);

    <E> Observable<CollectionChange<RealmResults<E>>> changesetsFrom(Realm realm, RealmResults<E> realmResults);

    Flowable<DynamicRealm> from(DynamicRealm dynamicRealm);

    Flowable<DynamicRealmObject> from(DynamicRealm dynamicRealm, DynamicRealmObject dynamicRealmObject);

    <E> Flowable<RealmList<E>> from(DynamicRealm dynamicRealm, RealmList<E> realmList);

    <E> Flowable<RealmResults<E>> from(DynamicRealm dynamicRealm, RealmResults<E> realmResults);

    Flowable<Realm> from(Realm realm);

    <E> Flowable<RealmList<E>> from(Realm realm, RealmList<E> realmList);

    <E extends RealmModel> Flowable<E> from(Realm realm, E e);

    <E> Flowable<RealmResults<E>> from(Realm realm, RealmResults<E> realmResults);

    <E> Single<RealmQuery<E>> from(DynamicRealm dynamicRealm, RealmQuery<E> realmQuery);

    <E> Single<RealmQuery<E>> from(Realm realm, RealmQuery<E> realmQuery);
}
