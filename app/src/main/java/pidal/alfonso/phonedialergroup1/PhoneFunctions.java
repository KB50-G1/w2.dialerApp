package pidal.alfonso.phonedialergroup1;

import android.widget.TextView;

/**
 * Created by Friso on 14/11/21.
 */
public final class PhoneFunctions {
    private static PhoneFunctions instance;

    private PhoneFunctions(){}

    public static PhoneFunctions getInstance() {
        if (instance == null) {
            instance = new PhoneFunctions();
        }

        return instance;
    }

    public String getCountry(String[] argStringArray, TextView argText){
        String country="";

        String[] rl=argStringArray;

        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[0].equals(getFirstThreeChar(argText))){
                country=g[1];
                break;
            }
            if (g[0].equals(getFirstTwoChar(argText))){
                country=g[1];
                break;
            }
            if (g[0].equals(getFirstChar(argText))){
                country=g[1];
                break;
            }
        }

        return country;
    }

    public String getFirstThreeChar(TextView argText){
        String threeChar;
        String text = argText.getText().toString();
        threeChar = text.substring(0,4);

        return threeChar;
    }

    public String getFirstTwoChar(TextView argText){
        String twoChar;
        String text = argText.getText().toString();
        twoChar = text.substring(0,3);

        return twoChar;
    }

    public String getFirstChar(TextView argText){
        String oneChar;
        String text = argText.getText().toString();
        oneChar = text.substring(0,2);

        return oneChar;
    }
}
