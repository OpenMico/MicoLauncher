package org.fourthline.cling.model.meta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.ServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.action.ActionExecutor;
import org.fourthline.cling.model.state.StateVariableAccessor;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public class LocalService<T> extends Service<LocalDevice, LocalService> {
    protected final Map<Action, ActionExecutor> actionExecutors;
    protected ServiceManager manager;
    protected final Map<StateVariable, StateVariableAccessor> stateVariableAccessors;
    protected final Set<Class> stringConvertibleTypes;
    protected final boolean supportsQueryStateVariables;

    public LocalService(ServiceType serviceType, ServiceId serviceId, Action[] actionArr, StateVariable[] stateVariableArr) throws ValidationException {
        super(serviceType, serviceId, actionArr, stateVariableArr);
        this.manager = null;
        this.actionExecutors = new HashMap();
        this.stateVariableAccessors = new HashMap();
        this.stringConvertibleTypes = new HashSet();
        this.supportsQueryStateVariables = true;
    }

    public LocalService(ServiceType serviceType, ServiceId serviceId, Map<Action, ActionExecutor> map, Map<StateVariable, StateVariableAccessor> map2, Set<Class> set, boolean z) throws ValidationException {
        super(serviceType, serviceId, (Action[]) map.keySet().toArray(new Action[map.size()]), (StateVariable[]) map2.keySet().toArray(new StateVariable[map2.size()]));
        this.supportsQueryStateVariables = z;
        this.stringConvertibleTypes = set;
        this.stateVariableAccessors = map2;
        this.actionExecutors = map;
    }

    public synchronized void setManager(ServiceManager<T> serviceManager) {
        if (this.manager == null) {
            this.manager = serviceManager;
        } else {
            throw new IllegalStateException("Manager is final");
        }
    }

    public synchronized ServiceManager<T> getManager() {
        if (this.manager != null) {
        } else {
            throw new IllegalStateException("Unmanaged service, no implementation instance available");
        }
        return this.manager;
    }

    public boolean isSupportsQueryStateVariables() {
        return this.supportsQueryStateVariables;
    }

    public Set<Class> getStringConvertibleTypes() {
        return this.stringConvertibleTypes;
    }

    public boolean isStringConvertibleType(Object obj) {
        return obj != null && isStringConvertibleType((Class) obj.getClass());
    }

    public boolean isStringConvertibleType(Class cls) {
        return ModelUtil.isStringConvertibleType(getStringConvertibleTypes(), cls);
    }

    public StateVariableAccessor getAccessor(String str) {
        StateVariable<LocalService> stateVariable = getStateVariable(str);
        if (stateVariable != null) {
            return getAccessor(stateVariable);
        }
        return null;
    }

    public StateVariableAccessor getAccessor(StateVariable stateVariable) {
        return this.stateVariableAccessors.get(stateVariable);
    }

    public ActionExecutor getExecutor(String str) {
        Action<LocalService> action = getAction(str);
        if (action != null) {
            return getExecutor(action);
        }
        return null;
    }

    public ActionExecutor getExecutor(Action action) {
        return this.actionExecutors.get(action);
    }

    @Override // org.fourthline.cling.model.meta.Service
    public Action getQueryStateVariableAction() {
        return getAction(QueryStateVariableAction.ACTION_NAME);
    }

    @Override // org.fourthline.cling.model.meta.Service
    public String toString() {
        return super.toString() + ", Manager: " + this.manager;
    }
}
