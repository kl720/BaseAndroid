package com.yulian.framework.activity.common;

public abstract class DataCallback<T> {
	public abstract void processData(T resultData); //请求成功，针对数据进行处理
	public abstract void getDataFailed();// 网络请求失败
}
