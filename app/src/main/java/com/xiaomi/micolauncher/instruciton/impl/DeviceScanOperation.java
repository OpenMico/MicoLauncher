package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.DeviceBinding;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class DeviceScanOperation extends BaseOperation<Instruction<DeviceBinding.ScanDevices>> {
    public static final String ACTION_DISCOVER_BY_UBUS = "action_discover_device_by_ubus";
    public static final String ACTION_DISCOVER_DEVICE = "action_discover_device";
    public static final String MICO_SKILL_MESH_ACTION = "mico://skill/mesh?action=action_discover_device";

    public DeviceScanOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected void onPreCreateOp() {
        setSpeakDependence();
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        DeviceBinding.ScanDevices scanDevices = (DeviceBinding.ScanDevices) this.instruction.getPayload();
        if (!scanDevices.getType().isPresent() || !DomainConfig.Mesh.DOMAIN_NAME.equalsIgnoreCase(scanDevices.getType().get())) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        SchemaManager.handleSchema(getContext(), MICO_SKILL_MESH_ACTION);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
