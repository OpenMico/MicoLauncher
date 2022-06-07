package com.xiaomi.accountsdk.request;

import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.accountsdk.account.PassportCAConstants;
import com.xiaomi.accountsdk.account.PassportCATokenManager;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.stat.CustomizedUrlStatUtil;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.io.IOException;
import java.util.UUID;

/* loaded from: classes2.dex */
public abstract class PassportLoginRequest extends PassportRequest {
    private static final String TAG = "PassportLoginRequest";
    private final PassportRequest request;

    protected abstract String getLoginType();

    protected abstract PassportCARequest makeCARequest(PassportCATokenManager passportCATokenManager, PassportRequestArguments passportRequestArguments);

    protected abstract PassportSimpleRequest makeHttpsRequest(PassportRequestArguments passportRequestArguments);

    public PassportLoginRequest(PassportRequestArguments passportRequestArguments) {
        PassportSimpleRequest makeHttpsRequest = makeHttpsRequest(passportRequestArguments);
        PassportFallbackableRequest requestTryCA = getRequestTryCA(passportRequestArguments, makeHttpsRequest);
        if (requestTryCA == null) {
            AccountLog.d(TAG, String.format("CA-Request not ready for login %s, fallback to https way", getLoginType()));
            this.request = makeHttpsRequest;
            return;
        }
        this.request = requestTryCA;
    }

    private PassportFallbackableRequest getRequestTryCA(PassportRequestArguments passportRequestArguments, PassportSimpleRequest passportSimpleRequest) {
        String caUrl;
        PassportCATokenManager instance = PassportCATokenManager.getInstance();
        if (instance == null || !instance.isReady() || (caUrl = instance.getCaUrl(passportRequestArguments.url)) == null) {
            return null;
        }
        String str = PassportCAConstants.IMPL_VERSION;
        PassportRequestArguments copy = passportRequestArguments.copy();
        String str2 = copy.params.get("sid");
        copy.setUrl(caUrl);
        copy.putParamOpt("_ver", str);
        copy.params.remove("sid");
        copy.putParamOpt("_sid", str2);
        copy.urlParams.easyPutOpt("_ver", str);
        copy.urlParams.easyPutOpt("_sid", str2);
        copy.putHeaderOpt("x-mistats-header", UUID.randomUUID().toString());
        return new PassportFallbackableRequest(makeCARequest(instance, copy), passportSimpleRequest) { // from class: com.xiaomi.accountsdk.request.PassportLoginRequest.1
            @Override // com.xiaomi.accountsdk.request.PassportFallbackableRequest
            protected boolean shouldTryRequest2(SimpleRequest.StringContent stringContent) {
                return stringContent == null;
            }

            @Override // com.xiaomi.accountsdk.request.PassportFallbackableRequest
            protected void onRequest1Success() {
                AccountLog.d(PassportLoginRequest.TAG, String.format("login %s with CA-Request succeeded to receive data from server", PassportLoginRequest.this.getLoginType()));
            }

            @Override // com.xiaomi.accountsdk.request.PassportFallbackableRequest
            protected void onRequest1Failed() {
                AccountLog.d(PassportLoginRequest.TAG, String.format("login %s with CA-Request failed to receive data from server", PassportLoginRequest.this.getLoginType()));
            }

            @Override // com.xiaomi.accountsdk.request.PassportFallbackableRequest
            protected boolean shouldTryRequest2(Exception exc) {
                return (exc instanceof IOException) || (exc.getCause() instanceof AuthenticationFailureException) || (exc.getCause() instanceof PassportCAException) || (exc.getCause() instanceof AccessDeniedException) || invalidResponseDueToHtmlOr302(exc.getCause());
            }

            private boolean invalidResponseDueToHtmlOr302(Throwable th) {
                return (th instanceof InvalidResponseException) && ((InvalidResponseException) th).isHtmlOr302;
            }
        };
    }

