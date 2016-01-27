package com.lib.custom.config;

/**
 * Created by chiely on 15/6/8.
 */
public enum EventType {
    /**
     * 登陆成功，学员信息不为空
     */
    Login_Success,

    Login_Failure,

    /**
     * 注销，当接口请求返回token错误时使用
     */
    Logout,

    /**
     * 新的系统推送消息
     */
    New_Server_Message,

    /**
     * 新的系统推送消息点击后
     */
    New_Server_Message_Click,

    /**
     * 检查版本
     */
    Check_Version,

    /**
     * 签退
     */
    Sign_Out,

    /**
     * 返回首页
     */
    Back_Index,
    /**
     * 返回练车记录
     */
    Back_Driving_Record,
    /**
     * 练车评价结果
     */
    Driving_Assess_result,
    /**
     * 取消练车
     */
    Driving_Cancel,

    /**
     * 切换到工作表
     */
    Turn_To_WorkTable,
    /**
     * 切换到日历
     */
    Turn_To_Calendar,

    /**
     * 跳转到首页
     */
    Turn_To_Index,
    /**
     * 跳转到学员详情
     */

    /**
     * 跳转到学员列表
     */
    Turn_To_StudentList,
    /**
     * 跳转到学员详情
     */
    Turn_To_StudentDetail,

    /**
     * 显示试题答案及解释
     */
    Show_Exam_Explain,

    /**
     * 隐藏试题答案及解释
     */
    Hide_Exam_Explain,
    /**
     * 试题滚动到底部
     */
    Topic_Body_Scroll_Bottom,

    /**
     * 启动小车动画
     */
    Start_Small_Car,

    /**
     * 停止小车动画
     */
    Stop_Small_Car,

    /**
     * 强制下车通知并刷新
     */
    Driving_Off_And_Refresh,

    /**
     * 离线包解压完成
     */
    EXAM_IMAGE_AND_VEDIO_UNZIP_SUCCESS,

    /**
     * 更新token成功
     */
    Update_Token_Success,

    /**
     * 预约教练名称
     */
    Book_Coach_Name,
}
