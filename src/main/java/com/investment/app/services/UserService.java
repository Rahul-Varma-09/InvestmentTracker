package com.investment.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.app.entities.User;
import com.investment.app.http.GenericResponse;
import com.investment.app.utils.MailUtility;
import com.investment.app.utils.OTPUtility;

@Service
public class UserService {
	
	@Autowired private OTPUtility otpUtility;
	@Autowired private MailUtility mailUtility;
	
	
	public GenericResponse<?> login(User user) {
		if (user.getRequestType() == 0) {
			
			User userWithGeneratedOtp = otpUtility.generateOTP(user.getEmail(), user);
			
			if (userWithGeneratedOtp != null && userWithGeneratedOtp.getOtp() != null) {
				
				Boolean mailSent = mailUtility.sendEmail(userWithGeneratedOtp.getEmail(), userWithGeneratedOtp.getOtp());
				
				if (mailSent) {
					
					return GenericResponse.builder().code("OK").message("OTP Sent to Registered Email Address").body(true).build();
				
				}
				
				return GenericResponse.builder().code("ERR").message("Something Wrong with the Mail Server").body(false).build();
			}			
			return GenericResponse.builder().code("ERR").message("Unable to Generate Otp").body(false).build();
		}
		return GenericResponse.builder().code("ERR").message("Unable to Verify Email Id").body(false).build();
	}

	
	public GenericResponse<?> verifyOtp(User user) {
		
		if (user.getRequestType() == 1) {
			
			Boolean otpVerified = otpUtility.verifyOTP(user.getEmail(), user);
			
			if (otpVerified) {
				
				otpUtility.clearOTP(user.getEmail());
				
				return GenericResponse.builder().code("OK").message("OTP Verified Successfully").body(true).build();
				
			}
			
			return GenericResponse.builder().code("ERR").message("Please Enter Valid OTP").body(false).build();
			
		}
		
		return GenericResponse.builder().code("ERR").message("Bad Request Type...Please Verfiy Email First").body(false).build();
		
		
	}

}
