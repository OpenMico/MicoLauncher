package com.xiaomi.micolauncher.module.homepage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.main.util.MainStatHelper;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicPatchWallLatestMvItem extends BaseViewGroup {
    private static int d;
    TextView a;
    ImageView b;
    PatchWall.Item c;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list) throws Exception {
    }

    public MusicPatchWallLatestMvItem(@NonNull Context context) {
        this(context, null);
    }

    public MusicPatchWallLatestMvItem(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallLatestMvItem(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.latest_mv_item, this);
        this.a = (TextView) findViewById(R.id.mv_title);
        this.b = (ImageView) findViewById(R.id.mv_img);
        d = getContext().getResources().getDimensionPixelOffset(R.dimen.common_radius);
    }

    @SuppressLint({"CheckResult"})
    public void initInMain() {
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MusicPatchWallLatestMvItem$eDiW8_LOPtKBFDCNwMorSHLZzEc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPatchWallLatestMvItem.this.a(obj);
            }
        });
        LocalPlayerManager.getInstance().loadBlackList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$MusicPatchWallLatestMvItem$cbK0SzGY0k_vHMqiOf4pYM0aF9Q.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        if (LocalPlayerManager.getInstance().isBlack(this.c.title)) {
            SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_BLACKLIST);
            return;
        }
        MusicHelper.playMV(getContext(), this.c);
        MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_MUSIC_RECOMMEND);
    }

    public void setDataBlockItem(PatchWall.Item item) {
        this.c = item;
        this.a.setText(item.title);
        GlideUtils.bindImageViewBlurCenterCrop(this.b, item.images.poster.url, R.drawable.loading_hold, UiUtils.getSize(getContext(), R.dimen.mv_title_layout_height), null);
    }
}
