package com.yulian.framework.activity.baseactivity;

import com.yulian.baseandroid.R;
import com.yulian.framework.Constant;
import com.yulian.framework.RequestInfo;
import com.yulian.framework.activity.common.BaseTask;
import com.yulian.framework.activity.common.DataCallback;
import com.yulian.framework.utils.ViewUtil;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * BASE_FragmentActivity
 * @author Administrator
 *
 */
public abstract class BASE_FragmentActivity extends FragmentActivity {
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
        //初始化
		init();
	}

	// 初始化
	private void init() {
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
	}

	/**
	 * 设置布局
	 */
	protected abstract void loadViewLayout();

	/**
	 * findViewById
	 */
	protected abstract void findViewById();

	/**
	 * 监听事件
	 */
	protected abstract void setListener();
	
	/**
	 * 需要自动向后台请求数据,做查询功能的话可以使用
	 */
	protected abstract void processLogic();

	/**
	 * 网络请求
	 * @param reqInfo 请求参数
	 * @param callBack 回调解析
	 * @param context 上下文，这里是为了防止dialog导致窗体泄露才传递，其实并没用到，由于有过先例，该参数先保留
	 */
	protected void getDataFromServer(RequestInfo reqInfo,DataCallback callBack ) {
		Log.i("getDataFromServer", "jin ru getDataFromServer()");
		//Handler对象
		BaseHandler handler = new BaseHandler(this,callBack);
		//子线程用户和服务端交互
		BaseTask task = new BaseTask(this, reqInfo, handler);
		//数据加载提示框
		showProgressDialog(this);
		new Thread(task).start();
	}
	/**
	 * Handler
	 * @author Administrator
	 *
	 */
	private class BaseHandler extends Handler {
		private Context cxt;
		private DataCallback callBack;
		//构造器
		public BaseHandler() {
			super();
		}
		public BaseHandler(Context cxt,DataCallback callBack) {
			super();
			this.cxt=cxt;
			this.callBack = callBack;
		}
		@Override
		public void handleMessage(Message msg) {
			closeProgressDialog();
			if(msg.what==Constant.SUCCESS){
				if(msg.obj==null){
					ViewUtil.showToast(cxt, getResources().getString(R.string.data_null));
					return;
				}
				callBack.processData(msg.obj);
			}
			else{
				callBack.getDataFailed();
			}
		}
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
