package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public abstract class BaseMusicView extends FrameLayout {
    ImageView a;
    TextView b;
    private PatchWall.Item c;
    private String d;

    protected abstract int layoutId();

    protected abstract void setCoverImg();

    public BaseMusicView(Context context) {
        this(context, null);
    }

    public BaseMusicView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseMusicView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        long beginTiming = DebugHelper.beginTiming();
        LayoutInflater.from(context).inflate(layoutId(), (ViewGroup) this, true);
        this.a = (ImageView) findViewById(R.id.cover_img);
        this.b = (TextView) findViewById(R.id.title_tv);
        DebugHelper.endTiming(beginTiming, this + " custom view inflate", new Object[0]);
    }

    public void initInMain() {
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$BaseMusicView$1RUdXvDEHPsTxDK33VUSV1M_Qyc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseMusicView.this.a(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        if (LocalPlayerManager.getInstance().isBlack(this.c.title)) {
            SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_BLACKLIST);
            return;
        }
        PatchWall.Item item = this.c;
        if (item != null && !TextUtils.isEmpty(item.target)) {
            SchemaManager.handleSchema(getContext(), this.c.target);
            MusicStatHelper.recordMusicClick(this.d, this.c.title);
        }
    }

    private void a() {
        this.b.setSingleLine();
        this.b.setText(this.c.title);
    }

    public void setData(String str, PatchWall.Item item) {
        this.d = str;
        this.c = item;
        setCoverImg();
        a();
    }

    public PatchWall.Item getItem() {
        return this.c;
    }

    public String getTitleName() {
        return this.d;
    }
}
