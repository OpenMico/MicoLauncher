package io.realm.rx;

import android.os.Looper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposables;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.ObjectChangeSet;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.IdentityHashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class RealmObservableFactory implements RxObservableFactory {
    private static final BackpressureStrategy e = BackpressureStrategy.LATEST;
    private final boolean a;
    private ThreadLocal<a<RealmResults>> b = new ThreadLocal<a<RealmResults>>() { // from class: io.realm.rx.RealmObservableFactory.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a<RealmResults> initialValue() {
            return new a<>();
        }
    };
    private ThreadLocal<a<RealmList>> c = new ThreadLocal<a<RealmList>>() { // from class: io.realm.rx.RealmObservableFactory.10
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a<RealmList> initialValue() {
            return new a<>();
        }
    };
    private ThreadLocal<a<RealmModel>> d = new ThreadLocal<a<RealmModel>>() { // from class: io.realm.rx.RealmObservableFactory.11
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a<RealmModel> initialValue() {
            return new a<>();
        }
    };

    public int hashCode() {
        return 37;
    }

    @Override // io.realm.rx.RxObservableFactory
    public Flowable<Realm> from(Realm realm) {
        if (realm.isFrozen()) {
            return Flowable.just(realm);
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<Realm>() { // from class: io.realm.rx.RealmObservableFactory.12
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<Realm> flowableEmitter) throws Exception {
                final Realm instance = Realm.getInstance(configuration);
                final RealmChangeListener<Realm> realmChangeListener = new RealmChangeListener<Realm>() { // from class: io.realm.rx.RealmObservableFactory.12.1
                    /* renamed from: a */
                    public void onChange(Realm realm2) {
                        if (!flowableEmitter.isCancelled()) {
                            FlowableEmitter flowableEmitter2 = flowableEmitter;
                            if (RealmObservableFactory.this.a) {
                                realm2 = realm2.freeze();
                            }
                            flowableEmitter2.onNext(realm2);
                        }
                    }
                };
                instance.addChangeListener(realmChangeListener);
                flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.12.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!instance.isClosed()) {
                            instance.removeChangeListener(realmChangeListener);
                            instance.close();
                        }
                    }
                }));
                if (RealmObservableFactory.this.a) {
                    instance = instance.freeze();
                }
                flowableEmitter.onNext(instance);
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    public RealmObservableFactory(boolean z) {
        this.a = z;
    }

    @Override // io.realm.rx.RxObservableFactory
    public Flowable<DynamicRealm> from(DynamicRealm dynamicRealm) {
        if (dynamicRealm.isFrozen()) {
            return Flowable.just(dynamicRealm);
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<DynamicRealm>() { // from class: io.realm.rx.RealmObservableFactory.13
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<DynamicRealm> flowableEmitter) throws Exception {
                final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                final RealmChangeListener<DynamicRealm> realmChangeListener = new RealmChangeListener<DynamicRealm>() { // from class: io.realm.rx.RealmObservableFactory.13.1
                    /* renamed from: a */
                    public void onChange(DynamicRealm dynamicRealm2) {
                        if (!flowableEmitter.isCancelled()) {
                            FlowableEmitter flowableEmitter2 = flowableEmitter;
                            if (RealmObservableFactory.this.a) {
                                dynamicRealm2 = dynamicRealm2.freeze();
                            }
                            flowableEmitter2.onNext(dynamicRealm2);
                        }
                    }
                };
                instance.addChangeListener(realmChangeListener);
                flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.13.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!instance.isClosed()) {
                            instance.removeChangeListener(realmChangeListener);
                            instance.close();
                        }
                    }
                }));
                if (RealmObservableFactory.this.a) {
                    instance = instance.freeze();
                }
                flowableEmitter.onNext(instance);
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Flowable<RealmResults<E>> from(Realm realm, final RealmResults<E> realmResults) {
        if (realm.isFrozen()) {
            return Flowable.just(realmResults);
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.14
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<RealmResults<E>> flowableEmitter) {
                if (realmResults.isValid()) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.b.get()).a(realmResults);
                    final RealmChangeListener<RealmResults<E>> realmChangeListener = new RealmChangeListener<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.14.1
                        /* renamed from: a */
                        public void onChange(RealmResults<E> realmResults2) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                RealmResults realmResults3 = realmResults2;
                                if (RealmObservableFactory.this.a) {
                                    realmResults3 = realmResults2.freeze();
                                }
                                flowableEmitter2.onNext(realmResults3 == 1 ? 1 : 0);
                            }
                        }
                    };
                    realmResults.addChangeListener(realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.14.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmResults.removeChangeListener(realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.b.get()).b(realmResults);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? realmResults.freeze() : realmResults);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    private Scheduler a() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            return AndroidSchedulers.from(myLooper);
        }
        throw new IllegalStateException("No looper found");
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Observable<CollectionChange<RealmResults<E>>> changesetsFrom(Realm realm, final RealmResults<E> realmResults) {
        if (realm.isFrozen()) {
            return Observable.just(new CollectionChange(realmResults, null));
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<CollectionChange<RealmResults<E>>>() { // from class: io.realm.rx.RealmObservableFactory.15
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<CollectionChange<RealmResults<E>>> observableEmitter) {
                if (realmResults.isValid()) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.b.get()).a(realmResults);
                    final OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.15.1
                        /* renamed from: a */
                        public void onChange(RealmResults<E> realmResults2, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                observableEmitter.onNext(new CollectionChange(RealmObservableFactory.this.a ? realmResults.freeze() : realmResults, orderedCollectionChangeSet));
                            }
                        }
                    };
                    realmResults.addChangeListener(orderedRealmCollectionChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.15.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmResults.removeChangeListener(orderedRealmCollectionChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.b.get()).b(realmResults);
                        }
                    }));
                    observableEmitter.onNext(new CollectionChange(RealmObservableFactory.this.a ? realmResults.freeze() : realmResults, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Flowable<RealmResults<E>> from(DynamicRealm dynamicRealm, final RealmResults<E> realmResults) {
        if (dynamicRealm.isFrozen()) {
            return Flowable.just(realmResults);
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.16
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<RealmResults<E>> flowableEmitter) {
                if (realmResults.isValid()) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.b.get()).a(realmResults);
                    final RealmChangeListener<RealmResults<E>> realmChangeListener = new RealmChangeListener<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.16.1
                        /* renamed from: a */
                        public void onChange(RealmResults<E> realmResults2) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                RealmResults realmResults3 = realmResults2;
                                if (RealmObservableFactory.this.a) {
                                    realmResults3 = realmResults2.freeze();
                                }
                                flowableEmitter2.onNext(realmResults3 == 1 ? 1 : 0);
                            }
                        }
                    };
                    realmResults.addChangeListener(realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.16.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmResults.removeChangeListener(realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.b.get()).b(realmResults);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? realmResults.freeze() : realmResults);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Observable<CollectionChange<RealmResults<E>>> changesetsFrom(DynamicRealm dynamicRealm, final RealmResults<E> realmResults) {
        if (dynamicRealm.isFrozen()) {
            return Observable.just(new CollectionChange(realmResults, null));
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<CollectionChange<RealmResults<E>>>() { // from class: io.realm.rx.RealmObservableFactory.17
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<CollectionChange<RealmResults<E>>> observableEmitter) {
                if (realmResults.isValid()) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.b.get()).a(realmResults);
                    final OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<E>>() { // from class: io.realm.rx.RealmObservableFactory.17.1
                        /* renamed from: a */
                        public void onChange(RealmResults<E> realmResults2, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                ObservableEmitter observableEmitter2 = observableEmitter;
                                RealmResults realmResults3 = realmResults2;
                                if (RealmObservableFactory.this.a) {
                                    realmResults3 = realmResults2.freeze();
                                }
                                observableEmitter2.onNext(new CollectionChange(realmResults3, orderedCollectionChangeSet));
                            }
                        }
                    };
                    realmResults.addChangeListener(orderedRealmCollectionChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.17.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmResults.removeChangeListener(orderedRealmCollectionChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.b.get()).b(realmResults);
                        }
                    }));
                    observableEmitter.onNext(new CollectionChange(RealmObservableFactory.this.a ? realmResults.freeze() : realmResults, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Flowable<RealmList<E>> from(Realm realm, final RealmList<E> realmList) {
        if (realm.isFrozen()) {
            return Flowable.just(realmList);
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.2
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<RealmList<E>> flowableEmitter) {
                if (realmList.isValid()) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.c.get()).a(realmList);
                    final RealmChangeListener<RealmList<E>> realmChangeListener = new RealmChangeListener<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.2.1
                        /* renamed from: a */
                        public void onChange(RealmList<E> realmList2) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                RealmList realmList3 = realmList2;
                                if (RealmObservableFactory.this.a) {
                                    realmList3 = realmList2.freeze();
                                }
                                flowableEmitter2.onNext(realmList3 == 1 ? 1 : 0);
                            }
                        }
                    };
                    realmList.addChangeListener(realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmList.removeChangeListener(realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.c.get()).b(realmList);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? realmList.freeze() : realmList);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Observable<CollectionChange<RealmList<E>>> changesetsFrom(Realm realm, final RealmList<E> realmList) {
        if (realm.isFrozen()) {
            return Observable.just(new CollectionChange(realmList, null));
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<CollectionChange<RealmList<E>>>() { // from class: io.realm.rx.RealmObservableFactory.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<CollectionChange<RealmList<E>>> observableEmitter) {
                if (realmList.isValid()) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.c.get()).a(realmList);
                    final OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener = new OrderedRealmCollectionChangeListener<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.3.1
                        /* renamed from: a */
                        public void onChange(RealmList<E> realmList2, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                ObservableEmitter observableEmitter2 = observableEmitter;
                                RealmList realmList3 = realmList2;
                                if (RealmObservableFactory.this.a) {
                                    realmList3 = realmList2.freeze();
                                }
                                observableEmitter2.onNext(new CollectionChange(realmList3, orderedCollectionChangeSet));
                            }
                        }
                    };
                    realmList.addChangeListener(orderedRealmCollectionChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmList.removeChangeListener(orderedRealmCollectionChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.c.get()).b(realmList);
                        }
                    }));
                    observableEmitter.onNext(new CollectionChange(RealmObservableFactory.this.a ? realmList.freeze() : realmList, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Flowable<RealmList<E>> from(DynamicRealm dynamicRealm, final RealmList<E> realmList) {
        if (dynamicRealm.isFrozen()) {
            return Flowable.just(realmList);
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.4
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<RealmList<E>> flowableEmitter) {
                if (realmList.isValid()) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.c.get()).a(realmList);
                    final RealmChangeListener<RealmList<E>> realmChangeListener = new RealmChangeListener<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.4.1
                        /* renamed from: a */
                        public void onChange(RealmList<E> realmList2) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                RealmList realmList3 = realmList2;
                                if (RealmObservableFactory.this.a) {
                                    realmList3 = realmList2.freeze();
                                }
                                flowableEmitter2.onNext(realmList3 == 1 ? 1 : 0);
                            }
                        }
                    };
                    realmList.addChangeListener(realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmList.removeChangeListener(realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.c.get()).b(realmList);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? realmList.freeze() : realmList);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Observable<CollectionChange<RealmList<E>>> changesetsFrom(DynamicRealm dynamicRealm, final RealmList<E> realmList) {
        if (dynamicRealm.isFrozen()) {
            return Observable.just(new CollectionChange(realmList, null));
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<CollectionChange<RealmList<E>>>() { // from class: io.realm.rx.RealmObservableFactory.5
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<CollectionChange<RealmList<E>>> observableEmitter) {
                if (realmList.isValid()) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.c.get()).a(realmList);
                    final OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener = new OrderedRealmCollectionChangeListener<RealmList<E>>() { // from class: io.realm.rx.RealmObservableFactory.5.1
                        /* renamed from: a */
                        public void onChange(RealmList<E> realmList2, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                ObservableEmitter observableEmitter2 = observableEmitter;
                                RealmList realmList3 = realmList2;
                                if (RealmObservableFactory.this.a) {
                                    realmList3 = realmList2.freeze();
                                }
                                observableEmitter2.onNext(new CollectionChange(realmList3, orderedCollectionChangeSet));
                            }
                        }
                    };
                    realmList.addChangeListener(orderedRealmCollectionChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.5.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                realmList.removeChangeListener(orderedRealmCollectionChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.c.get()).b(realmList);
                        }
                    }));
                    observableEmitter.onNext(new CollectionChange(RealmObservableFactory.this.a ? realmList.freeze() : realmList, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Flowable<E> from(final Realm realm, final E e2) {
        if (realm.isFrozen()) {
            return Flowable.just(e2);
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<E>() { // from class: io.realm.rx.RealmObservableFactory.6
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<E> flowableEmitter) {
                if (!realm.isClosed()) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.d.get()).a(e2);
                    final RealmChangeListener<E> realmChangeListener = new RealmChangeListener<E>() { // from class: io.realm.rx.RealmObservableFactory.6.1
                        /* JADX WARN: Incorrect types in method signature: (TE;)V */
                        /* renamed from: a */
                        public void onChange(RealmModel realmModel) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                if (RealmObservableFactory.this.a) {
                                    realmModel = RealmObject.freeze(realmModel);
                                }
                                flowableEmitter2.onNext(realmModel);
                            }
                        }
                    };
                    RealmObject.addChangeListener(e2, (RealmChangeListener<RealmModel>) realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.6.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                RealmObject.removeChangeListener(e2, realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.d.get()).b(e2);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? RealmObject.freeze(e2) : e2);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E extends RealmModel> Observable<ObjectChange<E>> changesetsFrom(Realm realm, final E e2) {
        if (realm.isFrozen()) {
            return Observable.just(new ObjectChange(e2, null));
        }
        final RealmConfiguration configuration = realm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<ObjectChange<E>>() { // from class: io.realm.rx.RealmObservableFactory.7
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ObjectChange<E>> observableEmitter) {
                if (RealmObject.isValid(e2)) {
                    final Realm instance = Realm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.d.get()).a(e2);
                    final RealmObjectChangeListener<E> realmObjectChangeListener = new RealmObjectChangeListener<E>() { // from class: io.realm.rx.RealmObservableFactory.7.1
                        /* JADX WARN: Incorrect types in method signature: (TE;Lio/realm/ObjectChangeSet;)V */
                        @Override // io.realm.RealmObjectChangeListener
                        public void onChange(RealmModel realmModel, ObjectChangeSet objectChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                ObservableEmitter observableEmitter2 = observableEmitter;
                                if (RealmObservableFactory.this.a) {
                                    realmModel = RealmObject.freeze(realmModel);
                                }
                                observableEmitter2.onNext(new ObjectChange(realmModel, objectChangeSet));
                            }
                        }
                    };
                    RealmObject.addChangeListener(e2, (RealmObjectChangeListener<RealmModel>) realmObjectChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.7.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                RealmObject.removeChangeListener(e2, realmObjectChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.d.get()).b(e2);
                        }
                    }));
                    observableEmitter.onNext(new ObjectChange(RealmObservableFactory.this.a ? RealmObject.freeze(e2) : e2, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public Flowable<DynamicRealmObject> from(final DynamicRealm dynamicRealm, final DynamicRealmObject dynamicRealmObject) {
        if (dynamicRealm.isFrozen()) {
            return Flowable.just(dynamicRealmObject);
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Flowable.create(new FlowableOnSubscribe<DynamicRealmObject>() { // from class: io.realm.rx.RealmObservableFactory.8
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<DynamicRealmObject> flowableEmitter) {
                if (!dynamicRealm.isClosed()) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.d.get()).a(dynamicRealmObject);
                    final RealmChangeListener<DynamicRealmObject> realmChangeListener = new RealmChangeListener<DynamicRealmObject>() { // from class: io.realm.rx.RealmObservableFactory.8.1
                        /* renamed from: a */
                        public void onChange(DynamicRealmObject dynamicRealmObject2) {
                            if (!flowableEmitter.isCancelled()) {
                                FlowableEmitter flowableEmitter2 = flowableEmitter;
                                if (RealmObservableFactory.this.a) {
                                    dynamicRealmObject2 = (DynamicRealmObject) RealmObject.freeze(dynamicRealmObject2);
                                }
                                flowableEmitter2.onNext(dynamicRealmObject2);
                            }
                        }
                    };
                    RealmObject.addChangeListener(dynamicRealmObject, realmChangeListener);
                    flowableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.8.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                RealmObject.removeChangeListener(dynamicRealmObject, realmChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.d.get()).b(dynamicRealmObject);
                        }
                    }));
                    flowableEmitter.onNext(RealmObservableFactory.this.a ? (DynamicRealmObject) RealmObject.freeze(dynamicRealmObject) : dynamicRealmObject);
                }
            }
        }, e).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public Observable<ObjectChange<DynamicRealmObject>> changesetsFrom(DynamicRealm dynamicRealm, final DynamicRealmObject dynamicRealmObject) {
        if (dynamicRealm.isFrozen()) {
            return Observable.just(new ObjectChange(dynamicRealmObject, null));
        }
        final RealmConfiguration configuration = dynamicRealm.getConfiguration();
        Scheduler a2 = a();
        return Observable.create(new ObservableOnSubscribe<ObjectChange<DynamicRealmObject>>() { // from class: io.realm.rx.RealmObservableFactory.9
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ObjectChange<DynamicRealmObject>> observableEmitter) {
                if (RealmObject.isValid(dynamicRealmObject)) {
                    final DynamicRealm instance = DynamicRealm.getInstance(configuration);
                    ((a) RealmObservableFactory.this.d.get()).a(dynamicRealmObject);
                    final RealmObjectChangeListener<DynamicRealmObject> realmObjectChangeListener = new RealmObjectChangeListener<DynamicRealmObject>() { // from class: io.realm.rx.RealmObservableFactory.9.1
                        /* renamed from: a */
                        public void onChange(DynamicRealmObject dynamicRealmObject2, ObjectChangeSet objectChangeSet) {
                            if (!observableEmitter.isDisposed()) {
                                ObservableEmitter observableEmitter2 = observableEmitter;
                                if (RealmObservableFactory.this.a) {
                                    dynamicRealmObject2 = (DynamicRealmObject) RealmObject.freeze(dynamicRealmObject2);
                                }
                                observableEmitter2.onNext(new ObjectChange(dynamicRealmObject2, objectChangeSet));
                            }
                        }
                    };
                    dynamicRealmObject.addChangeListener(realmObjectChangeListener);
                    observableEmitter.setDisposable(Disposables.fromRunnable(new Runnable() { // from class: io.realm.rx.RealmObservableFactory.9.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!instance.isClosed()) {
                                RealmObject.removeChangeListener(dynamicRealmObject, realmObjectChangeListener);
                                instance.close();
                            }
                            ((a) RealmObservableFactory.this.d.get()).b(dynamicRealmObject);
                        }
                    }));
                    observableEmitter.onNext(new ObjectChange<>(RealmObservableFactory.this.a ? (DynamicRealmObject) RealmObject.freeze(dynamicRealmObject) : dynamicRealmObject, null));
                }
            }
        }).subscribeOn(a2).unsubscribeOn(a2);
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Single<RealmQuery<E>> from(Realm realm, RealmQuery<E> realmQuery) {
        throw new RuntimeException("RealmQuery not supported yet.");
    }

    @Override // io.realm.rx.RxObservableFactory
    public <E> Single<RealmQuery<E>> from(DynamicRealm dynamicRealm, RealmQuery<E> realmQuery) {
        throw new RuntimeException("RealmQuery not supported yet.");
    }

    public boolean equals(Object obj) {
        return obj instanceof RealmObservableFactory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a<K> {
        private final Map<K, Integer> a;

        private a() {
            this.a = new IdentityHashMap();
        }

        public void a(K k) {
            Integer num = this.a.get(k);
            if (num == null) {
                this.a.put(k, 1);
            } else {
                this.a.put(k, Integer.valueOf(num.intValue() + 1));
            }
        }

        public void b(K k) {
            Integer num = this.a.get(k);
            if (num == null) {
                throw new IllegalStateException("Object does not have any references: " + k);
            } else if (num.intValue() > 1) {
                this.a.put(k, Integer.valueOf(num.intValue() - 1));
            } else if (num.intValue() == 1) {
                this.a.remove(k);
            } else {
                throw new IllegalStateException("Invalid reference count: " + num);
            }
        }
    }
}
