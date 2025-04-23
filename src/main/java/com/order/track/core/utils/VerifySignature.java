package com.order.track.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.order.track.adapter.constant.AppConstant;

public class VerifySignature {

	public boolean verifySignature(String orderId, String paymentId, String signature) {
	    try {
	        String payload = orderId + "|" + paymentId;
	        String expected = hmacSha256(payload, AppConstant.RAZORPAY_SECRET);

	        return expected.equals(signature);
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	private String hmacSha256(String data, String secret) throws Exception {
	    Mac sha256Hmac = Mac.getInstance("HmacSHA256");
	    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	    sha256Hmac.init(secretKey);
	    byte[] hash = sha256Hmac.doFinal(data.getBytes());
	    return Hex.encodeHexString(hash);
	}
}
