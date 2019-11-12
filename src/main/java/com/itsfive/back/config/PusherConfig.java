package com.itsfive.back.config;

import com.pusher.rest.Pusher;

//This pusher object is used throughout the application
public class PusherConfig {
	public static Pusher pusher = new Pusher("898049", "979175850cb82f80c70a", "12d219090c835dc6f539");
	
	public PusherConfig() {

	}
	
	public static Pusher setObj() {
		pusher.setCluster("ap2");
		pusher.setEncrypted(true);
		return pusher;
	}
}
