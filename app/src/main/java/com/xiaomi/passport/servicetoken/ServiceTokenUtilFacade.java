package com.xiaomi.passport.servicetoken;

/* loaded from: classes4.dex */
public final class ServiceTokenUtilFacade {
    private static final ServiceTokenUtilFacade sInstance = new ServiceTokenUtilFacade();

    private ServiceTokenUtilFacade() {
    }

    public static ServiceTokenUtilFacade getInstance() {
        return sInstance;
    }

    public IServiceTokenUtil buildMiuiServiceTokenUtil() {
        return new ServiceTokenUtilMiui();
    }

    public IServiceTokenUtil buildAMServiceTokenUtil(IAMUtil iAMUtil) {
        return new ServiceTokenUtilAM(iAMUtil);
    }

    public IAMUtil buildAMUtil() {
        return new AMUtilImpl(new AMKeys());
    }
}
