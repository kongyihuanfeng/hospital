package com.yuanjun.weixindemo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMessage {

	private String time;
	
	private String message;
	
	private String msgType = "2";
	
	public SystemMessage() {
		
	}
	
	public SystemMessage(Date time,String message) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.time = dataFormat.format(time);
		this.message = message;
	}
}
