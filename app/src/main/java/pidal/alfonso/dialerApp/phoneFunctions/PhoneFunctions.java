package pidal.alfonso.dialerApp.phoneFunctions;


import android.widget.TextView;

/**
 * Created by Friso on 14/11/21.
 */

public final class PhoneFunctions {

    private static PhoneFunctions instance;

    private PhoneFunctions() {
    }

    public static PhoneFunctions getInstance() {
        if (instance == null) {
            instance = new PhoneFunctions();
        }
        return instance;
    }

    public String getCountry(String[] argStringArray, String argText) {
        String country = "";

        if (argText.length() >= 4) {
            for (String item : argStringArray) {
                String[] g = item.split(",");
                if (g[0].equals(getFirstFourChar(argText))) {
                    country = g[1];
                    break;
                }
                if (g[0].equals(getFirstThreeChar(argText))) {
                    country = g[1];
                    break;
                }
                if (g[0].equals(getFirstTwoChar(argText))) {
                    country = g[1];
                    break;
                }
            }
        }

        return country;
    }

    public String getFirstFourChar(String argText) {
        String threeChar;
        String text = argText;
        threeChar = text.substring(0, 4);

        return threeChar;
    }

    public String getFirstThreeChar(String argText) {
        String twoChar;
        String text = argText;
        twoChar = text.substring(0, 3);

        return twoChar;
    }

    public String getFirstTwoChar(String argText) {
        String oneChar;
        String text = argText;
        oneChar = text.substring(0, 2);

        return oneChar;
    }

}
