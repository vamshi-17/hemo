package edu.communication.hemo.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import edu.communication.hemo.R;

public class Utils {
    public static boolean checkInternetConnection(Activity _activity) {
        ConnectivityManager conMgr = (ConnectivityManager) _activity.getSystemService("connectivity");
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    public static void showAlertOkCancel(final Activity _activity, String title, String alertMsg, final boolean goBack) {
        AlertDialog.Builder alert = new AlertDialog.Builder(_activity);
        alert.setTitle(title);
        alert.setCancelable(false);
        alert.setMessage(alertMsg);
        alert.setPositiveButton(_activity.getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.custom.Utils.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (goBack) {
                    _activity.finish();
                }
            }
        });
        alert.setNegativeButton(_activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.custom.Utils.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void showWarningDialogue(final Activity _activity, String title, String message, boolean setCancelable, final Class cls) {
        new AwesomeWarningDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(setCancelable).setButtonTextColor(R.color.white).setButtonText(_activity.getResources().getString(R.string.dialog_ok_button)).setWarningButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.3
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
                Intent intent = new Intent(_activity, cls);
                _activity.startActivity(intent);
            }
        }).show();
    }

    public static void showWarningDialogue(Activity _activity, String title, String message, boolean setCancelable, Class cls, String Date, String totalAmount, String merchantName) {
        new AwesomeWarningDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(setCancelable).setButtonTextColor(R.color.white).setButtonText(_activity.getResources().getString(R.string.dialog_ok_button)).setWarningButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.4
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
            }
        }).show();
    }

    public static void showErrorDialogue(Activity _activity, String message) {
        new AwesomeErrorDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(true).setButtonTextColor(R.color.white).setButtonText(_activity.getResources().getString(R.string.dialog_ok_button)).setErrorButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.5
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
            }
        }).show();
    }

    public static void showSuccessDialogue(final Activity _activity, String message, final Class cls, final Boolean isActivityClose) {
        new AwesomeSuccessDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(true).setPositiveButtonTextColor(R.color.white).setPositiveButtonText(_activity.getString(R.string.dialog_ok_button)).setPositiveButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.6
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
                if (isActivityClose.booleanValue()) {
                    Activity activity = _activity;
                    activity.startActivity(new Intent(activity, cls));
                    _activity.finish();
                    return;
                }
                Activity activity2 = _activity;
                activity2.startActivity(new Intent(activity2, cls));
            }
        }).show();
    }

    public static void showSuccessDialogue(final Activity _activity, String message, final Boolean isActivityClose) {
        new AwesomeSuccessDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(true).setPositiveButtonTextColor(R.color.white).setPositiveButtonText(_activity.getString(R.string.dialog_ok_button)).setPositiveButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.7
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
                if (isActivityClose.booleanValue()) {
                    _activity.finish();
                }
            }
        }).show();
    }

    public static void showSuccessDialogue(Activity _activity, String message) {
        new AwesomeSuccessDialog(_activity).setTitle(R.string.app_name).setMessage(message).setCancelable(true).setPositiveButtonTextColor(R.color.white).setPositiveButtonText(_activity.getString(R.string.dialog_ok_button)).setPositiveButtonClick(new Closure() { // from class: edu.communication.hemo.custom.Utils.8
            @Override // edu.communication.hemo.custom.Closure
            public void exec() {
            }
        }).show();
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    public static void showToast(Context context, String profile_image) {
        Toast.makeText(context, profile_image, 0).show();
    }
}
