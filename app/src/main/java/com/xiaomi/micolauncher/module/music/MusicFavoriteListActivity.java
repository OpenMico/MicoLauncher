package com.xiaomi.micolauncher.module.music;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicFavoriteListActivity extends BaseMusicListActivity {
    private List<String> a;

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected void autoPaging() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity, com.xiaomi.micolauncher.module.music.base.BaseMusicActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected String getTitleMessage() {
        return getString(R.string.music_favorite_list_title);
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity
    protected void loadData() {
        LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicFavoriteListActivity$ZMmGLP6GTYnvySNWRg473Cv6sYc
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = MusicFavoriteListActivity.this.b((List) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicFavoriteListActivity$Mb6Ir7dONuAG_2tt-WGenIQatUQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicFavoriteListActivity.this.a((List) obj);
            }
        }, $$Lambda$MusicFavoriteListActivity$LHGtkwo9JcnWTJsy9fNHH84IImA.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource b(List list) throws Exception {
        this.a = list;
        return ApiManager.minaService.getFavoriteSongBookList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        if (list == null || list.size() <= 0) {
            ToastUtil.showToast((int) R.string.music_favorite_list_empty_toast);
            return;
        }
        this.dataList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PatchWall.FavoriteSongBook favoriteSongBook = (PatchWall.FavoriteSongBook) it.next();
            if (!favoriteSongBook.isDefault) {
                this.dataList.add(new SquareItem(favoriteSongBook));
            }
        }
        this.adapter.setBlackList(this.a);
        this.adapter.setDataList(this.dataList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.music_favorite_list_failed_toast);
        XLog.e(th);
    }
}
