package com.xiaomi.micolauncher.module.music;

import android.content.Context;
import android.content.Intent;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicGroupListActivity extends BaseMusicListActivity {
    public static final String GROUP_TITLE = "key_group_title";
    public static final String SPECIAL_SYMBOL = "&";
    private String a;

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected void autoPaging() {
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected String getTitleMessage() {
        if (getIntent() != null) {
            this.a = getIntent().getStringExtra("EXTRA_GROUP_NAME");
        }
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected void loadData() {
        ApiManager.minaService.getMusicGroupList(this.a).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicGroupListActivity$dz4xLhouXwhxHWEL4Ig-F-uLAcY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicGroupListActivity.this.a((List) obj);
            }
        }, $$Lambda$MusicGroupListActivity$9DBjrDaRQr4qv1dm7DKLQE2zNFw.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        if (list != null && list.size() > 0) {
            this.dataList.clear();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.dataList.add(new SquareItem((PatchWall.Group) it.next()));
            }
            this.adapter.setDataList(this.dataList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.music_list_failed_toast);
        XLog.e(th);
    }

    public static void openMusicGroupListActivity(Context context, String str) {
        Intent intent = new Intent(context, MusicGroupListActivity.class);
        intent.putExtra("EXTRA_GROUP_NAME", str);
        context.startActivity(intent);
    }
}
