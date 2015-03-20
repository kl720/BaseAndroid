package com.yulian.framework.view.hvscrolllistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yulian.baseandroid.R;
import com.yulian.framework.Constant;
import com.yulian.framework.view.hvscrolllistview.HScrollView.OnScrollChangedListener;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 数据适配器
 * @author Administrator
 *
 */
public class DataAdapter extends BaseAdapter {
	
	private Context context;
	private List<Map<String, Object>> list;
	private String[] arr_key;
	private RelativeLayout head;
	private boolean isShowSelect;
	
	
	
	private Map<Integer,Boolean> isCheckMap=new HashMap<Integer, Boolean>();//维护选择项
	
	
	public DataAdapter(Context context,List<Map<String, Object>> list,String[] arr_key,RelativeLayout head,boolean isShowSelect) {
		super();
		this.context=context;
		this.list=list;
		this.arr_key=arr_key;
		this.head=head;
		this.isShowSelect=isShowSelect;
		initNoCheck(false);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Map<String, Object> getItem(int item) {
		return list.get(item);
	}

	@Override
	public long getItemId(int item) {
		return item;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parentView) {
		
		final Map<String,Object> item=list.get(position);
		
		RelativeLayout convertView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.hvscrolllistview_item, null);
			
		if(position%2==1){	
			convertView.setBackgroundColor(Color.GRAY);
		}	
			
		synchronized (this) {

			//创建每行的第一列
			LinearLayout ll_first_column =(LinearLayout) convertView.findViewById(R.id.ll_first_column);
			if(isShowSelect){
				final CheckBox cb=(CheckBox) convertView.findViewById(R.id.cb_ischeck);
				cb.setVisibility(View.VISIBLE);
				cb.setChecked(isCheckMap.get(position));
				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						
						isCheckMap.put(position, arg1);
						
//						DataAdapter.this.notifyDataSetChanged();
					}
				});
				
				
			}
			TextView tv_f_column=new TextView(context);
			tv_f_column.setLayoutParams(Constant.HVS_LPARAMS);
			tv_f_column.setText(item.get(arr_key[0]).toString());
			tv_f_column.setTextSize(13);
			tv_f_column.setGravity(Gravity.CENTER_VERTICAL);
			ll_first_column.addView(tv_f_column);
			
			//创建每一行
			LinearLayout ll_contnet_txt=(LinearLayout) convertView.findViewById(R.id.ll_contnet);
			for(int j=1;j<arr_key.length;j++){
				String key=arr_key[j];
				TextView tv=new TextView(context);
				tv.setLayoutParams(Constant.HVS_LPARAMS);
				tv.setTextSize(13);
				tv.setGravity(Gravity.CENTER_VERTICAL);
				tv.setText(item.get(key).toString());
				ll_contnet_txt.addView(tv);
			}
			
			if(position==0){
				CheckBox t_cb=(CheckBox) head.findViewById(R.id.cb_ischeck);
				t_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						initNoCheck(arg1);
						DataAdapter.this.notifyDataSetInvalidated();
					}
				});
			}
			
			
			HScrollView row_scroll = (HScrollView) convertView.findViewById(R.id.horizontalScrollView1);
			HScrollView head_scroll = (HScrollView) head.findViewById(R.id.horizontalScrollView1);
			head_scroll.AddOnScrollChangedListener(new OnScrollChangedListenerImp(row_scroll));
		}
		
		return convertView;
	}
	/**
	 * 默认所有项不选中
	 * @param bool
	 */
	public void initNoCheck(boolean bool){
		if(list==null){
			return;
		}
		for(int i=0;i<list.size();i++){
			isCheckMap.put(i, bool);
		}
	}
	
	/**
	 * 获得选中项对应的值
	 * @return
	 */
	public List<Map<String, Object>> getSelectItems(){
		
		List<Map<String, Object>> selectList=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<list.size();i++){
			if(isCheckMap.get(i)){
				selectList.add(list.get(i));
			}
		}
		return selectList;
	}
	
	class OnScrollChangedListenerImp implements OnScrollChangedListener {
		HScrollView mScrollViewArg;

		public OnScrollChangedListenerImp(HScrollView scrollViewar) {
			mScrollViewArg = scrollViewar;
		}
		@Override
		public void onScrollChanged(int l, int t, int oldl, int oldt) {
			mScrollViewArg.smoothScrollTo(l, t);
		}
	};
}
