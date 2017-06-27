package com.stone.common.viewResolver;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;

/**
 * Created by 石头 on 2017/6/2.
 */
public class HtmlViewResolver extends InternalResourceView {

    @Override
    public boolean checkResource(Locale locale){
        File file = new File(this.getServletContext().getRealPath("/") + getUrl());
        return file.exists();// 判断该页面是否存在
    }

}
