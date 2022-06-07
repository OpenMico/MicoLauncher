package com.xiaomi.miplay.mylibrary.external;

import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import java.util.Map;

/* loaded from: classes4.dex */
public class MediaControlCallbackImpl implements IMediaControlCallback {
    private static MiplayAccessExternalService a;

    public static void setExternalService(MiplayAccessExternalService miplayAccessExternalService) {
        a = miplayAccessExternalService;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onPositionChanged(r6);
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onPositionChanged(long r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onPositionChanged."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r7 = "externalservice is null."
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r7, r0)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r7 = "externalClientMap  is null."
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r7, r0)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onPositionChanged(r6)     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onPositionChanged(long):int");
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public int onPlayed() {
        Logger.i("MiplayMp_CallbackImpl", "onPlayed.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalservice is null.", new Object[0]);
            return -1;
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
            return -1;
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id onPlayed:" + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onPlayed();
                    } else {
                        Logger.i("MiplayMp_CallbackImpl", "callback id onstop:" + entry.getValue().a, new Object[0]);
                        entry.getValue().callback.onStopped(0);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            OneTrackWorldUrl.getInstance().stopTimer(1, MediaControlImpl.getAppId());
            OneTrackWorldUrl.getInstance().startTimer(0);
            OneTrackWorldUrl.getInstance().trackResult(true);
            return 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onStopped(r6);
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onStopped(int r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onStopped."
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0028
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0028:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0038
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0038:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0090
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0090
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0090
        L_0x0046:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0090
            if (r1 == 0) goto L_0x0094
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0090
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0090
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0090
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0090
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0090
            if (r3 == 0) goto L_0x0046
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0090
            r3.<init>()     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0090
            r3.append(r4)     // Catch: RemoteException -> 0x0090
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0090
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0090
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0090
            r0.onStopped(r6)     // Catch: RemoteException -> 0x0090
            goto L_0x0094
        L_0x0090:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0094:
            com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl r0 = com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl.getInstance()
            java.lang.String r1 = com.xiaomi.miplay.mylibrary.external.MediaControlImpl.getAppId()
            r0.stopTimer(r2, r1)
            r0 = 1
            if (r6 != r0) goto L_0x00aa
            com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl r6 = com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl.getInstance()
            r1 = 0
            r6.trackTarget(r0, r1)
        L_0x00aa:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onStopped(int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onPaused();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onPaused() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onPaused."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onPaused()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onPaused():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onResumed();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onResumed() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onResumed."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onResumed()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onResumed():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onSeekedTo(r6);
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onSeekedTo(long r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onSeekedTo."
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0028
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r7 = "externalservice is null."
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r7, r0)
            return r1
        L_0x0028:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0038
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r7 = "externalClientMap  is null."
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r7, r0)
            return r1
        L_0x0038:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0090
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0090
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0090
        L_0x0046:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0090
            if (r1 == 0) goto L_0x0094
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0090
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0090
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0090
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0090
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0090
            if (r3 == 0) goto L_0x0046
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0090
            r3.<init>()     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0090
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0090
            r3.append(r4)     // Catch: RemoteException -> 0x0090
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0090
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0090
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0090
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0090
            r0.onSeekedTo(r6)     // Catch: RemoteException -> 0x0090
            goto L_0x0094
        L_0x0090:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0094:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onSeekedTo(long):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onNotifyPropertiesInfo(r6);
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onNotifyPropertiesInfo(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onNotifyPropertiesInfo."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onNotifyPropertiesInfo(r6)     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onNotifyPropertiesInfo(java.lang.String):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onNext();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onNext() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onNext."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onNext()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onNext():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onPrev();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onPrev() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onPrev."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onPrev()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onPrev():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onCirculateStart();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onCirculateStart() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onCirculateStart."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onCirculateStart()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onCirculateStart():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onCirculateEnd();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onCirculateEnd() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onCirculateEnd."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onCirculateEnd()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onCirculateEnd():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onSeekDoneNotify();
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onSeekDoneNotify() {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onSeekDoneNotify."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalservice is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r3 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onSeekDoneNotify()     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onSeekDoneNotify():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        com.xiaomi.miplay.mylibrary.session.utils.Logger.i("MiplayMp_CallbackImpl", "callback id:" + com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a.getmPlayPackageName(), new java.lang.Object[0]);
        r1.getValue().callback.onCirculateFail(r6);
     */
    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onCirculateFail(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.String r1 = "onCirculateFail."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a
            r1 = -1
            if (r0 != 0) goto L_0x0019
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "onCirculateFail is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0019:
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()
            if (r0 != 0) goto L_0x0029
            java.lang.String r6 = "MiplayMp_CallbackImpl"
            java.lang.String r0 = "externalClientMap  is null."
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r6, r0, r2)
            return r1
        L_0x0029:
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r0 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.util.HashMap r0 = r0.getMiPlayExternalClientMap()     // Catch: RemoteException -> 0x0081
            java.util.Set r0 = r0.entrySet()     // Catch: RemoteException -> 0x0081
            java.util.Iterator r0 = r0.iterator()     // Catch: RemoteException -> 0x0081
        L_0x0037:
            boolean r1 = r0.hasNext()     // Catch: RemoteException -> 0x0081
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r0.next()     // Catch: RemoteException -> 0x0081
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: RemoteException -> 0x0081
            java.lang.Object r3 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r3 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r3     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.a     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch: RemoteException -> 0x0081
            if (r3 == 0) goto L_0x0037
            java.lang.String r0 = "MiplayMp_CallbackImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: RemoteException -> 0x0081
            r3.<init>()     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = "callback id:"
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService r4 = com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.a     // Catch: RemoteException -> 0x0081
            java.lang.String r4 = r4.getmPlayPackageName()     // Catch: RemoteException -> 0x0081
            r3.append(r4)     // Catch: RemoteException -> 0x0081
            java.lang.String r3 = r3.toString()     // Catch: RemoteException -> 0x0081
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r3, r4)     // Catch: RemoteException -> 0x0081
            java.lang.Object r0 = r1.getValue()     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo r0 = (com.xiaomi.miplay.mylibrary.external.MiPlayExternalClientInfo) r0     // Catch: RemoteException -> 0x0081
            com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback r0 = r0.callback     // Catch: RemoteException -> 0x0081
            r0.onCirculateFail(r6)     // Catch: RemoteException -> 0x0081
            goto L_0x0085
        L_0x0081:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0085:
            com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl r6 = com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl.getInstance()
            r6.trackResult(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl.onCirculateFail(java.lang.String):int");
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onVolumeChange(double d) {
        Logger.i("MiplayMp_CallbackImpl", "onVolumeChange.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "onVolumeChange is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id:" + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onVolumeChange(d);
                        return;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onCirculateModeChange(int i) {
        Logger.i("MiplayMp_CallbackImpl", "onCirculateModeChange." + i, new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalservice is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null || a.getMiPlayExternalClientMap().size() == 0) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null or size 0", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, Constant.PACKAGENAME_QIYI_VIDEO) && TextUtils.isEmpty(a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id com.qiyi.video", new Object[0]);
                        entry.getValue().callback.onCirculateModeChange(i);
                    }
                    if (TextUtils.equals(entry.getValue().a, Constant.PACKAGENAME_XIAOMI_VIDEO) && TextUtils.isEmpty(a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id com.miui.video", new Object[0]);
                        entry.getValue().callback.onCirculateModeChange(i);
                    }
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id:" + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onCirculateModeChange(i);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onConnectMirrorSuccess() {
        Logger.i("MiplayMp_CallbackImpl", "onConnectMirrorSuccess.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalservice is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, Constant.PACKAGENAME_QIYI_VIDEO) && TextUtils.isEmpty(a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id com.qiyi.video", new Object[0]);
                        entry.getValue().callback.onConnectMirrorSuccess();
                    }
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id:" + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onConnectMirrorSuccess();
                    } else if (TextUtils.equals(entry.getValue().a, Constant.PACKAGENAME_XIAOMI_VIDEO)) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id com.miui.video", new Object[0]);
                        entry.getValue().callback.onConnectMirrorSuccess();
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onBuffering() {
        Logger.i("MiplayMp_CallbackImpl", "onBuffering.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "onBuffering is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, Constant.PACKAGENAME_QIYI_VIDEO)) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id com.qiyi.video", new Object[0]);
                        entry.getValue().callback.onBuffering();
                        return;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onResumeMirrorSuccess() {
        Logger.i("MiplayMp_CallbackImpl", "onResumeMirrorSuccess.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "onResumeMirrorSuccess is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id " + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onResumeMirrorSuccess();
                        return;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControlCallback
    public void onResumeMirrorFail() {
        Logger.i("MiplayMp_CallbackImpl", "onResumeMirrorFail.", new Object[0]);
        MiplayAccessExternalService miplayAccessExternalService = a;
        if (miplayAccessExternalService == null) {
            Logger.i("MiplayMp_CallbackImpl", "onResumeMirrorFail is null.", new Object[0]);
        } else if (miplayAccessExternalService.getMiPlayExternalClientMap() == null) {
            Logger.i("MiplayMp_CallbackImpl", "externalClientMap  is null.", new Object[0]);
        } else {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : a.getMiPlayExternalClientMap().entrySet()) {
                    if (TextUtils.equals(entry.getValue().a, a.getmPlayPackageName())) {
                        Logger.i("MiplayMp_CallbackImpl", "callback id " + a.getmPlayPackageName(), new Object[0]);
                        entry.getValue().callback.onResumeMirrorFail();
                        return;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
