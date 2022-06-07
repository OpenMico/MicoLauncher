package com.xiaomi.micolauncher.common.instruction;

import com.xiaomi.mico.base.utils.Cache;
import com.xiaomi.mico.base.utils.ScheduleRefreshManager;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.application.ScheduleKey;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class InstructionManager {
    private List<MainScreen.InstructionDetail> a;

    /* loaded from: classes3.dex */
    private static class a {
        private static final InstructionManager a = new InstructionManager();
    }

    private InstructionManager() {
        ScheduleRefreshManager.getInstance().start(ScheduleKey.INSTRUCTION);
    }

    public static InstructionManager getInstance() {
        return a.a;
    }

    private void a(List<MainScreen.InstructionDetail> list) {
        synchronized (this) {
            this.a = list;
        }
        Cache.put("instruction_key", list);
        EventBusRegistry.getEventBus().post(new InstructionUpdateEvent());
    }

    public void loadDataFromCache() {
        List<MainScreen.InstructionDetail> list;
        Object serializableObject = Cache.getSerializableObject("instruction_key");
        if (serializableObject != null) {
            try {
                list = (List) serializableObject;
            } catch (ClassCastException e) {
                ArrayList arrayList = new ArrayList();
                L.base.e("failed to cast", e);
                list = arrayList;
            }
        } else {
            list = new ArrayList<>();
        }
        synchronized (this) {
            this.a = list;
        }
    }

    public void refreshData() {
        if (ScheduleRefreshManager.getInstance().shouldRefresh(ScheduleKey.INSTRUCTION)) {
            ApiManager.displayService.getInstructions().subscribeOn(MicoSchedulers.mainThread()).map(new Function() { // from class: com.xiaomi.micolauncher.common.instruction.-$$Lambda$InstructionManager$IF8yr6vv0qRM_jxBFOmZdBtw6uY
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    List b;
                    b = InstructionManager.this.b((List) obj);
                    return b;
                }
            }).subscribe(new DefaultObserver<List<MainScreen.InstructionDetail>>() { // from class: com.xiaomi.micolauncher.common.instruction.InstructionManager.1
                /* renamed from: a */
                public void onSuccess(List<MainScreen.InstructionDetail> list) {
                    ScheduleRefreshManager.getInstance().setRefreshed(ScheduleKey.INSTRUCTION);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List b(List list) throws Exception {
        a(list);
        return null;
    }

    private Observable<List<MainScreen.InstructionItem>> a(final String str, final String str2, final String str3) {
        List<MainScreen.InstructionItem> b = b(str, str2, str3);
        if (!ContainerUtil.isEmpty(b)) {
            return Observable.just(b);
        }
        return ApiManager.displayService.getInstructions().flatMap(new Function() { // from class: com.xiaomi.micolauncher.common.instruction.-$$Lambda$InstructionManager$MHXFAQSUwxZ6FqLkoWn33omRpoo
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = InstructionManager.this.a(str, str2, str3, (List) obj);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(String str, String str2, String str3, List list) throws Exception {
        a(list);
        ScheduleRefreshManager.getInstance().setRefreshed(ScheduleKey.INSTRUCTION);
        List<MainScreen.InstructionItem> b = b(str, str2, str3);
        if (!ContainerUtil.isEmpty(b)) {
            return Observable.just(b);
        }
        return Observable.empty();
    }

    private List<MainScreen.InstructionItem> b(String str, String str2, String str3) {
        List<MainScreen.InstructionDetail> list;
        if (str == null || str2 == null) {
            return null;
        }
        if (this.a == null) {
            loadDataFromCache();
        }
        synchronized (this) {
            list = this.a;
        }
        if (list == null) {
            return null;
        }
        for (MainScreen.InstructionDetail instructionDetail : list) {
            if (str.equalsIgnoreCase(instructionDetail.feature) && instructionDetail.pages != null) {
                for (MainScreen.InstructionPage instructionPage : instructionDetail.pages) {
                    if (instructionPage.pageType.equalsIgnoreCase(str2) && instructionPage.status != null) {
                        for (MainScreen.InstructionPageStatus instructionPageStatus : instructionPage.status) {
                            if (instructionPageStatus.status.equalsIgnoreCase(str3)) {
                                return instructionPageStatus.details;
                            }
                        }
                        continue;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public Observable<List<MainScreen.InstructionItem>> getCountdownInstructionList() {
        return a(Instruction.SCREEN_COUNTDOWN, Instruction.PAGE_COUNTDOWN, Instruction.STATUS_COUNTING);
    }

    public Observable<List<MainScreen.InstructionItem>> getVideoInstructionList() {
        return a(Instruction.SCREEN_VIDEO, Instruction.PAGE_VIDEO_LIST, "Default");
    }
}
