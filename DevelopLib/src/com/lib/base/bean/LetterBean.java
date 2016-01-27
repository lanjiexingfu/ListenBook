package com.lib.base.bean;

import android.support.annotation.NonNull;

import com.lib.base.utils.CheckUtils;
import com.lib.base.utils.pinyin.PingYinUtil;

import junit.framework.Assert;

/**
 * Created by chiely on 15/5/12.
 */
public abstract class LetterBean {

    public String firstLetter;
    public String pinYing;

    /**
     * 初始化完成后必须调用
     * @return
     */
    public LetterBean init() {
        String text = getPinYingText();
        Assert.assertTrue(CheckUtils.isAvailable(text));

        this.pinYing = PingYinUtil.getPingYin(text);

        if (CheckUtils.isAvailable(pinYing)) {
            firstLetter = pinYing.substring(0, 1);
        } else {
            firstLetter = text.substring(0, 1);
        }

        return this;
    }

    @NonNull
    protected abstract String getPinYingText();
}
