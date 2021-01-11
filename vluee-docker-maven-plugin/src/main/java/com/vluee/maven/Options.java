package com.vluee.maven;

public class Options {
    private String serviceName;
    private String port;
    private String image;
    private String networkName;

    public Options(String serviceName, String port, String image, String networkName) {
        this.serviceName = serviceName;
        this.port = port;
        this.image = image;
        this.networkName = networkName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPort() {
        return port;
    }

    public String getImage() {
        return image;
    }

    public String getNetworkName() {
        return networkName;
    }

    @Override
    public String toString() {
        return "Options{" +
                "serviceName='" + serviceName + '\'' +
                ", port='" + port + '\'' +
                ", image='" + image + '\'' +
                ", networkName='" + networkName + '\'' +
                '}';
    }
}
