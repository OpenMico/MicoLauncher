package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.weather.WeatherModel;
import com.xiaomi.micolauncher.skills.weather.WeatherQueryEvent;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateWeatherV2Operation extends BaseOperation<Instruction<Template.WeatherV2>> {
    public TemplateWeatherV2Operation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.WeatherV2 weatherV2 = (Template.WeatherV2) this.instruction.getPayload();
        Instruction loadDependentInstrByName = loadDependentInstrByName(AIApiConstants.Nlp.AuxiliaryIntention);
        if (loadDependentInstrByName == null) {
            loadDependentInstrByName = a();
        }
        if (loadDependentInstrByName == null) {
            L.ope.i("Template.WeatherV2 AuxiliaryIntention is empty");
            return BaseOperation.OpState.STATE_FAIL;
        }
        MiWeather translateWeather = new MiWeather().translateWeather(weatherV2, loadDependentInstrByName);
        L.weather.d("MiWeather is %s", Gsons.getGson().toJson(translateWeather));
        if (translateWeather == null || !translateWeather.isDataValid()) {
            L.ope.i("Weather data is invalid");
            return BaseOperation.OpState.STATE_FAIL;
        }
        WeatherModel.getInstance().setMiWeather(translateWeather);
        WeatherModel.getInstance().setDialogId(getDialogId());
        EventBusRegistry.getEventBus().post(new WeatherQueryEvent(getDialogId()));
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected List<String> dependenceInstruction() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AIApiConstants.Nlp.AuxiliaryIntention);
        return arrayList;
    }

    private Instruction a() {
        return InstructionUtil.getIntention(OperationManager.getInstance().getInstructions(getDialogId()), AIApiConstants.Nlp.AuxiliaryIntention);
    }
}
