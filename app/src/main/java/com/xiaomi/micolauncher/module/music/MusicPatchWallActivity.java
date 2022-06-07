package com.xiaomi.micolauncher.module.music;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.base.BaseMusicActivity;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallListView;
import com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView;
import com.xiaomi.micolauncher.skills.music.view.QqMusicLoginStatusPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicPatchWallActivity extends BaseMusicActivity {
    private MyHorizontalScrollView a;
    private MusicPatchWallListView b;
    private View c;
    private List<Object> d;
    private boolean e;
    private int f;
    private boolean g = true;
    private boolean h;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_music_patch_wall);
        a();
        b();
        c();
        MusicStatHelper.recordPatchWallShow();
    }

    private void a() {
        this.a = (MyHorizontalScrollView) findViewById(R.id.scroll_view);
        this.b = (MusicPatchWallListView) findViewById(R.id.list_view);
        this.c = findViewById(R.id.qq_expired_view);
        this.a.setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$cUL_G2bUlY70tx3ZNU6y6LKt1rc
            @Override // com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView.OnScrollListener
            public final void onScroll(MyHorizontalScrollView myHorizontalScrollView) {
                MusicPatchWallActivity.this.a(myHorizontalScrollView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MyHorizontalScrollView myHorizontalScrollView) {
        d();
    }

    private void b() {
        this.d = new ArrayList();
        this.d.add(new Header());
        PatchWall.Block block = new PatchWall.Block();
        block.items = new ArrayList();
        for (int i = 0; i < 6; i++) {
            block.items.add(new PatchWall.Item());
        }
        this.d.add(block);
        this.b.setDataList(this.d);
    }

    private void c() {
        this.h = true;
        MusicDataManager.getManager().loadMusicDataWithCache(this.f).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$LjKT7nvTglMaNKHE_DRgS3W_TEQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPatchWallActivity.this.b((PatchWall) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$xIgKvQ7ddjDgRVdA-XqLbPwlOPA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPatchWallActivity.this.b((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(PatchWall patchWall) throws Exception {
        this.e = true;
        this.h = false;
        this.d.clear();
        this.d.add(new Header());
        this.d.addAll(patchWall.blocks);
        this.b.setDataList(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        this.h = false;
        new QqMusicLoginStatusPresenter(this.c, this.b, new QqMusicLoginStatusPresenter.OnCancelBtnClickListener() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$XEsu2hCgAhOnyHB8-Xv_bObNUYo
            @Override // com.xiaomi.micolauncher.skills.music.view.QqMusicLoginStatusPresenter.OnCancelBtnClickListener
            public final void onCancelBtnClick() {
                MusicPatchWallActivity.this.e();
            }
        }).showLoginStatus(th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        finish();
    }

    private void d() {
        if (this.e && this.g && !this.h) {
            this.h = true;
            MusicDataManager manager = MusicDataManager.getManager();
            int i = this.f + 1;
            this.f = i;
            manager.loadMusicDataWithoutCacheAndAuthCheck(i).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$-XTq9IDYGLyu4kSx7wXwrgY1RwE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPatchWallActivity.this.a((PatchWall) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.-$$Lambda$MusicPatchWallActivity$QWt3eWD3HEJ46Iv2ADHCJ0vWplE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPatchWallActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall patchWall) throws Exception {
        if (patchWall.blocks == null || patchWall.blocks.size() <= 0) {
            this.g = false;
            return;
        }
        this.h = false;
        this.d.addAll(patchWall.blocks);
        this.b.addDataList(new ArrayList(patchWall.blocks));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.h = false;
        XLog.e(th);
    }
}
