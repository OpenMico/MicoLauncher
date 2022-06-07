package com.xiaomi.ai.android.core;

import android.text.TextUtils;
import com.xiaomi.ai.android.capability.InstructionCapability;
import com.xiaomi.ai.android.impl.a;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.common.Optional;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
public final class f {
    private final boolean a;
    private d b;
    private a c;

    public f(d dVar) {
        this.b = dVar;
        this.a = dVar.b().getInt(AivsConfig.Asr.VAD_TYPE) != 1 ? false : true;
        this.c = new a(this.b);
    }

    private void c() {
        a aVar = this.c;
        if (aVar != null) {
            aVar.a((byte[]) null, true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(com.xiaomi.ai.api.common.Instruction r5) {
        /*
            r4 = this;
            boolean r0 = r4.a
            if (r0 == 0) goto L_0x0011
            java.lang.String r0 = "SpeechRecognizer.StopCapture"
            java.lang.String r1 = r5.getFullName()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0011
            return
        L_0x0011:
            com.xiaomi.common.Optional r0 = r5.getDialogId()
            boolean r0 = r0.isPresent()
            if (r0 != 0) goto L_0x0023
            java.lang.String r5 = "InstructionManager"
            java.lang.String r0 = "processACK dialog is null"
            com.xiaomi.ai.log.Logger.b(r5, r0)
            return
        L_0x0023:
            com.xiaomi.common.Optional r0 = r5.getDialogId()
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "System.Ping"
            java.lang.String r2 = r5.getFullName()
            boolean r1 = r1.equals(r2)
            r2 = 0
            if (r1 == 0) goto L_0x0054
            com.xiaomi.ai.api.Sys$Ack r1 = new com.xiaomi.ai.api.Sys$Ack
            r1.<init>()
            java.lang.Object r5 = r5.getPayload()
            com.xiaomi.ai.api.Sys$Ping r5 = (com.xiaomi.ai.api.Sys.Ping) r5
            java.lang.String r3 = r5.getType()
            r1.setType(r3)
            java.lang.String r5 = r5.getId()
        L_0x0050:
            r1.setId(r5)
            goto L_0x0084
        L_0x0054:
            java.lang.String r1 = "General.Push"
            java.lang.String r3 = r5.getFullName()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x006f
            com.xiaomi.ai.api.Sys$Ack r1 = new com.xiaomi.ai.api.Sys$Ack
            r1.<init>()
            java.lang.String r3 = "Push"
        L_0x0067:
            r1.setType(r3)
            java.lang.String r5 = r5.getId()
            goto L_0x0050
        L_0x006f:
            java.lang.String r1 = "Dialog.Finish"
            java.lang.String r3 = r5.getFullName()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0083
            com.xiaomi.ai.api.Sys$Ack r1 = new com.xiaomi.ai.api.Sys$Ack
            r1.<init>()
            java.lang.String r3 = "Instruction"
            goto L_0x0067
        L_0x0083:
            r1 = r2
        L_0x0084:
            if (r1 == 0) goto L_0x008f
            com.xiaomi.ai.api.common.Event r5 = com.xiaomi.ai.api.common.APIUtils.buildEvent(r1, r2, r0)
            com.xiaomi.ai.android.core.d r0 = r4.b
            r0.postEvent(r5)
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.android.core.f.c(com.xiaomi.ai.api.common.Instruction):void");
    }

    private void d() {
        if (this.b.b().getInt(AivsConfig.Connection.KEEP_ALIVE_TYPE) == 2 && this.b.c().b() <= 0) {
            Logger.b("InstructionManager", "processFinish: stop Channel because of DO_NOT_KEEP_ALIVE");
            com.xiaomi.ai.core.a g = this.b.g();
            if (g != null) {
                g.stop();
            }
        }
    }

    private void d(Instruction instruction) {
        String fullName = instruction.getFullName();
        if (((fullName.hashCode() == 274747385 && fullName.equals(AIApiConstants.Dialog.Finish)) ? (char) 0 : (char) 65535) == 0) {
            d();
        }
    }

    private void e(Instruction instruction) {
        char c;
        String fullName = instruction.getFullName();
        int hashCode = fullName.hashCode();
        if (hashCode != -349709590) {
            if (hashCode == 978198135 && fullName.equals(AIApiConstants.SpeechSynthesizer.FinishSpeakStream)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                f(instruction);
                return;
            case 1:
                c();
                return;
            default:
                return;
        }
    }

    private void f(Instruction instruction) {
        SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
        String str = speak.getUrl().isPresent() ? speak.getUrl().get() : null;
        if (this.b.b().getBoolean(AivsConfig.Tts.ENABLE_INTERNAL_PLAYER, true) || TextUtils.isEmpty(str)) {
            Optional<String> dialogId = instruction.getDialogId();
            if (dialogId == null) {
                Logger.d("InstructionManager", "startAudioPlayer: dialogId is null," + instruction);
            } else if (dialogId.isPresent()) {
                String str2 = dialogId.get();
                if (this.b.e().a(str2)) {
                    int i = 16000;
                    if (speak.getSampleRate().isPresent()) {
                        i = speak.getSampleRate().get().intValue();
                    }
                    if (str == null) {
                        a aVar = this.c;
                        if (aVar == null || i != aVar.a()) {
                            this.c = new a(this.b, i, str2);
                        } else {
                            this.c.a(i, str2);
                        }
                        this.c.b();
                    } else if (this.b.b().getBoolean(AivsConfig.Tts.ENABLE_INTERNAL_PLAYER, true)) {
                        if (this.c == null) {
                            this.c = new a(this.b, i, str2);
                        }
                        if (!this.c.a(str) || !this.c.d()) {
                            this.c.c();
                            Logger.d("InstructionManager", "startAudioPlayer: failed to start url player, " + str);
                        }
                    }
                }
            }
        } else {
            Logger.a("InstructionManager", "startAudioPlayer: client play tts, url mode");
        }
    }

    public void a() {
        synchronized (this) {
            if (this.c != null) {
                this.c.e();
                this.c = null;
            }
        }
    }

    public void a(Instruction instruction) {
        if (Logger.getLogLevel() == 3) {
            Logger.a("InstructionManager", "handleInstruction: " + instruction);
        } else if (AIApiConstants.System.Exception.equals(instruction.getFullName()) || AIApiConstants.System.Abort.equals(instruction.getFullName())) {
            Logger.b("InstructionManager", "handleInstruction: " + instruction);
        } else {
            String str = "";
            if (instruction.getDialogId().isPresent()) {
                str = instruction.getDialogId().get();
            }
            Logger.b("InstructionManager", "handleInstruction:" + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str);
        }
        if (this.b.b().getBoolean(AivsConfig.Connection.ENABLE_INSTRUCTION_ACK, true)) {
            c(instruction);
        }
        this.b.k().a(instruction);
        if (this.b.e().a(instruction)) {
            synchronized (this) {
                d(instruction);
            }
            if (AIApiConstants.System.Heartbeat.equals(instruction.getFullName())) {
                String str2 = "";
                if (instruction.getDialogId().isPresent()) {
                    str2 = instruction.getDialogId().get();
                }
                Logger.b("InstructionManager", instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str2 + " no need pass to client");
                return;
            }
            InstructionCapability instructionCapability = (InstructionCapability) this.b.a(InstructionCapability.class);
            if (instructionCapability != null && instructionCapability.process(instruction)) {
                this.b.k().b(instruction);
            } else if (Logger.getLogLevel() == 3) {
                Logger.d("InstructionManager", "handleInstruction: failed to handle " + instruction.toString());
            } else {
                String str3 = "";
                if (instruction.getDialogId().isPresent()) {
                    str3 = instruction.getDialogId().get();
                }
                Logger.d("InstructionManager", "handleInstruction: failed to handle " + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str3);
            }
        } else if (Logger.getLogLevel() == 3) {
            Logger.c("InstructionManager", "handleInstruction: discard " + instruction.toString());
        } else {
            String str4 = "";
            if (instruction.getDialogId().isPresent()) {
                str4 = instruction.getDialogId().get();
            }
            Logger.c("InstructionManager", "handleInstruction: discard " + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str4);
        }
    }

    public void a(byte[] bArr) {
        this.b.k().e();
        synchronized (this) {
            if (this.c != null) {
                this.c.a(bArr, false);
            }
        }
    }

    public void b() {
        synchronized (this) {
            if (this.c != null) {
                this.c.c();
            }
        }
    }

    public void b(Instruction instruction) {
        if (Logger.getLogLevel() == 3) {
            Logger.a("InstructionManager", "handleSpeakInstruction: " + instruction);
        } else {
            String str = "";
            if (instruction.getDialogId().isPresent()) {
                str = instruction.getDialogId().get();
            }
            Logger.b("InstructionManager", "handleSpeakInstruction:" + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str);
        }
        if (this.b.e().a(instruction)) {
            synchronized (this) {
                e(instruction);
            }
        } else if (Logger.getLogLevel() == 3) {
            Logger.a("InstructionManager", "handleSpeakInstruction: discard " + instruction.toString());
        } else {
            String str2 = "";
            if (instruction.getDialogId().isPresent()) {
                str2 = instruction.getDialogId().get();
            }
            Logger.c("InstructionManager", "handleSpeakInstruction: discard " + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str2);
        }
    }
}
