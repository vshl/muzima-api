package com.muzima.api.model;

public class Provider extends OpenmrsSearchable implements Comparable<Provider> {

    private int id;

    private String name;

    private String systemId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Provider provider) {
        if (this.getName() != null && provider.getName() != null) {
            return this.getName().toLowerCase().compareTo(provider.getName().toLowerCase());
        }
        return 0;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
