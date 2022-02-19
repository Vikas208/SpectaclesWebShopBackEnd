package com.example.SpectaclesWebShop.Service;

import org.glassfish.jersey.internal.guava.CacheBuilder;
import org.glassfish.jersey.internal.guava.CacheLoader;
import org.glassfish.jersey.internal.guava.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class otpService {

    private static final int EXPIRE_MINS = 5;
    private LoadingCache otpCache;

    public otpService() {
        super();
        otpCache = CacheBuilder.newBuilder().expireAfterAccess(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader() {
            @Override
            public Object load(Object o) throws Exception {
                return null;
            }

        });
    }

    public int GenerateOtp(String key) {

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        this.otpCache.put(key, otp);
        return otp;
    }

    public int getOtp(String key) {
        try {
            return (int) otpCache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void clearOTP(String key) {
        this.otpCache.put(key, 0);
    }
}
