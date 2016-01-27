package com.lib.ext.widget.faceselector;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.lib.R;

@SuppressLint("NewApi")
public class FaceLayout extends LinearLayout implements OnClickListener{

	private OnClickFaceListener mClickFaceListener;

    
    protected ViewFlipper viewFlipper=null;
    protected LinearLayout pagePoint=null;
//    ArrayList<ArrayList<HashMap<String,Object>>> listGrid=null;
    ArrayList<ImageView> pointList=null;
    
    public static final int ActivityId=1;
    private int pageFaceCount = 15;//一页的表情个数
    
    private View contentView;
    private Context mContext;
    private LayoutInflater mInflater;
    private int pageCount = 0;
    
    public FaceLayout(Context context) {
    	super(context);
    	mContext = context;
    	init();
    }
    public FaceLayout(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	mContext = context;
    	init();
    }
    public FaceLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}
    
    /**
     * 
     */
    private void init(){
    	mInflater = LayoutInflater.from(mContext);
    	contentView = mInflater.inflate(R.layout.widget_face_history_layout, this,true);
         
         viewFlipper=(ViewFlipper)findViewById(R.id.faceFlipper);
         pagePoint=(LinearLayout)findViewById(R.id.pagePoint);
//         listGrid=new ArrayList<ArrayList<HashMap<String,Object>>>();
         pointList=new ArrayList<ImageView>();
         
         pageCount = (int) Math.ceil(1.0f * FaceData.size()/pageFaceCount);
//         addFaceData();
         addGridView();
         setPointEffect(0);
    }
    
    /**
     * 点击表情时候的回调
     * @param l
     */
    public void setOnClickFaceListener(OnClickFaceListener l){
    	mClickFaceListener = l;
    }
    
    private void addGridView(){
        if(viewFlipper!=null){
            viewFlipper.removeAllViews();
            pagePoint.removeAllViews();
            pointList.clear();               //更新前首先清除原有的所有数据
        }
        for(int i=0; i< pageCount;i++){
            View view=LayoutInflater.from(mContext).inflate(R.layout.widget_face_layout, null);
            GridView gv=(GridView)view.findViewById(R.id.myGridView);
            gv.setNumColumns(5);
            gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
            MyGridAdapter adapter=new MyGridAdapter(mContext);
            List<Face> data = new ArrayList<Face>();
            for(int j = i * pageFaceCount; j<FaceData.size();j++){
            	data.add(FaceData.get(j));
            	if(data.size() == pageFaceCount){
            		break;
            	}
            }
            adapter.setFaceData(data);
            gv.setAdapter(adapter);
            gv.setOnTouchListener(new MyTouchListener(viewFlipper));
            
//            viewFlipper.setOnTouchListener(new MyTouchListener(viewFlipper));
            viewFlipper.addView(view);
            /**
             * 这里不喜欢用Java代码设置Image的边框大小等，所以单独配置了一个Imageview的布局文件
             */
            View pointView=LayoutInflater.from(mContext).inflate(R.layout.point_image_layout, null);
            ImageView image=(ImageView)pointView.findViewById(R.id.pointImageView);
            image.setBackgroundResource(R.drawable.qian_point);
            pagePoint.addView(pointView);
            /**
             * 这里验证了LinearLayout属于ViewGroup类型，可以采用addView 动态添加view
             */
            pointList.add(image);
        }
    
    }
    
    
    /**
     * 设置游标（小点）的显示效果
     * @param darkPointNum    
     */
    private void setPointEffect(int darkPointNum){
        for(int i=0; i<pointList.size(); i++){
            pointList.get(i).setBackgroundResource(R.drawable.qian_point);
        }
        if(pointList.size()>0)
            pointList.get(darkPointNum).setBackgroundResource(R.drawable.shen_point);
    }
    private boolean moveable=true;
    private float startX=0;
    
    /**
     * 用到的方法 viewFlipper.getDisplayedChild()  获得当前显示的ChildView的索引
     * @author Administrator
     *
     */
    class MyTouchListener implements OnTouchListener{

        ViewFlipper viewFlipper=null;
        
        
        public MyTouchListener(ViewFlipper viewFlipper) {
            super();
            this.viewFlipper = viewFlipper;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:startX=event.getX(); moveable=true; break;
            case MotionEvent.ACTION_MOVE:
                if(moveable){
                    if(event.getX()-startX>40){
                        moveable=false;
                        int childIndex=viewFlipper.getDisplayedChild();
                        /**
                         * 这里的这个if检测是防止表情列表循环滑动
                         */
                        if(childIndex>0){
                            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));                        
                            viewFlipper.showPrevious();
                            setPointEffect(childIndex-1);
                        }
                    }
                    else if(event.getX()-startX<-40){
                        moveable=false;
                        int childIndex=viewFlipper.getDisplayedChild();
                        /**
                         * 这里的这个if检测是防止表情列表循环滑动
                         */
                        if(childIndex<pageCount-1){
                            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                            viewFlipper.showNext();
                            setPointEffect(childIndex+1);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:moveable=true;break;
            default:break;
            }
            
            return false;
        }
        
    }
    
    
    /**
     * GridViewAdapter
     * @param textView
     * @param text
     */
    
    class MyGridAdapter extends BaseAdapter{

        private Context mContext;
        private List<Face> data;
        public MyGridAdapter(Context context) {
            super();
            mContext = context;        }
        
        public void setFaceData(List<Face> d){
        	data = d;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }  

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            ImageView image=null;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder=null;
            if(convertView==null){
                convertView=LayoutInflater.from(mContext).inflate(R.layout.chat_grid_item, null);
                holder=new ViewHolder();
                holder.image=(ImageView)convertView.findViewById(R.id.gridImage);
                convertView.setTag(holder);
            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.image.setImageResource(data.get(position).getFaceRes());
            //这里创建了一个方法内部类
            holder.image.setTag(data.get(position).getPosition());
            holder.image.setOnClickListener(FaceLayout.this);
                    
            return convertView;
        }
        
    }

    /**
     * 更新数据
     */
    private void update(){
//        addFaceData();
        addGridView();
        setPointEffect(0);
    }
    
	@Override
	public void onClick(View v) {
		if(mClickFaceListener == null) return;
		int position = (Integer) v.getTag();//获取索引位置
		mClickFaceListener.onFaceClick(FaceData.get(position).getFaceName(),FaceData.get(position).getFaceRes());	

	}
}