package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.module.homepage.activity.ChildSongActivity;
import com.xiaomi.micolauncher.module.homepage.activity.ChildStoryActivity;
import com.xiaomi.micolauncher.module.homepage.activity.ChildVideoActivity;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class ChildHomeViewHolder extends BaseHomeViewHolder {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected boolean empty() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected void fillData() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void sendRefreshMsg() {
    }

    public ChildHomeViewHolder(View view) {
        super(view);
        this.a = (ImageView) view.findViewById(R.id.story_iv);
        this.b = (ImageView) view.findViewById(R.id.child_music_iv);
        this.c = (ImageView) view.findViewById(R.id.carton_iv);
        this.d = (ImageView) view.findViewById(R.id.english_draw_iv);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder, com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initViewInMain() {
        super.initViewInMain();
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$ChildHomeViewHolder$JtQVo4wATlSguWpM6tVIw6-bhyA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildHomeViewHolder.this.d(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$ChildHomeViewHolder$Q5bFAv5buQzreOFA518F19YyfQQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildHomeViewHolder.this.c(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$ChildHomeViewHolder$9qYD8p-RZR5adFEcMypzkjBCKgA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildHomeViewHolder.this.b(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$ChildHomeViewHolder$aHBW_8Cp4stSNTs1G-mLqlItW94
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildHomeViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.context, ChildStoryActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.context, ChildSongActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.context, ChildVideoActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        SchemaManager.handleSchema(this.context, SkillPath.GKID);
    }
}
