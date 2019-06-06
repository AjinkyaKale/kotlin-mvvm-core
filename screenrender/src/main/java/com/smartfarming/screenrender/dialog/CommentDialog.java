package com.smartfarming.screenrender.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.fragment.BaseDialogFragment;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.RatingCommentModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.view.CustomFontTextView;
import io.realm.Realm;

public class CommentDialog extends BaseDialogFragment implements OnFragmentLoad {

    Context context;

    @BindView(R2.id.etComment)
    EditText etComment;

    @BindView(R2.id.tv_dialog_description)
    CustomFontTextView tv_dialog_description;

    @BindView(R2.id.btnOk)
    Button btnOk;

    @BindView(R2.id.btnCancel)
    Button btnCancel;


    private String screenId;

    public CommentDialog(Context context, String screenId) {
        this.context = context;
        this.screenId = screenId;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_comment, container, false);

        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setLanguageLabel();
            setScreenContent();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    @OnClick(R2.id.btnOk)
    public void onSubmit() {

        String comment = etComment.getText().toString();

        if (!TextUtils.isEmpty(comment)) {

            Realm realm = RealmController.getInstance().getRealm();
            realm.beginTransaction();

            RatingCommentModel ratingCommentModel = null;
            ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screenId);
            if (ratingCommentModel != null) {
                ratingCommentModel.setComment(comment);
                if (!TextUtils.isEmpty(ratingCommentModel.getRating())) {
                    ratingCommentModel.setRating(ratingCommentModel.getRating());
                } else {
                    ratingCommentModel.setRating("");
                }
                if (!TextUtils.isEmpty(ratingCommentModel.getSelectedButton())) {
                    ratingCommentModel.setSelectedButton(ratingCommentModel.getSelectedButton());
                } else {
                    ratingCommentModel.setSelectedButton("");
                }
            } else {
                ratingCommentModel = new RatingCommentModel();
                ratingCommentModel.setScreenId(screenId);
                ratingCommentModel.setComment(comment);
                ratingCommentModel.setRating("");
                ratingCommentModel.setSelectedButton("");
            }
            ratingCommentModel.setSync(false);
            realm.commitTransaction();

            ScreenManager.saveRatingComment(ratingCommentModel);

           /* CommentModel newModel = new CommentModel();
            newModel.setScreenId(screenId);
            newModel.setComment(comment);
            ScreenManager.saveComment(newModel);*/
            dismiss();
        } else {
            AppUtility.showToastBykey(getActivity(), "comment_validation");
        }

    }

    @OnClick(R2.id.btnCancel)
    public void OnCancel() {
        dismiss();
    }


    private void setComment() {
        RatingCommentModel ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screenId);

        if (ratingCommentModel != null) {
            String previousComment = ratingCommentModel.getComment();
            if (!TextUtils.isEmpty(previousComment)) {
                etComment.setText(previousComment);
            }
        }
    }

    @Override
    public void getPreviousArguments() {

    }

    @Override
    public void setLanguageLabel() {
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        tv_dialog_description.setText(languageManager.getLabel(LabelConstant.LEAVE_COMMENT));
        btnCancel.setText(languageManager.getLabel(LabelConstant.BTN_CANCEL));
        btnOk.setText(languageManager.getLabel(LabelConstant.BTN_OK));
    }

    @Override
    public void setScreenContent() {
        setComment();
    }
}
