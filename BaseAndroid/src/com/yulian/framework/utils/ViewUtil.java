package com.yulian.framework.utils;

import com.yulian.baseandroid.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * View类
 * @author Administrator
 *
 */
public class ViewUtil {

	public static Dialog createDialog(Context context, String msg) {
		View view = LayoutInflater.from(context).inflate(R.layout.loading_view,null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.dialog_view);// 加载布局
		TextView tv_dialog_content = (TextView) view.findViewById(R.id.tv_dialog_content);// 提示文字
		tv_dialog_content.setText(msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 返回键不取消
		loadingDialog.setContentView(layout, 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		return loadingDialog;
	}

	/**
	 * toast提示
	 * @param context
	 * @param msg 显示内容        
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	/**
	 * toast提示
	 * @param context
	 * @param msg 显示内容        
	 */
	public static void showLongToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
