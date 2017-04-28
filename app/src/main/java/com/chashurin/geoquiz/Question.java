package com.chashurin.geoquiz;

/**
 * Created by Чашурин on 25.04.2017.
 */

class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    Question(int mTextResId, boolean mAnswerTrue) {
        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
    }

    int getmTextResId() {
        return mTextResId;
    }

    boolean ismAnswerTrue() {
        return mAnswerTrue;
    }
}
