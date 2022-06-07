package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.player.WakeupPlayer;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class SoundToneManager {
    private static volatile ToneType a;

    /* loaded from: classes3.dex */
    public enum ToneType {
        Default("AiNiRobot", "蜜糖(甜美可爱女声)", "", "", 1),
        XiaoMi("XiaoMi", "茉莉(温柔知性女声)", "_xiaomi", "", 2),
        XiaoMi_M88("XiaoMi_M88", "青葱(阳光活力男声)", "_xiaomi_m88", "", 3),
        XiaoMi_xinran("XiaoMi", "泡芙(奶萌乖巧童声)", "_xiaomi_xinran", "xinran", 4);
        
        private String nameEn;
        private String nameRaw;
        private String nameZh;
        private int toneOrder;
        private String vendor;

        ToneType(String str, String str2, String str3, String str4, int i) {
            this.vendor = str;
            this.nameZh = str2;
            this.nameRaw = str3;
            this.nameEn = str4;
            this.toneOrder = i;
        }

        public String getNameZh() {
            return this.nameZh;
        }

        public String getNameEn() {
            return this.nameEn;
        }

        public String getVendor() {
            return this.vendor;
        }

        public int getToneOrder() {
            return this.toneOrder;
        }
    }

    private static void a() {
        try {
            a = ToneType.valueOf(SystemSetting.getTtsVendor(ToneType.Default.name()));
        } catch (IllegalArgumentException unused) {
            a = ToneType.Default;
        }
    }

    public static int getToneRawId(int i, Context context) {
        ToneType toneType;
        int i2;
        Field[] declaredFields = R.raw.class.getDeclaredFields();
        String str = "";
        if (a == null) {
            a();
        }
        if (!ChildModeManager.getManager().isChildMode() || i == R.raw.tts_vendor_demo) {
            toneType = a;
        } else {
            toneType = ToneType.XiaoMi_xinran;
        }
        if (toneType == ToneType.Default) {
            return i;
        }
        for (Field field : declaredFields) {
            try {
                i2 = field.getInt(null);
            } catch (IllegalAccessException e) {
                L.base.e("%s filed %s exception=%s", "[SoundToneManager]:", field, e);
            }
            if (i2 == i) {
                str = field.getName();
                L.player.d("%s found raw file: fieldName=%s, fileInt=%s", "[SoundToneManager]:", str, Integer.valueOf(i2));
                break;
            }
            continue;
        }
        if (TextUtils.isEmpty(str)) {
            L.player.d("%s can not found raw file, may be error", "[SoundToneManager]:");
            return i;
        }
        if (a == null) {
            a();
        }
        String str2 = str + toneType.nameRaw;
        int identifier = context.getResources().getIdentifier(str2, "raw", context.getPackageName());
        if (identifier != 0) {
            L.player.d("%s found raw file %s by tone type %s", "[SoundToneManager]:", str2, toneType.getNameZh());
            return identifier;
        }
        L.player.d("%s not found raw file %s by tone type %s, use default file", "[SoundToneManager]:", str2, toneType.getNameZh());
        return i;
    }

    private static void a(final Context context, final boolean z) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$SoundToneManager$zymlGBhVgI9pN0ATVF5JAAd3kjo
            @Override // java.lang.Runnable
            public final void run() {
                SoundToneManager.a(z, context);
            }
        });
    }

    public static /* synthetic */ void a(boolean z, Context context) {
        if (z) {
            L.monitor.d("%s dialog_id=%s,player_name=PromptSoundPlayer,action=play,ToneTypeChange.event=play.type=%s", "[SoundToneManager]:", SpeechManager.getInstance().getDialogId(), a.getNameZh());
            PromptSoundPlayer.getInstance().play(context, R.raw.tts_vendor_demo);
        }
        SystemSetting.setTtsVendor(a.name());
        WakeupPlayer.onToneVendorChanged(context);
    }

    public static void onToneTypeChanged(String str, Context context, boolean z) {
        if (!a.name().equals(str)) {
            a = ToneType.valueOf(str);
            if (a == null) {
                L.base.e("%s onToneTypeChanged, can not found type by name %s", str);
                a = ToneType.Default;
            }
            a(context, z);
        } else if (z) {
            L.monitor.d("%s dialog_id=%s,player_name=PromptSoundPlayer,action=play,ToneTypeChange.event=play.type=%s", "[SoundToneManager]:", SpeechManager.getInstance().getDialogId(), a.getNameZh());
            PromptSoundPlayer.getInstance().play(context, R.raw.tts_vendor_demo);
        }
    }

    public static void onToneTypeChanged(String str, Context context) {
        onToneTypeChanged(str, context, true);
    }

    public static String getToneVendor() {
        if (a == null) {
            a();
        }
        if (ChildModeManager.getManager().isChildMode()) {
            return ToneType.XiaoMi_xinran.getVendor();
        }
        return a.getVendor();
    }

    public static String getToneName() {
        if (a == null) {
            a();
        }
        if (ChildModeManager.getManager().isChildMode()) {
            return ToneType.XiaoMi_xinran.getNameEn();
        }
        return a.getNameEn();
    }

    public static String getCurrentTone() {
        if (a == null) {
            a();
        }
        return a.name();
    }

    public static int getCurrentToneOrder() {
        if (a == null) {
            a();
        }
        return a.getToneOrder();
    }
}
