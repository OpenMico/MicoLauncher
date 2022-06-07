package com.xiaomi.micolauncher.module.skill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.skill.ui.fragment.SkillDetailInfoFragment;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SkillDetailListActivity extends BaseActivity {
    private ViewPager a;
    private String b;
    private List<Skill.SkillInfo> c = new ArrayList();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_skill_detail_list);
        if (getIntent() != null) {
            this.b = getIntent().getStringExtra("EXTRA_SKILL_ID");
        }
        a();
        d();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
        if (PreferenceUtils.getSettingBoolean(this, "skill_detail_first_visit", true)) {
            NotificationHelper.notify(this, 3);
            PreferenceUtils.setSettingBoolean(this, "skill_detail_first_visit", false);
        }
    }

    private void a() {
        this.a = (ViewPager) findViewById(R.id.view_pager);
    }

    private void b() {
        this.a.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) { // from class: com.xiaomi.micolauncher.module.skill.ui.SkillDetailListActivity.1
            @Override // androidx.fragment.app.FragmentStatePagerAdapter
            public Fragment getItem(int i) {
                return SkillDetailInfoFragment.newInstance(((Skill.SkillInfo) SkillDetailListActivity.this.c.get(i % SkillDetailListActivity.this.c.size())).id);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return SkillDetailListActivity.this.c.size();
            }
        });
        this.a.setCurrentItem(c());
        this.a.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.SkillDetailListActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                SkillDataManager.getManager().setCurrentPosition(i);
            }
        });
    }

    private int c() {
        if (!ContainerUtil.isEmpty(this.b)) {
            for (int i = 0; i < this.c.size(); i++) {
                if (this.b.equals(this.c.get(i).id)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void d() {
        addToDisposeBag(SkillDataManager.getManager().loadSkillInfoList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).map($$Lambda$SkillDetailListActivity$AztYjcR3HGDg1dG9iXW8WdArQOo.INSTANCE).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillDetailListActivity$yN_pW8l54Eg_VMCrTisynS4bGYE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDetailListActivity.this.a((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillDetailListActivity$GnSbBOaXH0cPoLPGRkX_aguKyRA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDetailListActivity.this.a((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(List list) throws Exception {
        ArrayList arrayList = new ArrayList(ContainerUtil.getSize(list));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Skill.SkillInfo skillInfo = (Skill.SkillInfo) it.next();
            if (skillInfo == null || !skillInfo.isValid()) {
                L.skill.w("Invalid skill %s returned by mico server", skillInfo);
            } else {
                arrayList.add(skillInfo);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        if (!ContainerUtil.isEmpty(list)) {
            this.c.clear();
            this.c.addAll(list);
            b();
            return;
        }
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        e();
        L.skillpage.e("load detail list error", th);
    }

    private void e() {
        ToastUtil.showToast((int) R.string.error_get_data_failed);
        finish();
    }

    public static void openSkillDetailListActivity(Context context, String str) {
        Intent intent = new Intent(context, SkillDetailListActivity.class);
        intent.putExtra("EXTRA_SKILL_ID", str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a.setAdapter(null);
        this.a.clearOnPageChangeListeners();
        this.a = null;
    }
}
