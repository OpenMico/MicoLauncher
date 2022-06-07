package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.milink.base.contract.LockContract;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StartAiqiyiUiEvent;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import com.xiaomi.micolauncher.module.video.ui.VideoGroupListActivity;
import com.xiaomi.micolauncher.skills.personalize.event.ThirdPartAppOpeEvent;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.GKidAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.KaraoketvCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TalkKidAppCommandProcessor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ThirdPartySchemaHandler implements SchemaHandler {
    public static final String AUTHORITY_IBILI = "ibili";
    public static final String AUTHORITY_MIJIA = "mijia";
    public static final String AUTHORITY_QIYI = "qiyi";
    public static final String AUTHORITY_TENCENTVIDEO = "tencentvideo";
    public static final String AUTHORITY_TIKTOK = "tiktok";
    public static final String AUTHORITY_YOUKU = "youku";
    public static final String AUTHORITY_YYTV = "yytv";
    public static final String PACKAGE_NAME_WUKONGPINYIN = "air.com.gongfubb.wkpy";
    public static final String PACKAGE_NAME_WUKONGSHIZI = "air.com.gongfubb.wksz";
    public static final String SCHEME = "thirdparty";
    private Runnable a = $$Lambda$ThirdPartySchemaHandler$_gF54atwnZDBNySWq4Pvwy78eJQ.INSTANCE;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Intent a(Intent intent, Intent intent2) {
        return intent;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return SCHEME.equals(uri.getScheme());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        String path = uri.getPath();
        L.base.d("ThirdPartySchemaHandler authority=%s, process.path=%s", uri.getAuthority(), path);
        if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(uri.getAuthority())) {
            a(context, uri, uri.getAuthority());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void a(Context context, Uri uri, String str) {
        char c;
        switch (str.hashCode()) {
            case -1891157146:
                if (str.equals(SkillStatHelper.SKILL_STAT_KARAOKETV)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1258989724:
                if (str.equals("wukongpinyin")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1173817853:
                if (str.equals("wukongshuxue")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -873713414:
                if (str.equals("tiktok")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 117588:
                if (str.equals("web")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3174655:
                if (str.equals(SkillStatHelper.SKILL_STAT_GKID)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3471144:
                if (str.equals("qiyi")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3724706:
                if (str.equals("yytv")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 99993581:
                if (str.equals("ibili")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 115168713:
                if (str.equals("youku")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 203777110:
                if (str.equals("tencentvideo")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 211935678:
                if (str.equals("gotoApp")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 835447236:
                if (str.equals("mangotv")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1097116013:
                if (str.equals("gotoAppPage")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1347596744:
                if (str.equals("wukongshizi")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1566149960:
                if (str.equals("51talk")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                p(context, uri);
                return;
            case 1:
                n(context, uri);
                return;
            case 2:
                e(context, uri);
                return;
            case 3:
                f(context, uri);
                return;
            case 4:
                g(context, uri);
                return;
            case 5:
                h(context, uri);
                return;
            case 6:
                d(context, uri);
                return;
            case 7:
                c(context, uri);
                return;
            case '\b':
                b(context, uri);
                return;
            case '\t':
                a(context, uri);
                return;
            case '\n':
                i(context, uri);
                return;
            case 11:
                j(context, uri);
                return;
            case '\f':
                k(context, uri);
                return;
            case '\r':
                l(context, uri);
                return;
            case 14:
                m(context, uri);
                return;
            case 15:
                o(context, uri);
                return;
            default:
                return;
        }
    }

    private void a(Context context, Uri uri) {
        a(context, "com.tencent.qqlive.audiobox", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void b(Context context, Uri uri) {
        if (uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true)) {
            a(context, MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO, false);
        } else {
            a(context, MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO);
        }
    }

    private void c(Context context, Uri uri) {
        a(context, "com.ss.android.ugc.aweme", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void d(Context context, Uri uri) {
        String path = uri.getPath();
        if (!"startapp".equalsIgnoreCase(path)) {
            L.video.w("Unexpected path %s", path);
        }
        a(context, "com.youku.iot", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void e(Context context, Uri uri) {
        final String path = uri.getPath();
        AppStoreApi.handleAppWithPkgName(context, "com.qiyi.video.speaker", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true), new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$Wsb2d3mXl6W_1Ycb_nnEiJ3QUdQ
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartySchemaHandler.this.c(path);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void c(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -1620943534) {
            if (hashCode == -131027349 && str.equals("/start_cast")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("/end_cast")) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                b("iqiyi://com.qiyi.video.speaker/player?command=start_cast");
                return;
            case 1:
                b("iqiyi://com.qiyi.video.speaker/player?command=end_cast");
                return;
            default:
                EventBusRegistry.getEventBus().post(new StartAiqiyiUiEvent());
                return;
        }
    }

    private void f(final Context context, Uri uri) {
        char c;
        String path = uri.getPath();
        boolean booleanQueryParameter = uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true);
        int hashCode = path.hashCode();
        if (hashCode != -131027349) {
            if (hashCode == 1467276938 && path.equals("/start_cast_directly")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (path.equals("/start_cast")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                if (Hardware.isBigScreen()) {
                    a(context, booleanQueryParameter);
                    return;
                } else {
                    AppStoreApi.handleAppWithPkgName(context, "tv.danmaku.bili", new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$XjkeCNuULh4hePF58ciSRqef-Jc
                        @Override // java.lang.Runnable
                        public final void run() {
                            VideoGroupListActivity.openVideoGroupListActivity(context, "bilibili");
                        }
                    });
                    return;
                }
            case 1:
                a(context, booleanQueryParameter);
                return;
            default:
                L.base.w("not implemented for path %s", path);
                return;
        }
    }

    private void g(Context context, Uri uri) {
        char c;
        String path = uri.getPath();
        boolean booleanQueryParameter = uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true);
        int hashCode = path.hashCode();
        if (hashCode == -570017386) {
            if (path.equals("/home/auto_mode_false")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 46613902) {
            if (hashCode == 1703395594 && path.equals("/course")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (path.equals("/home")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                b(context, booleanQueryParameter);
                return;
            default:
                L.base.w("not implemented for path %s", path);
                return;
        }
    }

    private void a(Context context, boolean z) {
        a(context, "tv.danmaku.bili", z);
    }

    private void b(final Context context, boolean z) {
        final String str = GKidAppCommandProcessor.PACKAGE_NAME_GKID;
        AppStoreApi.handleAppWithPkgName(context, str, z, new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$qjXcKh38107IuQbb6w2ZzeoeLks
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartySchemaHandler.c(context, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context, String str) {
        ThirdPartyAppProxy.getInstance().startApp(context, str, $$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0.INSTANCE);
    }

    private void b(String str) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    private void h(Context context, Uri uri) {
        a(context, "com.duowan.yytv", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void i(Context context, Uri uri) {
        a(context, KaraoketvCommandProcessor.PACKAGE_NAME_KARAOKETV, uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void j(Context context, Uri uri) {
        a(context, TalkKidAppCommandProcessor.PACKAGE_NAME_51TALK, uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void k(Context context, Uri uri) {
        a(context, PACKAGE_NAME_WUKONGSHIZI, uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void l(Context context, Uri uri) {
        a(context, PACKAGE_NAME_WUKONGPINYIN, uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void m(Context context, Uri uri) {
        a(context, "air.com.gongfubb.wk123", uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true));
    }

    private void n(Context context, Uri uri) {
        String queryParameter = uri.getQueryParameter("pkgname");
        if (ContainerUtil.hasData(queryParameter)) {
            a(context, queryParameter);
        }
    }

    private boolean a(Context context, String str) {
        return a(context, str, true);
    }

    private boolean a(final Context context, final String str, boolean z) {
        return AppStoreApi.handleAppWithPkgName(context, str, z, new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$90U-uy0xo7KQ6fvfq-pNmr4rmGY
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartySchemaHandler.this.b(context, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context, String str) {
        a();
        ThirdPartyAppProxy.getInstance().startApp(context, str, null);
    }

    private void o(final Context context, final Uri uri) {
        final String queryParameter = uri.getQueryParameter("pkgname");
        if (ContainerUtil.isEmpty(queryParameter)) {
            c(context, uri, queryParameter);
        } else {
            AppStoreApi.handleAppWithPkgName(context, queryParameter, uri.getBooleanQueryParameter(LockContract.Matcher.LOCK, true), new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$NT9lxu-mnbwD71s51rlTLC2pFTI
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartySchemaHandler.this.c(context, uri, queryParameter);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0108  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c(android.content.Context r11, android.net.Uri r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.schema.handler.ThirdPartySchemaHandler.c(android.content.Context, android.net.Uri, java.lang.String):void");
    }

    private void p(Context context, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (ContainerUtil.hasData(queryParameter)) {
            Intent intent = new Intent(context, SimpleWebActivity.class);
            intent.putExtra(SimpleWebActivity.NAME, queryParameter);
            ActivityLifeCycleManager.startActivityQuietly(intent);
        }
    }

    private void a() {
        ThreadUtil.getWorkHandler().removeCallbacks(this.a);
        long currentCmdDuration = PersonalizeExecution.getInstance().getCurrentCmdDuration();
        if (currentCmdDuration > 0) {
            ThreadUtil.getWorkHandler().postDelayed(this.a, TimeUnit.SECONDS.toMillis(currentCmdDuration));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b() {
        Context app2 = Proxies.getApp();
        ThirdPartyAppProxy.getInstance().stop(app2);
        ThirdPartyAppProxy.getInstance().quit(app2);
        EventBusRegistry.getEventBus().post(new ThirdPartAppOpeEvent(true));
    }
}
