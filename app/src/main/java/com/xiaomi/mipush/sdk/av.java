package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.google.android.exoplayer2.PlaybackException;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.Cif;
import com.xiaomi.push.aj;
import com.xiaomi.push.az;
import com.xiaomi.push.bl;
import com.xiaomi.push.db;
import com.xiaomi.push.ev;
import com.xiaomi.push.ew;
import com.xiaomi.push.fb;
import com.xiaomi.push.hh;
import com.xiaomi.push.hm;
import com.xiaomi.push.hr;
import com.xiaomi.push.ht;
import com.xiaomi.push.hu;
import com.xiaomi.push.hv;
import com.xiaomi.push.hx;
import com.xiaomi.push.hy;
import com.xiaomi.push.i;
import com.xiaomi.push.ic;
import com.xiaomi.push.id;
import com.xiaomi.push.ie;
import com.xiaomi.push.ig;
import com.xiaomi.push.ii;
import com.xiaomi.push.ik;
import com.xiaomi.push.im;
import com.xiaomi.push.io;
import com.xiaomi.push.iq;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import com.xiaomi.push.ix;
import com.xiaomi.push.s;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.ah;
import com.xiaomi.push.service.ap;
import com.xiaomi.push.service.z;
import com.xiaomi.smarthome.library.common.util.DateTimeHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class av {
    private static av a;
    private static Queue<String> c;
    private static Object d = new Object();
    private Context b;

    private av(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.Intent a(android.content.Context r6, java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            Method dump skipped, instructions count: 397
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.av.a(android.content.Context, java.lang.String, java.util.Map):android.content.Intent");
    }

    private PushMessageHandler.a a(id idVar, boolean z, byte[] bArr, String str, int i) {
        String str2;
        int i2;
        String str3;
        String str4;
        ew ewVar;
        MiPushMessage miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        miPushMessage = null;
        ArrayList arrayList3 = null;
        miPushMessage = null;
        try {
            is a2 = ar.a(this.b, idVar);
            if (a2 == null) {
                b.d("receiving an un-recognized message. " + idVar.a);
                ew.a(this.b).b(this.b.getPackageName(), ev.m896a(i), str, "receiving an un-recognized message.");
                return null;
            }
            hh a3 = idVar.a();
            b.m149a("processing a message, action=" + a3);
            switch (ag.a[a3.ordinal()]) {
                case 1:
                    if (!d.m727a(this.b).m736d() || z) {
                        ik ikVar = (ik) a2;
                        ht a4 = ikVar.a();
                        if (a4 != null) {
                            if (z) {
                                if (z.a(idVar)) {
                                    MiPushClient.a(this.b, a4.m980a(), idVar.m1021a(), idVar.b, a4.b());
                                } else {
                                    MiPushClient.a(this.b, a4.m980a(), idVar.m1021a(), a4.b());
                                }
                            }
                            if (!z) {
                                if (!TextUtils.isEmpty(ikVar.d()) && MiPushClient.aliasSetTime(this.b, ikVar.d()) < 0) {
                                    MiPushClient.a(this.b, ikVar.d());
                                } else if (!TextUtils.isEmpty(ikVar.c()) && MiPushClient.topicSubscribedTime(this.b, ikVar.c()) < 0) {
                                    MiPushClient.e(this.b, ikVar.c());
                                }
                            }
                            String str5 = (idVar.f118a == null || idVar.f118a.m989a() == null) ? null : idVar.f118a.f83a.get("jobkey");
                            if (TextUtils.isEmpty(str5)) {
                                str5 = a4.m980a();
                            }
                            if (z || !b(this.b, str5)) {
                                MiPushMessage generateMessage = PushMessageHelper.generateMessage(ikVar, idVar.m1021a(), z);
                                if (generateMessage.getPassThrough() != 0 || z || !z.a(generateMessage.getExtra())) {
                                    b.m149a("receive a message, msgid=" + a4.m980a() + ", jobkey=" + str5);
                                    if (!z || generateMessage.getExtra() == null || !generateMessage.getExtra().containsKey("notify_effect")) {
                                        miPushMessage = generateMessage;
                                    } else {
                                        Map<String, String> extra = generateMessage.getExtra();
                                        String str6 = extra.get("notify_effect");
                                        if (z.a(idVar)) {
                                            Intent a5 = a(this.b, idVar.b, extra);
                                            a5.putExtra("eventMessageType", i);
                                            a5.putExtra("messageId", str);
                                            if (a5 == null) {
                                                b.m149a("Getting Intent fail from ignore reg message. ");
                                                ew.a(this.b).b(this.b.getPackageName(), ev.m896a(i), str, "Getting Intent fail from ignore reg message.");
                                                return null;
                                            }
                                            String c2 = a4.c();
                                            if (!TextUtils.isEmpty(c2)) {
                                                a5.putExtra(SchemaActivity.KEY_PAYLOAD, c2);
                                            }
                                            this.b.startActivity(a5);
                                            ew a6 = ew.a(this.b);
                                            String packageName = this.b.getPackageName();
                                            String a7 = ev.m896a(i);
                                            a6.a(packageName, a7, str, 3006, "business message is clicked typeId " + str6);
                                        } else {
                                            Context context = this.b;
                                            Intent a8 = a(context, context.getPackageName(), extra);
                                            if (a8 != null) {
                                                if (!str6.equals(ap.c)) {
                                                    a8.putExtra(PushMessageHelper.KEY_MESSAGE, generateMessage);
                                                    a8.putExtra("eventMessageType", i);
                                                    a8.putExtra("messageId", str);
                                                }
                                                this.b.startActivity(a8);
                                                b.m149a("start activity succ");
                                                ew a9 = ew.a(this.b);
                                                String packageName2 = this.b.getPackageName();
                                                String a10 = ev.m896a(i);
                                                a9.a(packageName2, a10, str, 1006, "notification message is clicked typeId " + str6);
                                                if (str6.equals(ap.c)) {
                                                    ew a11 = ew.a(this.b);
                                                    String packageName3 = this.b.getPackageName();
                                                    String a12 = ev.m896a(i);
                                                    a11.a(packageName3, a12, str, "try open web page typeId " + str6);
                                                }
                                            }
                                        }
                                        return null;
                                    }
                                } else {
                                    z.a(this.b, idVar, bArr);
                                    return null;
                                }
                            } else {
                                b.m149a("drop a duplicate message, key=" + str5);
                                ew a13 = ew.a(this.b);
                                String packageName4 = this.b.getPackageName();
                                String a14 = ev.m896a(i);
                                a13.c(packageName4, a14, str, "drop a duplicate message, key=" + str5);
                            }
                            if (idVar.m1021a() == null && !z) {
                                a(ikVar, idVar);
                                break;
                            }
                        } else {
                            b.d("receive an empty message without push content, drop it");
                            ew.a(this.b).b(this.b.getPackageName(), ev.m896a(i), str, "receive an empty message without push content, drop it");
                            return null;
                        }
                    } else {
                        b.m149a("receive a message in pause state. drop it");
                        ew.a(this.b).a(this.b.getPackageName(), ev.m896a(i), str, "receive a message in pause state. drop it");
                        return null;
                    }
                    break;
                case 2:
                    ii iiVar = (ii) a2;
                    String str7 = d.m727a(this.b).a;
                    if (TextUtils.isEmpty(str7) || !TextUtils.equals(str7, iiVar.m1049a())) {
                        b.m149a("bad Registration result:");
                        ew.a(this.b).b(this.b.getPackageName(), ev.m896a(i), str, "bad Registration result:");
                        return null;
                    }
                    d.m727a(this.b).a = null;
                    if (iiVar.f143a == 0) {
                        d.m727a(this.b).b(iiVar.e, iiVar.f, iiVar.l);
                        ewVar = ew.a(this.b);
                        str4 = this.b.getPackageName();
                        str3 = ev.m896a(i);
                        i2 = PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
                        str2 = "receive register result success";
                    } else {
                        ewVar = ew.a(this.b);
                        str4 = this.b.getPackageName();
                        str3 = ev.m896a(i);
                        i2 = PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
                        str2 = "receive register result fail";
                    }
                    ewVar.a(str4, str3, str, i2, str2);
                    if (!TextUtils.isEmpty(iiVar.e)) {
                        arrayList3 = new ArrayList();
                        arrayList3.add(iiVar.e);
                    }
                    MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage(fb.COMMAND_REGISTER.f26a, arrayList3, iiVar.f143a, iiVar.d, null);
                    ay.a(this.b).d();
                    return generateCommandMessage;
                case 3:
                    if (((io) a2).a == 0) {
                        d.m727a(this.b).m729a();
                        MiPushClient.clearExtras(this.b);
                    }
                    PushMessageHandler.a();
                    break;
                case 4:
                    im imVar = (im) a2;
                    if (imVar.a == 0) {
                        MiPushClient.e(this.b, imVar.a());
                    }
                    if (!TextUtils.isEmpty(imVar.a())) {
                        arrayList2 = new ArrayList();
                        arrayList2.add(imVar.a());
                    }
                    return PushMessageHelper.generateCommandMessage(fb.COMMAND_SUBSCRIBE_TOPIC.f26a, arrayList2, imVar.a, imVar.d, imVar.b());
                case 5:
                    iq iqVar = (iq) a2;
                    if (iqVar.a == 0) {
                        MiPushClient.f(this.b, iqVar.a());
                    }
                    if (!TextUtils.isEmpty(iqVar.a())) {
                        arrayList = new ArrayList();
                        arrayList.add(iqVar.a());
                    }
                    return PushMessageHelper.generateCommandMessage(fb.COMMAND_UNSUBSCRIBE_TOPIC.f26a, arrayList, iqVar.a, iqVar.d, iqVar.b());
                case 6:
                    db.a(this.b.getPackageName(), this.b, a2, hh.Command, bArr.length);
                    ic icVar = (ic) a2;
                    String a15 = icVar.a();
                    List<String> a16 = icVar.m1016a();
                    if (icVar.a == 0) {
                        if (TextUtils.equals(a15, fb.COMMAND_SET_ACCEPT_TIME.f26a) && a16 != null && a16.size() > 1) {
                            MiPushClient.a(this.b, a16.get(0), a16.get(1));
                            if (!"00:00".equals(a16.get(0)) || !"00:00".equals(a16.get(1))) {
                                d.m727a(this.b).a(false);
                            } else {
                                d.m727a(this.b).a(true);
                            }
                            a16 = a(TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault(), a16);
                        } else if (TextUtils.equals(a15, fb.COMMAND_SET_ALIAS.f26a) && a16 != null && a16.size() > 0) {
                            MiPushClient.a(this.b, a16.get(0));
                        } else if (TextUtils.equals(a15, fb.COMMAND_UNSET_ALIAS.f26a) && a16 != null && a16.size() > 0) {
                            MiPushClient.b(this.b, a16.get(0));
                        } else if (TextUtils.equals(a15, fb.COMMAND_SET_ACCOUNT.f26a) && a16 != null && a16.size() > 0) {
                            MiPushClient.c(this.b, a16.get(0));
                        } else if (TextUtils.equals(a15, fb.COMMAND_UNSET_ACCOUNT.f26a) && a16 != null && a16.size() > 0) {
                            MiPushClient.d(this.b, a16.get(0));
                        } else if (TextUtils.equals(a15, fb.COMMAND_CHK_VDEVID.f26a)) {
                            if (a16 != null && a16.size() > 0) {
                                i.a(this.b, a16.get(0));
                            }
                            return null;
                        }
                    }
                    return PushMessageHelper.generateCommandMessage(a15, a16, icVar.a, icVar.d, icVar.b());
                case 7:
                    db.a(this.b.getPackageName(), this.b, a2, hh.Notification, bArr.length);
                    if (!(a2 instanceof hy)) {
                        if (a2 instanceof ig) {
                            ig igVar = (ig) a2;
                            if (!"registration id expired".equalsIgnoreCase(igVar.d)) {
                                if ("client_info_update_ok".equalsIgnoreCase(igVar.d)) {
                                    if (igVar.m1036a() != null && igVar.m1036a().containsKey("app_version")) {
                                        d.m727a(this.b).m730a(igVar.m1036a().get("app_version"));
                                        break;
                                    }
                                } else if (hr.AwakeApp.f67a.equalsIgnoreCase(igVar.d)) {
                                    if (idVar.m1029b() && igVar.m1036a() != null && igVar.m1036a().containsKey("awake_info")) {
                                        Context context2 = this.b;
                                        p.a(context2, d.m727a(context2).m728a(), ag.a(this.b).a(hm.AwakeInfoUploadWaySwitch.a(), 0), igVar.m1036a().get("awake_info"));
                                        break;
                                    }
                                } else {
                                    try {
                                        if (hr.NormalClientConfigUpdate.f67a.equalsIgnoreCase(igVar.d)) {
                                            Cif ifVar = new Cif();
                                            ir.a(ifVar, igVar.m1041a());
                                            ah.a(ag.a(this.b), ifVar);
                                        } else if (!hr.CustomClientConfigUpdate.f67a.equalsIgnoreCase(igVar.d)) {
                                            if (!hr.SyncInfoResult.f67a.equalsIgnoreCase(igVar.d)) {
                                                if (!hr.ForceSync.f67a.equalsIgnoreCase(igVar.d)) {
                                                    if (hr.CancelPushMessage.f67a.equals(igVar.d)) {
                                                        if (igVar.m1036a() != null) {
                                                            int i3 = -2;
                                                            if (igVar.m1036a().containsKey(ap.J)) {
                                                                String str8 = igVar.m1036a().get(ap.J);
                                                                if (!TextUtils.isEmpty(str8)) {
                                                                    try {
                                                                        i3 = Integer.parseInt(str8);
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                            if (i3 >= -1) {
                                                                MiPushClient.clearNotification(this.b, i3);
                                                                break;
                                                            } else {
                                                                String str9 = "";
                                                                String str10 = "";
                                                                if (igVar.m1036a().containsKey(ap.H)) {
                                                                    str9 = igVar.m1036a().get(ap.H);
                                                                }
                                                                if (igVar.m1036a().containsKey(ap.I)) {
                                                                    str10 = igVar.m1036a().get(ap.I);
                                                                }
                                                                MiPushClient.clearNotification(this.b, str9, str10);
                                                                break;
                                                            }
                                                        }
                                                    } else if (hr.HybridRegisterResult.f67a.equals(igVar.d)) {
                                                        ii iiVar2 = new ii();
                                                        ir.a(iiVar2, igVar.m1041a());
                                                        MiPushClient4Hybrid.onReceiveRegisterResult(this.b, iiVar2);
                                                    } else if (!hr.HybridUnregisterResult.f67a.equals(igVar.d)) {
                                                        if (hr.PushLogUpload.f67a.equals(igVar.d) && igVar.m1036a() != null && igVar.m1036a().containsKey("packages")) {
                                                            String[] split = igVar.m1036a().get("packages").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                                                            if (TextUtils.equals(this.b.getPackageName(), "com.xiaomi.xmsf")) {
                                                                Logger.uploadLogFile(this.b, true);
                                                                a(this.b, split);
                                                                break;
                                                            } else {
                                                                Logger.uploadLogFile(this.b, false);
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        io ioVar = new io();
                                                        ir.a(ioVar, igVar.m1041a());
                                                        MiPushClient4Hybrid.onReceiveUnregisterResult(this.b, ioVar);
                                                    }
                                                } else {
                                                    b.m149a("receive force sync notification");
                                                    be.a(this.b, false);
                                                    break;
                                                }
                                            } else {
                                                be.a(this.b, igVar);
                                                break;
                                            }
                                        } else {
                                            ie ieVar = new ie();
                                            ir.a(ieVar, igVar.m1041a());
                                            ah.a(ag.a(this.b), ieVar);
                                        }
                                        break;
                                    } catch (ix e2) {
                                        b.a(e2);
                                        break;
                                    }
                                }
                            } else {
                                List<String> allAlias = MiPushClient.getAllAlias(this.b);
                                List<String> allTopic = MiPushClient.getAllTopic(this.b);
                                List<String> allUserAccount = MiPushClient.getAllUserAccount(this.b);
                                String acceptTime = MiPushClient.getAcceptTime(this.b);
                                MiPushClient.a(this.b, hv.RegIdExpired);
                                for (String str11 : allAlias) {
                                    MiPushClient.b(this.b, str11);
                                    MiPushClient.setAlias(this.b, str11, null);
                                }
                                for (String str12 : allTopic) {
                                    MiPushClient.f(this.b, str12);
                                    MiPushClient.subscribe(this.b, str12, null);
                                }
                                for (String str13 : allUserAccount) {
                                    MiPushClient.d(this.b, str13);
                                    MiPushClient.setUserAccount(this.b, str13, null);
                                }
                                String[] split2 = acceptTime.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                                if (split2.length == 2) {
                                    MiPushClient.d(this.b);
                                    MiPushClient.a(this.b, split2[0], split2[1]);
                                    break;
                                }
                            }
                        }
                    } else {
                        hy hyVar = (hy) a2;
                        String a17 = hyVar.a();
                        if (hr.DisablePushMessage.f67a.equalsIgnoreCase(hyVar.d)) {
                            if (hyVar.a != 0) {
                                if ("syncing".equals(ao.a(this.b).a(bd.DISABLE_PUSH))) {
                                    synchronized (ao.class) {
                                        if (ao.a(this.b).m723a(a17)) {
                                            if (ao.a(this.b).a(a17) < 10) {
                                                ao.a(this.b).b(a17);
                                                ay.a(this.b).a(true, a17);
                                            } else {
                                                ao.a(this.b).c(a17);
                                            }
                                        }
                                    }
                                    break;
                                }
                            } else {
                                synchronized (ao.class) {
                                    if (ao.a(this.b).m723a(a17)) {
                                        ao.a(this.b).c(a17);
                                        if ("syncing".equals(ao.a(this.b).a(bd.DISABLE_PUSH))) {
                                            ao.a(this.b).a(bd.DISABLE_PUSH, "synced");
                                            MiPushClient.clearNotification(this.b);
                                            MiPushClient.clearLocalNotificationType(this.b);
                                            PushMessageHandler.a();
                                            ay.a(this.b).b();
                                        }
                                    }
                                }
                                break;
                            }
                        } else if (hr.EnablePushMessage.f67a.equalsIgnoreCase(hyVar.d)) {
                            if (hyVar.a != 0) {
                                if ("syncing".equals(ao.a(this.b).a(bd.ENABLE_PUSH))) {
                                    synchronized (ao.class) {
                                        if (ao.a(this.b).m723a(a17)) {
                                            if (ao.a(this.b).a(a17) < 10) {
                                                ao.a(this.b).b(a17);
                                                ay.a(this.b).a(false, a17);
                                            } else {
                                                ao.a(this.b).c(a17);
                                            }
                                        }
                                    }
                                    break;
                                }
                            } else {
                                synchronized (ao.class) {
                                    if (ao.a(this.b).m723a(a17)) {
                                        ao.a(this.b).c(a17);
                                        if ("syncing".equals(ao.a(this.b).a(bd.ENABLE_PUSH))) {
                                            ao.a(this.b).a(bd.ENABLE_PUSH, "synced");
                                        }
                                    }
                                }
                                break;
                            }
                        } else if (!hr.ThirdPartyRegUpdate.f67a.equalsIgnoreCase(hyVar.d)) {
                            if (hr.UploadTinyData.f67a.equalsIgnoreCase(hyVar.d)) {
                                a(hyVar);
                                break;
                            }
                        } else {
                            b(hyVar);
                            break;
                        }
                        ao.a(this.b).c(a17);
                        break;
                    }
                    break;
            }
            return miPushMessage;
        } catch (v e3) {
            b.a(e3);
            a(idVar);
            ew.a(this.b).a(this.b.getPackageName(), ev.m896a(i), str, e3);
            return null;
        } catch (ix e4) {
            b.a(e4);
            b.d("receive a message which action string is not valid. is the reg expired?");
            ew.a(this.b).a(this.b.getPackageName(), ev.m896a(i), str, e4);
            return null;
        }
    }

    private PushMessageHandler.a a(id idVar, byte[] bArr) {
        String str;
        is a2;
        String str2 = null;
        try {
            a2 = ar.a(this.b, idVar);
        } catch (v e) {
            b.a(e);
            str = "message arrived: receive a message but decrypt failed. report when click.";
        } catch (ix e2) {
            b.a(e2);
            str = "message arrived: receive a message which action string is not valid. is the reg expired?";
        }
        if (a2 == null) {
            b.d("message arrived: receiving an un-recognized message. " + idVar.a);
            return null;
        }
        hh a3 = idVar.a();
        b.m149a("message arrived: processing an arrived message, action=" + a3);
        if (ag.a[a3.ordinal()] != 1) {
            return null;
        }
        ik ikVar = (ik) a2;
        ht a4 = ikVar.a();
        if (a4 == null) {
            str = "message arrived: receive an empty message without push content, drop it";
            b.d(str);
            return null;
        }
        if (!(idVar.f118a == null || idVar.f118a.m989a() == null)) {
            str2 = idVar.f118a.f83a.get("jobkey");
        }
        MiPushMessage generateMessage = PushMessageHelper.generateMessage(ikVar, idVar.m1021a(), false);
        generateMessage.setArrivedMessage(true);
        b.m149a("message arrived: receive a message, msgid=" + a4.m980a() + ", jobkey=" + str2);
        return generateMessage;
    }

    public static av a(Context context) {
        if (a == null) {
            a = new av(context);
        }
        return a;
    }

    private void a() {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - sharedPreferences.getLong(Constants.SP_KEY_LAST_REINITIALIZE, 0L)) > 1800000) {
            MiPushClient.a(this.b, hv.PackageUnregistered);
            sharedPreferences.edit().putLong(Constants.SP_KEY_LAST_REINITIALIZE, currentTimeMillis).commit();
        }
    }

    public void a(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.SYNC_LOG");
                        PushMessageHandler.a(context, intent);
                        return;
                    } catch (Throwable unused) {
                        return;
                    }
                }
            }
        }
    }

    public static void a(Context context, String str) {
        synchronized (d) {
            c.remove(str);
            d.m727a(context);
            SharedPreferences a2 = d.a(context);
            String a3 = az.a(c, Constants.ACCEPT_TIME_SEPARATOR_SP);
            SharedPreferences.Editor edit = a2.edit();
            edit.putString("pref_msg_ids", a3);
            s.a(edit);
        }
    }

    private void a(Context context, String[] strArr) {
        aj.a(context).a(new af(this, strArr, context));
    }

    private void a(hy hyVar) {
        String a2 = hyVar.a();
        b.b("receive ack " + a2);
        Map<String, String> a3 = hyVar.m1003a();
        if (a3 != null) {
            String str = a3.get("real_source");
            if (!TextUtils.isEmpty(str)) {
                b.b("receive ack : messageId = " + a2 + "  realSource = " + str);
                bl.a(this.b).a(a2, str, Boolean.valueOf(hyVar.a == 0));
            }
        }
    }

    private void a(id idVar) {
        b.m149a("receive a message but decrypt failed. report now.");
        ig igVar = new ig(idVar.m1021a().f81a, false);
        igVar.c(hr.DecryptMessageFail.f67a);
        igVar.b(idVar.m1022a());
        igVar.d(idVar.b);
        igVar.f129a = new HashMap();
        igVar.f129a.put("regid", MiPushClient.getRegId(this.b));
        ay.a(this.b).a((ay) igVar, hh.Notification, false, (hu) null);
    }

    private void a(ik ikVar, id idVar) {
        hu a2 = idVar.m1021a();
        hx hxVar = new hx();
        hxVar.b(ikVar.b());
        hxVar.a(ikVar.m1056a());
        hxVar.a(ikVar.a().a());
        if (!TextUtils.isEmpty(ikVar.c())) {
            hxVar.c(ikVar.c());
        }
        if (!TextUtils.isEmpty(ikVar.d())) {
            hxVar.d(ikVar.d());
        }
        hxVar.a(ir.a(this.b, idVar));
        ay.a(this.b).a((ay) hxVar, hh.AckMessage, a2);
    }

    private void a(String str, long j, f fVar) {
        bd a2 = m.a(fVar);
        if (a2 != null) {
            if (j == 0) {
                synchronized (ao.class) {
                    if (ao.a(this.b).m723a(str)) {
                        ao.a(this.b).c(str);
                        if ("syncing".equals(ao.a(this.b).a(a2))) {
                            ao.a(this.b).a(a2, "synced");
                        }
                    }
                }
            } else if ("syncing".equals(ao.a(this.b).a(a2))) {
                synchronized (ao.class) {
                    if (ao.a(this.b).m723a(str)) {
                        if (ao.a(this.b).a(str) < 10) {
                            ao.a(this.b).b(str);
                            ay.a(this.b).a(str, a2, fVar);
                        } else {
                            ao.a(this.b).c(str);
                        }
                    }
                }
            } else {
                ao.a(this.b).c(str);
            }
        }
    }

    private void b(hy hyVar) {
        long j;
        f fVar;
        b.c("ASSEMBLE_PUSH : " + hyVar.toString());
        String a2 = hyVar.a();
        Map<String, String> a3 = hyVar.m1003a();
        if (a3 != null) {
            String str = a3.get(Constants.ASSEMBLE_PUSH_REG_INFO);
            if (!TextUtils.isEmpty(str)) {
                if (str.contains("brand:" + ap.FCM.name())) {
                    b.m149a("ASSEMBLE_PUSH : receive fcm token sync ack");
                    j.b(this.b, f.ASSEMBLE_PUSH_FCM, str);
                    j = hyVar.a;
                    fVar = f.ASSEMBLE_PUSH_FCM;
                } else {
                    if (str.contains("brand:" + ap.HUAWEI.name())) {
                        b.m149a("ASSEMBLE_PUSH : receive hw token sync ack");
                        j.b(this.b, f.ASSEMBLE_PUSH_HUAWEI, str);
                        j = hyVar.a;
                        fVar = f.ASSEMBLE_PUSH_HUAWEI;
                    } else {
                        if (str.contains("brand:" + ap.OPPO.name())) {
                            b.m149a("ASSEMBLE_PUSH : receive COS token sync ack");
                            j.b(this.b, f.ASSEMBLE_PUSH_COS, str);
                            j = hyVar.a;
                            fVar = f.ASSEMBLE_PUSH_COS;
                        } else {
                            if (str.contains("brand:" + ap.VIVO.name())) {
                                b.m149a("ASSEMBLE_PUSH : receive FTOS token sync ack");
                                j.b(this.b, f.ASSEMBLE_PUSH_FTOS, str);
                                j = hyVar.a;
                                fVar = f.ASSEMBLE_PUSH_FTOS;
                            } else {
                                return;
                            }
                        }
                    }
                }
                a(a2, j, fVar);
            }
        }
    }

    private void b(id idVar) {
        hu a2 = idVar.m1021a();
        hx hxVar = new hx();
        hxVar.b(idVar.m1022a());
        hxVar.a(a2.m988a());
        hxVar.a(a2.m986a());
        if (!TextUtils.isEmpty(a2.m993b())) {
            hxVar.c(a2.m993b());
        }
        hxVar.a(ir.a(this.b, idVar));
        ay.a(this.b).a((ay) hxVar, hh.AckMessage, false, idVar.m1021a());
    }

    private static boolean b(Context context, String str) {
        synchronized (d) {
            d.m727a(context);
            SharedPreferences a2 = d.a(context);
            if (c == null) {
                String[] split = a2.getString("pref_msg_ids", "").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                c = new LinkedList();
                for (String str2 : split) {
                    c.add(str2);
                }
            }
            if (c.contains(str)) {
                return true;
            }
            c.add(str);
            if (c.size() > 25) {
                c.poll();
            }
            String a3 = az.a(c, Constants.ACCEPT_TIME_SEPARATOR_SP);
            SharedPreferences.Editor edit = a2.edit();
            edit.putString("pref_msg_ids", a3);
            s.a(edit);
            return false;
        }
    }

    private boolean c(id idVar) {
        if (!TextUtils.equals(Constants.HYBRID_PACKAGE_NAME, idVar.b()) && !TextUtils.equals(Constants.HYBRID_DEBUG_PACKAGE_NAME, idVar.b())) {
            return false;
        }
        Map<String, String> a2 = idVar.m1021a() == null ? null : idVar.m1021a().m989a();
        if (a2 == null) {
            return false;
        }
        String str = a2.get(Constants.EXTRA_KEY_PUSH_SERVER_ACTION);
        return TextUtils.equals(str, Constants.EXTRA_VALUE_HYBRID_MESSAGE) || TextUtils.equals(str, Constants.EXTRA_VALUE_PLATFORM_MESSAGE);
    }

    public PushMessageHandler.a a(Intent intent) {
        String str;
        String action = intent.getAction();
        b.m149a("receive an intent from server, action=" + action);
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        String stringExtra2 = intent.getStringExtra("messageId");
        int intExtra = intent.getIntExtra("eventMessageType", -1);
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            if (byteArrayExtra == null) {
                b.d("receiving an empty message, drop");
                ew.a(this.b).a(this.b.getPackageName(), intent, "receiving an empty message, drop");
                return null;
            }
            id idVar = new id();
            try {
                ir.a(idVar, byteArrayExtra);
                d a2 = d.m727a(this.b);
                hu a3 = idVar.m1021a();
                if (idVar.a() == hh.SendMessage && a3 != null && !a2.m736d() && !booleanExtra) {
                    a3.a("mrt", stringExtra);
                    a3.a("mat", Long.toString(System.currentTimeMillis()));
                    if (!c(idVar)) {
                        b(idVar);
                    } else {
                        b.b("this is a mina's message, ack later");
                        a3.a(Constants.EXTRA_KEY_HYBRID_MESSAGE_TS, String.valueOf(a3.m986a()));
                        a3.a(Constants.EXTRA_KEY_HYBRID_DEVICE_STATUS, String.valueOf((int) ir.a(this.b, idVar)));
                    }
                }
                if (idVar.a() == hh.SendMessage && !idVar.m1029b()) {
                    if (!z.a(idVar)) {
                        Object[] objArr = new Object[2];
                        objArr[0] = idVar.b();
                        objArr[1] = a3 != null ? a3.m988a() : "";
                        b.m149a(String.format("drop an un-encrypted messages. %1$s, %2$s", objArr));
                        ew a4 = ew.a(this.b);
                        String packageName = this.b.getPackageName();
                        Object[] objArr2 = new Object[2];
                        objArr2[0] = idVar.b();
                        objArr2[1] = a3 != null ? a3.m988a() : "";
                        a4.a(packageName, intent, String.format("drop an un-encrypted messages. %1$s, %2$s", objArr2));
                        return null;
                    } else if (!booleanExtra || a3.m989a() == null || !a3.m989a().containsKey("notify_effect")) {
                        b.m149a(String.format("drop an un-encrypted messages. %1$s, %2$s", idVar.b(), a3.m988a()));
                        ew a5 = ew.a(this.b);
                        String packageName2 = this.b.getPackageName();
                        Object[] objArr3 = new Object[2];
                        objArr3[0] = idVar.b();
                        objArr3[1] = a3 != null ? a3.m988a() : "";
                        a5.a(packageName2, intent, String.format("drop an un-encrypted messages. %1$s, %2$s", objArr3));
                        return null;
                    }
                }
                if (a2.m735c() || idVar.a == hh.Registration) {
                    if (!a2.m735c() || !a2.m737e()) {
                        return a(idVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra);
                    }
                    if (idVar.a == hh.UnRegistration) {
                        a2.m729a();
                        MiPushClient.clearExtras(this.b);
                        PushMessageHandler.a();
                    } else {
                        MiPushClient.unregisterPush(this.b);
                    }
                } else if (z.a(idVar)) {
                    return a(idVar, booleanExtra, byteArrayExtra, stringExtra2, intExtra);
                } else {
                    b.d("receive message without registration. need re-register!");
                    ew.a(this.b).a(this.b.getPackageName(), intent, "receive message without registration. need re-register!");
                    a();
                }
            } catch (ix | Exception e) {
                ew.a(this.b).a(this.b.getPackageName(), intent, e);
                b.a(e);
            }
        } else if ("com.xiaomi.mipush.ERROR".equals(action)) {
            MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
            id idVar2 = new id();
            try {
                byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                if (byteArrayExtra2 != null) {
                    ir.a(idVar2, byteArrayExtra2);
                }
            } catch (ix unused) {
            }
            miPushCommandMessage.setCommand(String.valueOf(idVar2.a()));
            miPushCommandMessage.setResultCode(intent.getIntExtra("mipush_error_code", 0));
            miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
            b.d("receive a error message. code = " + intent.getIntExtra("mipush_error_code", 0) + ", msg= " + intent.getStringExtra("mipush_error_msg"));
            return miPushCommandMessage;
        } else if ("com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
            byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra3 == null) {
                b.d("message arrived: receiving an empty message, drop");
                return null;
            }
            id idVar3 = new id();
            try {
                ir.a(idVar3, byteArrayExtra3);
                d a6 = d.m727a(this.b);
                if (z.a(idVar3)) {
                    str = "message arrived: receive ignore reg message, ignore!";
                } else if (!a6.m735c()) {
                    str = "message arrived: receive message without registration. need unregister or re-register!";
                } else if (!a6.m735c() || !a6.m737e()) {
                    return a(idVar3, byteArrayExtra3);
                } else {
                    str = "message arrived: app info is invalidated";
                }
                b.d(str);
            } catch (ix | Exception e2) {
                b.a(e2);
            }
        }
        return null;
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) {
        if (timeZone.equals(timeZone2)) {
            return list;
        }
        long rawOffset = ((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60;
        long parseLong = Long.parseLong(list.get(0).split(Constants.COLON_SEPARATOR)[0]);
        long parseLong2 = Long.parseLong(list.get(0).split(Constants.COLON_SEPARATOR)[1]);
        long parseLong3 = Long.parseLong(list.get(1).split(Constants.COLON_SEPARATOR)[0]);
        long parseLong4 = Long.parseLong(list.get(1).split(Constants.COLON_SEPARATOR)[1]);
        long j = ((((parseLong * 60) + parseLong2) - rawOffset) + DateTimeHelper.sDayInMinutes) % DateTimeHelper.sDayInMinutes;
        long j2 = ((((parseLong3 * 60) + parseLong4) - rawOffset) + DateTimeHelper.sDayInMinutes) % DateTimeHelper.sDayInMinutes;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j / 60), Long.valueOf(j % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        return arrayList;
    }
}
