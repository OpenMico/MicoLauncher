package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.micolauncher.common.utils.HolderCacheManager;
import com.xiaomi.micolauncher.common.utils.ViewFactory;
import com.xiaomi.micolauncher.common.utils.ViewHolderFactory;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.model.MicoMode;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public class MiotViewCachedManager {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MiotViewCachedManager a;
    private FrameLayout b;
    private LayoutInflater c;
    private final LinkedList<MiotDeviceViewHolder> d = new LinkedList<>();
    private final LinkedList<View> e = new LinkedList<>();
    private final LinkedList<BaseViewHolder> f = new LinkedList<>();
    private BlockingQueue<BaseViewHolder> g = new LinkedBlockingQueue();
    private BlockingQueue<BaseViewHolder> h = new LinkedBlockingQueue();
    private BlockingQueue<BaseViewHolder> i = new LinkedBlockingQueue();
    private BlockingQueue<BaseViewHolder> j = new LinkedBlockingQueue();
    private BlockingQueue<BaseViewHolder> k = new LinkedBlockingQueue();
    private BlockingQueue<BaseViewHolder> l = new LinkedBlockingQueue();
    private LinkedList<MicoMediaViewHolder> m = new LinkedList<>();
    private LinkedList<SceneListViewHolder> n = new LinkedList<>();
    private ViewHolderFactory o = new ViewHolderFactory<MiotDeviceViewHolder>() { // from class: com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager.1
        /* renamed from: a */
        public MiotDeviceViewHolder createViewHolder() {
            return new MiotDeviceViewHolder(MiotViewCachedManager.this.c.inflate(R.layout.mico_miot_device_view_holder, (ViewGroup) MiotViewCachedManager.this.b, false));
        }
    };
    private ViewHolderFactory p = new ViewHolderFactory<MicoMediaViewHolder>() { // from class: com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager.2
        /* renamed from: a */
        public MicoMediaViewHolder createViewHolder() {
            return new MicoMediaViewHolder(MiotViewCachedManager.this.c.inflate(R.layout.mico_item_miot_media_player, (ViewGroup) MiotViewCachedManager.this.b, false));
        }
    };
    private ViewHolderFactory q = new ViewHolderFactory<SceneListViewHolder>() { // from class: com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager.3
        /* renamed from: a */
        public SceneListViewHolder createViewHolder() {
            return new SceneListViewHolder(MiotViewCachedManager.this.c.inflate(R.layout.item_mico_category_scene_list_view, (ViewGroup) MiotViewCachedManager.this.b, false), MicoMode.CATEGORY);
        }
    };
    private ViewFactory r = new ViewFactory<View>() { // from class: com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager.4
        /* renamed from: a */
        public View createView() {
            return MiotViewCachedManager.this.c.inflate(R.layout.item_type_group, (ViewGroup) MiotViewCachedManager.this.b, false);
        }
    };
    private ViewHolderFactory s = new ViewHolderFactory<SceneViewHolder>() { // from class: com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager.5
        /* renamed from: a */
        public SceneViewHolder createViewHolder() {
            return new SceneViewHolder(MiotViewCachedManager.this.c.inflate(R.layout.item_mico_scene_view, (ViewGroup) MiotViewCachedManager.this.b, false));
        }
    };

    private void a() {
    }

    public static MiotViewCachedManager getInstance() {
        if (a == null) {
            synchronized (MiotViewCachedManager.class) {
                if (a == null) {
                    a = new MiotViewCachedManager();
                }
            }
        }
        return a;
    }

    private MiotViewCachedManager() {
        a(Proxies.Instance.getApp());
    }

    private void a(Context context) {
        if (this.b == null) {
            this.b = new FrameLayout(context);
        }
        if (this.c == null) {
            this.c = LayoutInflater.from(context);
        }
    }

    public void initViews() {
        a(12);
        b();
        c();
        b(12);
        c(6);
        a();
    }

    private void a(int i) {
        HolderCacheManager.prepareViewHolders(i, this.d, this.o);
    }

    public BaseViewHolder fetchMiotDeviceHolder(Context context) {
        return HolderCacheManager.fetchViewHolder(this.d, this.o);
    }

    private void b() {
        HolderCacheManager.prepareViewHolders(1, this.m, this.p);
    }

    public BaseViewHolder fetchMediaViewHolder(Context context) {
        return HolderCacheManager.fetchViewHolder(this.m, this.p);
    }

    private void c() {
        HolderCacheManager.prepareViewHolders(1, this.n, this.q);
    }

    public BaseViewHolder fetchSceneListViewHolder(Context context) {
        return HolderCacheManager.fetchViewHolder(this.n, this.q);
    }

    private void b(int i) {
        HolderCacheManager.prepareViews(i, this.e, this.r);
    }

    public View fetchTypeGroupView(Context context) {
        return HolderCacheManager.fetchView(this.e, this.r);
    }

    private void c(int i) {
        HolderCacheManager.prepareViewHolders(i, this.f, this.s);
    }

    public BaseViewHolder fetchSceneViewHolder(MicoMode micoMode, Context context) {
        SceneViewHolder sceneViewHolder = (SceneViewHolder) HolderCacheManager.fetchViewHolder(this.f, this.s);
        sceneViewHolder.setMicoMode(micoMode);
        return sceneViewHolder;
    }

    public void clearMiotViewHolder() {
        this.d.clear();
        this.e.clear();
        this.g.clear();
        this.h.clear();
        this.i.clear();
        this.j.clear();
        this.k.clear();
        this.l.clear();
        this.f.clear();
        this.n.clear();
        this.m.clear();
    }
}
