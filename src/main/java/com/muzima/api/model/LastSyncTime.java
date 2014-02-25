package com.muzima.api.model;

import java.util.Date;
import java.util.UUID;

/*
This structure is going to hold the last sync time for the API along with its param signature. Useful to make the
 */
public class LastSyncTime extends OpenmrsSearchable implements Comparable<LastSyncTime>{

    private APIName apiName;
    private String paramSignature;
    private Date lastSyncDate;

    public LastSyncTime(){
        this.setUuid(UUID.randomUUID().toString());
    }

    public LastSyncTime(APIName apiName, Date utcTime){
        this(apiName, utcTime, null);
    }

    public LastSyncTime(APIName apiName, Date utcTime, String paramSignature){
        this();
        this.apiName = apiName;
        this.lastSyncDate = utcTime;
        this.paramSignature = paramSignature;
    }

    public APIName getApiName() {
        return apiName;
    }

    public void setApiName(APIName apiName) {
        this.apiName = apiName;
    }

    public String getParamSignature() {
        return paramSignature;
    }

    public void setParamSignature(String paramSignature) {
        this.paramSignature = paramSignature;
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    @Override
    public int compareTo(LastSyncTime lastSyncTime) {
        return this.lastSyncDate.compareTo(lastSyncTime.lastSyncDate);
    }
}
