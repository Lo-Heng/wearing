package com.androidlo.wearing.view;

import android.app.Activity;

/**
 * Created by 62361 on 2019/4/5.
 */

public interface IMainActivityView {

    void showToast(String text);

    void startNewPage(Class newPage);
}
