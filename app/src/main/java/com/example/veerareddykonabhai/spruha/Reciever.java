package com.example.veerareddykonabhai.spruha;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 12/11/2017.
 */

public class Reciever {

    public Long rID;
    public String name;
    public String contact;;
    public String locality;
    public String address;
    public String blood;

    public Reciever() {
        // Default constructor required for calls to DataSnapshot.getValue(DonUp.class)
    }

    public Long getrID() {
        return rID;
    }

    public void setrID(Long rID) {
        this.rID = rID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public Reciever(Long rID, String name, String contact, String locality, String address, String blood) {
        this.rID=rID;
        this.name = name;
        this.contact = contact;
        this.locality = locality;
        this.address = address;

        this.blood = blood;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("rID",rID);
        result.put("name", name);
        result.put("contact", contact);
        result.put("locality", locality);
        result.put("address", address);
        result.put("blood",blood);
        return result;
    }

}