package com.muzima.api.model;

public class SetupConfigurationTemplate extends OpenmrsSearchable {
    private String configJson;

    /**
     * Get the configuration Json.
     *
     * @return the configuration Json.
     */
    public String getConfigJson(){
        return configJson;
    }

    /**
     * Set the configuration Json.
     *
     * @param configJson the configuration Json to set.
     */
    public void setConfigJson(String configJson){
        this.configJson = configJson;
    }
}
