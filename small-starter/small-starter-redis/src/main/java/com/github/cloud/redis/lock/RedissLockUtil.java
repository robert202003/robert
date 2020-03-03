package com.github.cloud.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁工具类
 */
public class RedissLockUtil {

    private static DistributedLocker redisLock;

    public static void setLocker(DistributedLocker locker) {
        redisLock = locker;
    }

    public static void lock(String lockKey) {
        redisLock.lock(lockKey);
    }

    public static void unlock(String lockKey) {
        redisLock.unlock(lockKey);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static void lock(String lockKey, int timeout) {
        redisLock.lock(lockKey, timeout);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public static void lock(String lockKey, TimeUnit unit , int timeout) {
        redisLock.lock(lockKey, unit, timeout);
    }
}
