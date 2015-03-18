package com.yulian.framework.activity.baseactivity;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yulian.baseandroid.R;
import com.yulian.framework.RequestInfo;
import com.yulian.framework.activity.common.XDataCallback;
import com.yulian.framework.utils.ViewUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * XBASE_Activity(服务端请求使用xutil框架)
 * @author Administrator
 *
 */
public class XBASE_Activity extends Activity {
	/* actionBar */
	protected ActionBar actionBar;
	/* 提示框 */
	private Dialog dialog;	
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true); //4.x以上响应点击图标
            actionBar.setDisplayHomeAsUpEnabled(true);//设置显示箭头图标
        }

	}
	/**
	 * 网络请求
	 * @param reqInfo 请求参数
	 * @param callBack 回调解析
	 * @param context 上下文，这里是为了防止dialog导致窗体泄露才传递，其实并没用到，由于有过先例，该参数先保留
	 */
	protected void getDataFromServer(RequestInfo reqInfo,final XDataCallback callBack ) {
		
		//加载数据加载提示框
		showProgressDialog(this);
		
		String MethodType=reqInfo.method;
		HttpUtils http = new HttpUtils();
		
		RequestParams params=new RequestParams();
		String param=reqInfo.params;
		params.addQueryStringParameter("param", param==null?"":param);
		Log.i("param", "请求参数:"+param);
		
		http.send(MethodType.equalsIgnoreCase("GET")?HttpMethod.GET:HttpMethod.POST, reqInfo.requestUrl, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException httpException, String msg) {
				closeProgressDialog();
				//打印异常日志
				Log.e("request exception", "exceptionCode="+httpException.getExceptionCode()+",msg="+msg);
				httpException.printStackTrace();
				callBack.getDataFailed(httpException, msg);
			}
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				closeProgressDialog();
				callBack.processData(responseInfo.result);
			}
		});	
	}
	/**
	 * show the progressDialog
	 * @param context
	 */
	protected void showProgressDialog(Context context) {
		if (dialog == null){
			dialog = ViewUtil.createDialog(context,getString(R.string.LoadContent));	
		}
		dialog.show();
	}

	/**
	 * close the progressDialog
	 */
	protected void closeProgressDialog() {
		if (dialog != null && this.dialog.isShowing()){
			this.dialog.dismiss();
		}
		dialog = null;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
