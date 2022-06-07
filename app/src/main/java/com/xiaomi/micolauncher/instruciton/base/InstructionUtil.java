package com.xiaomi.micolauncher.instruciton.base;

import android.text.TextUtils;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.api.common.InstructionDependence;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.common.ContainerUtil;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class InstructionUtil {
    public static Instruction getIntention(List<Instruction> list, String str) {
        if (!ContainerUtil.hasData(list)) {
            return null;
        }
        for (Instruction instruction : list) {
            if (str.equals(instruction.getFullName())) {
                return instruction;
            }
        }
        return null;
    }

    public static boolean hasDependence(Instruction instruction) {
        Optional<InstructionDependence> dependence = instruction.getHeader().getDependence();
        if (dependence == null || !dependence.isPresent()) {
            return false;
        }
        return !TextUtils.isEmpty(dependence.get().getId());
    }

    public static String getDialogId(Instruction instruction) {
        return (instruction == null || !instruction.getDialogId().isPresent()) ? "" : instruction.getDialogId().get().toString();
    }

    public static Instruction buildInstruction(String str) {
        try {
            return APIUtils.readInstruction(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
