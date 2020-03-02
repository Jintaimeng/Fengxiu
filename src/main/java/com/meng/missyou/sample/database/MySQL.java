package com.meng.missyou.sample.database;

import com.meng.missyou.sample.IConnect;

public class MySQL implements IConnect {
    private String ip = "localhost";
    private Integer port = 3306;

    public MySQL() {
    }

    public MySQL(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void connect() {
        System.out.println(this.ip+"  "+this.port);

    }
}
