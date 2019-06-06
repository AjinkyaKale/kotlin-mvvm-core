package com.smartfarming.screenrender.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.dialog.CommentDialog;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.RatingCommentModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdviceImageTextQuestionYesNoRatingFragment extends BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.tvTextHyperlink)
    CustomFontTextView tvTextHyperlink;

    @BindView(R2.id.tvQuestion)
    CustomFontTextView tvQuestion;

    @BindView(R2.id.tvPlzRate)
    CustomFontTextView tvPlzRate;

    @BindView(R2.id.tvComment)
    CustomFontTextView tvComment;

    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.llRating)
    LinearLayout llRating;


    @BindView(R2.id.viewRatingBar_1)
    RatingBar rating_bar;

    private String screen_id;
    private ScreenModel screenModel;


    public static Fragment newInstance() {
        AdviceImageTextQuestionYesNoRatingFragment fragment = new AdviceImageTextQuestionYesNoRatingFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advice_image_text_question_yes_no_rating, container, false);
        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());
        return view;
    }


    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            screen_id = getArguments().getString(Constants.SCREEN_ID_KEY);
            title_label = getArguments().getString(Constants.SCREEN_TITLE_KEY);
        }
    }

    @Override
    public void setLanguageLabel() {
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        tvComment.setText(languageManager.getLabel("leave_comment"));
        tvPlzRate.setText(languageManager.getLabel("rate_advice"));

        btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(0).getLable()));
        btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(1).getLable()));

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvTextHyperlink, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tvQuestion, screenModel.getTextModels().get(1).getValue(), this);
            setImageAdapter(screenModel.getImageModels());
        }
        setRatingAndComment();
    }


    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }


    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imageDescriptionPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imageDescriptionPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }


    @OnClick(R2.id.llRating)
    public void OnclickComment() {
        CommentDialog commentDialog = new CommentDialog(getActivity(), screen_id);
        commentDialog.show(getFragmentManager(), getClass().getName());
    }


    /**
     * set previous rating and listener to rating bar
     */
    private void setRatingAndComment() {
        RatingCommentModel ratingCommentModel = null;
        ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screen_id);
        if (ratingCommentModel != null) {
            String previousRating = ratingCommentModel.getRating();
            if (!TextUtils.isEmpty(previousRating)) {
                rating_bar.setRating(Float.parseFloat(previousRating));
            }
        }

        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Rating : " + rating, Toast.LENGTH_SHORT).show();

                Realm realm = RealmController.getInstance().getRealm();
                realm.beginTransaction();
                RatingCommentModel ratingCommentModel = null;
                ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screen_id);
                if (ratingCommentModel != null) {
                    ratingCommentModel.setRating(rating + "");
                    if (!TextUtils.isEmpty(ratingCommentModel.getComment())) {
                        ratingCommentModel.setComment(ratingCommentModel.getComment());
                    } else {
                        ratingCommentModel.setComment("");
                    }
                    if (!TextUtils.isEmpty(ratingCommentModel.getSelectedButton())) {
                        ratingCommentModel.setSelectedButton(ratingCommentModel.getSelectedButton());
                    } else {
                        ratingCommentModel.setSelectedButton("");
                    }
                } else {
                    ratingCommentModel = new RatingCommentModel();
                    ratingCommentModel.setScreenId(screen_id);
                    ratingCommentModel.setRating(rating + "");
                    ratingCommentModel.setComment("");
                    ratingCommentModel.setSelectedButton("");
                }
                ratingCommentModel.setSync(false);
                realm.commitTransaction();
                ScreenManager.saveRatingComment(ratingCommentModel);
            }
        });
    }


}
