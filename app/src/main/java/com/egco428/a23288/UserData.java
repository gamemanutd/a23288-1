package com.egco428.a23288;

public class UserData {

    private long id;
    private String user;
    private String pass;
    private String lati;
    private String longti;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPasss(String pass) {this.pass = pass;}

    public String getLati() {return lati;
    }

    public void setLat(String lati) { this.lati = lati; }

    public String getLongti() { return longti; }

    public void setLong(String longti) { this.longti = longti; }



    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return user;
    }
}
