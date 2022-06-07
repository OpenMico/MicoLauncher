package com.xiaomi.micolauncher.skills.music.vip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.module.child.childvideo.SpaceItemDecoration;
import com.xiaomi.micolauncher.skills.music.vip.SingleEpisodePurchaseAdapter;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BookSinglePurchaseActivity extends BasePurchaseActivity {
    public static final String EXTRA_BOOK_DATA = "extra_book_data";
    public static final String EXTRA_BOOK_INDEX = "extra_book_index";
    public static final int MI_COIN_CONVERSION_RATE = 100;
    private ImageView a;
    private TextView b;
    private TextView c;
    private RecyclerView d;
    private ImageView e;
    private TextView f;
    private TextView g;
    private ArrayList<String> h;
    private SingleEpisodePurchaseAdapter i;
    private Remote.Response.TrackData j;
    private int k;
    private int l;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.music.vip.BasePurchaseActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        TextView textView;
        super.onCreate(bundle);
        setContentView(R.layout.activity_book_single_vip);
        setScheduleDuration(TimeUnit.MINUTES.toMillis(5L));
        if (getResources().getBoolean(R.bool.audio_book_page_vip_has_back_button_and_title)) {
            this.a = (ImageView) findViewById(R.id.audio_book_single_vip_page_back_button);
            this.a.setVisibility(0);
            this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookSinglePurchaseActivity$5weNcorPmsTBAf5bcjPwdUME9Is
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BookSinglePurchaseActivity.this.a(view);
                }
            });
            this.g = (TextView) findViewById(R.id.qr_code_title);
            this.g.setVisibility(0);
        }
        this.b = (TextView) findViewById(R.id.book_title);
        this.c = (TextView) findViewById(R.id.purchase_start);
        this.d = (RecyclerView) findViewById(R.id.recycler_view);
        this.e = (ImageView) findViewById(R.id.qr_iv);
        this.f = (TextView) findViewById(R.id.price);
        this.d.setLayoutManager(new GridLayoutManager(this, 2));
        if (this.d.getItemAnimator() != null) {
            ((SimpleItemAnimator) this.d.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        this.d.addItemDecoration(new SpaceItemDecoration(2, getResources().getDimensionPixelOffset(R.dimen.child_vip_product_item_space)));
        this.i = new SingleEpisodePurchaseAdapter();
        this.i.setOnPurchaseChoiceChangeListener(new SingleEpisodePurchaseAdapter.OnPurchaseChoiceChangeListener() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookSinglePurchaseActivity$kma7spFQc7PMeqahGpmh2KOwT6Q
            @Override // com.xiaomi.micolauncher.skills.music.vip.SingleEpisodePurchaseAdapter.OnPurchaseChoiceChangeListener
            public final void onPurchaseChoiceChange(SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem singleEpisodePurchaseItem) {
                BookSinglePurchaseActivity.this.a(singleEpisodePurchaseItem);
            }
        });
        this.d.setAdapter(this.i);
        this.j = (Remote.Response.TrackData) getIntent().getSerializableExtra(EXTRA_BOOK_DATA);
        this.k = getIntent().getIntExtra(EXTRA_BOOK_INDEX, 0);
        this.l = this.j.episodesNum;
        this.b.setText(this.j.album);
        this.c.setText(this.j.title);
        if (Hardware.isLx04() && (textView = this.g) != null) {
            textView.setText(String.format(getString(R.string.single_episode_purchase), Integer.valueOf(this.l)));
        }
        this.orderType = OrderType.SINGLE_ALBUM;
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem singleEpisodePurchaseItem) {
        this.e.setVisibility(4);
        this.indexList.clear();
        List<String> subList = this.h.subList(0, singleEpisodePurchaseItem.b);
        String json = Gsons.getGson().toJson(subList);
        for (int i = 0; i < ContainerUtil.getSize(subList); i++) {
            this.indexList.add(Integer.valueOf(this.k + i));
        }
        addToDisposeBag(ApiManager.paymentService.getAlbumOrder(this.j.album, this.j.cpOrigin, this.j.cover, this.j.cpAlbumId, json, "aibox", "", "tv", 1).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookSinglePurchaseActivity$5tLCBM7oHwKnOgNfmNdoIg9K67A
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BookSinglePurchaseActivity.this.a((MusicPrice.Order) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MusicPrice.Order order) throws Exception {
        this.order = order;
        this.f.setText(Util.getPriceStr(order.getTotalFee()));
        addToDisposeBag(getQrCodeImage(this.order).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookSinglePurchaseActivity$u88ry1pFv0wM_lQJnhRbUAJJIjk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BookSinglePurchaseActivity.this.a((String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        L.pay.i("all book qr code url %s", str);
        this.e.setVisibility(0);
        this.e.setImageBitmap(QRCodeUtil.createQRCodeBitmap(str, getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size), getResources().getDimensionPixelSize(R.dimen.music_vip_qr_code_size)));
    }

    private ArrayList<SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem> a(Context context, int i, float f) {
        ArrayList<SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem> arrayList = new ArrayList<>();
        if (i < 1) {
            return arrayList;
        }
        arrayList.add(new SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem(context, context.getString(R.string.single_episode_purchase_this_episode), 1, f));
        String string = context.getString(R.string.single_episode_purchase_last_episode);
        if (i >= 10) {
            arrayList.add(new SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem(context, String.format(string, 10), 10, f));
        }
        if (i >= 20) {
            arrayList.add(new SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem(context, String.format(string, 20), 20, f));
        }
        if ((i == 1 || i == 10 || i == 20) ? false : true) {
            arrayList.add(new SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem(context, String.format(context.getString(R.string.single_episode_purchase_all_in), Integer.valueOf(i)), i, f));
        }
        return arrayList;
    }

    private void a() {
        addToDisposeBag(ApiManager.paymentService.getUnpurchasedQuantity(this.j.cpAlbumId, this.j.cpOrigin, this.j.playSequence, this.l).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BookSinglePurchaseActivity$79z5Gqw3Owvh7DOqJCgOzRjbsm0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BookSinglePurchaseActivity.this.a((ArrayList) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ArrayList arrayList) throws Exception {
        this.h = arrayList;
        ArrayList<SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem> a = a(this, b(), ((float) this.j.salesPrice) / 100.0f);
        this.i.setData(a);
        a((SingleEpisodePurchaseAdapter.SingleEpisodePurchaseItem) ContainerUtil.getFirst(a));
    }

    private int b() {
        return ContainerUtil.getSize(this.h);
    }

    public static void startBookSinglePurchaseActivity(Context context, Remote.Response.TrackData trackData, int i) {
        Intent intent = new Intent(context, BookSinglePurchaseActivity.class);
        intent.putExtra(EXTRA_BOOK_DATA, trackData);
        intent.putExtra(EXTRA_BOOK_INDEX, i);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
