package cn.globalcash.spring.boot.store;

import cn.globalcash.spring.boot.user.GcSsoUser;

/**
 *  * make client sessionId
 *
 *      client: cookie = [userid#version]
 *      server: redis
 *                  key = [userid]
 *                  value = user (user.version, valid this)
 *
 * //   group         The same group shares the login status, Different groups will not interact
 * @author: gh
 * @date:2019/6/9
 */
public class SsoSessionIdHelper {

    /**
     * make client sessionId
     * @param gcSsoUser
     * @return
     */
    public static String makeSessionId(GcSsoUser gcSsoUser){
        String sessionId = gcSsoUser.getUserid().concat("_").concat(gcSsoUser.getVersion());
        return sessionId;
    }

    /**
     * parse storeKey from sessionId
     * @param sessionId
     * @return
     */
    public static String parseStoreKey(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[0]!=null
                    && sessionIdArr[0].trim().length()>0) {
                String userId = sessionIdArr[0].trim();
                return userId;
            }
        }
        return null;
    }

    /**
     * parse version from sessionId
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[1]!=null
                    && sessionIdArr[1].trim().length()>0) {
                String version = sessionIdArr[1].trim();
                return version;
            }
        }
        return null;
    }
}
