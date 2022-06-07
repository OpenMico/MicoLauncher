package com.xiaomi.micolauncher.module.skill.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager;
import com.xiaomi.micolauncher.module.skill.bean.SkillItem;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.skill.manager.SkillPagingManager;
import com.xiaomi.micolauncher.module.skill.ui.adapter.SkillCategoryListAdapter;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SkillCategoryListActivity extends BaseActivity {
    private String a;
    private String b;
    private RecyclerView c;
    private SkillCategoryListAdapter d;
    private List<Object> e = new ArrayList();
    private boolean f = false;
    private boolean g = true;
    private RecyclerViewScrollManager.OnScrollLocationChangeListener h = new RecyclerViewScrollManager.OnScrollLocationChangeListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.SkillCategoryListActivity.1
        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public int getEarlyToBottomItemCount() {
            return 10;
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onBottomWhenScrollDragging(RecyclerView recyclerView) {
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onTopWhenScrollIdle(RecyclerView recyclerView) {
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onBottomWhenScrollIdle(RecyclerView recyclerView) {
            SkillCategoryListActivity.this.f();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_skill_category_list);
        a();
        b();
        d();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        e();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SkillPagingManager.getManager().clear();
    }

    private void a() {
        if (getIntent() != null) {
            this.a = getIntent().getStringExtra("EXTRA_SKILL_ID");
        }
    }

    private void b() {
        this.c = (RecyclerView) findViewById(R.id.recycler_view);
        BaseGridLayoutManager baseGridLayoutManager = new BaseGridLayoutManager(this, 2, 0, false);
        this.c.setLayoutManager(baseGridLayoutManager);
        this.d = new SkillCategoryListAdapter(this, this.a);
        this.c.setAdapter(this.d);
        baseGridLayoutManager.setOnScrollLocationChangeListener(this.h);
        baseGridLayoutManager.getRecyclerViewScrollManager().registerScrollListener(this.c);
    }

    private void c() {
        this.e.add(new Header(this.b));
        for (int i = 0; i < 6; i++) {
            this.e.add(new SkillItem());
        }
        this.d.setDataList(this.e);
    }

    private void d() {
        SkillDataManager.getManager().loadSkillTypeById(this.a).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryListActivity$VFwuF3ISRkIDmjCU6Dmyq5jez8M
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillCategoryListActivity.this.a((Skill.SkillType) obj);
            }
        }, $$Lambda$SkillCategoryListActivity$b3wwP6AR7XehmQVWM6wCuZ0Kng.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Skill.SkillType skillType) throws Exception {
        this.b = skillType.name;
        this.e.clear();
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.error_get_data_failed);
        L.skillpage.e("load name data error", th);
    }

    private void e() {
        SkillPagingManager.getManager().loadData(this, this.a).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryListActivity$19tST_t9NbWX22ftnXQTisPyiCE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillCategoryListActivity.this.b((List) obj);
            }
        }, $$Lambda$SkillCategoryListActivity$9JfBFcoS0eHBHDYmlbR_3lfTgA4.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(List list) throws Exception {
        this.e.clear();
        this.e.add(new Header(this.b));
        this.e.addAll(list);
        this.d.setDataList(this.e);
        int index = SkillPagingManager.getManager().getIndex() + 1;
        if (index > this.e.size()) {
            index = this.e.size();
        }
        this.c.scrollToPosition(index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.error_get_data_failed);
        L.skillpage.e("load category list error", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (this.g && !this.f) {
            this.f = true;
            g();
            SkillPagingManager.getManager().autoPaging(this, this.a).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryListActivity$xfEcrlG6t-HRv4Tr6xoGesOGZdc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillCategoryListActivity.this.a((List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryListActivity$z95zPhZDzJotT67dlen_YUsNoEE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillCategoryListActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.f = false;
        List<Object> list2 = this.e;
        list2.remove(list2.size() - 1);
        this.e.addAll(list);
        this.d.setDataList(this.e);
        if (list.size() <= 0) {
            this.g = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.f = false;
        this.g = false;
        List<Object> list = this.e;
        list.remove(list.size() - 1);
        this.d.setDataList(this.e);
        L.skillpage.e("paging category list error", th);
    }

    private void g() {
        this.e.add(new Footer());
        this.d.setDataList(this.e);
        this.c.smoothScrollBy(DisplayUtils.dip2px((Activity) this, 80.0f), 0);
    }

    public static void openSkillCategoryListActivity(Context context, String str) {
        Intent intent = new Intent(context, SkillCategoryListActivity.class);
        intent.putExtra("EXTRA_SKILL_ID", str);
        context.startActivity(intent);
    }
}
