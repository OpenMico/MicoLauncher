package com.xiaomi.micolauncher.module.child.childvideo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.model.CheckOrder;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.child.childvideo.PriceAdapter;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.child.view.PriceView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MiTvVipActivity extends BaseActivity implements PriceAdapter.OnPriceItemClickListener {
    public static final String INTENT_EXTRA_MI_TV_TYPE = "vip_mi_tv_type";
    public static final String INTENT_EXTRA_PCODE = "vip_pcode";
    public static final int PRICE_CODEVER = 439;
    public static final int REQUEST_CODE_VIP = 291;
    public static final int RESULT_CODE_BUY_SUCCESS = 273;
    private static final long a = TimeUnit.MINUTES.toMillis(5);
    private ImageView A;
    private ImageView B;
    private ImageView C;
    private ImageView D;
    private ImageView E;
    private int F;
    private PriceAdapter b;
    private TextView c;
    private PriceView d;
    private ImageView e;
    private ProductPrice f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private String l;
    private boolean m;
    private Disposable n;
    private Disposable o;
    private TextView p;
    private boolean q;
    private ChildVideo.MiTvType s;
    private ImageView t;
    private ImageView u;
    private ImageView v;
    private ProductPrice.PriceBean w;
    private Handler x;
    private ImageView y;
    private ImageView z;
    private String r = ChildVideoDataManager.PCODE_ERTONG;
    private final int[] G = {R.drawable.img_benefits_goodanimation, R.drawable.img_benefits_internationalization, R.drawable.img_benefits_rich};
    private Handler.Callback H = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            MiTvVipActivity miTvVipActivity = MiTvVipActivity.this;
            miTvVipActivity.F = MiTvVipActivity.a(miTvVipActivity) % ContainerUtil.getSize(MiTvVipActivity.this.G);
            MiTvVipActivity.this.b();
            return false;
        }
    };

    static /* synthetic */ int a(MiTvVipActivity miTvVipActivity) {
        int i = miTvVipActivity.F + 1;
        miTvVipActivity.F = i;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        this.s = (ChildVideo.MiTvType) getIntent().getSerializableExtra(INTENT_EXTRA_MI_TV_TYPE);
        ChildVideo.MiTvType miTvType = this.s;
        if (miTvType != null) {
            this.r = miTvType.getpCode();
            this.q = !ChildVideo.MiTvType.CHILD_VIDEO.equals(this.s);
            L.course.i("current product code is %s", this.r);
        } else {
            this.q = false;
        }
        this.x = new WeakRefHandler(this.H);
        setContentView(this.q ? R.layout.activity_course_vip : R.layout.activity_mitv_vip);
        d();
        g();
        c();
        setScheduleDuration(a);
        a();
    }

    private void a() {
        if (!this.q) {
            this.y = (ImageView) findViewById(R.id.vip_right1);
            this.z = (ImageView) findViewById(R.id.vip_right2);
            this.A = (ImageView) findViewById(R.id.vip_right3);
            this.B = (ImageView) findViewById(R.id.vip_right4);
            this.C = (ImageView) findViewById(R.id.vip_rights_banner);
            this.D = (ImageView) findViewById(R.id.pre_img);
            this.E = (ImageView) findViewById(R.id.next_img);
            this.D.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$m0RDYB09okp2rkWGywzobxjzUs4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.j(view);
                }
            });
            this.E.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$3vhXV4ulVUqiH2mFj-DzrWY4mzs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.i(view);
                }
            });
            a(this.y, 0);
            this.y.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$V1GFFBJMkQS-CCv9bIASho58gD0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.h(view);
                }
            });
            this.z.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$YdXmQWLRz2-oS3PAp49UyO2tQKI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.g(view);
                }
            });
            this.A.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$V_i6CVC2CxGe2r0uv5H4VqnhN5E
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.f(view);
                }
            });
            this.B.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$t2DFwN5hA80BwfQTvNnaB4spKvs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MiTvVipActivity.this.e(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void j(View view) {
        this.F--;
        if (this.F == -1) {
            this.F = ContainerUtil.getSize(this.G) - 1;
        }
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void i(View view) {
        int i = this.F + 1;
        this.F = i;
        this.F = i % ContainerUtil.getSize(this.G);
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void h(View view) {
        a(view, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(View view) {
        a(view, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f(View view) {
        a(view, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(View view) {
        a(view, 3);
    }

    private void a(View view, int i) {
        this.y.setSelected(false);
        this.z.setSelected(false);
        this.A.setSelected(false);
        this.B.setSelected(false);
        view.setSelected(true);
        switch (i) {
            case 0:
                this.F = 0;
                this.C.setImageResource(R.drawable.img_benefits_goodanimation);
                this.x.sendEmptyMessageDelayed(291, RtspMediaSource.DEFAULT_TIMEOUT_MS);
                this.D.setVisibility(0);
                this.E.setVisibility(0);
                return;
            case 1:
                this.x.removeCallbacksAndMessages(null);
                this.D.setVisibility(8);
                this.E.setVisibility(8);
                this.C.setImageResource(R.drawable.img_benefits_multiterminal);
                return;
            case 2:
                this.x.removeCallbacksAndMessages(null);
                this.D.setVisibility(8);
                this.E.setVisibility(8);
                this.C.setImageResource(R.drawable.img_benefits_bilingual);
                return;
            case 3:
                this.x.removeCallbacksAndMessages(null);
                this.D.setVisibility(8);
                this.E.setVisibility(8);
                this.C.setImageResource(R.drawable.img_benefits_privilege);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.x.removeCallbacksAndMessages(null);
        this.C.setImageResource(this.G[this.F]);
        this.x.sendEmptyMessageDelayed(291, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    private void c() {
        addToDisposeBag(ChildVideoDataManager.getManager().a(this, this.r).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$t3b_TJkAMMWv1um-Dmnk_M29yJA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVipActivity.this.a((ProductPrice) obj);
            }
        }));
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ProductPrice productPrice) throws Exception {
        this.f = productPrice;
        this.b.a(productPrice.getData());
        if (ContainerUtil.hasData(productPrice.getData())) {
            a(productPrice.getData().get(0));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0126, code lost:
        if (r0.equals(com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager.PCODE_ERTONG) != false) goto L_0x012a;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void d() {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity.d():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        this.r = ChildVideoDataManager.PCODE_PRIMARY;
        a(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        this.r = ChildVideoDataManager.PCODE_JUNIOR;
        a(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        this.r = ChildVideoDataManager.PCODE_HIGH;
        a(view);
    }

    private void a(View view) {
        TextView textView = this.k;
        if (textView != view) {
            textView.setTextSize(getResources().getDimensionPixelSize(R.dimen.footnote));
            this.k = (TextView) view;
            this.k.setTextSize(getResources().getDimensionPixelSize(R.dimen.headline_2));
            c();
            this.b.setSelectPosition(0);
            g();
            L.course.i("current pcode is %s", this.r);
        }
    }

    private void e() {
        k();
        this.o = Observable.interval(5L, 5L, TimeUnit.SECONDS).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$k9YiuxyumhA1u5nUHk83idHbo5g
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVipActivity.this.a((Long) obj);
            }
        });
        addToDisposeBag(this.o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Long l) throws Exception {
        f();
    }

    private void f() {
        Disposable disposable = this.n;
        if (disposable != null && !disposable.isDisposed()) {
            this.n.dispose();
        }
        if (this.m) {
            h();
        } else {
            i();
        }
    }

    private void g() {
        Observable<ChildVideo.DueTime> c = ChildVideoDataManager.getManager().c(this.r);
        if (c != null) {
            addToDisposeBag(c.subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$mpkxjQrGMvnZN6SdMx1UdiLDNV0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiTvVipActivity.this.a((ChildVideo.DueTime) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo.DueTime dueTime) throws Exception {
        int i = 8;
        if (dueTime.getDueTime() == 0 || dueTime.getCode() != 0 || dueTime.getDueTime() * 1000 < System.currentTimeMillis()) {
            this.p.setText(getString(R.string.child_not_vip_tip));
            this.g.setVisibility(8);
            SystemSetting.setKeyMiTvVipStatus(false);
        } else {
            this.g.setText(getResources().getString(R.string.child_vip_due_time, String.valueOf(TimeUtils.getNowDiffDay(dueTime.getDueTime() * 1000))));
            this.p.setText(getString(R.string.child_vip_tip));
            this.g.setVisibility(0);
            SystemSetting.setKeyMiTvVipStatus(true);
        }
        TextView textView = this.p;
        if (!this.q) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    private void h() {
        this.n = ChildVideoDataManager.getManager().a(this.l).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$nYTVtlo2OKpPzQI-tnc5yI3dSCA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVipActivity.this.a((CheckOrder.CheckRenewOrder) obj);
            }
        }, $$Lambda$MiTvVipActivity$OKHQbZPNEyX_qBIn5A4kpx1s1xo.INSTANCE);
        addToDisposeBag(this.n);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(CheckOrder.CheckRenewOrder checkRenewOrder) throws Exception {
        if (checkRenewOrder.getData() != null) {
            CheckOrder.CheckRenewOrder.DataBean data = checkRenewOrder.getData();
            if (data.getSign_result() == 1 && data.getOrder_result() == 3) {
                L.childContent.i("check renew order success");
                j();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.childContent.e("check renew order failed ", th);
    }

    private void i() {
        this.n = ChildVideoDataManager.getManager().b(this.l).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$qXA2SK_WwDReZdmu4DYwyvJ9j24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVipActivity.this.a((CheckOrder.CheckNotRenewOrder) obj);
            }
        }, $$Lambda$MiTvVipActivity$ulCwkzT3BWnoE43ZLUMo2IuDJ0.INSTANCE);
        addToDisposeBag(this.n);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(CheckOrder.CheckNotRenewOrder checkNotRenewOrder) throws Exception {
        if (checkNotRenewOrder.getDataBean() != null && checkNotRenewOrder.getDataBean().getResult() == 3) {
            L.childContent.i("check not renew order success");
            j();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.childContent.e("check notRenew order failed ", th);
    }

    private void j() {
        ToastUtil.showToast(getString(R.string.vip_buy_success));
        setResult(273);
        k();
        g();
        ChildStatHelper.recordChildVideoVipPayPageFinish(this.w);
    }

    private void k() {
        Disposable disposable = this.o;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.childvideo.PriceAdapter.OnPriceItemClickListener
    public void onPriceClick(int i) {
        ProductPrice productPrice = this.f;
        if (productPrice != null && !ContainerUtil.isOutOfBound(i, productPrice.getData())) {
            a(this.f.getData().get(i));
            e();
        }
    }

    void a(ProductPrice.PriceBean priceBean) {
        this.w = priceBean;
        this.e.setVisibility(4);
        this.m = priceBean.isRenew();
        this.c.setText(priceBean.getUnit_desc());
        this.d.setPrice(Float.parseFloat(priceBean.getReal_price()) / 100.0f);
        b(priceBean);
        addToDisposeBag(ChildVideoDataManager.getManager().a(this, this.r, priceBean.getProduct_id(), priceBean.isRenew()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVipActivity$d28vZ1jeCvKNF1Y1uxsGaJsJcYg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVipActivity.this.a((String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        String str2;
        this.l = str;
        if (ApiConstants.isPrevEnv()) {
            str2 = "http://h5-test.sys.tv.mi.com/store/pricetag/shortkey/" + str;
        } else {
            str2 = "https://h5.tv.mi.com/store/pricetag/shortkey/" + str;
        }
        L.childContent.i("url    %s", str2);
        this.e.setVisibility(0);
        this.e.setImageBitmap(QRCodeUtil.createQRCodeBitmap(str2, getResources().getDimensionPixelSize(R.dimen.child_vip_qr_code_size), getResources().getDimensionPixelSize(R.dimen.child_vip_qr_code_size)));
    }

    private void b(ProductPrice.PriceBean priceBean) {
        this.t.setVisibility(8);
        this.u.setVisibility(8);
        this.v.setVisibility(8);
        if (ContainerUtil.hasData(priceBean.getPay_type_list())) {
            for (ProductPrice.PriceBean.PayTypeListBean payTypeListBean : priceBean.getPay_type_list()) {
                switch (payTypeListBean.getId()) {
                    case 1:
                        this.t.setVisibility(0);
                        break;
                    case 2:
                        this.u.setVisibility(0);
                        break;
                    case 3:
                        this.v.setVisibility(0);
                        break;
                }
            }
            return;
        }
        this.t.setVisibility(0);
        this.u.setVisibility(0);
        this.v.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ItemDecoration {
        private int a;
        private int b;

        a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i = this.a;
            int i2 = childAdapterPosition % i;
            int i3 = this.b;
            rect.left = (i2 * i3) / i;
            rect.right = i3 - (((i2 + 1) * i3) / i);
            if (childAdapterPosition >= i) {
                rect.top = i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.x.removeCallbacksAndMessages(null);
    }
}
