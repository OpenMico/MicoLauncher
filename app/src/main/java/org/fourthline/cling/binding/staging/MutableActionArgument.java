package org.fourthline.cling.binding.staging;

import org.fourthline.cling.model.meta.ActionArgument;

/* loaded from: classes5.dex */
public class MutableActionArgument {
    public ActionArgument.Direction direction;
    public String name;
    public String relatedStateVariable;
    public boolean retval;

    public ActionArgument build() {
        return new ActionArgument(this.name, this.relatedStateVariable, this.direction, this.retval);
    }
}
