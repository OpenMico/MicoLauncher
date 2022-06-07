package org.fourthline.cling.binding.staging;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.ActionArgument;

/* loaded from: classes5.dex */
public class MutableAction {
    public List<MutableActionArgument> arguments = new ArrayList();
    public String name;

    public Action build() {
        return new Action(this.name, createActionArgumennts());
    }

    public ActionArgument[] createActionArgumennts() {
        ActionArgument[] actionArgumentArr = new ActionArgument[this.arguments.size()];
        int i = 0;
        for (MutableActionArgument mutableActionArgument : this.arguments) {
            i++;
            actionArgumentArr[i] = mutableActionArgument.build();
        }
        return actionArgumentArr;
    }
}
