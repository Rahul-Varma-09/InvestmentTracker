package com.investment.app.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.investment.app.entities.User;


@Service
public class OTPUtility {

	@Value("${login_otp_expTimeout}")
	private String login_otp_expTimeout;

	//private static final Integer EXPIRE_MINS = 2;
	private LoadingCache<String, User> otpCache;

	public OTPUtility() {
		super();
	}

	@PostConstruct
	public void otpUtils() {
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(Long.parseLong(login_otp_expTimeout), TimeUnit.MINUTES)
				.build(new CacheLoader<String, User>() {
					public User load(String key) {
						return new User();
					}
				});
	}

	public User generateOTP(String userNameKey, User user) {
		System.out.println(otpCache.toString());
		try {
			User loginObj = otpCache.getIfPresent(userNameKey);
			if (loginObj != null) {
//				DefaultLogger.info("TEST :: Login Request Is Already in Process");
//				loginObj.setErrorCode("Login Request Is Already In Process");
				return loginObj;
			} else {
				Random random = new Random();
				int otp = 100000 + random.nextInt(900000);
				user.setOtp("" + otp);
//				DefaultLogger.info("Username :" + userNameKey + " " + "Insertion Object: " + loginVerification.toString());

				System.out.println(otpCache.toString());

				otpCache.put(userNameKey, user);

				System.out.println(otpCache.asMap());
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// This method is used to return the OTP number against Key->Key values is
	// username
	public User getOtp(String userNameKey) {
		try {
			return otpCache.get(userNameKey);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean verifyOTP(String key, User user) {
//		DefaultLogger.info("Login Object At Verify Otp : " + loginVerification.toString());
		try {
			int otp = Integer.parseInt(user.getOtp());
			User cachedObj = otpCache.get(key);
			if (cachedObj == null) {
				return false;
			}
//			DefaultLogger.info("Cached Object: " + cachedObj.toString());
			int cachedOtp = Integer.parseInt(cachedObj.getOtp());
//			DefaultLogger.info("Entered Otp : " + otp + " " + "Cached Otp : " + cachedOtp);
			if (otp == cachedOtp) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	// This method is used to clear the OTP cached already
	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}

}
