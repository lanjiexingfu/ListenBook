package com.lib.ext.widget.datepicker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.R;
import com.lib.base.app.Configure;
import com.lib.ext.widget.datepicker.wheelview.OnWheelScrollListener;
import com.lib.ext.widget.datepicker.wheelview.WheelView;
import com.lib.ext.widget.datepicker.wheelview.adapter.NumericWheelAdapter;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class DatePickerPopWindow extends PopupWindow{
    private Context context;
    private String startTime;
    private Date date;
    private int curYear,curMonth;
    private LayoutInflater mInflater;
    private View dateView;
    private WheelView yearView;
    private WheelView monthView;
    private WheelView dayView;
    private WheelView hourView;
    private WheelView minView;
    private WheelView secView;
    private TextView orderInfoTV1;
    private TextView orderInfoTV2;
    private TextView cancelBtnTV;
    private TextView confirmBtnTV;
    private EditText orderRemark;
    private String selectTime;
    private String selectHour;
    private String selectMinute;

    private int[] timeInt;
    public DatePickerPopWindow(Context context,String startTime){
        this.context=context;
        this.startTime=startTime;
        setStartTime();
        initWindow();
    }

    public void resetDate(String startTime){
        this.startTime=startTime;
        setStartTime();
        initWheel();
    }


    private void setStartTime() {
        // TODO Auto-generated method stub
        timeInt=new int[6];
        timeInt[0]=Integer.valueOf(startTime.substring(0, 4));
        timeInt[1]=Integer.valueOf(startTime.substring(4, 6));
        timeInt[2]=Integer.valueOf(startTime.substring(6, 8));
        timeInt[3]=Integer.valueOf(startTime.substring(8, 10));
        timeInt[4]=Integer.valueOf(startTime.substring(10, 12));
        timeInt[5]=Integer.valueOf(startTime.substring(12, 14));
    }
    private void initWindow() {
        // TODO Auto-generated method stub
        mInflater=LayoutInflater.from(context);
        dateView=mInflater.inflate(R.layout.wheel_date_picker, null);
        yearView=(WheelView) dateView.findViewById(R.id.year);
        monthView=(WheelView) dateView.findViewById(R.id.month);
        dayView=(WheelView) dateView.findViewById(R.id.day);
        hourView=(WheelView) dateView.findViewById(R.id.time);
        minView=(WheelView) dateView.findViewById(R.id.min);
        secView=(WheelView) dateView.findViewById(R.id.sec);
        orderInfoTV1 = (TextView)dateView.findViewById(R.id.order_info1);
        orderInfoTV2 = (TextView)dateView.findViewById(R.id.order_info2);
        cancelBtnTV = (TextView)dateView.findViewById(R.id.simple_dialog_cancel);
        confirmBtnTV = (TextView)dateView.findViewById(R.id.simple_dialog_confirm);
        orderRemark = (EditText)dateView.findViewById(R.id.order_remark);
        final String hintText = "<img src=\""+R.drawable.ic_editable+"\" /> 给学员留言";
        orderRemark.setHint(Html.fromHtml(hintText, imageGetter, null));
        secView.setVisibility(View.GONE);
        initWheel();
    }

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            int rId = Integer.parseInt(source);
            drawable = context.getResources().getDrawable(rId);
            drawable.setBounds(0, 0, 40, 40);
            return drawable;
        }
    };

    //设置预约信息 TextView
    public void setOrderInfoTV1(Spanned spanned){
        orderInfoTV1.setText(spanned);
    }

    //设置预约信息 TextView
    public void setOrderInfoTV2(String s){
        orderInfoTV2.setText(s);
    }

    //获取取消按钮 TextView
    public TextView getCancelBtnTV(){
        return cancelBtnTV;
    }

    //获取确认按钮 TextView
    public TextView getConfirmBtnTV(){
        return confirmBtnTV;
    }

    //获取备注框 EditView
    public TextView getBookRemark(){
        return orderRemark;
    }

    private void initWheel() {
        Calendar calendar=Calendar.getInstance();
        curYear=calendar.get(Calendar.YEAR);
        curMonth=calendar.get(Calendar.MONTH)+1;

        NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(context,curYear, curYear+10);
        numericWheelAdapter1.setLabel("年");
        yearView.setViewAdapter(numericWheelAdapter1);
        yearView.setCyclic(true);
        yearView.addScrollingListener(scrollListener);

        NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(context,1, 12, "%02d");
        numericWheelAdapter2.setLabel("月");
        monthView.setViewAdapter(numericWheelAdapter2);
        monthView.setCyclic(true);
        monthView.addScrollingListener(scrollListener);

        NumericWheelAdapter numericWheelAdapter3=new NumericWheelAdapter(context,1, getDay(curYear, curMonth), "%02d");
        numericWheelAdapter3.setLabel("日");
        dayView.setViewAdapter(numericWheelAdapter3);
        dayView.setCyclic(true);
        dayView.addScrollingListener(scrollListener);

        NumericWheelAdapter numericWheelAdapter4=new NumericWheelAdapter(context,0, 23, "%02d","0");
        numericWheelAdapter4.setLabel("时");
        hourView.setViewAdapter(numericWheelAdapter4);
        hourView.setWheelBackground(R.color.datepicker_bg);
        hourView.setDrawShadows(false);
        hourView.setCyclic(true);
        hourView.addScrollingListener(scrollListener);

        NumericWheelAdapter numericWheelAdapter5=new NumericWheelAdapter(context,0, 2, "%02d","1");
        numericWheelAdapter5.setLabel("分");
        minView.setViewAdapter(numericWheelAdapter5);
        minView.setWheelBackground(R.color.datepicker_bg);
        minView.setDrawShadows(false);
        minView.setCyclic(true);
        minView.setEnabled(false);

        yearView.setCurrentItem(timeInt[0]-curYear);
        monthView.setCurrentItem(timeInt[1]-1);
        dayView.setCurrentItem(timeInt[2]-1);
        hourView.setCurrentItem(timeInt[3]);
        minView.setCurrentItem(00);
        yearView.setVisibleItems(7);//设置显示行数
        monthView.setVisibleItems(7);
        dayView.setVisibleItems(7);
        hourView.setVisibleItems(3);
        minView.setVisibleItems(3);
        setContentView(dateView);
        setWidth((int) (Configure.screenWidth * 0.75));
        setHeight(LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        setBackgroundDrawable(dw);
        setFocusable(true);
        selectHour = Integer.valueOf(hourView.getCurrentItem())<0?"10"+hourView.getCurrentItem():+hourView.getCurrentItem()+"";
//        selectMinute = Integer.valueOf(minView.getCurrentItem())<0?"10"+minView.getCurrentItem():+minView.getCurrentItem()+"";
        selectTime = selectHour+":"+"00";
    }
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = yearView.getCurrentItem() + curYear;//年
            int n_month = monthView.getCurrentItem() + 1;//月
            initDay(n_year,n_month);
            selectHour = Integer.valueOf(hourView.getCurrentItem())<10?"0"+hourView.getCurrentItem():+hourView.getCurrentItem()+"";
//            selectMinute = Integer.valueOf(minView.getCurrentItem())<10?"0"+minView.getCurrentItem():+minView.getCurrentItem()+"";
            selectTime = selectHour+":00";
        }
    };

    //获取当前选中的时间
    public String getSelectTime(){
        return selectTime;
    }

    private void initDay(int arg1, int arg2) {
        NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(context,1, getDay(arg1, arg2), "%02d");
        numericWheelAdapter.setLabel("日");
        dayView.setViewAdapter(numericWheelAdapter);
    }
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }
}
