package com.xiaomi.micolauncher.module.miot;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.MiotDeviceCountChangeEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.miot.support.HostDeviceInfo;
import com.xiaomi.miot.support.MiotProvider;
import com.xiaomi.miot.support.ServiceTokenInfo;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;

/* loaded from: classes3.dex */
public class MiotProviderImp implements MiotProvider {
    @Override // com.xiaomi.miot.support.MiotProvider
    public String getUserId(Context context) {
        String userId = TokenManager.getInstance().getUserId();
        L.miot.i("MiotProviderImp getUserId=%s", userId);
        return userId;
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public String getCUserId(Context context) {
        return TokenManager.getInstance().getCUserId();
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public String getNickName(Context context) {
        String str;
        ThirdPartyResponse.UserCard userCard = SystemSetting.getUserProfile().getUserCard();
        if (userCard != null) {
            if (!TextUtils.isEmpty(userCard.miliaoNick)) {
                str = userCard.miliaoNick;
            } else if (!TextUtils.isEmpty(userCard.aliasNick)) {
                str = userCard.aliasNick;
            }
            L.miot.i("MiotProviderImp nickName:%s", str);
            return str;
        }
        str = null;
        L.miot.i("MiotProviderImp nickName:%s", str);
        return str;
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public ServiceTokenInfo getServiceToken(Context context, String str) {
        L.miot.i("MiotProviderImp getServiceToken sid:%s", str);
        ThreadUtil.verifyThread();
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(str);
        if (blockGetServiceToken == null) {
            return null;
        }
        ServiceTokenInfo serviceTokenInfo = new ServiceTokenInfo(str);
        serviceTokenInfo.serviceToken = blockGetServiceToken.serviceToken;
        serviceTokenInfo.security = blockGetServiceToken.security;
        return serviceTokenInfo;
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public void displayImage(ImageView imageView, String str) {
        L.miot.i("MiotProviderImp displayImage url:%s", str);
        Glide.with(MicoApplication.getGlobalContext()).load(str).into(imageView);
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public void onDevicesCountChanged(int i, int i2) {
        MiotFragment.sDeviceCount = i2;
        L.miot.i("MiotProviderImp onDevicesCountChanged prev:%d current:%d", Integer.valueOf(i), Integer.valueOf(i2));
        EventBusRegistry.getEventBus().post(new MiotDeviceCountChangeEvent(i, i2));
    }

    @Override // com.xiaomi.miot.support.MiotProvider
    public HostDeviceInfo getHostDeviceInfo(Context context) {
        L.miot.i("MiotProviderImp getHostDeviceInfo");
        return new HostDeviceInfo();
    }
}
