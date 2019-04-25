package com.vlonjatg.progressactivity;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.view.View;

import java.util.List;

public interface ProgressLayout {

    @ColorInt
    int PROGRESS_COLOR = 0xFF3F51B5;

    void showContent();

    void showContent(List<Integer> idsOfViewsNotToShow);

    void showLoading();

    void showLoading(List<Integer> idsOfViewsNotToHide);

    void showEmpty(int icon, String title, String description);

    void showEmpty(Drawable icon, String title, String description);

    void showEmpty(int icon, String title, String description, List<Integer> idsOfViewsNotToHide);

    void showEmpty(Drawable icon, String title, String description, List<Integer> idsOfViewsNotToHide);

    void showError(int icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener);

    void showError(Drawable icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener);

    void showError(int icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide);

    void showError(Drawable icon, String title, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide);

    String getCurrentState();

    boolean isContentCurrentState();

    boolean isLoadingCurrentState();

    boolean isEmptyCurrentState();

    boolean isErrorCurrentState();
}
