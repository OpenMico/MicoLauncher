package com.xiaomi.micolauncher.skills.music.vip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import com.xiaomi.mitv.client.MitvClient;
import com.xiaomi.mitv.entity.OrderInfoParam;
import com.xiaomi.mitv.entity.SignInfoParam;
import com.xiaomi.mitv.exception.MitvCommonException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"Registered"})
/* loaded from: classes3.dex */
public class BasePurchaseActivity extends BaseActivity {
    protected static final String SHORT_KEY_PREFIX_PREVIEW = "http://h5-test.sys.tv.mi.com/store/thirdparty/pricetag/shortkey/";
    protected static final String SHORT_KEY_PREFIX_PRODUCTION = "https://h5.tv.mi.com/store/thirdparty/pricetag/shortkey/";
    private Disposable a;
    private Disposable b;
    protected List<Integer> indexList = new ArrayList();
    protected MusicPrice.Order order;
    protected OrderType orderType;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setScheduleDuration(TimeUnit.MINUTES.toMillis(5L));
        startInterval();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    protected void startInterval() {
        this.b = Observable.interval(5L, 5L, TimeUnit.SECONDS).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BasePurchaseActivity$Ypcm6y2U-THtQcX_NfMc-H0CQLc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BasePurchaseActivity.this.a((Long) obj);
            }
        });
        addToDisposeBag(this.b);
    }

    public /* synthetic */ void a(Long l) throws Exception {
        L.pay.i("interval   %d", l);
        a();
    }

    private void a() {
        Disposable disposable = this.a;
        if (disposable != null) {
            disposable.dispose();
        }
        if (this.order != null) {
            this.a = ApiManager.paymentService.orderStatus(this.order.getOrderId()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BasePurchaseActivity$l4vplMYt4hl_TDy-Xli-jFDUmyQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePurchaseActivity.this.a((String) obj);
                }
            }, $$Lambda$BasePurchaseActivity$WgiGZG_CQam0JTEbizm1kscCr4w.INSTANCE);
        }
    }

    public /* synthetic */ void a(String str) throws Exception {
        if (MusicPrice.Order.TRADE_DELIVERED.equals(str) || MusicPrice.Order.TRADE_FINISHED.equals(str) || MusicPrice.Order.TRADE_FINISHING.equals(str)) {
            L.pay.i("check order success");
            b();
        }
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.pay.e("check order failed ", th);
    }

    private void b() {
        ToastUtil.showToast(getString(R.string.vip_buy_success));
        stopInterval();
        AudioInfoCache.clearDiskCacheIfNecessary();
        finish();
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayPaySuccess(this.orderType, this.indexList));
    }

    protected void stopInterval() {
        Disposable disposable = this.b;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private String a(MusicPrice.Order order) {
        String str;
        String str2 = "";
        try {
            if (order.isRenew()) {
                SignInfoParam signInfoParam = new SignInfoParam();
                signInfoParam.setUserId(TokenManager.getInstance().getUserId());
                signInfoParam.setMac(WiFiUtil.getMacAddress());
                signInfoParam.setPlatform(Integer.valueOf((int) ChildVideoDataManager.PTF));
                signInfoParam.setBiz(136);
                signInfoParam.setProductId(order.getMitvContractProductId());
                signInfoParam.setAppId(2882303761517783133L);
                signInfoParam.setCustomerSignId(order.getContractId());
                signInfoParam.setProductName(order.getProductName());
                signInfoParam.setOrderAmount(Long.valueOf(order.getContractFee()));
                signInfoParam.setPlanId(order.getWechatPlanId());
                signInfoParam.setBizChannel(ChildVideoDataManager.BIZ_CHANNEL);
                str = MitvClient.createSignShortkey(signInfoParam, 1, 1, ApiConstants.isPrevEnv());
            } else {
                OrderInfoParam orderInfoParam = new OrderInfoParam();
                orderInfoParam.setUserId(Long.valueOf(TokenManager.getInstance().getUserId()));
                orderInfoParam.setPlatform(Integer.valueOf((int) ChildVideoDataManager.PTF));
                orderInfoParam.setBiz(136);
                orderInfoParam.setMac(WiFiUtil.getMacAddress());
                orderInfoParam.setDeviceID(SystemSetting.getDeviceID());
                orderInfoParam.setSdk_version("23");
                orderInfoParam.setLanguage("zh");
                orderInfoParam.setCountry("CN");
                orderInfoParam.setSn("unknow");
                orderInfoParam.setCodever("3");
                orderInfoParam.setCustomerOrderId(order.getOrderId());
                orderInfoParam.setRid("1");
                orderInfoParam.setTrxAmount(Long.valueOf(order.getTotalFee()));
                orderInfoParam.setBizChannel(ChildVideoDataManager.BIZ_CHANNEL);
                orderInfoParam.setAppId(2882303761517783133L);
                str = MitvClient.createShortkey(orderInfoParam, 0, 1, ApiConstants.isPrevEnv());
            }
            str2 = new JSONObject(str).getJSONObject("data").getString("shortKey");
        } catch (MitvCommonException | UnsupportedEncodingException | JSONException e) {
            L.pay.e("create short key error", e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ApiConstants.isPrevEnv() ? SHORT_KEY_PREFIX_PREVIEW : SHORT_KEY_PREFIX_PRODUCTION);
        sb.append(str2);
        return sb.toString();
    }

    protected Observable<String> getQrCodeImage(final MusicPrice.Order order) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.music.vip.-$$Lambda$BasePurchaseActivity$mWSzygeFYnyKAvoZv7WIiHotjLg
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                BasePurchaseActivity.this.a(order, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(MusicPrice.Order order, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(a(order));
        observableEmitter.onComplete();
    }
}
