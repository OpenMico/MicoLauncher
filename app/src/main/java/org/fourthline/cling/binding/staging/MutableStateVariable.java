package org.fourthline.cling.binding.staging;

import java.util.List;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.meta.StateVariableAllowedValueRange;
import org.fourthline.cling.model.meta.StateVariableEventDetails;
import org.fourthline.cling.model.meta.StateVariableTypeDetails;
import org.fourthline.cling.model.types.Datatype;

/* loaded from: classes5.dex */
public class MutableStateVariable {
    public MutableAllowedValueRange allowedValueRange;
    public List<String> allowedValues;
    public Datatype dataType;
    public String defaultValue;
    public StateVariableEventDetails eventDetails;
    public String name;

    public StateVariable build() {
        String[] strArr;
        String str = this.name;
        Datatype datatype = this.dataType;
        String str2 = this.defaultValue;
        List<String> list = this.allowedValues;
        StateVariableAllowedValueRange stateVariableAllowedValueRange = null;
        if (list == null || list.size() == 0) {
            strArr = null;
        } else {
            List<String> list2 = this.allowedValues;
            strArr = (String[]) list2.toArray(new String[list2.size()]);
        }
        MutableAllowedValueRange mutableAllowedValueRange = this.allowedValueRange;
        if (mutableAllowedValueRange != null) {
            stateVariableAllowedValueRange = new StateVariableAllowedValueRange(mutableAllowedValueRange.minimum.longValue(), this.allowedValueRange.maximum.longValue(), this.allowedValueRange.step.longValue());
        }
        return new StateVariable(str, new StateVariableTypeDetails(datatype, str2, strArr, stateVariableAllowedValueRange), this.eventDetails);
    }
}
