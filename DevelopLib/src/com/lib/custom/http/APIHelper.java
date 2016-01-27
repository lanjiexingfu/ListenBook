package com.lib.custom.http;


/**
 * Created by chiely on 14-7-11.
 */
public class APIHelper {

    /**  */
    private static String Service = "";
    /**  */
    private static String Domain = "";

    public static String getService() {
        return Service;
    }

    public static void setService(String service, String port) {
        Service = service + ":" + port;
    }

    public static void setService(String service) {
        Service = service;
    }

    public static String getDomain() {
        return Domain;
    }

    public static void setDomain(String domain) {
        Domain = domain;
    }

    public static String getCompleteUrl(String filePath) {
        return String.format("http://%s/%s", Service, filePath);
    }

    public static String getUrl(String action) {
        return String.format("http://%s%s%s", Service, Domain, action);
    }
}
