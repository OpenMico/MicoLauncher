package com.xiaomi.micolauncher.skills.personalize.model;

import com.alibaba.fastjson.parser.JSONLexer;
import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Personalize;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import io.netty.util.internal.StringUtil;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public class PersonalizeExecution {
    private static volatile PersonalizeExecution instance;
    private List<Personalize.PersonalCmd> cmdList;
    private String eventId;
    private Personalize.PersonalCmd mCurrentCmd;
    private Instruction<Personalize.Execute> personalizeInstruction;
    private boolean processIngCmd;

    public String getEventId() {
        return this.eventId;
    }

    public Personalize.PersonalCmd getCurrentCmd() {
        return this.mCurrentCmd;
    }

    public long getCurrentCmdDuration() {
        Personalize.PersonalCmd personalCmd = this.mCurrentCmd;
        if (personalCmd == null || !personalCmd.getDuration().isPresent()) {
            return 0L;
        }
        return this.mCurrentCmd.getDuration().get().longValue();
    }

    public boolean isCurrentCmdDurationValid() {
        Personalize.PersonalCmd personalCmd = this.mCurrentCmd;
        return personalCmd != null && personalCmd.getDuration().isPresent() && this.mCurrentCmd.getDuration().get().longValue() > 0;
    }

    public boolean isProcessIngCmd() {
        return this.processIngCmd;
    }

    public void clearCmdList() {
        this.processIngCmd = false;
        this.mCurrentCmd = null;
        this.cmdList.clear();
    }

    public void setCmdList(List<Personalize.PersonalCmd> list) {
        this.processIngCmd = false;
        this.cmdList = list;
        sendNextCmd();
    }

    public void setPersonalizeInstruction(Instruction<Personalize.Execute> instruction) {
        this.personalizeInstruction = instruction;
    }

    public static PersonalizeExecution getInstance() {
        if (instance == null) {
            synchronized (PersonalizeExecution.class) {
                if (instance == null) {
                    instance = new PersonalizeExecution();
                }
            }
        }
        return instance;
    }

    private void sendNextCmd() {
        if (ContainerUtil.hasData(this.cmdList)) {
            this.processIngCmd = true;
            this.mCurrentCmd = this.cmdList.remove(0);
            Event buildEvent = APIUtils.buildEvent(new Nlp.Request(this.mCurrentCmd.getCmd()));
            this.eventId = buildEvent.getId();
            L.capability.d("PersonalizeExecution sendNextCmd eventId=%s", this.eventId);
            SpeechManager.getInstance().sendEventRequest(buildEvent);
            if (ContainerUtil.isEmpty(this.cmdList)) {
                this.processIngCmd = false;
                return;
            }
            return;
        }
        this.mCurrentCmd = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
        if (r1.equals(com.xiaomi.ai.api.AIApiConstants.VideoPlayer.PlayList) != false) goto L_0x004a;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.ai.api.common.Instruction filterAndSetDependence(java.util.List<com.xiaomi.ai.api.common.Instruction> r8) {
        /*
            r7 = this;
            com.xiaomi.ai.api.common.Instruction r0 = r7.findPrevDependsInstruction(r8)
            if (r0 == 0) goto L_0x007f
            com.elvishew.xlog.Logger r1 = com.xiaomi.micolauncher.common.L.capability
            java.lang.String r2 = "PersonalizeExecution find process_ing instruction=%s"
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r5 = 0
            r4[r5] = r0
            r1.d(r2, r4)
            java.lang.String r1 = r0.getFullName()
            r2 = -1
            int r4 = r1.hashCode()
            r6 = -1790160732(0xffffffff954c50a4, float:-4.1261086E-26)
            if (r4 == r6) goto L_0x0040
            r3 = -835318042(0xffffffffce360ee6, float:-7.6360742E8)
            if (r4 == r3) goto L_0x0036
            r3 = 813380520(0x307b33a8, float:9.1386676E-10)
            if (r4 == r3) goto L_0x002c
            goto L_0x0049
        L_0x002c:
            java.lang.String r3 = "VideoPlayer.LaunchPlayApp"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0049
            r3 = 2
            goto L_0x004a
        L_0x0036:
            java.lang.String r3 = "VideoPlayer.Play"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0049
            r3 = r5
            goto L_0x004a
        L_0x0040:
            java.lang.String r4 = "VideoPlayer.PlayList"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0049
            goto L_0x004a
        L_0x0049:
            r3 = r2
        L_0x004a:
            switch(r3) {
                case 0: goto L_0x004e;
                case 1: goto L_0x004e;
                case 2: goto L_0x004e;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x005d
        L_0x004e:
            com.xiaomi.ai.api.Personalize$PersonalCmd r1 = r7.mCurrentCmd
            if (r1 == 0) goto L_0x005c
            com.xiaomi.common.Optional r1 = r1.getDuration()
            boolean r1 = r1.isPresent()
            if (r1 != 0) goto L_0x005d
        L_0x005c:
            return r0
        L_0x005d:
            com.xiaomi.ai.api.common.InstructionDependence r1 = new com.xiaomi.ai.api.common.InstructionDependence
            r1.<init>()
            java.lang.String r2 = r0.getId()
            r1.setId(r2)
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            java.lang.String r2 = r2.toString()
            r1.setPredicate(r2)
            com.xiaomi.ai.api.common.Instruction<com.xiaomi.ai.api.Personalize$Execute> r2 = r7.personalizeInstruction
            if (r2 == 0) goto L_0x008d
            r2.setDependence(r1)
            com.xiaomi.ai.api.common.Instruction<com.xiaomi.ai.api.Personalize$Execute> r1 = r7.personalizeInstruction
            r8.add(r1)
            goto L_0x008d
        L_0x007f:
            com.elvishew.xlog.Logger r1 = com.xiaomi.micolauncher.common.L.capability
            java.lang.String r2 = "PersonalizeExecution 未找到可以依赖的耗时指令，剩下的cmd指令添加回去"
            r1.w(r2)
            com.xiaomi.ai.api.common.Instruction<com.xiaomi.ai.api.Personalize$Execute> r1 = r7.personalizeInstruction
            if (r1 == 0) goto L_0x008d
            r8.add(r1)
        L_0x008d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution.filterAndSetDependence(java.util.List):com.xiaomi.ai.api.common.Instruction");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:69:0x005d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.ai.api.common.Instruction findPrevDependsInstruction(java.util.List<com.xiaomi.ai.api.common.Instruction> r10) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution.findPrevDependsInstruction(java.util.List):com.xiaomi.ai.api.common.Instruction");
    }

    public boolean findExpectSpeech(List<Instruction> list) {
        for (int size = list.size() - 1; size > -1; size--) {
            if (list.get(size).getFullName().equals(AIApiConstants.SpeechRecognizer.ExpectSpeech)) {
                return true;
            }
        }
        return false;
    }

    public boolean findSelectInstruction(List<Instruction> list) {
        for (int size = list.size() - 1; size > -1; size--) {
            if (list.get(size).getFullName().equals(AIApiConstants.Selector.Select)) {
                return true;
            }
        }
        return false;
    }

    public Instruction findNextDependsInstruction(List<Instruction> list) {
        for (Instruction instruction : list) {
            String fullName = instruction.getFullName();
            char c = 65535;
            switch (fullName.hashCode()) {
                case -2079307189:
                    if (fullName.equals(AIApiConstants.Template.AncientPoem)) {
                        c = '3';
                        continue;
                    } else {
                        continue;
                    }
                case -1990497024:
                    if (fullName.equals(AIApiConstants.PlaybackController.SetProperty)) {
                        c = '$';
                        continue;
                    } else {
                        continue;
                    }
                case -1977710701:
                    if (fullName.equals(AIApiConstants.MiotController.Operate)) {
                        c = 21;
                        continue;
                    } else {
                        continue;
                    }
                case -1954408854:
                    if (fullName.equals(AIApiConstants.PlaybackController.Next)) {
                        c = 29;
                        continue;
                    } else {
                        continue;
                    }
                case -1954343253:
                    if (fullName.equals(AIApiConstants.PlaybackController.Play)) {
                        c = 24;
                        continue;
                    } else {
                        continue;
                    }
                case -1954337366:
                    if (fullName.equals(AIApiConstants.PlaybackController.Prev)) {
                        c = 28;
                        continue;
                    } else {
                        continue;
                    }
                case -1954260497:
                    if (fullName.equals(AIApiConstants.PlaybackController.Seek)) {
                        c = '!';
                        continue;
                    } else {
                        continue;
                    }
                case -1954245767:
                    if (fullName.equals(AIApiConstants.PlaybackController.Stop)) {
                        c = 27;
                        continue;
                    } else {
                        continue;
                    }
                case -1836500200:
                    if (fullName.equals(AIApiConstants.System.SetProperty)) {
                        c = StringUtil.COMMA;
                        continue;
                    } else {
                        continue;
                    }
                case -1790160732:
                    if (fullName.equals(AIApiConstants.VideoPlayer.PlayList)) {
                        c = '\b';
                        continue;
                    } else {
                        continue;
                    }
                case -1674901709:
                    if (fullName.equals(AIApiConstants.System.UpgradeRom)) {
                        c = '+';
                        continue;
                    } else {
                        continue;
                    }
                case -1616270202:
                    if (fullName.equals(AIApiConstants.System.Power)) {
                        c = '(';
                        continue;
                    } else {
                        continue;
                    }
                case -1603244712:
                    if (fullName.equals(AIApiConstants.Speaker.AdjustVolume)) {
                        c = Typography.amp;
                        continue;
                    } else {
                        continue;
                    }
                case -1487063953:
                    if (fullName.equals(AIApiConstants.UIController.Navigate)) {
                        c = '8';
                        continue;
                    } else {
                        continue;
                    }
                case -1387314916:
                    if (fullName.equals(AIApiConstants.Template.WeatherV2)) {
                        c = '2';
                        continue;
                    } else {
                        continue;
                    }
                case -1315060253:
                    if (fullName.equals(AIApiConstants.PlaybackController.StopAfter)) {
                        c = '\"';
                        continue;
                    } else {
                        continue;
                    }
                case -1234733648:
                    if (fullName.equals(AIApiConstants.PlaybackController.ContinuePlaying)) {
                        c = 25;
                        continue;
                    } else {
                        continue;
                    }
                case -1221739794:
                    if (fullName.equals(AIApiConstants.Microphone.TurnOff)) {
                        c = 20;
                        continue;
                    } else {
                        continue;
                    }
                case -1171720590:
                    if (fullName.equals(AIApiConstants.PlaybackController.Rewind)) {
                        c = ' ';
                        continue;
                    } else {
                        continue;
                    }
                case -1171153257:
                    if (fullName.equals(AIApiConstants.Template.ImageCard)) {
                        c = '1';
                        continue;
                    } else {
                        continue;
                    }
                case -902666468:
                    if (fullName.equals(AIApiConstants.AudioPlayer.CancelFromFavorites)) {
                        c = 6;
                        continue;
                    } else {
                        continue;
                    }
                case -876418020:
                    if (fullName.equals(AIApiConstants.Bluetooth.Disconnect)) {
                        c = '\r';
                        continue;
                    } else {
                        continue;
                    }
                case -850010575:
                    if (fullName.equals(AIApiConstants.FullScreenTemplate.Dialogue)) {
                        c = 17;
                        continue;
                    } else {
                        continue;
                    }
                case -835318042:
                    if (fullName.equals(AIApiConstants.VideoPlayer.Play)) {
                        c = 7;
                        continue;
                    } else {
                        continue;
                    }
                case -795955311:
                    if (fullName.equals(AIApiConstants.Alerts.SetAlert)) {
                        c = 0;
                        continue;
                    } else {
                        continue;
                    }
                case -751535297:
                    if (fullName.equals(AIApiConstants.PlaybackController.StartOver)) {
                        c = 30;
                        continue;
                    } else {
                        continue;
                    }
                case -711740740:
                    if (fullName.equals(AIApiConstants.Bluetooth.TurnOn)) {
                        c = '\n';
                        continue;
                    } else {
                        continue;
                    }
                case -698913236:
                    if (fullName.equals(AIApiConstants.Phone.MakeCall)) {
                        c = 22;
                        continue;
                    } else {
                        continue;
                    }
                case -643459826:
                    if (fullName.equals(AIApiConstants.Template.RichPicture)) {
                        c = '6';
                        continue;
                    } else {
                        continue;
                    }
                case -589126606:
                    if (fullName.equals(AIApiConstants.Bluetooth.TurnOff)) {
                        c = 11;
                        continue;
                    } else {
                        continue;
                    }
                case -554195084:
                    if (fullName.equals(AIApiConstants.Template.General)) {
                        c = JsonPointer.SEPARATOR;
                        continue;
                    } else {
                        continue;
                    }
                case -455407265:
                    if (fullName.equals(AIApiConstants.PlaybackController.Pause)) {
                        c = JSONLexer.EOI;
                        continue;
                    } else {
                        continue;
                    }
                case -349709590:
                    if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                        c = '9';
                        continue;
                    } else {
                        continue;
                    }
                case -178370:
                    if (fullName.equals(AIApiConstants.Template.General2)) {
                        c = '0';
                        continue;
                    } else {
                        continue;
                    }
                case 304115208:
                    if (fullName.equals(AIApiConstants.Template.DeviceList)) {
                        c = '7';
                        continue;
                    } else {
                        continue;
                    }
                case 412115826:
                    if (fullName.equals(AIApiConstants.PlaybackController.FastForward)) {
                        c = 31;
                        continue;
                    } else {
                        continue;
                    }
                case 506647148:
                    if (fullName.equals(AIApiConstants.Speaker.SetMute)) {
                        c = '\'';
                        continue;
                    } else {
                        continue;
                    }
                case 555340283:
                    if (fullName.equals(AIApiConstants.Launcher.LaunchGeneralQuickApp)) {
                        c = 18;
                        continue;
                    } else {
                        continue;
                    }
                case 557206493:
                    if (fullName.equals(AIApiConstants.Template.Alarm)) {
                        c = '.';
                        continue;
                    } else {
                        continue;
                    }
                case 557738813:
                    if (fullName.equals(AIApiConstants.Template.Translation)) {
                        c = '4';
                        continue;
                    } else {
                        continue;
                    }
                case 670244535:
                    if (fullName.equals(AIApiConstants.Alerts.UpdateAlerts)) {
                        c = 2;
                        continue;
                    } else {
                        continue;
                    }
                case 692050764:
                    if (fullName.equals(AIApiConstants.Template.Videos)) {
                        c = '5';
                        continue;
                    } else {
                        continue;
                    }
                case 813380520:
                    if (fullName.equals(AIApiConstants.VideoPlayer.LaunchPlayApp)) {
                        c = '\t';
                        continue;
                    } else {
                        continue;
                    }
                case 827010726:
                    if (fullName.equals(AIApiConstants.Application.Operate)) {
                        c = 3;
                        continue;
                    } else {
                        continue;
                    }
                case 850442777:
                    if (fullName.equals(AIApiConstants.Alerts.DeleteAlerts)) {
                        c = 1;
                        continue;
                    } else {
                        continue;
                    }
                case 1029794369:
                    if (fullName.equals(AIApiConstants.BrightnessController.AdjustBrightness)) {
                        c = 15;
                        continue;
                    } else {
                        continue;
                    }
                case 1033105579:
                    if (fullName.equals(AIApiConstants.AudioPlayer.Play)) {
                        c = 4;
                        continue;
                    } else {
                        continue;
                    }
                case 1084629551:
                    if (fullName.equals(AIApiConstants.System.UnlockScreen)) {
                        c = '*';
                        continue;
                    } else {
                        continue;
                    }
                case 1200982678:
                    if (fullName.equals(AIApiConstants.System.LockScreen)) {
                        c = ')';
                        continue;
                    } else {
                        continue;
                    }
                case 1247548208:
                    if (fullName.equals(AIApiConstants.Phone.ShowContacts)) {
                        c = 23;
                        continue;
                    } else {
                        continue;
                    }
                case 1277115268:
                    if (fullName.equals(AIApiConstants.Education.EduShowSearchPage)) {
                        c = 16;
                        continue;
                    } else {
                        continue;
                    }
                case 1327732074:
                    if (fullName.equals(AIApiConstants.Bluetooth.Connect)) {
                        c = '\f';
                        continue;
                    } else {
                        continue;
                    }
                case 1388751090:
                    if (fullName.equals(AIApiConstants.BrightnessController.SetBrightness)) {
                        c = 14;
                        continue;
                    } else {
                        continue;
                    }
                case 1492976498:
                    if (fullName.equals(AIApiConstants.AudioPlayer.AddToFavorites)) {
                        c = 5;
                        continue;
                    } else {
                        continue;
                    }
                case 1629463348:
                    if (fullName.equals(AIApiConstants.TVController.Operate)) {
                        c = '-';
                        continue;
                    } else {
                        continue;
                    }
                case 1808506541:
                    if (fullName.equals(AIApiConstants.Speaker.SetVolume)) {
                        c = '%';
                        continue;
                    } else {
                        continue;
                    }
                case 1868090825:
                    if (fullName.equals(AIApiConstants.PlaybackController.CancelStopAfter)) {
                        c = '#';
                        continue;
                    } else {
                        continue;
                    }
                case 2094405077:
                    if (fullName.equals(AIApiConstants.Launcher.LaunchQuickApp)) {
                        c = 19;
                        continue;
                    } else {
                        continue;
                    }
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case ' ':
                case '!':
                case '\"':
                case '#':
                case '$':
                case '%':
                case '&':
                case '\'':
                case '(':
                case ')':
                case '*':
                case '+':
                case ',':
                case '-':
                case '.':
                case '/':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                    return instruction;
                case '9':
                    return instruction;
            }
        }
        return null;
    }
}
