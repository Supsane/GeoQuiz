package com.chashurin.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.chashurin.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.chashurin.geoquiz.answer_shown";
    private static final String KEY_ANSWER_IS_TRUE = "key_answer_is_true";
    private TextView mAnswerTextView, mLevelAPITextView;
    private Button mShowAnswer;
    private boolean mAnswerIsTrue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CheatActivity", "onCreate");
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE);
        }
        initView ();
        mShowAnswerBehavior ();

        String versionAPI = "API level " + Build.VERSION.SDK_INT;
        mLevelAPITextView.setText(versionAPI);
    }



    private void initView() {
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mLevelAPITextView = (TextView) findViewById(R.id.version_SDK_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
    }

    private void mShowAnswerBehavior() {
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                int cx = mShowAnswer.getWidth() / 2;
                int cy = mShowAnswer.getHeight() / 2;
                float radius = mShowAnswer.getWidth();


                Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnswerTextView.setVisibility(View.VISIBLE);
                        mShowAnswer.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    private void setAnswerShownResult (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown (Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("CheatActivity", "onSaveInstanceState");
        outState.putBoolean(KEY_ANSWER_IS_TRUE, mAnswerIsTrue);
        super.onSaveInstanceState(outState);
    }
}
