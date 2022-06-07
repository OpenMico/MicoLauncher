package com.xiaomi.micolauncher.instruciton.base;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.AlertsOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.ApplicationOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.AudioPlayerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.BluetoothOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.BrightnessControllerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.CustomDirectiveOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.DeviceBindingOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.EducationOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.FullScreenTemplateOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.GeneralOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.LauncherOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.MicrophoneOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.MiotControllerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.NlpOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.PersonalizeOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.PhoneOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.PlaybackControllerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SceneOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SelectorOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SpeakerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SpeechRecognizerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SpeechSynthesizerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.SystemOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.TVControllerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.TemplateOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.UIControllerOperationFactory;
import com.xiaomi.micolauncher.instruciton.factory.VideoPlayerOperationFactory;
import com.xiaomi.micolauncher.instruciton.impl.LauncherLaunchQuickAppOperation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class OperationFactory {
    private final ArrayMap<String, AbstractOperationFactory> a;
    private List<String> b;

    /* loaded from: classes3.dex */
    public static class a {
        static OperationFactory a = new OperationFactory();
    }

    public static OperationFactory getInstance() {
        return a.a;
    }

    private OperationFactory() {
        this.b = null;
        this.a = new ArrayMap<>();
        a();
        b();
    }

    private void a() {
        a(new AlertsOperationFactory());
        a(new ApplicationOperationFactory());
        a(new AudioPlayerOperationFactory());
        a(new BluetoothOperationFactory());
        a(new BrightnessControllerOperationFactory());
        a(new CustomDirectiveOperationFactory());
        a(new DeviceBindingOperationFactory());
        a(new LauncherOperationFactory());
        a(new MicrophoneOperationFactory());
        a(new MiotControllerOperationFactory());
        a(new NlpOperationFactory());
        a(new PersonalizeOperationFactory());
        a(new PhoneOperationFactory());
        a(new PlaybackControllerOperationFactory());
        a(new SelectorOperationFactory());
        a(new SpeakerOperationFactory());
        a(new SpeechSynthesizerOperationFactory());
        a(new SpeechRecognizerOperationFactory());
        a(new SystemOperationFactory());
        a(new SceneOperationFactory());
        a(new TemplateOperationFactory());
        a(new TVControllerOperationFactory());
        a(new UIControllerOperationFactory());
        a(new VideoPlayerOperationFactory());
        a(new GeneralOperationFactory());
        a(new FullScreenTemplateOperationFactory());
        a(new EducationOperationFactory());
    }

    private void a(AbstractOperationFactory abstractOperationFactory) {
        this.a.put(abstractOperationFactory.namespace(), abstractOperationFactory);
    }

    private void b() {
        if (this.b == null) {
            this.b = new ArrayList();
            this.b.add(AIApiConstants.Suggestion.NAME);
            this.b.add(AIApiConstants.Template.SwitchPanelList);
        }
    }

    public LinkedList<BaseOperation> parse(List<Instruction> list) {
        int i;
        AbstractOperationFactory abstractOperationFactory;
        LinkedList<BaseOperation> linkedList = new LinkedList<>();
        if (list == null) {
            return null;
        }
        L.ope.i("OperationFactory parse start, instructions size=%s", Integer.valueOf(list.size()));
        for (Instruction instruction : list) {
            if (instruction.getFullName().equals(AIApiConstants.Launcher.LaunchQuickApp) && (abstractOperationFactory = this.a.get(instruction.getNamespace())) != null) {
                BaseOperation build = abstractOperationFactory.build(instruction);
                if (build instanceof LauncherLaunchQuickAppOperation) {
                    ((LauncherLaunchQuickAppOperation) build).setInstructions(list);
                    linkedList.add(build);
                    L.ope.i("instruction=%s,operation=%s", instruction.getFullName(), build);
                    L.ope.i("parse end");
                    return linkedList;
                }
            }
        }
        boolean isPhoneRunning = OperationManager.getInstance().isPhoneRunning();
        L.ope.d("phone is running=%s", Boolean.valueOf(isPhoneRunning));
        int size = list.size();
        Instruction instruction2 = null;
        BaseOperation baseOperation = null;
        for (int i2 = 0; i2 < size; i2++) {
            Instruction instruction3 = list.get(i2);
            String namespace = instruction3.getNamespace();
            String fullName = instruction3.getFullName();
            L.ope.i("parse=%s", fullName);
            if (!isPhoneRunning || a(namespace) || b(fullName)) {
                AbstractOperationFactory abstractOperationFactory2 = this.a.get(namespace);
                if (abstractOperationFactory2 == null) {
                    L.ope.i("no operation factory to build=%s", fullName);
                    if (baseOperation != null) {
                        baseOperation.consumeInstruction(instruction3);
                    }
                } else {
                    BaseOperation build2 = abstractOperationFactory2.build(instruction3);
                    if (build2 == null) {
                        if (AIApiConstants.Template.PlayInfo.equals(fullName)) {
                            instruction2 = instruction3;
                        } else if (baseOperation != null) {
                            baseOperation.consumeInstruction(instruction3);
                        }
                        L.ope.i("factory=%s return null operation for instruction=%s", abstractOperationFactory2.getClass().getSimpleName(), fullName);
                        i = 2;
                    } else {
                        if (instruction2 != null) {
                            if (AIApiConstants.AudioPlayer.Play.equals(build2.fullName())) {
                                if (build2.consumeInstruction(instruction2)) {
                                    instruction2 = null;
                                }
                                i = 2;
                            } else {
                                i = 2;
                            }
                        } else if (AIApiConstants.AudioPlayer.Play.equals(fullName)) {
                            Instruction a2 = a(list, i2, AIApiConstants.Template.PlayInfo);
                            if (a2 != null) {
                                i = 2;
                                L.ope.i("Operation=%s,consume Instruction=%s,consume=%s", build2.getOpName(), fullName, Boolean.valueOf(build2.consumeInstruction(a2)));
                            } else {
                                i = 2;
                            }
                        } else {
                            i = 2;
                        }
                        Logger logger = L.ope;
                        Object[] objArr = new Object[i];
                        objArr[0] = fullName;
                        objArr[1] = build2;
                        logger.i("instruction=%s,operation=%s", objArr);
                        linkedList.add(build2);
                        baseOperation = build2;
                    }
                }
            } else {
                L.ope.i("phone is running pass ins=%s ignore!!", fullName);
            }
            i = 2;
        }
        L.ope.i("parse end");
        return linkedList;
    }

    private Instruction a(List<Instruction> list, int i, String str) {
        while (i < list.size()) {
            Instruction instruction = list.get(i);
            if (str.equals(instruction.getFullName())) {
                return instruction;
            }
            i++;
        }
        return null;
    }

    private boolean a(String str) {
        return AIApiConstants.Phone.NAME.equals(str);
    }

    private boolean b(String str) {
        return AIApiConstants.General.Push.equals(str);
    }

    public boolean containsNamespace(String str) {
        ArrayMap<String, AbstractOperationFactory> arrayMap;
        return (TextUtils.isEmpty(str) || (arrayMap = this.a) == null || arrayMap.get(str) == null) ? false : true;
    }
}
