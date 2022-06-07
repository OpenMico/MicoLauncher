package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.appstore.bean.DownloadRequest;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AppInstallManager {
    public static final int STATUS_INVALID_DOWNLOAD_ID = -3;
    public static final int STATUS_PENDING = -2;
    private static float a = 99.0f;
    private static float b = 100.0f;

    private AppInstallManager() {
    }

    /* loaded from: classes3.dex */
    private static class a {
        private static final AppInstallManager a = new AppInstallManager();
    }

    public static AppInstallManager getManager() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b {
        private SynchronousQueue<Intent> a;
        private IIntentSender.Stub b;

        private b() {
            this.a = new SynchronousQueue<>();
            this.b = new IIntentSender.Stub() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppInstallManager.b.1
                public void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) {
                    try {
                        b.this.a.offer(intent, 5L, TimeUnit.SECONDS);
                    } catch (InterruptedException unused) {
                        throw new IllegalStateException("Interrupted");
                    }
                }
            };
        }

        public IntentSender a() {
            return new IntentSender(this.b);
        }

        public Intent b() {
            try {
                L.base.i("%s before get intent exception", "AppInstallManager");
                return this.a.take();
            } catch (InterruptedException e) {
                L.base.e("AppInstallManager get intent exception", e);
                throw new IllegalStateException("Interrupted");
            }
        }
    }

    public Observable<Boolean> deleteFile(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppInstallManager$aB5f6q-dU5dNFZICQDwhb5_MLuo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppInstallManager.a(str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(Boolean.valueOf(ContainerUtil.hasData(str) ? new File(new URI(str)).delete() : false));
        observableEmitter.onComplete();
    }

    public Observable<Integer> silentInstall(final Context context, final DownloadRequest downloadRequest) {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        try {
            try {
                final PackageInstaller.Session openSession = packageInstaller.openSession(packageInstaller.createSession(new PackageInstaller.SessionParams(1)));
                return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppInstallManager$aE7918LCvRnz15nA0prky7oOy9A
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        AppInstallManager.a(DownloadRequest.this, openSession, context, observableEmitter);
                    }
                });
            } catch (IOException e) {
                L.storage.e("AppInstallManager failed to create open session", e);
                downloadRequest.setInstallStatus(1);
                return Observable.just(1);
            }
        } catch (IOException e2) {
            L.storage.e("AppInstallManager failed to create install session", e2);
            downloadRequest.setInstallStatus(1);
            return Observable.just(1);
        } catch (IllegalStateException e3) {
            L.storage.e("AppInstallManager failed to create install session", e3);
            downloadRequest.setInstallStatus(1);
            return Observable.just(1);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 9, insn: 0x012e: MOVE  (r13 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]), block:B:52:0x012e
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 9, insn: 0x012e: MOVE  (r13 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]), block:B:52:0x012e
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r16v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public Observable<Boolean> silentUninstall(final Context context, final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppInstallManager$lkLKXCHgMa0FPqOgba6Vo2I3b8k
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppInstallManager.a(context, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, String str, ObservableEmitter observableEmitter) throws Exception {
        try {
            PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            b bVar = new b();
            packageInstaller.uninstall(str, bVar.a());
            Intent b2 = bVar.b();
            boolean z = false;
            int intExtra = b2.getIntExtra("android.content.pm.extra.STATUS", 0);
            L.storage.i("AppInstallManager silentUninstall %s", Integer.valueOf(intExtra));
            ThirdPartyAppProxy.getInstance().updateThirdPartyPackagesAndLauncherAsync(context);
            if (intExtra == 0) {
                z = true;
            }
            observableEmitter.onNext(Boolean.valueOf(z));
            observableEmitter.onComplete();
        } catch (Exception e) {
            L.storage.e("AppInstallManagerfailed to silentUninstall", e);
            observableEmitter.onError(e);
            observableEmitter.onComplete();
        }
    }

    public static int getInstalledPackageVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 16384).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public static boolean isSystemApp(Context context, String str) {
        try {
            return (context.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
