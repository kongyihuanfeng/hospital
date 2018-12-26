package com.yuanjun.weixindemo.bean;
/**
 * 
 * 类名称: VoiceMessage
 * 类描述: 语音消息
 * @author yuanjun
 * 创建时间:2017年12月8日下午6:48:36
 */
public class VoiceMessage extends BaseMessage{
	
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
	
}
