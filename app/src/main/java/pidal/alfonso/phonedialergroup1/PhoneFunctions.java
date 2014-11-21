package pidal.alfonso.phonedialergroup1;

/**
 * Created by Friso on 14/11/21.
 */
public final class PhoneFunctions {
    private PhoneFunctions instance;

    private PhoneFunctions(){}

    public PhoneFunctions getInstance(){
        if (instance == null) {
            instance = new PhoneFunctions();
        }

        return instance;
    }
}
