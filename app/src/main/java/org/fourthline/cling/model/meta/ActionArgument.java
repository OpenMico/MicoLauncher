package org.fourthline.cling.model.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.Validatable;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.Datatype;

/* loaded from: classes5.dex */
public class ActionArgument<S extends Service> implements Validatable {
    private static final Logger log = Logger.getLogger(ActionArgument.class.getName());
    private Action<S> action;
    private final String[] aliases;
    private final Direction direction;
    private final String name;
    private final String relatedStateVariableName;
    private final boolean returnValue;

    /* loaded from: classes5.dex */
    public enum Direction {
        IN,
        OUT
    }

    public ActionArgument(String str, String str2, Direction direction) {
        this(str, new String[0], str2, direction, false);
    }

    public ActionArgument(String str, String[] strArr, String str2, Direction direction) {
        this(str, strArr, str2, direction, false);
    }

    public ActionArgument(String str, String str2, Direction direction, boolean z) {
        this(str, new String[0], str2, direction, z);
    }

    public ActionArgument(String str, String[] strArr, String str2, Direction direction, boolean z) {
        this.name = str;
        this.aliases = strArr;
        this.relatedStateVariableName = str2;
        this.direction = direction;
        this.returnValue = z;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public boolean isNameOrAlias(String str) {
        if (getName().equalsIgnoreCase(str)) {
            return true;
        }
        for (String str2 : this.aliases) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public String getRelatedStateVariableName() {
        return this.relatedStateVariableName;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isReturnValue() {
        return this.returnValue;
    }

    public Action<S> getAction() {
        return this.action;
    }

    public void setAction(Action<S> action) {
        if (this.action == null) {
            this.action = action;
            return;
        }
        throw new IllegalStateException("Final value has been set already, model is immutable");
    }

    public Datatype getDatatype() {
        return getAction().getService().getDatatype(this);
    }

    @Override // org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        if (getName() == null || getName().length() == 0) {
            Class<?> cls = getClass();
            arrayList.add(new ValidationError(cls, "name", "Argument without name of: " + getAction()));
        } else if (!ModelUtil.isValidUDAName(getName())) {
            Logger logger = log;
            logger.warning("UPnP specification violation of: " + getAction().getService().getDevice());
            Logger logger2 = log;
            logger2.warning("Invalid argument name: " + this);
        } else if (getName().length() > 32) {
            Logger logger3 = log;
            logger3.warning("UPnP specification violation of: " + getAction().getService().getDevice());
            Logger logger4 = log;
            logger4.warning("Argument name should be less than 32 characters: " + this);
        }
        if (getDirection() == null) {
            Class<?> cls2 = getClass();
            arrayList.add(new ValidationError(cls2, "direction", "Argument '" + getName() + "' requires a direction, either IN or OUT"));
        }
        if (isReturnValue() && getDirection() != Direction.OUT) {
            Class<?> cls3 = getClass();
            arrayList.add(new ValidationError(cls3, "direction", "Return value argument '" + getName() + "' must be direction OUT"));
        }
        return arrayList;
    }

    public ActionArgument<S> deepCopy() {
        return new ActionArgument<>(getName(), getAliases(), getRelatedStateVariableName(), getDirection(), isReturnValue());
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ", " + getDirection() + ") " + getName();
    }
}
