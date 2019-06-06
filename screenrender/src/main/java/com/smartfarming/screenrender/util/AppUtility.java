package com.smartfarming.screenrender.util;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.smartfarming.screenrender.listeners.OnDateSelectListener;
import com.smartfarming.screenrender.manager.LanguageManager;
import org.xml.sax.XMLReader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AppUtility {


    /**
     * Checks if is network available.
     *
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED
                                || info[i].getState() == NetworkInfo.State.CONNECTING) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Show alert com.ee.com.smartfarming.dialog.
     *
     * @param context     the context
     * @param title       the title
     * @param message     the message
     * @param positiveBtn the positive btn
     * @param negativeBtn the negative btn
     * @param listner     the listner
     */
    public static void showAlertDialog(Context context, String title,
                                       String message, String positiveBtn, String negativeBtn,
                                       OnDialogButtonClickListener listner) {
        final OnDialogButtonClickListener mListner = listner;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        mListner.onDialogButtonClickListner(Constants.DIALOG_ACTION_OK);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        mListner.onDialogButtonClickListner(Constants.DIALOG_ACTION_CANCEL);
                        dialog.dismiss();
                        break;

                    default:
                        break;
                }
            }
        };
        AlertDialog dialog;

        if (!negativeBtn.equalsIgnoreCase("reminder")) {
            if (title.equalsIgnoreCase("")) {
                dialog = new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton(positiveBtn, dialogClickListener)
                        .setNegativeButton(negativeBtn, dialogClickListener)
                        .setCancelable(true).create();
            } else {
                dialog = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(positiveBtn, dialogClickListener)
                        .setNegativeButton(negativeBtn, dialogClickListener)
                        .setCancelable(true).create();
            }
        } else {
            dialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveBtn, dialogClickListener)
                    .setCancelable(true).create();
        }


        dialog.show();
    }


    /**
     * only show the alert dialog
     *
     * @param mContext
     * @param msg
     */
    public static void showAlertDialog(Context mContext, String msg) {
        LanguageManager languageManager = LanguageManager.getInstance(mContext);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                languageManager.getLabel(LabelConstant.BTN_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    /**
     * show dialog with Ok button and
     * finish the activity
     *
     * @param mContext
     * @param msg
     */
    public static void showDialogAndFinish(final Context mContext, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        ((Activity) mContext).finish();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    /**
     * get date with added days for the given date
     *
     * @param current_date
     * @param format
     * @param addNoOfDays
     * @return
     */
    public static String getAddedDateWithFormat(String current_date, String format, int addNoOfDays) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(current_date));
            c.add(Calendar.DAY_OF_YEAR, addNoOfDays);
            //sdf = new SimpleDateFormat(format);
            Date resultDate = new Date(c.getTimeInMillis());
            String dateInString = sdf.format(resultDate);
            return dateInString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * show toast by the label-key
     *
     * @param context
     * @param key
     */
    public static void showToastBykey(Context context, String key) {
        LanguageManager languageManager = LanguageManager.getInstance(context);
        Toast.makeText(context, languageManager.getLabel(key), Toast.LENGTH_SHORT).show();
    }


    /**
     * show the validation toast by the message.
     *
     * @param context
     * @param msg
     */
    public static void validationToast(Context context, String msg) {
        LanguageManager languageManager = LanguageManager.getInstance(context);
        Toast.makeText(context, languageManager.getLabel("Please_select") + " " + languageManager.getLabel(msg), Toast.LENGTH_SHORT).show();
    }


    /**
     * round the double value
     *
     * @param val
     * @return
     */
    public static String getRoundValue(Double val) {
        Double a = Math.ceil(val);
        return String.valueOf(a);
    }


    /**
     * get current date with  datePickerDialog-MM-yyyy format
     *
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }


    /**
     * get date for given format
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format) {

        try {
            SimpleDateFormat curFormater = new SimpleDateFormat(format);
            Date dateObj = curFormater.parse(date);
            return dateObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * method to render the html content with hyperlink
     *
     * @param textView
     * @param screenContent
     * @param listener
     */
    public static void renderHtmlInATextView(TextView textView, String screenContent, final OnHtmlLinkClickListener listener) {

        CharSequence sequence;
        if (TextUtils.isEmpty(screenContent)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sequence = Html.fromHtml(screenContent, Html.FROM_HTML_MODE_LEGACY, null, new UlTagHandler());
        } else {
            sequence = Html.fromHtml(screenContent, null, new UlTagHandler());
        }
//        CharSequence sequence = Html.fromHtml(screenContent, null, new UlTagHandler());    ///get the html  sequence from String Name
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence); //get the string in spannable string builder
        URLSpan[] myspan = strBuilder.getSpans(0, sequence.length(), URLSpan.class);//get  the span  of clickable text from index 0 to length
        for (URLSpan span : myspan) {
            makeLinkClickable(strBuilder, span, listener);// method to make the span clickable
        }
        textView.setText(strBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public static class UlTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.equals("ul") && !opening) output.append("\n");
            if (tag.equals("li") && opening) output.append("\n\n\t• ");
        }
    }


    private static void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span, final OnHtmlLinkClickListener listener) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {                      /////Method to write onclick events of Span
            public void onClick(View view) {
                listener.onHtmlLinkClickListener(span.getURL());
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);      ///Remove the specified object from the range of text to which it was attached
    }


    /**
     * Check permission for access external storage
     *
     * @param context
     * @return
     */
    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * request for the storage permission
     *
     * @param activity
     */
    public static void requestPermission(Activity activity) {

        //ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_CODE);

    }


    /**
     * show date picker dialog and set selected date to textview
     *
     * @param context
     * @param id
     */
    public static void showDatePickerDialog(Context context, final String dateFormat, final int id, final OnDateSelectListener listener) {
        {
            // Get Current Date
            final Calendar currentDate = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                            String strDate = format.format(calendar.getTime());
                            listener.onDateSelectListener(strDate, id);

                        }
                    }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
            //datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, context.getString(R.string.all_ok), datePickerDialog);
            datePickerDialog.show();
        }
    }

}
