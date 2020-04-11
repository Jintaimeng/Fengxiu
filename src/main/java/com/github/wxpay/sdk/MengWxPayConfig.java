package com.github.wxpay.sdk;

import java.io.InputStream;

public class MengWxPayConfig extends WXPayConfig {

    public String getAppID() {
        return "wx41e234cedd658326";
    }


    public String getMchID() {
        return "";
    }


    public String getKey() {
        return "";
    }

    public InputStream getCertStream() {
        return null;
    }

    public int getHttpConnectTimeoutMs() {
        return 6 * 1000;
    }

    public int getHttpReadTimeoutMs() {
        return 8 * 1000;
    }

    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

}
