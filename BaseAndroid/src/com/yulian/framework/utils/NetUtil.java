package com.yulian.framework.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.yulian.framework.RequestInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 网络相关工具类
 * @author Administrator
 *
 */
public class NetUtil {
	/**
	 * post 请求
	 * @param reqInfo
	 * @return
	 */
	public static String post(RequestInfo reqInfo) {
		DefaultHttpClient httpclient = null;
		try {

			httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(reqInfo.requestUrl);
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
			HttpConnectionParams.setSoTimeout(httpParams, 80000);
			httpost.setParams(httpParams);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("param", reqInfo.params));
			
			if(reqInfo.fileMap == null){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);
				httpost.setEntity(entity);
				HttpResponse response = httpclient.execute(httpost);
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity(),
							HTTP.UTF_8);
					if (entity != null) {
						entity.consumeContent();
					}
					return result;
				}
				
			} else {
				
				Set<String> set = reqInfo.fileMap.keySet();
				MultipartEntity entity = new MultipartEntity();
				for(String fileKey : set){
					entity.addPart(fileKey, new FileBody(reqInfo.fileMap.get(fileKey)));
				}
				entity.addPart("param", new StringBody(reqInfo.params, Charset.forName("UTF-8")));
				httpost.setEntity(entity);
				HttpResponse response = httpclient.execute(httpost);
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity(),
							HTTP.UTF_8);
					if (entity != null) {
						entity.consumeContent();
					}
					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpclient)
				httpclient.getConnectionManager().shutdown();
		}

		return null;
	}
	
	/**
	 * get 请求
	 * @param reqInfo
	 * @return
	 */
	public static String get(RequestInfo reqInfo) {
		String params =reqInfo.params;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(reqInfo.requestUrl + params);
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		get.setParams(httpParams);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != client)
				client.getConnectionManager().shutdown();
		}
		return null;
	}


    public static String get(String path, String params) {
        System.out.println(path+params);
        try {
            params = URLEncoder.encode(params, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(path + params);
        HttpParams paramss = new BasicHttpParams();//
        HttpConnectionParams.setConnectionTimeout(paramss, 80000);
        HttpConnectionParams.setSoTimeout(paramss, 80000);
        get.setParams(paramss);
        try {
            HttpResponse response = client.execute(get);
            if (response!=null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null)
                client.getConnectionManager().shutdown();
        }
        return null;
    }
	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	/**
	 * 判断WIFI网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	/**
	 * 判断MOBILE网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	/**
	 * 获取当前网络连接的类型信息
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
	/**
	 * 打开网络设置界面
	 * @param activity
	 */
	public static void openSetting(Activity activity){
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
}
