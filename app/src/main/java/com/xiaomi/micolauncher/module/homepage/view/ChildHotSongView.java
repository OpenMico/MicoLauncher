package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildHotSongView extends ConstraintLayout {
    ChildHotSongCardView[] a;
    TextView b;

    public ChildHotSongView(Context context) {
        this(context, null);
    }

    public ChildHotSongView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildHotSongView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new ChildHotSongCardView[6];
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.child_hot_song_view, this);
        this.a[0] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong0);
        this.a[1] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong1);
        this.a[2] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong2);
        this.a[3] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong3);
        this.a[4] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong4);
        this.a[5] = (ChildHotSongCardView) findViewById(R.id.chineseHotSong5);
        this.b = (TextView) findViewById(R.id.tvTitle);
    }

    public void setPatchBlockData(PatchWall.Block block) {
        if (!(block == null || ContainerUtil.isEmpty(block.items))) {
            List<PatchWall.Item> list = block.items;
            int size = list.size();
            for (int i = 0; i < this.a.length && i < size; i++) {
                final PatchWall.Item item = list.get(i);
                ChildHotSongCardView childHotSongCardView = this.a[i];
                childHotSongCardView.updateTitleAndImage(item.title, item.getItemImageUrl(), R.drawable.home_child_song_img_holder_with_bg_color_4342ff);
                RxViewHelp.debounceClicksWithOneSeconds(childHotSongCardView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildHotSongView$pDCd4DTMTAzUrRIutSoTX7V2ByE
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ChildHotSongView.this.a(item, obj);
                    }
                });
                childHotSongCardView.setOnTouchListener(MicoAnimationTouchListener.getInstance());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Item item, Object obj) throws Exception {
        ChildSongDataManager.playPatchWallItem(getContext(), item);
    }

    public void setTitle(@StringRes int i) {
        this.b.setText(i);
    }
}
