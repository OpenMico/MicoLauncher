package com.xiaomi.micolauncher.module.skill.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.module.homepage.event.LoadSkillDataSuccessEvent;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import com.xiaomi.micolauncher.module.skill.ui.view.SkillPatchWallBlockView;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes3.dex */
public class SkillPatchWallActivity extends BaseActivity {
    private MyHorizontalScrollView a;
    private SkillPatchWallBlockView b;
    private SkillPatchWallBlockView c;
    private SkillPatchWallBlockView d;
    private MusicPatchWallTitle e;
    private MusicPatchWallTitle f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_skill_patch_wall);
        a();
        b();
        c();
        d();
        if (DebugHelper.isDebugVersion()) {
            e();
        }
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        MyHorizontalScrollView myHorizontalScrollView;
        super.onResume();
        int currentPosition = SkillDataManager.getManager().getCurrentPosition();
        SkillPatchWallBlockView skillPatchWallBlockView = this.c;
        if (skillPatchWallBlockView != null && (myHorizontalScrollView = this.a) != null && currentPosition > 0) {
            myHorizontalScrollView.scrollTo(skillPatchWallBlockView.getScrollToPositionX(), 0);
            SkillDataManager.getManager().setCurrentPosition(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SkillDataManager.getManager().setCurrentPosition(0);
    }

    private void a() {
        this.a = (MyHorizontalScrollView) findViewById(R.id.scroll_view);
        this.b = (SkillPatchWallBlockView) findViewById(R.id.app_block_view);
        this.c = (SkillPatchWallBlockView) findViewById(R.id.skill_block_view);
        this.d = (SkillPatchWallBlockView) findViewById(R.id.type_block_view);
        this.e = (MusicPatchWallTitle) findViewById(R.id.skill_title);
        this.f = (MusicPatchWallTitle) findViewById(R.id.type_title);
        this.e.setupView(R.string.skill_title);
        this.f.setupView(R.string.skill_type_title);
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SkillApp(R.drawable.icon_skill_call, SkillPath.CONTACTS, true, "call"));
        arrayList.add(new SkillApp(R.drawable.icon_skill_app_setup, SkillPath.SETUP, false, SkillStatHelper.SKILL_STAT_SETUP));
        arrayList.add(new SkillApp(R.drawable.icon_skill_app_ibili, SkillPath.IBILI, true, "bilibili"));
        arrayList.add(new SkillApp(R.drawable.icon_skill_app_album, SkillPath.PHOTO, false, "album"));
        arrayList.add(new SkillApp(R.drawable.icon_skill_kids, "mico://launcher/kids", true, "kids"));
        arrayList.add(new SkillApp(R.drawable.icon_skill_white_noise, SkillPath.WHITE_NOISE, true, SkillStatHelper.SKILL_STAT_WHITE_NOISE));
        arrayList.add(new SkillApp(R.drawable.icon_skill_app_gkid, SkillPath.GKID, true, SkillStatHelper.SKILL_STAT_GKID));
        this.b.setDataList(arrayList);
    }

    private void c() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_data_empty_1));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_data_empty_2));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_data_empty_3));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_data_empty_4));
        this.c.setDataList(arrayList);
    }

    @Subscribe
    public void onLoadSkillDataSuccess(LoadSkillDataSuccessEvent loadSkillDataSuccessEvent) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(loadSkillDataSuccessEvent.skillBeans);
        this.c.removeAllViews();
        this.c.setDataList(arrayList);
    }

    private void d() {
        SkillDataManager.getManager().loadSkillData();
    }

    private void e() {
        SkillDataManager.getManager().loadSkillTypeList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillPatchWallActivity$cwc_9iRu0pWc-MyKA4ra7J9FVoc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillPatchWallActivity.this.a((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillPatchWallActivity$pruh8hCxRySmkL9jD-Pu8qFrJrg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillPatchWallActivity.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        this.f.setVisibility(0);
        this.d.setDataList(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.f.setVisibility(8);
        L.skillpage.e("load type data error", th);
    }
}
