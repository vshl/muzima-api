package com.muzima.api.model;

/**
 * Created by savai on 10/24/16.
 */
public class SetupConfigurationTemplate extends OpenmrsSearchable {
    private String configJson;

    public String getConfigJson(){
        return configJson;
    }

    public void setConfigJson(String configJson){
        this.configJson = configJson;
        System.out.println("+setConfigJson(String configJson)"+getUuid());
    }
}
