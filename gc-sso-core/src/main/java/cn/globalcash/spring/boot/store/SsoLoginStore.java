package cn.globalcash.spring.boot.store;

import cn.globalcash.spring.boot.conf.Conf;
import cn.globalcash.spring.boot.user.GcSsoUser;
import cn.globalcash.spring.boot.util.JedisUtil;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class SsoLoginStore {
    private static int redisExpireMinite = 1440;    // 1440 minite, 24 hour
    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }
    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static GcSsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            GcSsoUser gcUser = (GcSsoUser) objectValue;
            return gcUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param gcUser
     */
    public static void put(String storeKey, GcSsoUser gcUser) {
        String redisKey = redisKey(storeKey);
        JedisUtil.setObjectValue(redisKey, gcUser, redisExpireMinite * 60);  // minite to second
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }
}
