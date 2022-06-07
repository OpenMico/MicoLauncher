package com.xiaomi.micolauncher.module.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class MusicCategoryListActivity extends BaseMusicListActivity {
    public static final int PAGE_SIZE = 10;
    private String a;
    private String b;
    private int c;
    private boolean d = true;
    private boolean e;
    private boolean f;

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected String getTitleMessage() {
        if (getIntent() != null) {
            this.a = getIntent().getStringExtra("EXTRA_CATEGORY_ID");
            this.b = getIntent().getStringExtra("EXTRA_CATEGORY_NAME");
        }
        String settingString = PreferenceUtils.getSettingString(this, MusicGroupListActivity.GROUP_TITLE, "");
        if (!ContainerUtil.isEmpty(settingString)) {
            this.b = settingString;
        }
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    @SuppressLint({"CheckResult"})
    protected void loadData() {
        L.homepage.i("MusicCategoryListActivity loadData start");
        ApiManager.minaService.getMusicCategoryList(this.a, this.b, this.c, 10).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicCategoryListActivity$3PVk45u8rgbb8FtLDbTU8a7BnBc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicCategoryListActivity.this.b((PatchWall.Category) obj);
            }
        }, $$Lambda$MusicCategoryListActivity$2n0id0FgPrSN2tUa4nz8M62Zs.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(PatchWall.Category category) throws Exception {
        if (!(category == null || category.items == null || category.items.size() <= 0)) {
            this.c++;
            this.f = true;
            this.dataList.clear();
            this.dataList.addAll(category.items);
            this.adapter.setDataList(this.dataList);
        }
        L.homepage.i("MusicCategoryListActivity loadData end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.homepage.i("MusicCategoryListActivity loadData end");
        ToastUtil.showToast((int) R.string.music_list_failed_toast);
        XLog.e(th);
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    @SuppressLint({"CheckResult"})
    protected void autoPaging() {
        if (this.f && this.d && !this.e) {
            this.e = true;
            b();
            ApiManager.minaService.getMusicCategoryList(this.a, this.b, this.c, 10).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicCategoryListActivity$cX7EL8yuTiUS1ha3-akOJWs_U7U
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicCategoryListActivity.this.a((PatchWall.Category) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicCategoryListActivity$plihoW4VC6wJMbnhl2w3m01iLbc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicCategoryListActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Category category) throws Exception {
        this.c++;
        this.e = false;
        a();
        int size = this.dataList.size();
        if (category == null || category.items == null || category.items.size() <= 0) {
            this.d = false;
            return;
        }
        this.dataList.addAll(category.items);
        this.adapter.notifyItemRangeInserted(size, category.items.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.e = false;
        this.d = false;
        a();
        XLog.e(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        L.homepage.i("MusicCategoryListActivity onDestroy");
    }

    private void a() {
        int size = this.dataList.size() - 1;
        this.dataList.remove(size);
        this.adapter.notifyItemRemoved(size);
    }

    private void b() {
        this.dataList.add(new Footer());
        this.adapter.notifyItemInserted(this.dataList.size() - 1);
        this.recyclerView.smoothScrollBy(UiUtils.getSize(this, R.dimen.progress_width), 0);
    }

    public static void openMusicCategoryListActivity(Context context, String str, String str2) {
        Intent intent = new Intent(context, MusicCategoryListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", str);
        intent.putExtra("EXTRA_CATEGORY_NAME", str2);
        context.startActivity(intent);
    }
}
