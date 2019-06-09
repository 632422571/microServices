package cn.globalcash.spring.boot.login;

import cn.globalcash.spring.boot.conf.Conf;
import cn.globalcash.spring.boot.store.SsoLoginStore;
import cn.globalcash.spring.boot.store.SsoSessionIdHelper;
import cn.globalcash.spring.boot.user.GcSsoUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class SsoTokenLoginHelper {
    /**
     * client login
     *
     * @param sessionId
     * @param gcUser
     */
    public static void login(String sessionId, GcSsoUser gcUser) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        SsoLoginStore.put(storeKey, gcUser);
    }

    /**
     * client logout
     *
     * @param sessionId
     */
    public static void logout(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return;
        }

        SsoLoginStore.remove(storeKey);
    }

    /**
     * client logout
     *
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        logout(headerSessionId);
    }


    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static GcSsoUser loginCheck(String  sessionId){

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        GcSsoUser gcUser = SsoLoginStore.get(storeKey);
        if (gcUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (gcUser.getVersion().equals(version)) {

                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - gcUser.getExpireFreshTime()) > gcUser.getExpireMinite()/2) {
                    gcUser.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(storeKey, gcUser);
                }

                return gcUser;
            }
        }
        return null;
    }


    /**
     * login check
     *
     * @param request
     * @return
     */
    public static GcSsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }
}
