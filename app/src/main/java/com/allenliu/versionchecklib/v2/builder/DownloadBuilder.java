package com.allenliu.versionchecklib.v2.builder;

import android.content.Context;
import androidx.annotation.NonNull;
import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.utils.FileHelper;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v2.ui.VersionService;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class DownloadBuilder {
    private RequestVersionBuilder a;
    private boolean b;
    private String c;
    private boolean d;
    private String e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private NotificationBuilder j;
    private APKDownloadListener k;
    private CustomDownloadFailedListener l;
    private CustomDownloadingDialogListener m;
    private CustomVersionDialogListener n;
    private OnCancelListener o;
    private ForceUpdateListener p;
    private UIData q;
    private Integer r;
    private String s;

    public DownloadBuilder() {
        throw new RuntimeException("can not be instantiated from outside");
    }

    private void a() {
        this.b = false;
        this.c = FileHelper.getDownloadApkCachePath();
        this.d = false;
        this.f = true;
        this.g = true;
        this.i = false;
        this.h = true;
        this.j = NotificationBuilder.create();
    }

    public DownloadBuilder(RequestVersionBuilder requestVersionBuilder, UIData uIData) {
        this.a = requestVersionBuilder;
        this.q = uIData;
        a();
    }

    public ForceUpdateListener getForceUpdateListener() {
        return this.p;
    }

    public DownloadBuilder setForceUpdateListener(ForceUpdateListener forceUpdateListener) {
        this.p = forceUpdateListener;
        return this;
    }

    public DownloadBuilder setApkName(String str) {
        this.s = str;
        return this;
    }

    public DownloadBuilder setVersionBundle(@NonNull UIData uIData) {
        this.q = uIData;
        return this;
    }

    public UIData getVersionBundle() {
        return this.q;
    }

    public DownloadBuilder setOnCancelListener(OnCancelListener onCancelListener) {
        this.o = onCancelListener;
        return this;
    }

    public DownloadBuilder setCustomDownloadFailedListener(CustomDownloadFailedListener customDownloadFailedListener) {
        this.l = customDownloadFailedListener;
        return this;
    }

    public DownloadBuilder setCustomDownloadingDialogListener(CustomDownloadingDialogListener customDownloadingDialogListener) {
        this.m = customDownloadingDialogListener;
        return this;
    }

    public DownloadBuilder setCustomVersionDialogListener(CustomVersionDialogListener customVersionDialogListener) {
        this.n = customVersionDialogListener;
        return this;
    }

    public DownloadBuilder setSilentDownload(boolean z) {
        this.b = z;
        return this;
    }

    public Integer getNewestVersionCode() {
        return this.r;
    }

    public DownloadBuilder setNewestVersionCode(Integer num) {
        this.r = num;
        return this;
    }

    public DownloadBuilder setDownloadAPKPath(String str) {
        this.c = str;
        return this;
    }

    public DownloadBuilder setForceRedownload(boolean z) {
        this.d = z;
        return this;
    }

    public DownloadBuilder setDownloadUrl(@NonNull String str) {
        this.e = str;
        return this;
    }

    public DownloadBuilder setShowDownloadingDialog(boolean z) {
        this.f = z;
        return this;
    }

    public DownloadBuilder setShowNotification(boolean z) {
        this.g = z;
        return this;
    }

    public DownloadBuilder setShowDownloadFailDialog(boolean z) {
        this.h = z;
        return this;
    }

    public DownloadBuilder setApkDownloadListener(APKDownloadListener aPKDownloadListener) {
        this.k = aPKDownloadListener;
        return this;
    }

    public boolean isSilentDownload() {
        return this.b;
    }

    public String getDownloadAPKPath() {
        return this.c;
    }

    public boolean isForceRedownload() {
        return this.d;
    }

    public String getDownloadUrl() {
        return this.e;
    }

    public boolean isShowDownloadingDialog() {
        return this.f;
    }

    public boolean isShowNotification() {
        return this.g;
    }

    public boolean isShowDownloadFailDialog() {
        return this.h;
    }

    public APKDownloadListener getApkDownloadListener() {
        return this.k;
    }

    public CustomDownloadFailedListener getCustomDownloadFailedListener() {
        return this.l;
    }

    public OnCancelListener getOnCancelListener() {
        return this.o;
    }

    public CustomDownloadingDialogListener getCustomDownloadingDialogListener() {
        return this.m;
    }

    public CustomVersionDialogListener getCustomVersionDialogListener() {
        return this.n;
    }

    public RequestVersionBuilder getRequestVersionBuilder() {
        return this.a;
    }

    public NotificationBuilder getNotificationBuilder() {
        return this.j;
    }

    public DownloadBuilder setNotificationBuilder(NotificationBuilder notificationBuilder) {
        this.j = notificationBuilder;
        return this;
    }

    public String getApkName() {
        return this.s;
    }

    public boolean isDirectDownload() {
        return this.i;
    }

    public DownloadBuilder setDirectDownload(boolean z) {
        this.i = z;
        return this;
    }

    public void executeMission(Context context) {
        if (this.s == null) {
            this.s = context.getApplicationContext().getPackageName();
        }
        EventBus.getDefault().postSticky(this);
        VersionService.enqueueWork(context.getApplicationContext());
    }
}
