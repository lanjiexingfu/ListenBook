package com.lib.custom.config;

/**
 * 消息推送通知类型
 * Created by Chiely on 15/8/6.
 */
public class INotifyType {

    /**
     * 通知信息，可以在通知界面查看
     */
    public static final int MESSAGE = Integer.MIN_VALUE;

    /**
     * 教练预约通知
     */
    public static final int COACH_BOOK = 1;

    /**
     * 学员预约确定/取消通知
     */
    public static final int STUDENT_BOOK_RESULT = 2;

    /**
     * 学员签到/签退通知
     */
    public static final int STUDENT_SIGN_IN = 3;

    /**
     * 文章推送
     */
    public static final int NEWS_PUSH = 4;
}
