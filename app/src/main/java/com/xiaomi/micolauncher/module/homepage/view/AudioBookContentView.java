package com.xiaomi.micolauncher.module.homepage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.AudiobookStatUtils;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookSingleCategoryActivity;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioBookContentView extends FrameLayout {
    TextView a;
    TextView b;
    ImageView c;
    ImageView d;
    View e;
    TextView[] f;
    View g;
    private String[] h;
    private final int[] i;
    private final int[] j;
    private final int[] k;
    private String l;
    private String m;
    private String n;
    private int o;
    private TrackWidget p;
    private AudiobookContent q;
    private int r;
    private Station.Item s;
    private List<String> t;
    private String u;
    private String v;

    protected int layout() {
        return R.layout.audiobook_content;
    }

    public AudioBookContentView(Context context) {
        this(context, null);
    }

    public AudioBookContentView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AudioBookContentView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = new TextView[3];
        this.i = new int[]{R.drawable.audiobook_bg_0, R.drawable.audiobook_bg_1, R.drawable.audiobook_bg_2, R.drawable.audiobook_bg_3, R.drawable.audiobook_bg_4, R.drawable.audiobook_bg_5, R.drawable.audiobook_bg_6, R.drawable.audiobook_bg_7};
        this.j = new int[]{R.color.color_FFFDED, R.color.color_FBFFC6, R.color.color_FF5039, R.color.color_4421FF, R.color.color_5CFFF8, R.color.color_FDFF32, R.color.color_FDFF32, R.color.color_4D6F96};
        this.k = new int[]{R.color.white, R.color.white, R.color.black, R.color.black};
        X2CWrapper.inflateNoX2C(context, layout(), this);
        this.a = (TextView) findViewById(R.id.audiobook_album);
        this.b = (TextView) findViewById(R.id.audiobook_title);
        this.c = (ImageView) findViewById(R.id.audiobook_cover);
        this.d = (ImageView) findViewById(R.id.category_img);
        this.e = findViewById(R.id.foreground_view);
        this.f[0] = (TextView) findViewById(R.id.tip_one);
        this.f[1] = (TextView) findViewById(R.id.tip_two);
        this.f[2] = (TextView) findViewById(R.id.tip_three);
        this.g = findViewById(R.id.black_fore);
    }

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public void initInMain() {
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AudioBookContentView$my0W0GAD4lSoNhOhTwPA0ydZIZ4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookContentView.this.a(obj);
            }
        });
        final int i = 0;
        while (true) {
            TextView[] textViewArr = this.f;
            if (i < textViewArr.length) {
                AnimLifecycleManager.repeatOnAttach(textViewArr[i], MicoAnimConfigurator4EntertainmentAndApps.get());
                RxViewHelp.debounceClicksWithOneSeconds(this.f[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AudioBookContentView$t_ATUUH2iL-97b8bsPrcgydFcLE
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudioBookContentView.this.a(i, obj);
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        if (a()) {
            SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_BLACKLIST);
        } else if (CommonUtil.checkHasNetworkAndShowToast(getContext())) {
            if (!TextUtils.isEmpty(this.l)) {
                ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse(this.l)));
                return;
            }
            PlayerApi.playStation(getContext(), this.m, this.n, this.o);
            b();
        }
    }

    public /* synthetic */ void a(int i, Object obj) throws Exception {
        AudiobookSingleCategoryActivity.startAudiobookSingleCategoryActivity(getContext(), this.h[i]);
    }

    private boolean a() {
        this.t = LocalPlayerManager.getInstance().getBlackListKey();
        boolean z = false;
        if (ContainerUtil.isEmpty(this.t)) {
            return false;
        }
        for (String str : this.t) {
            if ((!TextUtils.isEmpty(this.u) && this.u.contains(str)) || (!TextUtils.isEmpty(this.v) && this.v.contains(str))) {
                z = true;
            }
        }
        return z;
    }

    private void b() {
        if (this.p != null) {
            switch (this.p) {
                case STATION_DISCOVER_PROMOTION_1:
                    AudiobookStatUtils.recordPromotion1(this.q, EventType.CLICK);
                    return;
                case STATION_DISCOVER_PROMOTION_2:
                    AudiobookStatUtils.recordPromotion2(this.q, this.r, EventType.CLICK);
                    return;
                case STATION_DISCOVER_RECOMMEND:
                    AudiobookStatUtils.recordPromotionRecommend(this.q, this.r, EventType.CLICK);
                    return;
                case STATION_DISCOVER_CATEGORY_RECOMMEND:
                    Station.Item item = this.s;
                    if (item != null) {
                        AudiobookStatUtils.recordCategoryRecommend(item, this.r, EventType.CLICK);
                        return;
                    } else {
                        AudiobookStatUtils.recordCategoryRecommend(this.q, this.r, EventType.CLICK);
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void setData(AudiobookContent audiobookContent, TrackWidget trackWidget, int i) {
        this.r = i;
        this.q = audiobookContent;
        this.p = trackWidget;
        this.l = audiobookContent.getActionURL();
        this.m = audiobookContent.getCpAlbumId();
        this.n = audiobookContent.getCp();
        this.o = audiobookContent.getTypeId();
        a(audiobookContent.getCpAlbumTitle(), audiobookContent.getLastSoundTitle(), audiobookContent.getCover(), a(audiobookContent.getCategory()));
    }

    public void setData(Station.Item item, TrackWidget trackWidget, int i) {
        this.s = item;
        this.r = i;
        this.p = trackWidget;
        this.l = item.getTarget();
        this.m = item.getId();
        this.n = item.getOrigin();
        this.o = item.getTypeId();
        a(item.getBroadcaster(), item.getTitle(), item.getCover(), a(item.getCategory()));
    }

    public void setBlackList(List<String> list) {
        this.t = list;
    }

    private String[] a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.split(";");
        }
        return null;
    }

    public void setData(Order.OrderInfo orderInfo, int i) {
        this.r = i;
        this.l = orderInfo.action;
        this.m = orderInfo.productId;
        this.n = orderInfo.origin;
        this.o = orderInfo.getTypeId(orderInfo.type);
        a(TextUtils.isEmpty(orderInfo.broadcaster) ? orderInfo.originName : orderInfo.broadcaster, orderInfo.productName, orderInfo.pictureUrl, a(orderInfo.category));
    }

    private void a(String str, String str2, String str3, String[] strArr) {
        this.u = str2;
        this.v = str;
        this.a.setText(str);
        this.b.setText(str2);
        c();
        GlideUtils.bindImageViewCircle(getContext(), str3, this.c, R.drawable.book_album_cicler_hold, UiUtils.getSize(getContext(), R.dimen.audiobook_content_cover_size), UiUtils.getSize(getContext(), R.dimen.audiobook_content_cover_size));
        if (ContainerUtil.isEmpty(strArr)) {
            this.d.setImageResource(R.drawable.book_recreation_icon);
            return;
        }
        this.h = strArr;
        GlideUtils.bindImageView(this.d, AudiobookDataManager.getManager().getResId(strArr[0]));
        for (int i = 0; i < strArr.length && i != 2; i++) {
            this.f[i].setText(getContext().getString(R.string.category_mark, strArr[i]));
            this.f[i].setVisibility(0);
        }
        if (a()) {
            this.g.setVisibility(0);
        } else if (this.g.getVisibility() == 0) {
            this.g.setVisibility(8);
        }
    }

    private void c() {
        for (TextView textView : this.f) {
            textView.setVisibility(8);
        }
    }

    public void setResourceIndex(int i) {
        int i2 = i % 8;
        this.e.setBackgroundResource(this.i[i2]);
        this.a.setTextColor(getResources().getColor(this.j[i2]));
        this.b.setTextColor(getResources().getColor(this.k[i % 4]));
    }
}
