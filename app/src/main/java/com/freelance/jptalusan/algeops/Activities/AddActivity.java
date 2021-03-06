package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.AlgeOpsImageView;
import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.AutoResizeTextView;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.LayoutUtilities;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseOpsActivity {
    private static final String TAG = "AddActivity";
    private ImageButton leftXAdd;
    private ImageButton leftXSub;
    private ImageButton rightXAdd;
    private ImageButton rightXSub;
    private ImageButton leftOneAdd;
    private ImageButton leftOneSub;
    private ImageButton rightOneAdd;
    private ImageButton rightOneSub;

    protected AlgeOpsRelativeLayout layoutRightX;
    protected AlgeOpsRelativeLayout layoutLeftX;
    protected AlgeOpsRelativeLayout layoutRightOne;
    protected AlgeOpsRelativeLayout layoutLeftOne;

    private View oneSeekBarIVRL;

    private int countX = 0;
    private int countOne = 0;

    private boolean hasStartedAnimationX = false;
    private boolean hasStartedAnimation1 = false;
    private int currentScore = 0;
    private int totalPlayed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (prefs.getBoolean(Constants.IS_FIRST_RUN_ADD, true)) {
            prefs.edit().putBoolean(Constants.IS_FIRST_RUN_ADD, false).apply();
            prefs.edit().putInt(Constants.ADD_LEVEL, 1).apply();
            prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, 0).apply();
        }
        level = prefs.getInt(Constants.ADD_LEVEL, 1);

        startButton     = findViewById(R.id.newQuestionButton);
        checkButton     = findViewById(R.id.checkButton);
        resetButton     = findViewById(R.id.resetButton);
        goBackToLevel1  = findViewById(R.id.goBackToLevel1);

        leftXAdd        = findViewById(R.id.leftXAdd);
        leftXSub        = findViewById(R.id.leftXSub);
        rightXAdd       = findViewById(R.id.rightXAdd);
        rightXSub       = findViewById(R.id.rightXSub);
        leftOneAdd      = findViewById(R.id.leftOneAdd);
        leftOneSub      = findViewById(R.id.leftOneSub);
        rightOneAdd     = findViewById(R.id.rightOneAdd);
        rightOneSub     = findViewById(R.id.rightOneSub);

        layoutLeftX     = findViewById(R.id.layoutLeftX);
        layoutRightX    = findViewById(R.id.layoutRightX);
        layoutLeftOne   = findViewById(R.id.layoutLeftOne);
        layoutRightOne  = findViewById(R.id.layoutRightOne);

        firstPartEq     = findViewById(R.id.firstEqTextView);
        secondPartEq    = findViewById(R.id.secondEqTextView);

        xSeekbar        = findViewById(R.id.xSeekBar);
        oneSeekbar      = findViewById(R.id.oneSeekBar);

        score           = findViewById(R.id.score);

        oneSeekBarIVRL  = findViewById(R.id.oneSeekBarIVRL);

        //1 thumb
        ArrayList<String> points = new ArrayList<>();
        points.add("-10");
        points.add("-9");
        points.add("-8");
        points.add("-7");
        points.add("-6");
        points.add("-5");
        points.add("-4");
        points.add("-3");
        points.add("-2");
        points.add("-1");
        points.add(" 0");
        points.add(" 1");
        points.add(" 2");
        points.add(" 3");
        points.add(" 4");
        points.add(" 5");
        points.add(" 6");
        points.add(" 7");
        points.add(" 8");
        points.add(" 9");
        points.add("10");

        xSeekbar.seekBar.setMax(20);
        xSeekbar.setValues(points);
        xSeekbar.getViewDimensions();
        xSeekbar.seekBar.setProgress(10);
        xSeekbar.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d(TAG, "thumbx: " + progress);
                xSeekbar.removeAllViewsInRelativeLayout();
                xSeekbar.setUserAnswer(progress - 10);
                xSeekbar.drawValuesInRelativeLayout(progress - 10, false);
                if (progress != 21) {
                    xSeekbarImageView.setText(Integer.toString(progress - 10));
                    if (progress - 10 > 0)
                        xSeekbarImageView.setTextColor(Color.GREEN);
                    else if (progress - 10 < 0)
                        xSeekbarImageView.setTextColor(Color.RED);
                    else
                        xSeekbarImageView.setTextColor(Color.BLACK);
                } else if (progress == 21) {
                    xSeekbarImageView.setText(Integer.toString(10));
                    xSeekbarImageView.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        oneSeekbar.seekBar.setMax(20);
        oneSeekbar.setValues(points);
        oneSeekbar.getViewDimensions();
        oneSeekbar.seekBar.setProgress(10);
        oneSeekbar.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d(TAG, "thumb1: " + progress);
                oneSeekbar.removeAllViewsInRelativeLayout();
                oneSeekbar.setUserAnswer(progress - 10);
                oneSeekbar.drawValuesInRelativeLayout(progress - 10, false);
                if (progress != 21) {
                    oneSeekbarImageView.setText(Integer.toString(progress - 10));
                    if (progress - 10 > 0)
                        oneSeekbarImageView.setTextColor(Color.GREEN);
                    else if (progress - 10 < 0)
                        oneSeekbarImageView.setTextColor(Color.RED);
                    else
                        oneSeekbarImageView.setTextColor(Color.BLACK);
                } else if (progress == 21) {
                    xSeekbarImageView.setText(Integer.toString(10));
                    xSeekbarImageView.setTextColor(Color.GREEN);
                }

                if (oneSeekbarImageView.getText().toString().contains("10")) {
                    float a = prefs.getFloat("textSize", 8) - 2;
                    Log.d(TAG, "if 10: " + (prefs.getFloat("textSize", 8) - 2));
                    oneSeekbarImageView.setTextSize(a);
                } else {
                    Log.d(TAG, "else: " + (prefs.getFloat("textSize", 8)));
                    oneSeekbarImageView.setTextSize(prefs.getFloat("textSize", 8));
                }
            }
        });

        xSeekbarImageView   = findViewById(R.id.xSeekBarImage);
        oneSeekbarImageView = findViewById(R.id.oneSeekBarImage);

        operationImageView = findViewById(R.id.operationImageView);
        operationImageView.setImageResource(R.drawable.plus);

        startAlgeOps();

        goBackToLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().putInt(Constants.ADD_LEVEL, 1).apply();
                prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, 0).apply();
                startAlgeOps();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStartedAnimationX && !hasStartedAnimation1)
                    reset();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPlayed++;
                if (!hasStartedAnimationX && !hasStartedAnimation1) {
                    int correctAnswers = prefs.getInt(Constants.CORRECT_ADD_ANSWERS, 0);
                    if (!isSecondAnswerCorrect) {
                        Log.d(TAG, "First ans correct.");
                        if (isSeekBarAnswerCorrect()) {
                            isSecondAnswerCorrect = true;
                            //Count if the user has 10 consecutive answers
                            correctAnswers++;
                            Log.d(TAG, "Correct: " + correctAnswers);
                            Toast.makeText(AddActivity.this, "You are correct!", Toast.LENGTH_SHORT).show();
                            if (correctAnswers == Constants.LEVEL_UP) {
                                Toast.makeText(AddActivity.this, "Congratulations! You are now in Level 2", Toast.LENGTH_SHORT).show();
                                prefs.edit().putInt(Constants.ADD_LEVEL, 2).apply();
                            }
                            prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, correctAnswers).apply();

                            currentScore++;
                            score.setText("Score: " + currentScore + "/" + totalPlayed);
                        } else {
                            score.setText("Score: " + currentScore + "/" + totalPlayed);
                            Toast.makeText(AddActivity.this, "You are incorrect.", Toast.LENGTH_SHORT).show();
                            playSound(R.raw.wrong);
                            if (correctAnswers != Constants.LEVEL_UP) {
                                correctAnswers = 0;
                                prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, correctAnswers).apply();
                                Log.d(TAG, "Back to start: " + correctAnswers);
                            }
                        }
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startAlgeOps();
                            }
                        }, 3000);
                    }
                }
            }
        });

        answerIsWrong();

        layoutLeftX.getViewDimensions();
        layoutRightX.getViewDimensions();
        layoutLeftOne.getViewDimensions();
        layoutRightOne.getViewDimensions();

        leftXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, layoutLeftX));
        leftXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_X, layoutLeftX));

        rightXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, layoutRightX));
        rightXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_X, layoutRightX));

        leftOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_ONE, layoutLeftOne));
        leftOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_ONE, layoutLeftOne));

        rightOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_ONE, layoutRightOne));
        rightOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_ONE, layoutRightOne));

        layoutLeftX.onAnimationEndListener(new AlgeOpsRelativeLayout.AnimationEndListener() {
            @Override
            public void onAnimationEnded(int val) {
                Log.d(TAG, "val/countX: " + val + "/" + (countX - 1));
                if (val == countX - 1) {
                    hasStartedAnimationX = false;
                }
                if (!hasStartedAnimation1 && !hasStartedAnimationX) {
                    answerIsCorrect();
                }
            }
        });

        layoutLeftOne.onAnimationEndListener(new AlgeOpsRelativeLayout.AnimationEndListener() {
            @Override
            public void onAnimationEnded(int val) {
                Log.d(TAG, "val/count1: " + val + "/" + (countOne - 1));
                if (val == countOne - 1) {
                    hasStartedAnimation1 = false;
                }
                if (!hasStartedAnimation1 && !hasStartedAnimationX) {
                    answerIsCorrect();
                }
            }
        });
    }

    //TODO: shouldn't be able to press reset when animation is starting
    @Override
    protected void reset() {
        super.reset();
        setAllButtonsClickabilitiy(true);
        layoutLeftX.resetLayout();
        layoutRightX.resetLayout();
        layoutLeftOne.resetLayout();
        layoutRightOne.resetLayout();
    }

    private void cancelOutViews() {
        Handler handler1 = new Handler();
        countX = LayoutUtilities.getNumberOfViewsToRemove(layoutLeftX, layoutRightX, Constants.OPS_X);
        countOne = LayoutUtilities.getNumberOfViewsToRemove(layoutLeftOne, layoutRightOne, Constants.OPS_ONE);
        Log.d(TAG, "To cancel out: X: " + countX + "/ One: " + countOne);
        hasStartedAnimationX = countX > 0;
        hasStartedAnimation1 = countOne > 0;

        if (!hasStartedAnimationX && !hasStartedAnimation1) {
            answerIsCorrect();
        }
        for (int i = 0; i < countX; i++) {
            final int temp = i;
            View v = layoutLeftX.getChildAt(i);
            if (v instanceof AlgeOpsImageView) {
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutLeftX.fadeOut(temp);
                        layoutRightX.fadeOut(temp);
                    }
                }, 1500 * i);
            }
        }

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler2 = new Handler();
                //TODO: add handler that would start only after i * 1000 ms
                for (int i = 0; i < countOne; i++) {
                    final int temp = i;
                    View v = layoutLeftOne.getChildAt(i);
                    if (v instanceof AlgeOpsImageView) {
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layoutLeftOne.fadeOut(temp);
                                layoutRightOne.fadeOut(temp);
                            }
                        }, 1500 * i);
                    }
                }
            }
        }, 2000 * (countX + 1) + 1000);

    }

    private boolean isSeekBarAnswerCorrect() {
        Log.d("Seekbar", "corrX:" + (eq.getAx() + eq.getCx()) + "");
        Log.d("Seekbar", "corr1:" + (eq.getB() + eq.getD()) + "");

        int xCorrectAnswer = eq.getAx() + eq.getCx();
        int oneCorrectAnswer = eq.getB() + eq.getD();

        if (xSeekbar.getUserAnswer() == xCorrectAnswer &&
                oneSeekbar.getUserAnswer() == oneCorrectAnswer) {
            playSound(R.raw.correct);
            Log.d(TAG, "correct");
            xSeekbar.seekBar.setEnabled(false);
            oneSeekbar.seekBar.setEnabled(false);
            return true;
        } else {
            playSound(R.raw.wrong);
            if (xSeekbar.getUserAnswer() != xCorrectAnswer) {
                xSeekbar.setCorrectAnswer(xCorrectAnswer);
                xSeekbar.answerIsIncorrect();
                xSeekbarImageView.setText(Integer.toString(xCorrectAnswer));
                if (xCorrectAnswer > 0)
                    xSeekbarImageView.setTextColor(Color.GREEN);
                else
                    xSeekbarImageView.setTextColor(Color.RED);
            }

            if (oneCorrectAnswer == 10 || oneCorrectAnswer == -10) {
                float a = prefs.getFloat("textSize", 8) - 2;
                Log.d(TAG, "if 10: " + (prefs.getFloat("textSize", 8) - 2));
                oneSeekbarImageView.setTextSize(a);
            } else {
                Log.d(TAG, "else: " + (prefs.getFloat("textSize", 8)));
                oneSeekbarImageView.setTextSize(prefs.getFloat("textSize", 8));
            }

            if (oneSeekbar.getUserAnswer() != oneCorrectAnswer) {
                oneSeekbar.setCorrectAnswer(oneCorrectAnswer);
                oneSeekbar.answerIsIncorrect();
                oneSeekbarImageView.setText(Integer.toString(oneCorrectAnswer));
                if (oneCorrectAnswer > 0)
                    oneSeekbarImageView.setTextColor(Color.GREEN);
                else
                    oneSeekbarImageView.setTextColor(Color.RED);
            }

            return false;
        }
    }

    protected void setEquationsLayout() {

        final int[] first = eq.getIntArr(1);
        final int[] second = eq.getIntArr(2);

        final int factor = getResources().getInteger(R.integer.addfactor);
        firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int tempH = firstPartEq.getMeasuredHeight();
                if (first[0] != 0) {
                    TextView tv = new TextView(AddActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[0])));
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    ImageView iv = new ImageView(AddActivity.this);
                    if (first[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);

                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (first[1] != 0) {
                    TextView tv = new TextView(AddActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[1])));
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    ImageView iv = new ImageView(AddActivity.this);
                    if (first[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);

                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (second[0] != 0) {
                    TextView tv = new TextView(AddActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[0])));
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    ImageView iv = new ImageView(AddActivity.this);
                    if (second[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);

                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }

                if (second[1] != 0) {
                    TextView tv = new TextView(AddActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[1])));
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    ImageView iv = new ImageView(AddActivity.this);
                    if (second[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);

                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }
            }
        });
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        firstPartEq.removeAllViews();
        secondPartEq.removeAllViews();

        if (prefs.getInt(Constants.ADD_LEVEL, 1) == Constants.LEVEL_1) {
            setEquationsLayout();
        } else {
            TextView tv1 = new TextView(this);
            tv1.setHeight(50);
            tv1.setText(firstPart);
            TextView tv2 = new TextView(this);
            tv2.setText(secondPart);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(tv1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(tv2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

            firstPartEq.addView(tv1);
            secondPartEq.addView(tv2);
        }

        layoutLeftX.resetLayout();
        layoutRightX.resetLayout();
        layoutLeftOne.resetLayout();
        layoutRightOne.resetLayout();

        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();

        xSeekbarImageView.setText("0");
        xSeekbarImageView.setTextColor(Color.BLACK);

        oneSeekbarImageView.setText("0");
        oneSeekbarImageView.setTextColor(Color.BLACK);

        setAllButtonsClickabilitiy(true);

        answerIsWrong();
    }

    private void checkIfTilesAreCorrect() {
        if (!isFirstAnswerCorrect) {
            boolean isCorrect = eq.isAdditionAnswerCorrect(layoutLeftX.currXVal,
                    layoutLeftOne.currOneVal,
                    layoutRightX.currXVal,
                    layoutRightOne.currOneVal);

            Log.d(TAG, "Check if 1st is correct: " + isCorrect);
            if (isCorrect) {
                isFirstAnswerCorrect = true;
                xSeekbar.getViewDimensions();
                oneSeekbar.getViewDimensions();
                cancelOutViews();
                setAllButtonsClickabilitiy(false);
                prefs.edit().putFloat("textSize", 14).commit();
            }
        }
    }

    public void setAllButtonsClickabilitiy(boolean b) {
        leftXAdd.setEnabled(b);
        leftXSub.setEnabled(b);
        rightXAdd.setEnabled(b);
        rightXSub.setEnabled(b);
        leftOneAdd.setEnabled(b);
        leftOneSub.setEnabled(b);
        rightOneAdd.setEnabled(b);
        rightOneSub.setEnabled(b);
    }

    public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
        private static final String TAG = "Add:ClickListener";
        private Context mContext;
        private int mOperation;
        private AlgeOpsRelativeLayout mView;

        AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
            mContext = context;
            mOperation = operation;
            mView = view;
        }

        @Override
        public void onClick(View view) {
            if (hasStarted) {
                if (mView.setImage(mContext, mOperation)) {
                    checkIfTilesAreCorrect();
                    playSound(R.raw.correct);
                } else {
                    playSound(R.raw.wrong);
                }
            }
        }
    }
}
