package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicCategoryItemView extends FrameLayout {
    TextView[] a;
    ImageView b;
    TextView c;
    private List<PatchWall.Group> d;
    private PatchWall.Item e;
    private String f;

    protected int layoutId() {
        return R.layout.music_category_item;
    }

    public MusicCategoryItemView(Context context) {
        this(context, null);
    }

    public MusicCategoryItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicCategoryItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new TextView[2];
        a(context);
    }

    private void a(Context context) {
        long beginTiming = DebugHelper.beginTiming();
        View.inflate(context, R.layout.music_category_item, this);
        this.a[0] = (TextView) findViewById(R.id.tip_first);
        this.a[1] = (TextView) findViewById(R.id.tip_second);
        this.b = (ImageView) findViewById(R.id.cover_img);
        this.c = (TextView) findViewById(R.id.title_tv);
        DebugHelper.endTiming(beginTiming, this + " custom view inflate", new Object[0]);
    }

    public void initInMain() {
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MusicCategoryItemView$yTVfBYdpmpoAPUEnV4GMYOgRdTA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicCategoryItemView.this.a(obj);
            }
        });
        final int i = 0;
        while (true) {
            TextView[] textViewArr = this.a;
            if (i < textViewArr.length) {
                AnimLifecycleManager.repeatOnAttach(textViewArr[i], MicoAnimConfigurator4EntertainmentAndApps.get());
                RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MusicCategoryItemView$OUZy_Viu8McWDzuKainauZCfm6o
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MusicCategoryItemView.this.a(i, obj);
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        if (LocalPlayerManager.getInstance().isBlack(this.e.title)) {
            SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_BLACKLIST);
            return;
        }
        PatchWall.Item item = this.e;
        if (item != null && !TextUtils.isEmpty(item.target)) {
            SchemaManager.handleSchema(getContext(), this.e.target);
            MusicStatHelper.recordMusicClick(this.f, this.e.title);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        PatchWall.Group group = this.d.get(i);
        if (group != null && !TextUtils.isEmpty(group.target)) {
            SchemaManager.handleSchema(getContext(), group.target);
            MusicStatHelper.recordMusicClick(getTitleName(), group.categoryName);
        }
    }

    protected void setCoverImg() {
        GlideUtils.bindImageView(getContext(), getItem().getItemImageUrl(), this.b, (int) R.drawable.rectangle_loading);
    }

    public void setData(String str, PatchWall.Item item) {
        this.f = str;
        L.homepage.i("jiangliang title name : %s", str);
        this.e = item;
        setCoverImg();
        b();
        this.d = item.groups;
        if (ContainerUtil.hasData(this.d)) {
            a();
        }
    }

    private void a() {
        for (int i = 0; i < this.a.length; i++) {
            L.homepage.i("jiangliang category name : %s", this.d.get(i).categoryName);
            this.a[i].setText(getContext().getString(R.string.category_mark, this.d.get(i).categoryName));
            this.a[i].setSingleLine();
            this.a[i].setVisibility(0);
        }
    }

    private void b() {
        this.c.setText(this.e.title);
    }

    public PatchWall.Item getItem() {
        return this.e;
    }

    public String getTitleName() {
        return this.f;
    }
}
