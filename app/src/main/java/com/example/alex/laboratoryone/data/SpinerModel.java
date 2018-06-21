package com.example.alex.laboratoryone.data;

import java.io.Serializable;


public class SpinerModel implements Serializable {

    private int spinerOne;
    private int spinerTwo;
    private String edOne;
    private String edTwo;

    public int getSpinerOne() {
        return spinerOne;
    }

    public void setSpinerOne(int spinerOne) {
        this.spinerOne = spinerOne;
    }

    public int getSpinerTwo() {
        return spinerTwo;
    }

    public void setSpinerTwo(int spinerTwo) {
        this.spinerTwo = spinerTwo;
    }

    public String getEdOne() {
        return edOne;
    }

    public void setEdOne(String edOne) {
        this.edOne = edOne;
    }

    public String getEdTwo() {
        return edTwo;
    }

    public void setEdTwo(String edTwo) {
        this.edTwo = edTwo;
    }


}
