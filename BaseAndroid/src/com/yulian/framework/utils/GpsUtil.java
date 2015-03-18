package com.yulian.framework.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Gps类
 * @author Administrator
 *
 */
public class GpsUtil {
	/**
     * 判断GPS是否打开方法，并引导用户进行GPS打开操作(取消不退出程序)
     * @param context
     */
    public static void isValidateGPS(final Context context,final Activity activity) {

        LocationManager lmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("GPS功能没有开启，点击确定，跳转至GPS设置界面。取消则会退出系统");
            builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent("com.inspur.bss.EXITAPP");
                    activity.sendBroadcast(it);
                    dialog.dismiss();
                    return;
                }
            });
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                    Intent gpsintent = new Intent();
                    gpsintent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(gpsintent);
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }
    /**
     * 判断GPS是否打开方法，并引导用户进行GPS打开操作（取消退出程序）
     * @param context
     */
    public static void GPSIsOpen(final Context context) {
        LocationManager lmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("GPS功能没有开启，这将影响系统功能，点击确定，跳转至GPS设置界面。请点击取消，退出应用程序");
            builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent("com.inspur.bss.EXITAPP");
                    context.sendBroadcast(it);
                    dialog.dismiss();
                    return;
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) {
                    Intent gpsintent = new Intent();
                    gpsintent
                            .setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(gpsintent);
                }
            });
            builder.show();
        }
    }
    /**
     * 清除基本信息方法（GPS信息）
     * @param context
     */
    public static void clearConfigInfo(Context context) {
        OutputStreamWriter osw = null;
        try {
            FileOutputStream fs = context.openFileOutput("gps.txt",Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fs);
            osw.write("", 0, 0);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 清除配置信息（服务器信息）
     * @param context
     */
    public static void clearConfiginfo(Context context) {

        OutputStreamWriter osw = null;
        try {
            FileOutputStream fs = context.openFileOutput("iscomestation.txt", Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fs);
            osw.write("", 0, 0);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /************ 计算距离************/ 
    private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	/**
	 * 
	 * @param lat1 纬度
	 * @param lng1 经度
	 * @param lat2 纬度
	 * @param lng2 经度
	 * @return 距离
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
		
		try {
			double radLat1 = rad(lat1);
			double radLat2 = rad(lat2);
			double a = radLat1 - radLat2;
			double b = rad(lng1) - rad(lng2);
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;
		   return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
