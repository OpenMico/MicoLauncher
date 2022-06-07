package org.fourthline.cling.model.state;

import org.fourthline.cling.model.Command;
import org.fourthline.cling.model.ServiceManager;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.StateVariable;

/* loaded from: classes5.dex */
public abstract class StateVariableAccessor {
    public abstract Class<?> getReturnType();

    public abstract Object read(Object obj) throws Exception;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.fourthline.cling.model.state.StateVariableAccessor$1AccessCommand */
    /* loaded from: classes5.dex */
    public class AnonymousClass1AccessCommand implements Command {
        Object result;
        final /* synthetic */ Object val$serviceImpl;
        final /* synthetic */ StateVariable val$stateVariable;

        AnonymousClass1AccessCommand(Object obj, StateVariable stateVariable) {
            StateVariableAccessor.this = r1;
            this.val$serviceImpl = obj;
            this.val$stateVariable = stateVariable;
        }

        @Override // org.fourthline.cling.model.Command
        public void execute(ServiceManager serviceManager) throws Exception {
            this.result = StateVariableAccessor.this.read(this.val$serviceImpl);
            if (((LocalService) this.val$stateVariable.getService()).isStringConvertibleType(this.result)) {
                this.result = this.result.toString();
            }
        }
    }

    public StateVariableValue read(StateVariable<LocalService> stateVariable, Object obj) throws Exception {
        AnonymousClass1AccessCommand r0 = new AnonymousClass1AccessCommand(obj, stateVariable);
        stateVariable.getService().getManager().execute(r0);
        return new StateVariableValue(stateVariable, r0.result);
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ")";
    }
}
