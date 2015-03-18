package com.yulian.framework.activity.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yulian.framework.Constant;
import com.yulian.framework.RequestInfo;
import com.yulian.framework.utils.NetUtil;

public class BaseTask implements Runnable {
	
	private Context context;
	private RequestInfo reqInfo;
	private Handler handler;
	//构造器
	public BaseTask() {
		super();
	}
	public BaseTask(Context context, RequestInfo reqInfo, Handler handler) {
		super();
		this.context = context;
		this.reqInfo = reqInfo;
		this.handler = handler;
	}
	@Override
	public void run() {
		String result=null;
		Message msg=new Message();
		//判断是否连接网络
		if(NetUtil.hasNetwork(context)){
			//判断请求方式（GET or POST）
			if(reqInfo.method.equalsIgnoreCase("GET")){
				result=NetUtil.get(reqInfo);
			}
			else{
				result=NetUtil.post(reqInfo);
			}
			msg.what=Constant.SUCCESS;
			msg.obj=result;
		}else{
			msg.what=Constant.FAILED;
			msg.obj=result;
		}
		handler.sendMessage(msg);
	}

}
