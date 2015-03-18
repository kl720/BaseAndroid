package com.yulian.framework.activity.common;

import com.lidroid.xutils.exception.HttpException;

public abstract class XDataCallback<T> {
	public abstract void processData(T resultData); //请求成功，针对数据进行处理
	public abstract void getDataFailed(HttpException httpException, String msg);// 网络请求失败
}
