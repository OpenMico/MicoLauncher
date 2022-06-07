package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class AudioBookBigCardView extends FrameLayout {
    ImageView a;
    TextView b;
    TextView c;
    private Context d;
    private Station.Item e;

    public AudioBookBigCardView(Context context) {
        this(context, null);
    }

    public AudioBookBigCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AudioBookBigCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View.inflate(context, R.layout.audiobook_big_item_optimise, this);
        this.a = (ImageView) findViewById(R.id.audiobook_iv);
        this.b = (TextView) findViewById(R.id.audiobook_name);
        this.c = (TextView) findViewById(R.id.audiobook_title);
        this.d = context;
    }

    public void initInMain() {
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AudioBookBigCardView$b4fu4U35PqQVo4h8TyOGV9Ydk1c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookBigCardView.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && this.e != null) {
            PlayerApi.playStation(getContext(), this.e.getAlbumId(), this.e.getOrigin(), this.e.getSaleType());
        }
    }

    private void setAudiobookImg(final String str) {
        UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AudioBookBigCardView$ZDnZlKdPO648MO-aX1XhXnUsxfY
            @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
            public final void onLayoutComplete(boolean z) {
                AudioBookBigCardView.this.a(str, z);
            }
        }, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, boolean z) {
        Context context = this.d;
        GlideUtils.bindImageViewCircle(context, str, this.a, UiUtils.getSize(context, R.dimen.audiobook_img_width), UiUtils.getSize(this.d, R.dimen.audiobook_img_height));
    }

    private void setAudiobookName(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.setText(str);
        }
    }

    private void setAudiobookContent(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.setText(str);
        }
    }

    public void setData(Station.Item item) {
        this.e = item;
        setAudiobookName(item.getAlbumName());
        setAudiobookContent(item.getTitle());
        setAudiobookImg(item.getCover());
    }
}
