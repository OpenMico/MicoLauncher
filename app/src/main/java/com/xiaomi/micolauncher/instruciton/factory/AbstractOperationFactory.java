package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public abstract class AbstractOperationFactory {
    public abstract BaseOperation build(Instruction instruction, String str);

    public abstract String namespace();

    public BaseOperation build(Instruction instruction) {
        if (instruction != null) {
            return build(instruction, instruction.getFullName());
        }
        return null;
    }
}
