package ibcs.cs_ia_serviceapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * utils class to create and show ProgressDialogs easier
 */
public class DialogUtils
{

    //https://stackoverflow.com/a/37428936
    /**
     * creates and shows new progress dialog, then returns the object so it can be dismissed.
     *
     * @param activity activity of where it'll show up
     * @param message message to be shown on dialog
     * @return the created ProgressDialog object so it can be dismissed
     */
    public static ProgressDialog makeProgressDialog(Activity activity, String message)
    {
        ProgressDialog progress = new ProgressDialog(activity);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(message);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        return progress;
    }
}