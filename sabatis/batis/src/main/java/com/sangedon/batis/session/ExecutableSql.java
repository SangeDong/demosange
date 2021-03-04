package com.sangedon.batis.session;

import com.sangedon.batis.util.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class ExecutableSql {
    private String parseSql;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public String getParseSql() {
        return parseSql;
    }

    public void setParseSql(String parseSql) {
        this.parseSql = parseSql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
