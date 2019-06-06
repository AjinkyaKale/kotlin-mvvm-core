package com.smartfarming.screenrender.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {

    DatePickerDialog datePickerDialog;
    private Context context;
    private Calendar minDate, maxDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static String DATE_PICKER = "DatePicker";


    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener callback, Calendar MinDate, Calendar MaxDate, Context context) {
        mDateSetListener = callback;
        this.minDate = MinDate;
        this.maxDate = MaxDate;
        this.context = context;

    }

    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener callback, Context context) {
        mDateSetListener = callback;
        this.context = context;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(), this.mDateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        }

        if (maxDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
        }


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return datePickerDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}