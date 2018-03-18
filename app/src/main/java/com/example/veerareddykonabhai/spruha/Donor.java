package com.example.veerareddykonabhai.spruha;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by VeeraReddyKonabhai on 12/7/2017.
 */

public class Donor {
    public Long dID;
    public String dName;
    public String dContact1;

    public Long getdID() {
        return dID;
    }

    public void setdID(Long dID) {
        this.dID = dID;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdContact1() {
        return dContact1;
    }

    public void setdContact1(String dContact1) {
        this.dContact1 = dContact1;
    }

    public String getdContact2() {
        return dContact2;
    }

    public void setdContact2(String dContact2) {
        this.dContact2 = dContact2;
    }

    public String getdEmail() {
        return dEmail;
    }

    public void setdEmail(String dEmail) {
        this.dEmail = dEmail;
    }

    public String getdLocality() {
        return dLocality;
    }

    public void setdLocality(String dLocality) {
        this.dLocality = dLocality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String dContact2;
    public String dEmail;
    public String dLocality;
    public String gender;
    public String blood;
    public Donor() {
        // Default constructor required for calls to DataSnapshot.getValue(Donor.class)
    }

    public Donor(Long did, String dname, String dcontact1, String dcontact2,String demail, String dlocality, String gender, String blood) {
        this.dID = did;
        this.dName = dname;
        this.dContact1 = dcontact1;
        this.dContact2 = dcontact2;
        this.dEmail = demail;
        this.dLocality = dlocality;
        this.gender=gender;
        this.blood=blood;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dID", dID);
        result.put("dName", dName);
        result.put("dContact1",dContact1);
        result.put("dContact2",dContact2);
        result.put("dEmail",dEmail);
        result.put("dLocality",dLocality);
        result.put("gender",gender);
        result.put("blood",blood);
        return result;
    }
}
