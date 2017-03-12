package com.model;

import com.util.DateUtil;

import java.util.Date;

/**
 * Created by yangliu on 18/12/2016.
 */
public class Nominee {

    private Date dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;

    public Nominee(){}

    public Nominee(String dateOfBirth, String firstName, String middleName, String lastName) {
        this.dateOfBirth = DateUtil.stringToDate(dateOfBirth);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String generateKey() {
        return this.getDateOfBirth() + "|" + this.getLastName();
    }

    public String getDateOfBirth() {
        return DateUtil.dateToString(dateOfBirth);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = DateUtil.stringToDate(dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
