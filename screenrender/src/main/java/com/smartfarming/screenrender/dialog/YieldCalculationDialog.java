package com.smartfarming.screenrender.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.listeners.OnYieldCalculation;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.util.LabelConstant;

public class YieldCalculationDialog extends Dialog {

    public static final String AVERAGE_DESIRED_RATE = "average_desired_rate";
    public static final String MULTIPLICATION_DESIRED_RATE = "multiplication_desired_rate";

    @BindView(R2.id.tvTitlePumpCapacityDialog)
    TextView tvTitlePumpCapacityDialog;

    /*@BindView(R2.id.tv_desired_rate)
    TextView tv_desired_rate;*/

    @BindView(R2.id.tvHowToCalculate)
    TextView tvHowToCalculate;

    @BindView(R2.id.tvDescription)
    TextView tvDescription;

    @BindView(R2.id.et_yeild)
    EditText et_yeild;

    @BindView(R2.id.etSeed)
    EditText etSeed;

    /*@BindView(R2.id.edt_manual_desired_rate)
    EditText edt_manual_desired_rate;*/

    @BindView(R2.id.btnSubmit)
    Button btnSubmit;

    private LanguageManager mLanguageManager;
    private Context mContext;
    private OnYieldCalculation mListener;
    private String mNavigateFrom = "";

    public YieldCalculationDialog(@NonNull Context context, OnYieldCalculation onYieldCalculation, String navigateFrom) {
        super(context);
        this.mContext = context;
        this.mListener = onYieldCalculation;
        this.mNavigateFrom = navigateFrom;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.dialog_yield_capacity);
        setCanceledOnTouchOutside(false);

        mLanguageManager = mLanguageManager.getInstance(getContext());
        ButterKnife.bind(this);
        setLabel();
    }

    /**
     * set label to screens
     */
    private void setLabel() {

        tvHowToCalculate.setText(mLanguageManager.getLabel(LabelConstant.HOW_TO_CALCULATE));
        et_yeild.setHint(mLanguageManager.getLabel(LabelConstant.BAGS_YIELD) + " ");
        etSeed.setHint(mLanguageManager.getLabel(LabelConstant.BAGS_SEEDS));
        btnSubmit.setText(mLanguageManager.getLabel(LabelConstant.SUBMIT));

        if (mNavigateFrom.equalsIgnoreCase(AVERAGE_DESIRED_RATE)) {
            // tv_desired_rate.setText(mLanguageManager.getLabel(LabelConstant.AVERAGE_DESIRED_RATE_THIS_YEAR));
            tvTitlePumpCapacityDialog.setText(mLanguageManager.getLabel(LabelConstant.CALCULATE_AVERAGE_RATE));
        } else if (mNavigateFrom.equalsIgnoreCase(MULTIPLICATION_DESIRED_RATE)) {
            //tv_desired_rate.setText(mLanguageManager.getLabel(LabelConstant.MULTIPLICATION_DESIRED_RATE_THIS_YEAR));
            tvTitlePumpCapacityDialog.setText(mLanguageManager.getLabel(LabelConstant.CALCULATE_DESIRE_RATE));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(mLanguageManager.getLabel(LabelConstant.AVERAGE_RATE_CALCULATION_DESCRIPTION), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(mLanguageManager.getLabel(LabelConstant.AVERAGE_RATE_CALCULATION_DESCRIPTION)));
        }
    }

    /**
     * Calculate desired multiplication rate
     */
    @OnClick(R2.id.btnSubmit)
    public void onSubmitClick() {

        //String manualDesiredRate = edt_manual_desired_rate.getText().toString();

       /* if (!TextUtils.isEmpty(manualDesiredRate)) {
            //Toast.makeText(mContext ,manualDesiredRate ,Toast.LENGTH_LONG).show();
            mListener.onResultCalculation(manualDesiredRate);
            dismiss();
        } else {*/
        // calculate average rate
        if (TextUtils.isEmpty(et_yeild.getText().toString()) && TextUtils.isEmpty(etSeed.getText().toString())) {
            // if both yield and seeds are empty
            Toast.makeText(mContext, mLanguageManager.getLabel(LabelConstant.ENTER_VALID_DESIRED_RATE), Toast.LENGTH_SHORT).show();
        } else {
            if (!TextUtils.isEmpty(et_yeild.getText().toString())) {
                if (!TextUtils.isEmpty(etSeed.getText().toString())) {
                    int result = Integer.parseInt(et_yeild.getText().toString()) / Integer.parseInt(etSeed.getText().toString());
                    mListener.onResultCalculation(String.valueOf(result));
                    dismiss();
                } else {
                    Toast.makeText(mContext, mLanguageManager.getLabel(LabelConstant.ENTER_VALID_BAGS_SEED), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, mLanguageManager.getLabel(LabelConstant.ENTER_VALID_BAGS_YIELD), Toast.LENGTH_SHORT).show();
            }
        }
        //}
    }
}
