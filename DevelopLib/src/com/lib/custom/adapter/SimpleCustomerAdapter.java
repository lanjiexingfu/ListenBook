package com.lib.custom.adapter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lib.base.adapter.AbsBaseAdapter;

/**
 * 简便使用的自定义适配器，只需要把BaseViewHolder子类的Class对象作为参赛传入就可以实现自动缓存的适配器，首次调用时性能上有一点点影响
 * Created by chiely on 15/1/13.
 */
public class SimpleCustomerAdapter<ViewData, ViewHolder extends SimpleCustomerAdapter.BaseViewHolder<ViewData>>
		extends AbsBaseAdapter<ViewData> {

	protected Context context;
	protected Class<? extends BaseViewHolder<ViewData>> viewHolderClass;
	protected AdapterView.OnItemClickListener onMainItemClickListener;
	protected AdapterView.OnItemClickListener[] onItemClickListeners;
	private int checked;

	// 私有化以控制不被外部调用
	private SimpleCustomerAdapter() {
		super();
	}

	// 私有化以控制不被外部调用
	private SimpleCustomerAdapter(final List<ViewData> datas) {
		super(datas);
	}

	// 私有化以控制不被外部调用
	private SimpleCustomerAdapter(final ViewData[] datas) {
		super(datas);
	}

	public SimpleCustomerAdapter(final Context context) {
		super();
		this.context = context;
		init();
	}

	public SimpleCustomerAdapter(final Context context,
			final List<ViewData> datas) {
		super(datas);
		this.context = context;
		init();
	}

	public SimpleCustomerAdapter(final Context context, final ViewData[] datas) {
		super(datas);
		this.context = context;
		init();
	}

	@Override
	public void setData(final List<ViewData> datas) {
		super.setData(datas);
		init();
	}

	@Override
	public void setData(final ViewData[] datas) {
		super.setData(datas);
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		checked = -1;
		viewHolderClass = (Class<? extends BaseViewHolder<ViewData>>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * 监听整个Item的点击事件（在于子View有冲突时，优先处理他子view的监听）
	 * 
	 * @param onItemClickListeners
	 */
	public void setOnMainItemClickListener(
			AdapterView.OnItemClickListener onItemClickListeners) {
		this.onMainItemClickListener = onItemClickListeners;
	}

	/**
	 * 子View的监听事件 注：必须在对应的ViewHolder添加监听器index对应的控件
	 */
	public void setOnItemClickListeners(
			AdapterView.OnItemClickListener... onItemClickListeners) {
		this.onItemClickListeners = onItemClickListeners;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ViewData[] getDatasOfArray() {
		return (ViewData[]) mDatas.toArray();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = newViewHolder();
			Assert.assertNotNull(viewHolder);

			convertView = viewHolder.createView(context, parent);
		} else {
			// noinspection unchecked
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (onMainItemClickListener != null) {
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					onMainItemClickListener.onItemClick(
							(AdapterView<?>) parent, v, position, -1);
				}
			});
		}
		// 当被选中时
		viewHolder.handleChecked(checked == position);
		viewHolder.setOnItemClickListeners(onItemClickListeners);
		// 绑定View与数据的关系
		viewHolder.bindDataWithView(position, mDatas.get(position));
		// 绑定View与相应点击事件
		viewHolder.bindItemClickLitener(position);

		return convertView;
	}

	@SuppressWarnings("unchecked")
	private ViewHolder newViewHolder() {
		try {
			return (ViewHolder) viewHolderClass.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract static class BaseViewHolder<ViewData> {
		protected Context context;
		protected View mainView;
		protected AdapterView.OnItemClickListener[] onItemClickListeners;
		private ArrayList<View> clickListenerViewList;

		View createView(final Context context, final ViewGroup parent) {
			this.context = context;
			this.mainView = LayoutInflater.from(context).inflate(getLayoutId(),
					parent, false);
			bindView(mainView);
			setViewDefaultParam();
			mainView.setTag(this);
			return mainView;
		}

		protected abstract int getLayoutId();

		// 实例GirdItem内部子view
		protected abstract void bindView(View view);

		// 设置View的默认属性，默认不需要处理
		protected void setViewDefaultParam() {
		}

		protected void handleChecked(boolean isChecked) {
		}

		public void setOnItemClickListeners(
				AdapterView.OnItemClickListener... onItemClickListeners) {
			this.onItemClickListeners = onItemClickListeners;
		}

		public abstract void bindDataWithView(final int position,
				final ViewData viewData);

		// 添加GirdItem内部子view到对应的点击事件，点击事件顺序与添加顺序一致
		protected void addClickListenerView(View view) {
			if (view == null)
				return;
			if (clickListenerViewList == null) {
				clickListenerViewList = new ArrayList<View>();
			}
			clickListenerViewList.add(view);
		}

		protected void addClickListenerViews(View... views) {
			if (views == null)
				return;
			if (clickListenerViewList == null) {
				clickListenerViewList = new ArrayList<View>();
			}
			for (View view : views) {
				if (view == null) {
					return;
				}
				clickListenerViewList.add(view);
			}
		}

		public void bindItemClickLitener(final int position) {
			if (clickListenerViewList != null && onItemClickListeners != null
					&& !clickListenerViewList.isEmpty()) {
				for (int i = 0; i < clickListenerViewList.size(); i++) {
					final View view = clickListenerViewList.get(i);
					if (view != null && notEmpty(onItemClickListeners, i)) {
						final AdapterView.OnItemClickListener onItemClickLitener = onItemClickListeners[i];
						view.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(final View v) {
								onItemClickLitener.onItemClick(null, view,
										position, -1);
							}
						});
					}
				}
			}
		}

		private boolean notEmpty(ArrayList arrayList, int position) {
			return arrayList.size() > position
					&& arrayList.get(position) != null;
		}

		private <T> boolean notEmpty(T[] array, int position) {
			return array.length > position && array[position] != null;
		}
	}
}
