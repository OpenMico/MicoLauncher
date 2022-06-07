package com.xiaomi.micolauncher.module.skill.manager;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.module.skill.bean.SkillItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillPagingManager {
    private boolean a;
    private int b;
    private boolean c;
    private int d;
    private boolean e;
    private List<SkillItem> f;

    /* loaded from: classes3.dex */
    private static class a {
        private static final SkillPagingManager a = new SkillPagingManager();
    }

    public static SkillPagingManager getManager() {
        return a.a;
    }

    private SkillPagingManager() {
        this.f = new ArrayList();
        a();
    }

    private void a() {
        this.a = true;
        this.b = 1;
        this.d = 0;
        this.e = false;
    }

    public void clear() {
        this.f.clear();
        a();
    }

    public void setIndex(int i) {
        this.d = i;
    }

    public int getIndex() {
        return this.d;
    }

    public Observable<List<SkillItem>> loadData(final Context context, final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$EVNaxLQXtM_xxS-a-hMFquEmgx8
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillPagingManager.this.b(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context, String str, final ObservableEmitter observableEmitter) throws Exception {
        List<SkillItem> list = this.f;
        if (list == null || list.size() <= 0) {
            SkillDataManager.getManager().loadCategorySkills(Hardware.current(context).getName(), this.b, 20, str).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$K4xkBX9CbaZjVs50VtEgPYSzrnQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillPagingManager.this.a(observableEmitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$RcCxBPYCZhZWGSUMNeVO6hIgDbI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillPagingManager.b(ObservableEmitter.this, (Throwable) obj);
                }
            });
            return;
        }
        observableEmitter.onNext(this.f);
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter, List list) throws Exception {
        this.b++;
        this.e = true;
        this.f.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.f.add(new SkillItem((SkillStore.SkillV2) it.next()));
        }
        observableEmitter.onNext(this.f);
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    public Observable<List<SkillItem>> autoPaging(final Context context, final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$5-LCUJ8nUyvKHWObWXK23lABvFs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillPagingManager.this.a(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, String str, final ObservableEmitter observableEmitter) throws Exception {
        final ArrayList arrayList = new ArrayList();
        if (!this.a || this.c || !this.e) {
            observableEmitter.onNext(arrayList);
            observableEmitter.onComplete();
            return;
        }
        this.c = true;
        SkillDataManager.getManager().loadCategorySkills(Hardware.current(context).getName(), this.b, 20, str).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$qxWtPHgzjC5sur_RyqLRJ2_61-o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillPagingManager.this.a(arrayList, observableEmitter, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillPagingManager$O9Qnlu61InCThgheC0ERr5OKHkU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillPagingManager.this.a(observableEmitter, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, ObservableEmitter observableEmitter, List list2) throws Exception {
        this.b++;
        this.c = false;
        if (list2 == null || list2.size() <= 0) {
            this.a = false;
        } else {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                list.add(new SkillItem((SkillStore.SkillV2) it.next()));
            }
        }
        if (this.e) {
            this.f.addAll(list);
        }
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        this.c = false;
        this.a = false;
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }
}
