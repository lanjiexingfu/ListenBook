package com.listen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.lib.base.FinalBitmap;
import com.lib.base.utils.DisplayUtils;
import com.listen.activity.R;
import java.util.ArrayList;

public class IndexGridViewAdapter extends BaseAdapter {

	Activity context;
	ArrayList<String> list;
	private FinalBitmap finalImageLoader;
	private int wh;
	
	public IndexGridViewAdapter(Activity context, ArrayList<String> data) {
		this.context=context;
		this.wh=(DisplayUtils.getScreenWidth()-DisplayUtils.dip2px(99))/3;
		this.list=data;
		this.finalImageLoader=FinalBitmap.create(context);
		this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);//
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view==null) {
			view=LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
			holder=new Holder();
			holder.imageView=(ImageView) view.findViewById(R.id.imageView);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}
		finalImageLoader.display(holder.imageView, list.get(position));
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(wh,wh);
        view.setLayoutParams(param);
		return view;
	}
	
	class Holder{
		ImageView imageView;
	}
}
