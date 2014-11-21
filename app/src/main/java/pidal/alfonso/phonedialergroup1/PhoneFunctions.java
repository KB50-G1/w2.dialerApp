package pidal.alfonso.phonedialergroup1;

import java.lang.Comparable;

/**
 * Created by Friso on 14/11/21.
 */
public final class PhoneFunctions implements Comparable<Object> {

    private PhoneFunctions instance;
    private String phoneNumber;

    private PhoneFunctions(){}

    public PhoneFunctions getInstance(){
        if (instance == null) {
            instance = new PhoneFunctions();
        }
        return instance;
    }

    @Override
    public int compareTo(Object otherObject) {
        String otherNumber = (String) otherObject;
        if(phoneNumber != otherNumber) {
            return -1;
        } else if(phoneNumber.equals(otherNumber)){
            return 0;
        } else {
            return 1;
        }
    }
}
