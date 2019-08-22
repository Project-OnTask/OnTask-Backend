package com.itsfive.back.config;

import com.pusher.rest.Pusher;

public class PusherConfig {
	public static Pusher pusher = new Pusher("812414", "1f56dbce61dcd7d6da58", "6ae18872d1d3f335110e");
	
	public PusherConfig() {

	}
	
	public static Pusher setObj() {
		pusher.setCluster("ap2");
		pusher.setEncrypted(true);
		return pusher;
	}
}
