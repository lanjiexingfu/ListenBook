package com.listen.view;

import android.os.Bundle;
import android.view.View;
import com.lib.base.adapter.AbsBaseAdapter;
import com.lib.base.app.view.BusinessFragment;
import com.listen.model.respond.BaseRespond;

import junit.framework.Assert;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by chiely on 15/3/19.
 */
public abstract class PullRefreshListFragment<Respond extends BaseRespond, DataVo, Adapter extends AbsBaseAdapter<DataVo>> extends BusinessFragment implements IPullRefreshList<Respond, DataVo, Adapter> {

    private PullRefreshListDelegate<Respond, DataVo, Adapter> pullRefreshListDelegate;

    @Override
    protected void onSubViewCreated(View view, Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {
        ParameterizedType genericSuperclass = getParameterizedType(getClass());
        Assert.assertTrue("子类没有正确继承！", genericSuperclass != null);
        Class<Respond> clz = (Class<Respond>) genericSuperclass.getActualTypeArguments()[0];
        addDelegate(pullRefreshListDelegate = new PullRefreshListDelegate<Respond, DataVo, Adapter>(this, this, clz));
    }

    private ParameterizedType getParameterizedType(Class clz) {
        if (clz.getSimpleName().contains("PullRefreshListFragment")) {
            return null;
        }

        Type genericSuperclass = clz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return (ParameterizedType) genericSuperclass;
        } else {
            return getParameterizedType(clz.getSuperclass());
        }
    }

    public PullRefreshListDelegate<Respond, DataVo, Adapter> getPullRefreshListDelegate() {
        return pullRefreshListDelegate;
    }

    public void setOnChangeDataFinishListener(PullRefreshListDelegate.OnChangeDataFinishListener onChangeDataFinishListener) {
        pullRefreshListDelegate.setOnChangeDataFinishListener(onChangeDataFinishListener);
    }

    public void setOnItemClickListener(PullRefreshListDelegate.OnItemClickListener<DataVo> onItemClick) {
        pullRefreshListDelegate.setOnItemClickListener(onItemClick);
    }
}