    @Override // com.xiaomi.accountsdk.request.PassportRequest
    public SimpleRequest.StringContent execute() throws IOException, PassportRequestException {
        CustomizedUrlStatUtil customizedUrlStatUtil;
        Object[] objArr = new Object[2];
        objArr[0] = getLoginType();
        try {
            objArr[1] = this.request instanceof PassportFallbackableRequest ? "withCA" : "withoutCA";
            customizedUrlStatUtil = new CustomizedUrlStatUtil(String.format("login/%s/%s", objArr), PassportCAConstants.IMPL_VERSION);
            customizedUrlStatUtil.startStat();
            try {
                try {
                    return this.request.execute();
                } catch (PassportRequestException e) {
                    if (e.getCause() instanceof PassportCAException) {
                        customizedUrlStatUtil.statError((Exception) e.getCause());
                    }
                    throw e;
                }
            } catch (IOException e2) {
                customizedUrlStatUtil.statError(e2);
                throw e2;
            }
        } finally {
            customizedUrlStatUtil.finishStat();
        }
    }

    public boolean isResultFromCA() {
        PassportRequest passportRequest = this.request;
        return (passportRequest instanceof PassportFallbackableRequest) && !((PassportFallbackableRequest) passportRequest).isRequest2Used();
    }

    /* loaded from: classes2.dex */
    public static class ByPassword extends PassportLoginRequest {
        private final MetaLoginData mmetaLoginData;
        private final String serviceId;
        private final String userId;

        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected String getLoginType() {
            return "byPassword";
        }

        public ByPassword(PassportRequestArguments passportRequestArguments, String str, String str2, MetaLoginData metaLoginData) {
            super(passportRequestArguments);
            this.userId = str;
            this.serviceId = str2;
            this.mmetaLoginData = metaLoginData;
        }

        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected PassportSimpleRequest makeHttpsRequest(PassportRequestArguments passportRequestArguments) {
            return new PassportSimpleRequest.PostAsString(passportRequestArguments) { // from class: com.xiaomi.accountsdk.request.PassportLoginRequest.ByPassword.1
                @Override // com.xiaomi.accountsdk.request.PassportSimpleRequest.PostAsString, com.xiaomi.accountsdk.request.PassportRequest
                public SimpleRequest.StringContent execute() throws IOException, PassportRequestException {
                    MetaLoginData metaLoginData = ByPassword.this.mmetaLoginData;
                    if (metaLoginData == null) {
                        try {
                            metaLoginData = XMPassport.getMetaLoginData(ByPassword.this.userId, ByPassword.this.serviceId);
                            if (metaLoginData == null) {
                                throw new PassportRequestException(new InvalidResponseException("Empty meta login data"));
                            }
                        } catch (InvalidUserNameException e) {
                            throw new PassportRequestException(e);
                        } catch (AccessDeniedException e2) {
                            throw new PassportRequestException(e2);
                        } catch (AuthenticationFailureException e3) {
                            throw new PassportRequestException(e3);
                        } catch (InvalidResponseException e4) {
                            throw new PassportRequestException(e4);
                        } catch (IOException e5) {
                            throw e5;
                        }
                    }
                    this.arguments.params.easyPut("_sign", metaLoginData.sign);
                    this.arguments.params.easyPut("qs", metaLoginData.qs);
                    this.arguments.params.easyPut("callback", metaLoginData.callback);
                    return super.execute();
                }
            };
        }

        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected PassportCARequest makeCARequest(PassportCATokenManager passportCATokenManager, PassportRequestArguments passportRequestArguments) {
            return new PassportCARequest(new PassportSimpleRequest.PostAsString(passportRequestArguments), passportCATokenManager);
        }
    }

    /* loaded from: classes2.dex */
    public static class ByPassToken extends PassportLoginRequest {
        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected String getLoginType() {
            return "byPassToken";
        }

        public ByPassToken(PassportRequestArguments passportRequestArguments) {
            super(passportRequestArguments);
        }

        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected PassportSimpleRequest makeHttpsRequest(PassportRequestArguments passportRequestArguments) {
            return new PassportSimpleRequest.GetAsString(passportRequestArguments);
        }

        @Override // com.xiaomi.accountsdk.request.PassportLoginRequest
        protected PassportCARequest makeCARequest(PassportCATokenManager passportCATokenManager, PassportRequestArguments passportRequestArguments) {
            return new PassportCARequest(new PassportSimpleRequest.GetAsString(passportRequestArguments), passportCATokenManager);
        }
    }
}
