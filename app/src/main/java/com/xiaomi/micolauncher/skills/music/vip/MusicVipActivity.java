package com.xiaomi.micolauncher.skills.music.vip;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.module.child.childvideo.SpaceItemDecoration;
import com.xiaomi.micolauncher.skills.music.vip.PriceAdapter;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicVipActivity extends BasePurchaseActivity implements PriceAdapter.OnPriceItemClickListener {
    private PriceAdapter a;
    private boolean b;
    private TextView c;
    private TextView d;
    private ImageView e;
    private ImageView f;
    private TextView g;
    private List<MusicPrice> h;
    private MusicPrice i;
    private ImageView j;
    private int k;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.music.vip.BasePurchaseActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_music_vip);
        addToDisposeBag(ApiManager.paymentService.getMusicPrice("aibox", true).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$MusicVipActivity$K0FU-5aWDoZB1zd_T6x1Mrf_4ns
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicVipActivity.this.a((List) obj);
            }
        }));
        this.k = Hardware.isBigScreen() ? 3 : 2;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (getResources().getBoolean(R.bool.music_vip_has_qr_code_title)) {
            this.c = (TextView) findViewById(R.id.qr_code_title);
            this.c.setVisibility(0);
        }
        this.d = (TextView) findViewById(R.id.qr_price_desc);
        this.e = (ImageView) findViewById(R.id.qr_iv);
        this.g = (TextView) findViewById(R.id.price);
        this.j = (ImageView) findViewById(R.id.vippay_mi);
        recyclerView.setLayoutManager(new GridLayoutManager(this, this.k));
        if (recyclerView.getItemAnimator() != null) {
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        this.a = new PriceAdapter(this);
        this.a.a(this);
        recyclerView.setAdapter(this.a);
        recyclerView.addItemDecoration(new SpaceItemDecoration(this.k, getResources().getDimensionPixelOffset(R.dimen.child_vip_product_item_space)));
        this.orderType = OrderType.MUSIC;
        this.f = (ImageView) findViewById(R.id.music_vip_back_iv);
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$MusicVipActivity$LZ0dKAodIYN0bZBVa0gxTron4VU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MusicVipActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.h = list;
        this.a.a(list);
        a((MusicPrice) list.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    void a(final MusicPrice musicPrice) {
        this.b = musicPrice.isRenew();
        this.j.setVisibility(this.b ? 8 : 0);
        this.d.setText(musicPrice.getShortName());
        this.e.setVisibility(4);
        this.g.setText(Util.getPriceStr(musicPrice.getSalePrice()));
        addToDisposeBag(ApiManager.paymentService.getOrder(musicPrice.getProductName(), musicPrice.getProductId(), 1, musicPrice.getOrigin(), "", this.b ? musicPrice.getContractResourceId() : "", "aibox", "tv").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$MusicVipActivity$1eBp8Q5JZQ6XohCES1RnU9ffbKk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicVipActivity.this.a(musicPrice, (MusicPrice.Order) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MusicPrice musicPrice, MusicPrice.Order order) throws Exception {
        this.order = order;
        order.setProductName(musicPrice.getProductName());
        addToDisposeBag(getQrCodeImage(order).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$MusicVipActivity$_p3Inf1ApyXCYgB3kF2fYVjlb2g
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicVipActivity.this.a((String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        L.pay.i("music vip qr code url %s", str);
        this.e.setVisibility(0);
        this.e.setImageBitmap(QRCodeUtil.createQRCodeBitmap(str, getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size), getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size)));
    }

    @Override // com.xiaomi.micolauncher.skills.music.vip.PriceAdapter.OnPriceItemClickListener
    public void onPriceClick(int i) {
        if (ContainerUtil.hasData(this.h)) {
            this.i = this.h.get(i);
            a(this.i);
        }
    }
}
