package pidal.alfonso.phonedialergroup1;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by alfonsopidal on 23/11/14.
 */
public class NetworkHelper {

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}
