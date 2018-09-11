package com.stone.common.model;

import com.stone.core.exception.MyException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 石头 on 2018/2/27.
 */
public class DataStatusHandler extends BaseTypeHandler<DataStatus> {

    private DataStatus codeOfEnum(String code) {
        for (DataStatus s : DataStatus.values()) {
            if (s.getCode().equals(code)) {
                return s;
            }
        }
        throw new MyException("未知的枚举类型：" + code + "，请核对" + DataStatus.class.getSimpleName());
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DataStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public DataStatus getNullableResult(ResultSet rs, String s) throws SQLException {
        String content = rs.getString(s);
        if (rs.wasNull()) {
            return null;
        } else {
            return this.codeOfEnum(content);
        }
    }

    @Override
    public DataStatus getNullableResult(ResultSet rs, int i) throws SQLException {
        String content = rs.getString(i);
        if (rs.wasNull()) {
            return null;
        } else {
            return this.codeOfEnum(content);
        }
    }

    @Override
    public DataStatus getNullableResult(CallableStatement cs, int i) throws SQLException {
        String content = cs.getString(i);
        if (cs.wasNull()) {
            return null;
        } else {
            return this.codeOfEnum(content);
        }
    }
}
