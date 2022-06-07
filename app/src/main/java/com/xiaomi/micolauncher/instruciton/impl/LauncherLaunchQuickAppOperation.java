package com.xiaomi.micolauncher.instruciton.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Launcher;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.quickapp.QuickAppQuitEvent;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.List;
import org.hapjs.features.channel.vui.VuiBridgeCallback;
import org.hapjs.features.channel.vui.VuiBridgeManager;

/* loaded from: classes3.dex */
public class LauncherLaunchQuickAppOperation extends BaseOperation<Instruction<Launcher.LaunchQuickApp>> {
    private List<Instruction> b;

    public LauncherLaunchQuickAppOperation(Instruction instruction) {
        super(instruction);
    }

    public void setInstructions(List<Instruction> list) {
        this.b = list;
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        String str;
        Launcher.LaunchQuickApp launchQuickApp = (Launcher.LaunchQuickApp) this.instruction.getPayload();
        String pkgName = launchQuickApp.getPkgName();
        int indexOf = pkgName.indexOf(Constants.COLON_SEPARATOR);
        if (indexOf != -1) {
            String substring = pkgName.substring(0, indexOf);
            str = pkgName.substring(indexOf + 1, pkgName.length());
            pkgName = substring;
        } else {
            str = null;
        }
        if (launchQuickApp.getTask().isPresent() && launchQuickApp.getTask().get().getLoadType() == Template.TaskLoadType.QUIT) {
            EventBusRegistry.getEventBus().post(new QuickAppQuitEvent(true));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StringBuilder sb = new StringBuilder("{\"instructions\":[");
            int i = 0;
            for (Instruction instruction : this.b) {
                sb.append(instruction.toJsonString());
                i++;
                if (i < this.b.size()) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
            }
            sb.append("]}");
            JsonNode readTree = objectMapper.readTree(sb.toString());
            ObjectNode createObjectNode = objectMapper.createObjectNode();
            createObjectNode.set("ins", readTree);
            createObjectNode.put("type", "send_ins");
            String writeValueAsString = objectMapper.writeValueAsString(createObjectNode);
            L.quickapp.i("sendRawData 【%s】,【dataLength=%s】,【%s】", pkgName, Integer.valueOf(writeValueAsString.length()), str);
            VuiBridgeManager.getInstance().sendRawData(pkgName, writeValueAsString, str, new VuiBridgeCallback<JsonNode>() { // from class: com.xiaomi.micolauncher.instruciton.impl.LauncherLaunchQuickAppOperation.1
                /* renamed from: a */
                public void onSuccess(JsonNode jsonNode) {
                    L.quickapp.i("【sendRawData onSuccess】: %s", jsonNode.toString());
                }

                @Override // org.hapjs.features.channel.vui.VuiBridgeCallback
                public void onFail(int i2, String str2) {
                    L.quickapp.i("【sendRawData onFail】: %s,%s", Integer.valueOf(i2), str2);
                }
            });
            return BaseOperation.OpState.STATE_SUCCESS;
        } catch (JsonProcessingException e) {
            L.quickapp.i("LauncherLaunchQuickAppOperation onProcess", e);
            return BaseOperation.OpState.STATE_FAIL;
        } catch (IOException e2) {
            L.quickapp.i("LauncherLaunchQuickAppOperation onProcess", e2);
            return BaseOperation.OpState.STATE_FAIL;
        }
    }
}
