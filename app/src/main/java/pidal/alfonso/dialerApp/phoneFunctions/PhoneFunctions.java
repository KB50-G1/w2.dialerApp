package pidal.alfonso.dialerApp.phoneFunctions;

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
        return argText.substring(0, 4);
    }

    public String getFirstThreeChar(String argText) {
        return argText.substring(0, 3);
    }

    public String getFirstTwoChar(String argText) {
        return argText.substring(0, 2);
    }

}
