package org.eclipse.jetty.client.security;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.client.HttpDestination;
import org.eclipse.jetty.client.HttpEventListenerWrapper;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class SecurityListener extends HttpEventListenerWrapper {
    private static final Logger LOG = Log.getLogger(SecurityListener.class);
    private int _attempts = 0;
    private HttpDestination _destination;
    private HttpExchange _exchange;
    private boolean _needIntercept;
    private boolean _requestComplete;
    private boolean _responseComplete;

    public SecurityListener(HttpDestination httpDestination, HttpExchange httpExchange) {
        super(httpExchange.getEventListener(), true);
        this._destination = httpDestination;
        this._exchange = httpExchange;
    }

    protected String scrapeAuthenticationType(String str) {
        if (str.indexOf(StringUtils.SPACE) == -1) {
            return str.toString().trim();
        }
        String str2 = str.toString();
        return str2.substring(0, str2.indexOf(StringUtils.SPACE)).trim();
    }

    protected Map<String, String> scrapeAuthenticationDetails(String str) {
        HashMap hashMap = new HashMap();
        StringTokenizer stringTokenizer = new StringTokenizer(str.substring(str.indexOf(StringUtils.SPACE) + 1, str.length()), Constants.ACCEPT_TIME_SEPARATOR_SP);
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            String[] split = nextToken.split("=");
            if (split.length == 2) {
                hashMap.put(split[0].trim(), StringUtil.unquote(split[1].trim()));
            } else {
                Logger logger = LOG;
                logger.debug("SecurityListener: missed scraping authentication details - " + nextToken, new Object[0]);
            }
        }
        return hashMap;
    }

    @Override // org.eclipse.jetty.client.HttpEventListenerWrapper, org.eclipse.jetty.client.HttpEventListener
    public void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("SecurityListener:Response Status: " + i, new Object[0]);
        }
        if (i != 401 || this._attempts >= this._destination.getHttpClient().maxRetries()) {
            setDelegatingResponses(true);
            setDelegatingRequests(true);
            this._needIntercept = false;
        } else {
            setDelegatingResponses(false);
            this._needIntercept = true;
        }
        super.onResponseStatus(buffer, i, buffer2);
    }

    @Override // org.eclipse.jetty.client.HttpEventListenerWrapper, org.eclipse.jetty.client.HttpEventListener
    public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("SecurityListener:Header: " + buffer.toString() + " / " + buffer2.toString(), new Object[0]);
        }
        if (!isDelegatingResponses() && HttpHeaders.CACHE.getOrdinal(buffer) == 51) {
            String obj = buffer2.toString();
            String scrapeAuthenticationType = scrapeAuthenticationType(obj);
            Map<String, String> scrapeAuthenticationDetails = scrapeAuthenticationDetails(obj);
            RealmResolver realmResolver = this._destination.getHttpClient().getRealmResolver();
            if (realmResolver != null) {
                Realm realm = realmResolver.getRealm(scrapeAuthenticationDetails.get("realm"), this._destination, "/");
                if (realm == null) {
                    Logger logger2 = LOG;
                    logger2.warn("Unknown Security Realm: " + scrapeAuthenticationDetails.get("realm"), new Object[0]);
                } else if ("digest".equalsIgnoreCase(scrapeAuthenticationType)) {
                    this._destination.addAuthorization("/", new DigestAuthentication(realm, scrapeAuthenticationDetails));
                } else if ("basic".equalsIgnoreCase(scrapeAuthenticationType)) {
                    this._destination.addAuthorization("/", new BasicAuthentication(realm));
                }
            }
        }
        super.onResponseHeader(buffer, buffer2);
    }

    @Override // org.eclipse.jetty.client.HttpEventListenerWrapper, org.eclipse.jetty.client.HttpEventListener
    public void onRequestComplete() throws IOException {
        this._requestComplete = true;
        if (!this._needIntercept) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("onRequestComplete, delegating to super with Request complete=" + this._requestComplete + ", response complete=" + this._responseComplete + StringUtils.SPACE + this._exchange, new Object[0]);
            }
            super.onRequestComplete();
        } else if (!this._requestComplete || !this._responseComplete) {
            if (LOG.isDebugEnabled()) {
                Logger logger2 = LOG;
                logger2.debug("onRequestComplete, Response not yet complete onRequestComplete, calling super for " + this._exchange, new Object[0]);
            }
            super.onRequestComplete();
        } else {
            if (LOG.isDebugEnabled()) {
                Logger logger3 = LOG;
                logger3.debug("onRequestComplete, Both complete: Resending from onResponseComplete " + this._exchange, new Object[0]);
            }
            this._responseComplete = false;
            this._requestComplete = false;
            setDelegatingRequests(true);
            setDelegatingResponses(true);
            this._destination.resend(this._exchange);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListenerWrapper, org.eclipse.jetty.client.HttpEventListener
    public void onResponseComplete() throws IOException {
        this._responseComplete = true;
        if (!this._needIntercept) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("OnResponseComplete, delegating to super with Request complete=" + this._requestComplete + ", response complete=" + this._responseComplete + StringUtils.SPACE + this._exchange, new Object[0]);
            }
            super.onResponseComplete();
        } else if (!this._requestComplete || !this._responseComplete) {
            if (LOG.isDebugEnabled()) {
                Logger logger2 = LOG;
                logger2.debug("onResponseComplete, Request not yet complete from onResponseComplete,  calling super " + this._exchange, new Object[0]);
            }
            super.onResponseComplete();
        } else {
            if (LOG.isDebugEnabled()) {
                Logger logger3 = LOG;
                logger3.debug("onResponseComplete, Both complete: Resending from onResponseComplete" + this._exchange, new Object[0]);
            }
            this._responseComplete = false;
            this._requestComplete = false;
            setDelegatingResponses(true);
            setDelegatingRequests(true);
            this._destination.resend(this._exchange);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListenerWrapper, org.eclipse.jetty.client.HttpEventListener
    public void onRetry() {
        this._attempts++;
        setDelegatingRequests(true);
        setDelegatingResponses(true);
        this._requestComplete = false;
        this._responseComplete = false;
        this._needIntercept = false;
        super.onRetry();
    }
}
