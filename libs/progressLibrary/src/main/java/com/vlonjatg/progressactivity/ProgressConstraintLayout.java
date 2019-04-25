package com.vlonjatg.progressactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressConstraintLayout extends ConstraintLayout implements ProgressLayout {

    private final String CONTENT = "type_content";
    private final String LOADING = "type_loading";
    private final String EMPTY = "type_empty";
    private final String ERROR = "type_error";

    private LayoutInflater inflater;
    private View view;
    private Drawable defaultBackground;

    private List<View> contentViews = new ArrayList<>();

    private View loadingState;
    private ProgressBar loadingStateProgressBar;

    private View emptyState;
    private ImageView emptyStateImageView;
    private TextView emptyStateTitleTextView;
    private TextView emptyStateContentTextView;

    private View errorState;
    private ImageView errorStateImageView;
    private TextView errorStateTitleTextView;
    private TextView errorStateContentTextView;
    private LinearLayout errorStateButton;

    private int loadingStateProgressBarWidth;
    private int loadingStateProgressBarHeight;
    private int loadingStateProgressBarColor;
    private int loadingStateBackgroundColor;

    private int emptyStateImageWidth;
    private int emptyStateImageHeight;
    private int emptyStateTitleTextSize;
    private int emptyStateTitleTextColor;
    private int emptyStateContentTextSize;
    private int emptyStateContentTextColor;
    private int emptyStateBackgroundColor;

    private int errorStateBackgroundColor;

    private String state = CONTENT;

    int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    int getDimensionPixels(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public ProgressConstraintLayout(Context context) {
        super(context);
    }

    public ProgressConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressConstraintLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressActivity);

        //Loading state attrs
        loadingStateProgressBarWidth =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_loadingProgressBarWidth, 108);

        loadingStateProgressBarHeight =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_loadingProgressBarHeight, 108);

        loadingStateProgressBarColor =
                typedArray.getColor(R.styleable.ProgressActivity_loadingProgressBarColor, PROGRESS_COLOR);

        loadingStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressActivity_loadingBackgroundColor, Color.TRANSPARENT);

        //Empty state attrs
        emptyStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyImageWidth, getDimensionPixels(R.dimen.empty_state_image_width));

        emptyStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyImageHeight, getDimensionPixels(R.dimen.empty_state_image_height));

        emptyStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyTitleTextSize, 18);

        emptyStateTitleTextColor =
                typedArray.getColor(R.styleable.ProgressActivity_emptyTitleTextColor, getColor(R.color.empty_state_title_text_color));

        emptyStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyContentTextSize, 12);

        emptyStateContentTextColor =
                typedArray.getColor(R.styleable.ProgressActivity_emptyContentTextColor, getColor(R.color.empty_state_content_text_color));

        emptyStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressActivity_emptyBackgroundColor, Color.TRANSPARENT);

        errorStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressActivity_errorBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        defaultBackground = this.getBackground();
    }

    @Override
    public void showContent() {
        switchState(CONTENT, 0, null, null, null, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showContent(List<Integer> idsOfViewsNotToShow) {
        switchState(CONTENT, 0, null, null, null, null, idsOfViewsNotToShow);
    }

    @Override
    public void showLoading() {
        switchState(LOADING, 0, null, null, null, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showLoading(List<Integer> idsOfViewsNotToHide) {
        switchState(LOADING, 0, null, null, null, null, idsOfViewsNotToHide);
    }

    @Override
    public void showEmpty(int icon, String title, String description) {
        switchState(EMPTY, icon, title, description, null, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showEmpty(Drawable icon, String title, String description) {
        switchState(EMPTY, icon, title, description, null, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showEmpty(int icon, String title, String description, List<Integer> idsOfViewsNotToHide) {
        switchState(EMPTY, icon, title, description, null, null, idsOfViewsNotToHide);
    }

    @Override
    public void showEmpty(Drawable icon, String title, String description, List<Integer> idsOfViewsNotToHide) {
        switchState(EMPTY, icon, title, description, null, null, idsOfViewsNotToHide);
    }

    @Override
    public void showError(int icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, Collections.<Integer>emptyList());
    }

    @Override
    public void showError(Drawable icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, Collections.<Integer>emptyList());
    }

    @Override
    public void showError(int icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, idsOfViewsNotToHide);
    }

    @Override
    public void showError(Drawable icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, idsOfViewsNotToHide);
    }

    private void switchState(String state, int icon, String title, String description,
                             String buttonText, OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide) {
        this.state = state;

        hideAllStates();

        switch (state) {
            case CONTENT:
                setContentVisibility(true, idsOfViewsNotToHide);
                break;
            case LOADING:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateLoadingView();
                break;
            case EMPTY:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateEmptyView();

                emptyStateImageView.setImageResource(icon);
                emptyStateTitleTextView.setText(title);
                emptyStateContentTextView.setText(description);
                break;
            case ERROR:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateErrorView();

                errorStateImageView.setImageResource(icon);
                errorStateTitleTextView.setText(title);
                errorStateContentTextView.setText(description);
                errorStateButton.setOnClickListener(buttonClickListener);
                break;
        }
    }

    private void switchState(String state, Drawable icon, String title, String description,
                             String buttonText, OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide) {
        this.state = state;

        hideAllStates();

        switch (state) {
            case CONTENT:
                setContentVisibility(true, idsOfViewsNotToHide);
                break;
            case LOADING:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateLoadingView();
                break;
            case EMPTY:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateEmptyView();

                emptyStateImageView.setImageDrawable(icon);
                emptyStateTitleTextView.setText(title);
                emptyStateContentTextView.setText(description);
                break;
            case ERROR:
                setContentVisibility(false, idsOfViewsNotToHide);
                inflateErrorView();

                errorStateImageView.setImageDrawable(icon);
                errorStateTitleTextView.setText(title);
                errorStateContentTextView.setText(description);
                errorStateButton.setOnClickListener(buttonClickListener);
                break;
        }
    }

    private void hideAllStates() {
        hideLoadingView();
        hideEmptyView();
        hideErrorView();
        restoreDefaultBackground();
    }

    private void hideLoadingView() {
        if (loadingState != null) {
            loadingState.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyState != null) {
            emptyState.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorState != null) {
            errorState.setVisibility(GONE);
        }
    }

    private void restoreDefaultBackground() {
        this.setBackgroundDrawable(defaultBackground);
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void inflateLoadingView() {
        if (loadingState == null) {
            view = inflater.inflate(R.layout.view_loading, null);
            loadingState = view.findViewById(R.id.layout_loading);
            loadingState.setTag(LOADING);

            loadingStateProgressBar = view.findViewById(R.id.progress_bar_loading);
            loadingStateProgressBar.getLayoutParams().width = loadingStateProgressBarWidth;
            loadingStateProgressBar.getLayoutParams().height = loadingStateProgressBarHeight;
            loadingStateProgressBar.getIndeterminateDrawable()
                    .setColorFilter(loadingStateProgressBarColor, PorterDuff.Mode.SRC_IN);
            loadingStateProgressBar.requestLayout();

            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(loadingStateBackgroundColor);
            }

            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(loadingState, layoutParams);
        } else {
            loadingState.setVisibility(VISIBLE);
        }
    }

    private void inflateEmptyView() {
        if (emptyState == null) {
            view = inflater.inflate(R.layout.view_empty, null);
            emptyState = view.findViewById(R.id.layout_empty);
            emptyState.setTag(EMPTY);

            emptyStateImageView = view.findViewById(R.id.image_icon);
            emptyStateTitleTextView = view.findViewById(R.id.text_title);
            emptyStateContentTextView = view.findViewById(R.id.text_description);

            emptyStateImageView.getLayoutParams().width = emptyStateImageWidth;
            emptyStateImageView.getLayoutParams().height = emptyStateImageHeight;
            emptyStateImageView.requestLayout();

            emptyStateTitleTextView.setTextSize(emptyStateTitleTextSize);
            emptyStateTitleTextView.setTextColor(emptyStateTitleTextColor);

            emptyStateContentTextView.setTextSize(emptyStateContentTextSize);
            emptyStateContentTextView.setTextColor(emptyStateContentTextColor);

            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(emptyStateBackgroundColor);
            }

            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(emptyState, layoutParams);
        } else {
            emptyState.setVisibility(VISIBLE);
        }
    }

    private void inflateErrorView() {
        if (errorState == null) {
            view = inflater.inflate(R.layout.view_error, null);
            errorState = view.findViewById(R.id.layout_error);
            errorState.setTag(ERROR);

            errorStateImageView = view.findViewById(R.id.image_icon);
            errorStateTitleTextView = view.findViewById(R.id.text_title);
            errorStateContentTextView = view.findViewById(R.id.text_description);
            errorStateButton = view.findViewById(R.id.button_retry);

            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(errorStateBackgroundColor);
            }

            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(errorState, layoutParams);
        } else {
            errorState.setVisibility(VISIBLE);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(LOADING) &&
                !child.getTag().equals(EMPTY) && !child.getTag().equals(ERROR))) {

            contentViews.add(child);
        }
    }

    @Override
    public String getCurrentState() {
        return state;
    }

    @Override
    public boolean isContentCurrentState() {
        return state.equals(CONTENT);
    }

    @Override
    public boolean isLoadingCurrentState() {
        return state.equals(LOADING);
    }

    @Override
    public boolean isEmptyCurrentState() {
        return state.equals(EMPTY);
    }

    @Override
    public boolean isErrorCurrentState() {
        return state.equals(ERROR);
    }
}
