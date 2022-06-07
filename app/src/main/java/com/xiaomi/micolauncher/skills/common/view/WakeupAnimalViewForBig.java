package com.xiaomi.micolauncher.skills.common.view;

import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.common.WakeupHelper;
import com.xiaomi.micolauncher.skills.common.view.wakeup.FrameSurfaceView;
import com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class WakeupAnimalViewForBig implements WakeupAnimalHelper {
    public static final String LOG_TAG = "WakeupAnimalView";
    private FrameSurfaceView a;
    private List<String> b;
    private List<String> c;
    private List<String> d;
    private List<String> e;
    private List<String> f;
    private List<String> g;
    private List<String> h;
    private WakeupHelper.WakeupStatus i;
    private Runnable j = new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.view.WakeupAnimalViewForBig.1
        @Override // java.lang.Runnable
        public void run() {
            switch (AnonymousClass2.a[WakeupAnimalViewForBig.this.i.ordinal()]) {
                case 1:
                    WakeupAnimalViewForBig.this.stepListening();
                    return;
                case 2:
                    WakeupAnimalViewForBig.this.stepSpeaking();
                    return;
                case 3:
                    WakeupAnimalViewForBig.this.stepLoading();
                    return;
                case 4:
                    WakeupAnimalViewForBig.this.stepTts();
                    return;
                default:
                    return;
            }
        }
    };

    public WakeupAnimalViewForBig(FrameSurfaceView frameSurfaceView) {
        this.a = frameSurfaceView;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void init(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.b = new ArrayList();
        while (i < i2) {
            this.b.add("wakeup/huanxing_0000" + i + ".webp");
            i++;
        }
        this.c = new ArrayList();
        while (i2 < i3) {
            this.c.add("wakeup/huanxing_000" + i2 + ".webp");
            i2++;
        }
        this.d = new ArrayList();
        while (i3 < i4) {
            this.d.add("wakeup/" + (i3 < 100 ? "huanxing_000" + i3 : "huanxing_00" + i3) + ".webp");
            i3++;
        }
        this.e = new ArrayList();
        while (i4 < i5) {
            this.e.add("wakeup/huanxing_00" + i4 + ".webp");
            i4++;
        }
        this.f = new ArrayList();
        while (i5 < i6) {
            this.f.add("wakeup/huanxing_00" + i5 + ".webp");
            i5++;
        }
        this.g = new ArrayList();
        for (int i9 = i6; i9 < i7; i9++) {
            this.g.add("wakeup/huanxing_00" + i9 + ".webp");
        }
        this.h = new ArrayList();
        while (i6 < i8) {
            this.h.add("wakeup/huanxing_00" + i6 + ".webp");
            i6++;
        }
        this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_INIT;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepInit() {
        L.speech.d("WakeupAnimalViewstepInit");
        this.a.removeCallbacks(this.j);
        this.a.setbitmapNames(this.b);
        this.a.setDuration(ContainerUtil.getSize(this.b) * 40);
        this.a.setRepeatTimes(1);
        this.a.start();
        this.a.postDelayed(this.j, ContainerUtil.getSize(this.b) * 40);
        this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_LISTENING;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepListening() {
        L.speech.d("WakeupAnimalViewstepListening");
        this.a.setbitmapNames(this.c);
        this.a.setRepeatTimes(-1);
        this.a.start();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepListeningTransToSpeaking() {
        L.speech.d("WakeupAnimalViewstepListeningTransToSpeaking");
        if (this.i == WakeupHelper.WakeupStatus.WAKEUP_STATUS_LISTENING) {
            this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING;
            this.a.removeCallbacks(this.j);
            this.a.setbitmapNames(this.d);
            this.a.setRepeatTimes(1);
            this.a.setDuration(ContainerUtil.getSize(this.d) * 40);
            this.a.start();
            this.a.postDelayed(this.j, ContainerUtil.getSize(this.d) * 40);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepSpeaking() {
        L.speech.d("WakeupAnimalViewstepSpeaking");
        if (this.i != WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING) {
            this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING;
            this.a.setbitmapNames(this.e);
            this.a.setRepeatTimes(-1);
            this.a.start();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepTts() {
        L.speech.d("WakeupAnimalViewstepTts");
        this.a.setbitmapNames(this.e);
        this.a.setRepeatTimes(-1);
        this.a.start();
        this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_TTS;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepSpeakingTransToLoading() {
        L.speech.d("WakeupAnimalViewstepSpeakingTransToLoading");
        if (this.i == WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING) {
            this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_LOADING;
            this.a.removeCallbacks(this.j);
            this.a.setbitmapNames(this.f);
            this.a.setRepeatTimes(1);
            this.a.setDuration(ContainerUtil.getSize(this.f) * 40);
            this.a.start();
            this.a.postDelayed(this.j, ContainerUtil.getSize(this.f) * 40);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepLoading() {
        this.a.setbitmapNames(this.g);
        this.a.setRepeatTimes(-1);
        this.a.start();
        L.speech.d("WakeupAnimalViewstepLoading");
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepLoadingTransToSpeaking() {
        Logger logger = L.speech;
        logger.d("WakeupAnimalViewstepLoadingTransToSpeaking: mstatus=" + this.i);
        if (WakeupHelper.WakeupStatus.WAKEUP_STATUS_LOADING == this.i) {
            this.a.setbitmapNames(this.h);
            this.a.setRepeatTimes(1);
            this.a.setDuration(ContainerUtil.getSize(this.h) * 40);
            this.a.start();
        }
        this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_TTS;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepStop() {
        L.speech.d("WakeupAnimalViewstepStop");
        if (this.i != WakeupHelper.WakeupStatus.WAKEUP_STATUS_STOP) {
            this.a.destroy();
            this.i = WakeupHelper.WakeupStatus.WAKEUP_STATUS_STOP;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public WakeupHelper.WakeupStatus getStatus() {
        return this.i;
    }

    /* renamed from: com.xiaomi.micolauncher.skills.common.view.WakeupAnimalViewForBig$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[WakeupHelper.WakeupStatus.values().length];

        static {
            try {
                a[WakeupHelper.WakeupStatus.WAKEUP_STATUS_LISTENING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[WakeupHelper.WakeupStatus.WAKEUP_STATUS_LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[WakeupHelper.WakeupStatus.WAKEUP_STATUS_TTS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
