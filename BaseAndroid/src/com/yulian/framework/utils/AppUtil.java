package com.yulian.framework.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 跟App相关的辅助类
 * @author Administrator
 *
 */
public class AppUtil{
	private AppUtil(){
		// cannot be instantiated
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	/**
	 * 获取应用程序名称
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context){
		
		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * [获取应用程序版本名称信息]
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context){
		
		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
     * 发送通知，点击查看
     * @param shorttitle
     * @param title
     * @param content
     * @param icon
     * @param intent
     */
    public static void sendNotification(Context context,String shorttitle,String title,String content,Intent intent){
    	
        try {
        	int icon = android.R.drawable.stat_notify_chat;
        	Notification notification = new Notification(icon,shorttitle, System.currentTimeMillis());
            //根据意图和当前对象去创建一个打开通知后转向的界面
            PendingIntent pending =PendingIntent.getActivity(context, 10, intent, 0);
            //第四个参数：当点击通知的时候打开应用的显示界面
            notification.setLatestEventInfo(context, title, content, pending);
            //设置通知的声音
            notification.defaults = Notification.DEFAULT_SOUND;
            //设置点击通知后，通知消失在状态栏
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            //通过内部服务取得通知管理器
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            //发送通知，参数1：通知的id，参数2:通知对象
            manager.notify(100,notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
