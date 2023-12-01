package model;

import java.io.Serializable;
import java.time.LocalDate;

public class CustomObject implements Serializable {
    private String stringValue;
    private int intValue;
    private LocalDate localDateValue;

    public CustomObject(String passWord, int intValue, LocalDate localDateValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
        this.localDateValue = localDateValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public LocalDate getLocalDateValue() {
        return localDateValue;
    }
    
    public void setCount(int count)
    {
    	this.intValue = count;
    }
    
    public void setDate()
    {
    	this.localDateValue = LocalDate.now();
    }
}
