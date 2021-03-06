package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;
import com.freelance.jptalusan.algeops.Utilities.LayoutUtilities;

import java.util.ArrayList;
import java.util.List;

public class SubtractActivity extends BaseOpsActivity {
    private static final String TAG = "SubActivity";
    private ImageView addXImageView;
    private ImageView subXImageView;
    private ImageView addOneImageView;
    private ImageView subOneImageView;
    private ImageView addsubXImageView;
    private ImageView addsubOneImageView;

    private ImageButton subPosXButton;
    private ImageButton subNegXButton;
    private ImageButton addPosNegXButton;
    private ImageButton subPosOneButton;
    private ImageButton subNegOneButton;
    private ImageButton addPosNegOneButton;

    private AlgeOpsRelativeLayout subLayout;

    private View oneSeekBarIVRL;

    private int countX = 0;
    private int count1 = 0;
    private int animatedCounter = 0;
    private boolean hasStartedAnimationX = false;
    private boolean hasStartedAnimation1 = false;
    private int currentScore = 0;
    private int totalPlayed = 0;

    private int subPosXClickCount = 0;
    private int subNegXClickCount = 0;

    private int addPosNegXClickCount = 0;

    private int subPosOneClickCount = 0;
    private int subNegOneClickCount = 0;

    private int addPosNegOneClickCount = 0;

