package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.utils.HolderCacheManager;
import com.xiaomi.micolauncher.common.utils.ViewHolderFactory;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AllAppItemViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppAlarmViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppGalleryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppSelectedHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppWeatherViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationListHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationSingleHolder;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class AppSkillHolderCacheManager {
    private HorizontalRecyclerView a;
    private ConcurrentHashMap<Integer, LinkedList<BaseAppHolder>> b;
    private ConcurrentHashMap<Integer, LinkedList<BaseAppHolder>> c;

    /* loaded from: classes3.dex */
    public static class a {
        private static final AppSkillHolderCacheManager a = new AppSkillHolderCacheManager();
    }

    public static AppSkillHolderCacheManager getManager() {
        return a.a;
    }

    private AppSkillHolderCacheManager() {
        this.b = new ConcurrentHashMap<>();
        this.c = new ConcurrentHashMap<>();
        a(MicoApplication.getApp());
    }

    public void initAppSkillViewHolder() {
        clearAllAppViewHolder();
        a();
        b();
        c();
        d();
    }

    public void initSkillMyViewHolder() {
        this.c.clear();
        LinkedList<BaseAppHolder> linkedList = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(1, linkedList, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$V00rJ5JE9G0-wt633HTT0cS3TEY
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder l;
                l = AppSkillHolderCacheManager.this.l();
                return l;
            }
        });
        this.c.put(1, linkedList);
        LinkedList<BaseAppHolder> linkedList2 = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(1, linkedList2, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$LERuRC_zIHxCRhnHJomj0BG12Xk
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder k;
                k = AppSkillHolderCacheManager.this.k();
                return k;
            }
        });
        this.c.put(2, linkedList2);
        LinkedList<BaseAppHolder> linkedList3 = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(1, linkedList3, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$rHCA7flTAPMayMKSsLVLRqGAqQ0
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder j;
                j = AppSkillHolderCacheManager.this.j();
                return j;
            }
        });
        this.c.put(3, linkedList3);
        LinkedList<BaseAppHolder> linkedList4 = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(6, linkedList4, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$W4KjWdw8qSc1C5SmTbly1908OQ4
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder i;
                i = AppSkillHolderCacheManager.this.i();
                return i;
            }
        });
        this.c.put(0, linkedList4);
    }

    public /* synthetic */ BaseAppHolder l() {
        return new AppWeatherViewHolder(a(R.layout.apps_card_weather));
    }

    public /* synthetic */ BaseAppHolder k() {
        return new AppGalleryViewHolder(a(R.layout.apps_card_gallery));
    }

    public /* synthetic */ BaseAppHolder j() {
        return new AppAlarmViewHolder(a(R.layout.apps_card_alarm));
    }

    public /* synthetic */ BaseAppHolder i() {
        return new AppViewHolder(a(R.layout.app_item));
    }

    public BaseAppHolder fetchViewHolderHolder(int i) {
        return (BaseAppHolder) HolderCacheManager.fetchViewHolder(this.c.get(Integer.valueOf(i)), null);
    }

    private void a() {
        LinkedList<BaseAppHolder> linkedList = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(1, linkedList, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$_wyLO26ohr77VIXDMjTCjXbzJMc
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder h;
                h = AppSkillHolderCacheManager.this.h();
                return h;
            }
        });
        this.b.put(1, linkedList);
    }

    public /* synthetic */ BaseAppHolder h() {
        return new OperationSingleHolder(a(R.layout.app_operation_single_layout));
    }

    private void b() {
        LinkedList<BaseAppHolder> linkedList = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(2, linkedList, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$fYcY-TlVOPpLjoyB5xRtKvIe3oc
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder g;
                g = AppSkillHolderCacheManager.this.g();
                return g;
            }
        });
        this.b.put(2, linkedList);
    }

    public /* synthetic */ BaseAppHolder g() {
        return new AppSelectedHolder(a(R.layout.app_selected_layout));
    }

    private void c() {
        LinkedList<BaseAppHolder> linkedList = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(2, linkedList, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$xTEMOLoY_1Z4BbAnsOdREgqpXrI
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder f;
                f = AppSkillHolderCacheManager.this.f();
                return f;
            }
        });
        this.b.put(3, linkedList);
    }

    public /* synthetic */ BaseAppHolder f() {
        return new OperationListHolder(a(R.layout.app_operation_list_layout));
    }

    private void d() {
        LinkedList<BaseAppHolder> linkedList = new LinkedList<>();
        HolderCacheManager.prepareViewHolders(6, linkedList, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppSkillHolderCacheManager$QgY970oJcthuNKSJT0YYluyPGfc
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                BaseAppHolder e;
                e = AppSkillHolderCacheManager.this.e();
                return e;
            }
        });
        this.b.put(5, linkedList);
    }

    public /* synthetic */ BaseAppHolder e() {
        return new AllAppItemViewHolder(a(R.layout.app_all_item_layout));
    }

    public BaseAppHolder fetchBaseHolder(int i) {
        return (BaseAppHolder) HolderCacheManager.fetchViewHolder(this.b.get(Integer.valueOf(i)), null);
    }

    private void a(Context context) {
        if (this.a == null) {
            this.a = new HorizontalRecyclerView(context);
        }
    }

    private View a(int i) {
        return X2CWrapper.inflate(this.a.getContext(), i, (ViewGroup) this.a, false);
    }

    public void clearAllAppViewHolder() {
        this.b.clear();
    }
}
