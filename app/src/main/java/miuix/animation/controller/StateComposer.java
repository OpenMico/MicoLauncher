package miuix.animation.controller;

import java.lang.reflect.Method;
import miuix.animation.IAnimTarget;
import miuix.animation.utils.StyleComposer;

/* loaded from: classes5.dex */
public class StateComposer {
    private static final StyleComposer.IInterceptor<IFolmeStateStyle> a = new StyleComposer.IInterceptor<IFolmeStateStyle>() { // from class: miuix.animation.controller.StateComposer.1
        @Override // miuix.animation.utils.StyleComposer.IInterceptor
        public boolean shouldIntercept(Method method, Object[] objArr) {
            return method.getName().equals("getState");
        }

        /* renamed from: a */
        public Object onMethod(Method method, Object[] objArr, IFolmeStateStyle[] iFolmeStateStyleArr) {
            if (iFolmeStateStyleArr.length <= 0 || objArr.length <= 0) {
                return null;
            }
            AnimState state = iFolmeStateStyleArr[0].getState(objArr[0]);
            for (int i = 1; i < iFolmeStateStyleArr.length; i++) {
                iFolmeStateStyleArr[i].addState(state);
            }
            return state;
        }
    };

    private StateComposer() {
    }

    public static IFolmeStateStyle composeStyle(IAnimTarget... iAnimTargetArr) {
        if (iAnimTargetArr == null || iAnimTargetArr.length == 0) {
            return null;
        }
        if (iAnimTargetArr.length == 1) {
            return new FolmeState(iAnimTargetArr[0]);
        }
        FolmeState[] folmeStateArr = new FolmeState[iAnimTargetArr.length];
        for (int i = 0; i < iAnimTargetArr.length; i++) {
            folmeStateArr[i] = new FolmeState(iAnimTargetArr[i]);
        }
        return (IFolmeStateStyle) StyleComposer.compose(IFolmeStateStyle.class, a, folmeStateArr);
    }
}
