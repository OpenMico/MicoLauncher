package org.fourthline.cling.support.shared;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.swing.JFrame;
import org.seamless.swing.Controller;

/* loaded from: classes5.dex */
public class PlatformApple {
    public static void setup(Controller<JFrame> controller, String str) throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", str);
        System.setProperty("apple.awt.showGrowBox", "true");
        Class<?> cls = Class.forName("com.apple.eawt.Application");
        cls.getDeclaredMethod("addApplicationListener", Class.forName("com.apple.eawt.ApplicationListener")).invoke(cls.newInstance(), AppListenerProxy.newInstance(Class.forName("com.apple.eawt.ApplicationAdapter").newInstance(), controller));
    }

    /* loaded from: classes5.dex */
    static class AppListenerProxy implements InvocationHandler {
        private Controller<JFrame> appController;
        private Object object;

        public static Object newInstance(Object obj, Controller<JFrame> controller) {
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new AppListenerProxy(obj, controller));
        }

        private AppListenerProxy(Object obj, Controller<JFrame> controller) {
            this.object = obj;
            this.appController = controller;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Object obj2 = null;
            try {
                if (!"handleQuit".equals(method.getName())) {
                    obj2 = method.invoke(this.object, objArr);
                } else if (this.appController != null) {
                    this.appController.dispose();
                    this.appController.getView().dispose();
                }
            } catch (Exception unused) {
            }
            return obj2;
        }
    }
}
