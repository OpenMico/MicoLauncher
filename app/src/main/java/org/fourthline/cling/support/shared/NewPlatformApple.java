package org.fourthline.cling.support.shared;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes5.dex */
public class NewPlatformApple {
    public static void setup(ShutdownHandler shutdownHandler, String str) throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", str);
        System.setProperty("apple.awt.showGrowBox", "true");
        Class<?> cls = Class.forName("com.apple.eawt.Application");
        cls.getDeclaredMethod("addApplicationListener", Class.forName("com.apple.eawt.ApplicationListener")).invoke(cls.newInstance(), AppListenerProxy.newInstance(Class.forName("com.apple.eawt.ApplicationAdapter").newInstance(), shutdownHandler));
    }

    /* loaded from: classes5.dex */
    static class AppListenerProxy implements InvocationHandler {
        private Object object;
        private ShutdownHandler shutdownHandler;

        public static Object newInstance(Object obj, ShutdownHandler shutdownHandler) {
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new AppListenerProxy(obj, shutdownHandler));
        }

        private AppListenerProxy(Object obj, ShutdownHandler shutdownHandler) {
            this.object = obj;
            this.shutdownHandler = shutdownHandler;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Object obj2 = null;
            try {
                if (!"handleQuit".equals(method.getName())) {
                    obj2 = method.invoke(this.object, objArr);
                } else if (this.shutdownHandler != null) {
                    this.shutdownHandler.shutdown();
                }
            } catch (Exception unused) {
            }
            return obj2;
        }
    }
}