    private int addPosNegXClickLimit = 0;
    private int addPosNegOneClickLimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract);

        if (prefs.getBoolean(Constants.IS_FIRST_RUN_SUB, true)) {
            prefs.edit().putBoolean(Constants.IS_FIRST_RUN_SUB, false).apply();
            prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
        }
        //weird request to restart to level 1 when you exit
        prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
        level = prefs.getInt(Constants.SUB_LEVEL, Constants.LEVEL_1);

        startButton         = findViewById(R.id.startButton);
        checkButton         = findViewById(R.id.checkButton);
        resetButton         = findViewById(R.id.resetButton);
        goBackToLevel1      = findViewById(R.id.goBackToLevel1);

        firstPartEq         = findViewById(R.id.firstEqTextView);
        secondPartEq        = findViewById(R.id.secondEqTextView);

        addXImageView       =  findViewById(R.id.addXImageView);
        subXImageView       =  findViewById(R.id.subXImageView);
        addOneImageView     =  findViewById(R.id.addOneImageView);
        subOneImageView     =  findViewById(R.id.subOneImageView);
        addsubXImageView    =  findViewById(R.id.addsubXImageView);
        addsubOneImageView  =  findViewById(R.id.addsubOneImageView);

        xSeekbarImageView   = findViewById(R.id.xSeekbarImageView);
        oneSeekBarIVRL      = findViewById(R.id.oneSeekBarIVRL);
        oneSeekbarImageView = oneSeekBarIVRL.findViewById(R.id.oneSeekbarImageView);

        operationImageView  = findViewById(R.id.operationImageView);

        subPosXButton       = findViewById(R.id.subPosXButton);
        subNegXButton       = findViewById(R.id.subNegXButton);
        addPosNegXButton    = findViewById(R.id.addPosNegXButton);

        subPosOneButton     = findViewById(R.id.subPosOneButton);
        subNegOneButton     = findViewById(R.id.subNegOneButton);
        addPosNegOneButton  = findViewById(R.id.addPosNegOneButton);

        subLayout           = findViewById(R.id.subAlgeOpsRelLayout);

        xSeekbar            = findViewById(R.id.subXSeekBar);
        oneSeekbar          = findViewById(R.id.subOneSeekBar);

        score               = findViewById(R.id.score);

        operationImageView.setImageResource(R.drawable.minus);
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
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        goBackToLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().putInt(Constants.SUB_LEVEL, 1).apply();
                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                startAlgeOps();
            }
        });

        //TODO: Add level up mechanics
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPlayed++;
                if (!hasStartedAnimationX && !hasStartedAnimation1) {
                    Log.d(TAG, "checkButton clicked");
                    int correctAnswers = prefs.getInt(Constants.CORRECT_SUB_ANSWERS, 0);
                    if (!isSecondAnswerCorrect) {
                        Log.d(TAG, "First ans correct.");
                        if (isSeekBarAnswerCorrect()) {
                            isSecondAnswerCorrect = true;
                            //Count if the user has 10 consecutive answers (4 levels)
                            correctAnswers++;
                            int currLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
                            Toast.makeText(SubtractActivity.this, "You are correct!", Toast.LENGTH_SHORT).show();
                            if (correctAnswers == Constants.LEVEL_UP && currLevel != Constants.LEVEL_4) {
                                currLevel++;
                                Toast.makeText(SubtractActivity.this, "Congratulations! You are now in Level " + currLevel, Toast.LENGTH_SHORT).show();
                                prefs.edit().putInt(Constants.SUB_LEVEL, currLevel).apply();
                                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                            } else {
                                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, correctAnswers).apply();
                            }
                            Log.d(TAG, "Correct: " + correctAnswers);
//                            startAlgeOps();

                            currentScore++;
                            score.setText("Score: " + currentScore + "/" + totalPlayed);
                        } else {
                            score.setText("Score: " + currentScore + "/" + totalPlayed);
                            Toast.makeText(SubtractActivity.this, "You are incorrect.", Toast.LENGTH_SHORT).show();
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

        subPosXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_X, subLayout));
        subNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_X, subLayout));
        addPosNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_X, subLayout));

        subPosOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_ONE, subLayout));
        subNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_ONE, subLayout));
        addPosNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_ONE, subLayout));

        subLayout.getViewDimensions();
        ViewTreeObserver vto = subLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                subLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                startAlgeOps();
            }
        });

        subLayout.onAnimationEndListener(new AlgeOpsRelativeLayout.AnimationEndListener() {
            @Override
            public void onAnimationEnded(int val) {
                Log.d(TAG, "val/countX/1: " + val + "/" + countX + "/" + count1);
                animatedCounter++;
                if (animatedCounter == ((count1 + countX) * 2)) {
                    hasStartedAnimationX = false;
                    hasStartedAnimation1 = false;
                }
            }
        });
    }

    //TODO: Do i also have to cancel out the initial views? or only added views?
    private void cancelOutViews() {
        Handler handler1 = new Handler();
        countX = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_X);
        Log.d(TAG, "Cancelling out X: " + countX);
        for (int i = 0; i < countX; i++) {
            hasStartedAnimationX = true;
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subLayout.removeSubImage(Constants.POS_X);
                    subLayout.removeSubImage(Constants.NEG_X);
                }
            }, 3200 * i);
        }

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler3 = new Handler();
                count1 = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_ONE);
                Log.d(TAG, "Cancelling out 1: " + count1);
                for (int i = 0; i < count1; i++) {
                    hasStartedAnimation1 = true;
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subLayout.removeSubImage(Constants.POS_ONE);
                            subLayout.removeSubImage(Constants.NEG_ONE);
                        }
                    }, 3200 * i);
                }
            }
        }, countX * 3200);

    }

    private boolean isSeekBarAnswerCorrect() {
        Log.d("Seekbar", "corrX:" + (eq.getAx() - eq.getCx()) + "");
        Log.d("Seekbar", "corr1:" + (eq.getB() - eq.getD()) + "");

        int xCorrectAnswer = eq.getAx() - eq.getCx();
        int oneCorrectAnswer = eq.getB() - eq.getD();

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

    @Override
    protected void reset() {
        super.reset();
        subLayout.resetLayout();
        subLayout.populateImageViewBasedOnLeftSideEq(SubtractActivity.this, eq);
    }

    private void setImageViewSources(int level) {
        if (level % 2 == 0) {
            addXImageView.setImageResource(R.drawable.green_cubenum);
            subXImageView.setImageResource(R.drawable.red_cubenum);
            addOneImageView.setImageResource(R.drawable.twocubesnum);

            subOneImageView.setImageResource(R.drawable.green_circlenum);
            addsubXImageView.setImageResource(R.drawable.red_circlenum);
            addsubOneImageView.setImageResource(R.drawable.twocirclesnum);
        } else {

            addXImageView.setImageResource(R.drawable.green_cube);
            subXImageView.setImageResource(R.drawable.red_cube);
            addOneImageView.setImageResource(R.drawable.twocubes);

            subOneImageView.setImageResource(R.drawable.green_circle);
            addsubXImageView.setImageResource(R.drawable.red_circle);
            addsubOneImageView.setImageResource(R.drawable.twocircles);
        }
    }

    private void setButtonClickLimits(Equation eq) {
        Log.d(TAG, "Limits:" );
        Log.d(TAG, "ax: " + eq.getAx());
        Log.d(TAG, "b: " + eq.getB());

        Log.d(TAG, "cx: " + eq.getCx());
        Log.d(TAG, "d: " + eq.getD());
        int ax = eq.getAx();
        int b  = eq.getB();
        int cx = eq.getCx();
        int d  = eq.getD();

        //TODO: What if ax > |cx|?
        if (cx > 0) {
            if (ax > 0) {
                if (ax >= cx) {
                    addPosNegXClickLimit = 0;
                } else if (ax < cx){
                    addPosNegXClickLimit = cx - Math.abs(ax);
                }
            } else if (ax <= 0) {
                addPosNegXClickLimit = cx;
            }
            //Add boolean stating you cannot click subNegX
            subNegXButton.setAlpha(0.4f);
            subNegXButton.setEnabled(false);
            subNegXButton.setClickable(false);
            subXImageView.setAlpha(0.4f);
        } else if (cx < 0) {
            if (ax >= 0) {
                addPosNegXClickLimit = Math.abs(cx);
            } else if (ax < 0) {
                if (Math.abs(ax) >= Math.abs(cx)) {
                    addPosNegXClickLimit = 0;
                } else if (Math.abs(ax) < Math.abs(cx)){
                    addPosNegXClickLimit = Math.abs(cx) - Math.abs(ax);
                }
            }
            //Add boolean stating you cannot click subPosX
            subPosXButton.setAlpha(0.4f);
            subPosXButton.setEnabled(false);
            subPosXButton.setClickable(false);
            addXImageView.setAlpha(0.4f);
        } else {
            subNegXButton.setAlpha(0.4f);
            subNegXButton.setEnabled(false);
            subNegXButton.setClickable(false);
            subXImageView.setAlpha(0.4f);

            subPosXButton.setAlpha(0.4f);
            subPosXButton.setEnabled(false);
            subPosXButton.setClickable(false);
            addXImageView.setAlpha(0.4f);
        }

        //Ones
        if (d > 0) {
            if (b > 0) {
                if (Math.abs(b) >= Math.abs(d)) {
                    addPosNegOneClickLimit = 0;
                } else if (b < d){
                    addPosNegOneClickLimit = d - Math.abs(b);
                }
            } else if (b <= 0) {
                addPosNegOneClickLimit = d;
            }
            //Add boolean stating you cannot click subNegOne
            subNegOneButton.setAlpha(0.4f);
            subNegOneButton.setEnabled(false);
            subNegOneButton.setClickable(false);
            addsubXImageView.setAlpha(0.4f);
        } else if (d < 0) {
            if (b >= 0) {
                addPosNegOneClickLimit = Math.abs(d);
            } else if (b < 0) {
                if (Math.abs(b) >= Math.abs(d)) {
                    addPosNegOneClickLimit = 0;
                } else if (Math.abs(b) < Math.abs(d)){
                    addPosNegOneClickLimit = Math.abs(d) - Math.abs(b);
                }
            }
            //Add boolean stating you cannot click subPosOne
            subPosOneButton.setAlpha(0.4f);
            subPosOneButton.setEnabled(false);
            subPosOneButton.setClickable(false);
            subOneImageView.setAlpha(0.4f);
        } else {
            subNegOneButton.setAlpha(0.4f);
            subNegOneButton.setEnabled(false);
            subNegOneButton.setClickable(false);
            addsubXImageView.setAlpha(0.4f);

            subPosOneButton.setAlpha(0.4f);
            subPosOneButton.setEnabled(false);
            subPosOneButton.setClickable(false);
            subOneImageView.setAlpha(0.4f);
        }

        Log.d(TAG, "AddX Limit: " + addPosNegXClickLimit);
        Log.d(TAG, "AddOne Limit: " + addPosNegOneClickLimit);

        if (addPosNegXClickLimit == 0) {
            addPosNegXButton.setAlpha(0.4f);
            addPosNegXButton.setEnabled(false);
            addPosNegXButton.setClickable(false);
            addOneImageView.setAlpha(0.4f);
        }

        if (addPosNegOneClickLimit == 0) {
            addPosNegOneButton.setAlpha(0.4f);
            addPosNegOneButton.setEnabled(false);
            addPosNegOneButton.setClickable(false);
            addsubOneImageView.setAlpha(0.4f);
        }
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        firstPartEq.removeAllViews();
        secondPartEq.removeAllViews();

        int subLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
        Log.d(TAG, "currLevel: " + subLevel);
        eq = EquationGeneration.generateEquation("SUB", subLevel);
        setButtonAbility(eq);
        //If level 3 onwards
        subPosXClickCount = 0;
        subNegXClickCount = 0;

        addPosNegXClickCount = 0;

        subPosOneClickCount = 0;
        subNegOneClickCount = 0;

        addPosNegOneClickCount = 0;

        addPosNegXClickLimit = 0;
        addPosNegOneClickLimit = 0;
        if (subLevel >= 3) {
            setButtonClickLimits(eq);
        }

        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        setImageViewSources(subLevel);

        final int factor = getResources().getInteger(R.integer.text_box_factor);
        if (subLevel == Constants.LEVEL_1) {
            setEquationsLayout();
        } else if (subLevel == Constants.LEVEL_2) {
            firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int tempH = firstPartEq.getMeasuredHeight();
                    TextView tv1 = new TextView(SubtractActivity.this);
                    //tv1.setText(" from, " + firstPart);
                    TextView tv2 = new TextView(SubtractActivity.this);
                    tv2.setText("Remove " + secondPart);// + ", from " + firstPart);
                    tv1.setTextSize(tempH / factor);
                    tv2.setTextSize(tempH / factor);
                    firstPartEq.addView(tv1);
                    secondPartEq.addView(tv2);
                }
            });

        } else if (subLevel == Constants.LEVEL_3) {
            setEquationsLayout();
        } else if (subLevel == Constants.LEVEL_4) {
            firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int tempH = firstPartEq.getMeasuredHeight();
                    TextView tv1 = new TextView(SubtractActivity.this);
                    //tv1.setText(" from, " + firstPart);
                    TextView tv2 = new TextView(SubtractActivity.this);
                    tv2.setText("Remove " + secondPart);// + ", from " + firstPart);
                    tv1.setTextSize(tempH / factor);
                    tv2.setTextSize(tempH / factor);
                    firstPartEq.addView(tv1);
                    secondPartEq.addView(tv2);
                }
            });

        } else {
            //impossible
        }
        xSeekbarImageView.setText("0");
        xSeekbarImageView.setTextColor(Color.BLACK);

        oneSeekbarImageView.setText("0");
        oneSeekbarImageView.setTextColor(Color.BLACK);

        subLayout.resetLayout();
        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();
        answerIsWrong();

        subLayout.populateImageViewBasedOnLeftSideEq(SubtractActivity.this, eq);
    }

    private void setButtonAbility(Equation eq) {
        Log.d(TAG, "equation: " + eq.toString() + ", ax: " + eq.getCx());
        addXImageView.setVisibility(View.VISIBLE);
        subXImageView.setVisibility(View.VISIBLE);
        addsubXImageView.setVisibility(View.VISIBLE);

        addOneImageView.setVisibility(View.VISIBLE);
        subOneImageView.setVisibility(View.VISIBLE);
        addsubOneImageView.setVisibility(View.VISIBLE);

        //
        subXImageView.setAlpha(1.0f);
        addXImageView.setAlpha(1.0f);

        addsubXImageView.setAlpha(1.0f);
        subOneImageView.setAlpha(1.0f);
        //

        subNegXButton.setVisibility(View.VISIBLE);
        subNegXButton.setEnabled(true);

        subPosXButton.setVisibility(View.VISIBLE);
        subPosXButton.setEnabled(true);

        subNegOneButton.setVisibility(View.VISIBLE);
        subNegOneButton.setEnabled(true);

        subPosOneButton.setVisibility(View.VISIBLE);
        subPosOneButton.setEnabled(true);

        //
        subNegXButton.setAlpha(1.0f);
        subNegXButton.setClickable(true);

        subPosXButton.setAlpha(1.0f);
        subPosXButton.setClickable(true);

        subNegOneButton.setAlpha(1.0f);
        subNegOneButton.setClickable(true);

        subPosOneButton.setAlpha(1.0f);
        subPosOneButton.setClickable(true);

        addPosNegXButton.setAlpha(1.0f);
        addOneImageView.setAlpha(1.0f);

        addPosNegOneButton.setAlpha(1.0f);
        addsubOneImageView.setAlpha(1.0f);

        addPosNegXButton.setAlpha(1.0f);
        addPosNegXButton.setEnabled(true);
        addPosNegXButton.setClickable(true);
        addOneImageView.setAlpha(1.0f);

        addPosNegOneButton.setAlpha(1.0f);
        addPosNegOneButton.setEnabled(true);
        addPosNegOneButton.setClickable(true);
        addsubOneImageView.setAlpha(1.0f);

        float alphaValue = 0.4f;

        if (prefs.getInt(Constants.SUB_LEVEL, 0) < Constants.LEVEL_3) {
            addPosNegXButton.setAlpha(alphaValue);
            addOneImageView.setAlpha(alphaValue);

            addPosNegOneButton.setAlpha(alphaValue);
            addsubOneImageView.setAlpha(alphaValue);

            if (eq.getCx() > 0) {
                subNegXButton.setEnabled(false);
                subNegXButton.setAlpha(alphaValue);
                subNegXButton.setClickable(false);

//            subNegXButton.setVisibility(View.INVISIBLE);
                subXImageView.setAlpha(alphaValue);

                subPosXButton.setEnabled(true);
            } else if (eq.getCx() < 0) {
                subPosXButton.setEnabled(false);
                subPosXButton.setAlpha(alphaValue);
                subPosXButton.setClickable(false);

//            subPosXButton.setVisibility(View.INVISIBLE);
                addXImageView.setAlpha(alphaValue);

                subNegXButton.setEnabled(true);
            } else if (eq.getCx() == 0){
                subPosXButton.setEnabled(false);
                subNegXButton.setEnabled(false);

                subPosXButton.setAlpha(alphaValue);
                subPosXButton.setClickable(false);

                subNegXButton.setAlpha(alphaValue);
                subNegXButton.setClickable(false);

                subXImageView.setAlpha(alphaValue);
                addXImageView.setAlpha(alphaValue);
            }

            if (eq.getD() > 0) {
                subNegOneButton.setEnabled(false);
                subNegOneButton.setAlpha(alphaValue);
                subNegOneButton.setClickable(false);

//            subNegOneButton.setVisibility(View.INVISIBLE);
                addsubXImageView.setAlpha(alphaValue);

                subPosOneButton.setEnabled(true);
            } else if (eq.getD() < 0) {
                subPosOneButton.setEnabled(false);
                subPosOneButton.setAlpha(alphaValue);
                subPosOneButton.setClickable(false);

//            subPosOneButton.setVisibility(View.INVISIBLE);
                subOneImageView.setAlpha(alphaValue);

                subNegOneButton.setEnabled(true);
            } else if (eq.getD() == 0) {
                subPosOneButton.setEnabled(false);
                subNegOneButton.setEnabled(false);

                subPosOneButton.setAlpha(alphaValue);
                subPosOneButton.setClickable(false);

                subNegOneButton.setAlpha(alphaValue);
                subNegOneButton.setClickable(false);

                addsubXImageView.setAlpha(alphaValue);
                subOneImageView.setAlpha(alphaValue);
            }
        } else {
            addPosNegXButton.setAlpha(1.0f);
            addOneImageView.setAlpha(1.0f);

            addPosNegOneButton.setAlpha(1.0f);
            addsubOneImageView.setAlpha(1.0f);
        }
    }

    protected void setEquationsLayout() {
        final int[] first = eq.getIntArr(1);
        final int[] second = eq.getIntArr(2);
        final int factor = getResources().getInteger(R.integer.text_with_image);
        firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int tempH = firstPartEq.getMeasuredHeight();
                if (first[0] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[0])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (first[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);
                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (first[1] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[1])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (first[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);
                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (second[0] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[0])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (second[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);
                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }

                if (second[1] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[1])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (second[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);
                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }

                TextView tv = (TextView) secondPartEq.getChildAt(0);
                tv.setGravity(Gravity.START);
                String temp = tv.getText().toString();
                tv.setText("Remove: " + temp);

                firstPartEq.removeAllViews();
//                TextView tv2 = new TextView(SubtractActivity.this);
//                tv2.setGravity(Gravity.START);
//                tv2.setText(" From: ");
//                tv2.setTextSize(tempH / factor);
//                firstPartEq.addView(tv2);
            }
        });
    }

    private void checkIfTilesAreCorrect() {
        if (!isFirstAnswerCorrect) {
            //answerX = initx + -1(posX - negX)
            //answer1 = init1 + -1(pos1 - neg1)
            int answX = subLayout.positiveX - subLayout.negativeX;
            int answ1 = subLayout.positiveOne - subLayout.negativeOne;
            boolean isCorrect = eq.isSubtractAnswerCorrect(answX, answ1);

            if (isCorrect) {
                isFirstAnswerCorrect = true;
                xSeekbar.getViewDimensions();
                oneSeekbar.getViewDimensions();
                answerIsCorrect();
                cancelOutViews();
            }
        }
    }
    public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
        private static final String TAG = "Sub:ClickListener";
        private Context mContext;
        private int mOperation;
        private AlgeOpsRelativeLayout mView;

        /* Rules
        (ax + b) (cx + d)

        if (c > 0)
            if (a > 0)
                must click subPosX c-|a| times only
                must click addPosNegX c-|a| times only
            else if (a <= 0)
                must click subPosX c times only
                must click addPosNegX c times only
            must not be able to click subNegX
        else if (c < 0)
            if (a >= 0)
                must click subNegX c times only
                must click addPosNegX c times only
            else if (a < 0)
                must click subNegX c-|a|
                must click addPosNegX c-|a| times only
            must not be able to click subPosX


        if (d > 0)
            if (b > 0)
                must click subPosOne d-|b| times only
                must click addPosNegOne d-|b| times only
            else if (b <= 0)
                must click subPosOne d times only
                must click addPosNegOne d times only
            must not be able to click subNegOne
        else if (b < 0)
            if (d >= 0)
                must click subNegOne d times only
                must click addPosNegOne d times only
            else if (d < 0)
                must click subNegOne d-|b|
                must click addPosNegOne d-|b| times only
            must not be able to click subPosOne
         */
        AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
            mContext = context;
            mOperation = operation;
            mView = view;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "OnClick");
            Log.d(TAG, "Eq onclick: " + eq.toString());
            if (hasStarted) {
                Log.d(TAG, prefs.getInt(Constants.SUB_LEVEL, 1) + "");
                if (prefs.getInt(Constants.SUB_LEVEL, 1) < Constants.LEVEL_3) {
                    if (view.getId() == R.id.addPosNegOneButton || view.getId() == R.id.addPosNegXButton)
                    {
                        playSound(R.raw.wrong);
                        return;
                    }
                    if (mView.setSubImage(mContext, mOperation, eq)) {
                        checkIfTilesAreCorrect();
                        playSound(R.raw.correct);
                    } else {
                        playSound(R.raw.wrong);
                    }
                } else {
                    //TODO: Prevent subtraction of objects beyond the equation
                    Log.d(TAG, "Level 3: else");
                    switch (view.getId()) {
                        case R.id.addPosNegXButton:
                            ++addPosNegXClickCount;
                            break;
                        case R.id.addPosNegOneButton:
                            ++addPosNegOneClickCount;
                            break;
                        default:
                            break;
                    }
                    Log.d(TAG, "X count/limit: " + addPosNegXClickCount + "/" + addPosNegXClickLimit);
                    Log.d(TAG, "1 count/limit: " + addPosNegOneClickCount + "/" + addPosNegOneClickLimit);

                    if (mView.setSubImage(mContext, mOperation)) {
                        playSound(R.raw.correct);
                        checkIfTilesAreCorrect();
                    } else {
                        playSound(R.raw.wrong);
                    }

                    if (mOperation == Constants.OPS_ADD_POS_NEG_X) {
                        if (addPosNegXClickCount >= addPosNegXClickLimit) {
                            addPosNegXButton.setAlpha(0.4f);
                            addPosNegXButton.setEnabled(false);
                            addPosNegXButton.setClickable(false);
                            addOneImageView.setAlpha(0.4f);
                            Log.d(TAG, "addX limit reached.");
                            return;
                        }
                    } else if (mOperation == Constants.OPS_ADD_POS_NEG_ONE) {
                        if (addPosNegOneClickCount >= addPosNegOneClickLimit) {
                            addPosNegOneButton.setAlpha(0.4f);
                            addPosNegOneButton.setEnabled(false);
                            addPosNegOneButton.setClickable(false);
                            addsubOneImageView.setAlpha(0.4f);
                            Log.d(TAG, "addOne limit reached.");
                            return;
                        }
                    }
                }
            }
        }
    }
}
