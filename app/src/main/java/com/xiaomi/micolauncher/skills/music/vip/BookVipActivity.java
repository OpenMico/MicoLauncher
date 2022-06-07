package com.xiaomi.micolauncher.skills.music.vip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BookVipActivity extends BasePurchaseActivity {
    private ImageView a;
    private TextView b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private ImageView i;
    private Remote.Response.PlayingData j;
    private String k;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.music.vip.BasePurchaseActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_book_vip);
        setScheduleDuration(TimeUnit.MINUTES.toMillis(5L));
        this.j = (Remote.Response.PlayingData) getIntent().getSerializableExtra(PlayerActivityV2.EXTRA_PLAYER_STATUS);
        this.c = (ImageView) findViewById(R.id.song_cover);
        this.d = (TextView) findViewById(R.id.song_name);
        this.f = (TextView) findViewById(R.id.price);
        this.e = (TextView) findViewById(R.id.song_tag);
        this.g = (TextView) findViewById(R.id.song_price);
        this.i = (ImageView) findViewById(R.id.qr_iv);
        GlideUtils.bindImageViewWithRoundCorners(this.c, this.j.cover, UiUtils.getSize(this, R.dimen.child_vip_qr_code_radius), R.drawable.player_cover, UiUtils.getSize(this, R.dimen.audio_book_vip_page_audiobook_cover_size), UiUtils.getSize(this, R.dimen.audio_book_vip_page_audiobook_cover_size));
        if (getResources().getBoolean(R.bool.audio_book_page_vip_has_back_button_and_title)) {
            this.a = (ImageView) findViewById(R.id.audio_book_vip_page_back_button);
            this.a.setVisibility(0);
            this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookVipActivity$J4KN_IaxyxOU2fz5OwF2LuaG-HI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BookVipActivity.this.a(view);
                }
            });
            this.b = (TextView) findViewById(R.id.audio_book_vip_page_title);
            this.b.setVisibility(0);
            this.b.setText(this.j.albumName);
            this.h = (TextView) findViewById(R.id.qr_code_title);
            this.h.setVisibility(0);
        }
        this.d.setText(this.j.albumName);
        this.f.setText(Util.getPriceStr((float) this.j.salesPrice));
        this.g.setText(Util.getPriceStr((float) this.j.salesPrice));
        this.e.setText(getString(R.string.china_money));
        this.orderType = OrderType.ALBUM;
        this.k = TextUtils.isEmpty(this.j.cpAlbumId) ? this.j.albumGlobalID : this.j.cpAlbumId;
        String str = TextUtils.isEmpty(this.j.albumName) ? "" : this.j.albumName;
        if (ApiManager.paymentService != null) {
            addToDisposeBag(ApiManager.paymentService.getAlbumOrder(str, this.j.cpOrigin, this.j.cover, this.k, null, "aibox", "", "tv", 2).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookVipActivity$0G5nQzdU7RkoGp8zYII4Y2OGBt0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BookVipActivity.this.a((MusicPrice.Order) obj);
                }
            }));
            return;
        }
        ToastUtil.showToast((int) R.string.voip_is_not_init);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MusicPrice.Order order) throws Exception {
        this.order = order;
        this.f.setText(Util.getPriceStr(order.getTotalFee()));
        this.g.setText(Util.getPriceStr(order.getTotalFee()));
        addToDisposeBag(getQrCodeImage(order).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookVipActivity$B98wlnSI-kcWEGLQxgHTeuHRBLQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BookVipActivity.this.a((String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        L.pay.i("all book qr code url %s", str);
        this.i.setImageBitmap(QRCodeUtil.createQRCodeBitmap(str, getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size), getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size)));
    }

    public static void startBookPurchaseActivity(Context context, Remote.Response.TrackData trackData) {
        Intent intent = new Intent(context, BookVipActivity.class);
        intent.putExtra(PlayerActivityV2.EXTRA_PLAYER_STATUS, Remote.Response.PlayingData.from(trackData));
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
