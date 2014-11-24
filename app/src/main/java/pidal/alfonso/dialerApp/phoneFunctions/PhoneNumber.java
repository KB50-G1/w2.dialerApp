package pidal.alfonso.dialerApp.phoneFunctions;

import java.io.Serializable;

/**
 * Created by alfonsopidal on 23/11/14.
 */
public class PhoneNumber implements Serializable {

    private String number;
    private String formattedNumber;
    private String country;
    private String lineType;

    public PhoneNumber(String number) {
        this.number = number;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public String getNumber() {
        return number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}
