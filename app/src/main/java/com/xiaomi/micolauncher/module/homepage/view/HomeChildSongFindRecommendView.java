package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeChildSongFindRecommendView extends ConstraintLayout {
    ImageView[] a;
    TextView b;
    ImageView c;
    private Context d;

    public HomeChildSongFindRecommendView(Context context) {
        this(context, null);
    }

    public HomeChildSongFindRecommendView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HomeChildSongFindRecommendView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new ImageView[6];
        this.d = context;
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.layout_home_child_song_find_recommend, this);
        this.a[0] = (ImageView) findViewById(R.id.like_img_1);
        this.a[1] = (ImageView) findViewById(R.id.like_img_2);
        this.a[2] = (ImageView) findViewById(R.id.like_img_3);
        this.a[3] = (ImageView) findViewById(R.id.like_img_4);
        this.a[4] = (ImageView) findViewById(R.id.like_img_5);
        this.a[5] = (ImageView) findViewById(R.id.like_img_6);
        this.b = (TextView) findViewById(R.id.tvDate);
        this.c = (ImageView) findViewById(R.id.iv_play_state);
        b();
    }

    private void b() {
        this.b.setText(new SimpleDateFormat("MM.dd").format(Long.valueOf(new Date().getTime())));
    }

    public void setPatchBlockData(final PatchWall.Block block) {
        if (!(block == null || ContainerUtil.isEmpty(block.items))) {
            b();
            List<PatchWall.Item> list = block.items;
            for (final int i = 0; i < this.a.length; i++) {
                if (i < list.size()) {
                    final PatchWall.Item item = list.get(i);
                    UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$HomeChildSongFindRecommendView$8o07FCX2Hc77Mkct9UZ9yPmqPa0
                        @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
                        public final void onLayoutComplete(boolean z) {
                            HomeChildSongFindRecommendView.this.a(i, item, z);
                        }
                    }, this.a);
                    RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$HomeChildSongFindRecommendView$Af8raUVRhVAsw4p8fnQRJ3tQ7IM
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            HomeChildSongFindRecommendView.this.a(block, i, i, obj);
                        }
                    });
                    this.a[i].setOnTouchListener(MicoAnimationTouchListener.getInstance());
                } else {
                    return;
                }
            }
            RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$HomeChildSongFindRecommendView$_H8zUDA8h5W9rI58tPokSSOM7hM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    HomeChildSongFindRecommendView.this.a(obj);
                }
            });
            this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, PatchWall.Item item, boolean z) {
        GlideUtils.bindImageViewWithRoundCorners(getContext(), item.getItemImageUrl(), this.a[i], UiUtils.getCornerRadius(), i == 0 ? R.drawable.home_child_song_big_img_holder_with_bg_color_ffb03d : R.drawable.home_child_song_img_holder_with_bg_color_ffb03d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Block block, int i, int i2, Object obj) throws Exception {
        if (ContainerUtil.hasData(block.songList)) {
            PlayerApi.playMusicList(this.d, block.songList, i);
        } else {
            PlayerApi.playPatchwallBlock(getContext(), "radio", Long.parseLong(ChildSongDataManager.ID_CHILD_SONG_RECOMMEND), LoopType.SHUFFLE.ordinal(), i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        PlayerApi.playPatchwallBlock(getContext(), "radio", Long.parseLong(ChildSongDataManager.ID_CHILD_SONG_RECOMMEND), LoopType.SHUFFLE.ordinal(), 0);
    }
}
