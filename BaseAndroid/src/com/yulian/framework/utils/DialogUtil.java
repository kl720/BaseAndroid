package com.yulian.framework.utils;

import java.util.ArrayList;
import java.util.List;
import com.yulian.baseandroid.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

	private static int selectID;
	private static List<Integer> selectIDList;
	/**
	 * 确定取消信息框
	 * @param drawableID
	 * @param cxt
	 * @param title
	 * @param dialogCallBack
	 * @return
	 */
	public static Builder createComfirmOrCancelDialog(Integer drawableID,Context cxt,String title,final DialogCallBack dialogCallBack ){
		Builder builder=new Builder(cxt);
		if(drawableID!=null){
			builder.setIcon(drawableID);
		}
		else{
			builder.setIcon(R.drawable.ic_launcher);
		}
		builder.setTitle(title);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				dialogCallBack.isComfirmOrCancel(true);
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				dialogCallBack.isComfirmOrCancel(false);
			}		
		});
		return builder;
	}
	/**
	 * 多个按钮信息框
	 * @param cxt
	 * @param title
	 * @param content
	 * @param array
	 * @param dialogCallBackItem
	 * @return
	 */
	public static Builder createMorechoiceDialog(Integer drawableID,Context cxt,String title,String content,String[] array,final DialogCallBackItem dialogCallBackItem){
	
		Builder builder=new Builder(cxt);
		if(drawableID!=null){
			builder.setIcon(drawableID);
		}
		else{
			builder.setIcon(R.drawable.ic_launcher);
		}
		builder.setTitle(title);
		builder.setMessage(content);
		
		builder.setPositiveButton(array[0], new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogCallBackItem.getItem(0);
			}	
		});
		builder.setNeutralButton(array[1], new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogCallBackItem.getItem(1);
			}	
		});
		builder.setNegativeButton(array[2], new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogCallBackItem.getItem(2);
			}	
		});
		return builder;
	}
	/**
	 * 列表选择框
	 * @param cxt
	 * @param title
	 * @param array
	 * @param dialogCallBackItem
	 * @return
	 */
	public static Builder createListDialog(Context cxt,String title,String[] array,final DialogCallBackItem dialogCallBackItem){
		
		Builder builder=new Builder(cxt);
		builder.setTitle(title);
		builder.setItems(array, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogCallBackItem.getItem(which);
			}
		});
		return builder;
	}
	/**
	 * 单项选择列表框
	 * @param cxt
	 * @param title
	 * @param array
	 * @param dialogCallBackItem
	 * @return
	 */
	public static Builder createSingleDialog(Integer drawableID,Context cxt,String title,String[] array,final DialogCallBackItem dialogCallBackItem) {
		
		Builder builder=new Builder(cxt);
		if(drawableID!=null){
			builder.setIcon(drawableID);
		}
		else{
			builder.setIcon(R.drawable.ic_launcher);
		}
		builder.setTitle(title);

		selectID=0;//默认选中第一项
		builder.setSingleChoiceItems(array, selectID,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				selectID = whichButton;
			}		
		});
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				dialogCallBackItem.getItem(selectID);
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				
			}
		});	
		return builder;
	}
	/**
	 * 多项选择列表框
	 * @param drawableID
	 * @param cxt
	 * @param title
	 * @param array
	 * @param dialogCallBackItem
	 * @return
	 */
	public static Builder createMultipleDialog(Integer drawableID,Context cxt,String title,String[] array,final DialogCallBackMulItem dialogCallBackMulItem){
		
		selectIDList=new ArrayList<Integer>();
		Builder builder=new Builder(cxt);
		builder.setTitle(title);
		if(drawableID!=null){
			builder.setIcon(drawableID);
		}
		else{
			builder.setIcon(R.drawable.ic_launcher);
		}
		boolean[] mr_choices=new boolean[array.length];
		for(int i=0;i<mr_choices.length;i++){
			mr_choices[i]=false;
		}
		builder.setMultiChoiceItems(array, mr_choices,new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked){
					selectIDList.add(Integer.valueOf(which));
				}
				else{
					selectIDList.remove(Integer.valueOf(which));
				}
			}
		});
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {
				dialogCallBackMulItem.getItems(selectIDList);
			}				
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int whichButton) {

			}
		});
		return builder;
	}
	
	public abstract interface DialogCallBack{
		public abstract void isComfirmOrCancel(boolean flag);
	}
	public abstract interface DialogCallBackItem{
		public abstract void getItem(int item);
	}
	public abstract interface DialogCallBackMulItem{
		public abstract void getItems(List<Integer> items);
	}
	
}
