package com.stone.demo.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by 石头 on 2017/6/21.
 */
public class DataSourceInjection {

    @Autowired
    private DruidDataSource dataSource;

    public void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void doDataSourceTest() {
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
