package com.igottadroid.AnimatedTextSwitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by acopp
 * Date: 9/11/2017.
 * Time: 7:02 AM
 * Original Project: DashLube
 * Original Package Name: com.thunderstick.carcompanion
 * You have permission to use this file for any reason that is not for evil doing, as long as you please leave the header intact.
 */

public class AnimatedTextSwitch extends LinearLayout {
    private AppCompatSeekBar mSeekBar;
    private TextView mTextView;
    private int MAX = 20;
    private int TARGET = 0;
    private int START = 0;
    private boolean switchedOn = false;
    private Drawable ON_PROGRESS_BACKGROUND = null;
    private Drawable OFF_PROGRESS_BACKGROUND = null;
    private Drawable ON_TOUCH_ICON = null;
    private Drawable ON_RELEASE_ICON = null;
    private Drawable ON_ICON = null;
    private Drawable OFF_ICON = null;
    private Integer OFF_COLOR = null;
    private Integer ON_COLOR = null;
    private String ORIENTATION = "";

    public AnimatedTextSwitch(Context context) {
        super(context);
        mSeekBar = new AppCompatSeekBar(getContext());
        mTextView = new TextView(getContext());
    }

    public AnimatedTextSwitch(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mSeekBar = new AppCompatSeekBar(getContext());
        mTextView = new TextView(getContext());
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AnimatedTextSwitch, 0, 0);
        ON_TOUCH_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_on_touch_icon);
        ON_RELEASE_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_on_release_icon);
        ON_ICON  = a.getDrawable(R.styleable.AnimatedTextSwitch_on_icon);
        OFF_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_off_icon);
        ON_PROGRESS_BACKGROUND = a.getDrawable(R.styleable.AnimatedTextSwitch_on_progress_background);
        OFF_PROGRESS_BACKGROUND = a.getDrawable(R.styleable.AnimatedTextSwitch_off_progress_background);
        OFF_COLOR = a.getColor(R.styleable.AnimatedTextSwitch_off_color, 0);
        ON_COLOR = a.getColor(R.styleable.AnimatedTextSwitch_on_color, 0);
        mTextView.setText(a.getString(R.styleable.AnimatedTextSwitch_text));
        mTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(a.getDrawable(R.styleable.AnimatedTextSwitch_textImage),null,null,null);
        ORIENTATION = a.getString(R.styleable.AnimatedTextSwitch_textOrientaion);

        if(ORIENTATION == null){
            ORIENTATION = "";
        }else{
            ORIENTATION = ORIENTATION.toLowerCase();
        }
        super.setWeightSum(4f);
        LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        tvLP.weight = 1.0f;

        LinearLayout.LayoutParams paramsSB = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paramsSB.weight = 3.0f;
        paramsSB.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int FIFTEEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int TEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int FIVE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        paramsSB.setMarginEnd(FIVE);
        paramsSB.setMarginStart(FIVE);
        mSeekBar.setPadding(FIFTEEN,TEN,FIFTEEN,TEN);


        switch (ORIENTATION){
            case "top":
                super.setOrientation(LinearLayout.VERTICAL);
                paramsSB.gravity = Gravity.CENTER;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.CENTER;
                mTextView.setLayoutParams(tvLP);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
            case "bottom":
                super.setOrientation(LinearLayout.VERTICAL);
                paramsSB.gravity = Gravity.CENTER;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.CENTER;
                mTextView.setLayoutParams(tvLP);
                super.addView(mTextView);
                super.addView(mSeekBar,0);
                break;
            case "start":
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mTextView.setLayoutParams(tvLP);
                mTextView.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
            case "end":
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mTextView.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);
                mTextView.setLayoutParams(tvLP);
                super.addView(mTextView);
                super.addView(mSeekBar,0);
                break;
            default:
                //Default to text at start
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mTextView.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                mTextView.setLayoutParams(tvLP);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
        }
        a.recycle();
        mSeekBar.setMax(MAX);
        mSeekBar.setOnSeekBarChangeListener(mChangeListener);
        switchOff();
    }

    public AnimatedTextSwitch(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        mSeekBar = new AppCompatSeekBar(getContext());
        mTextView = new TextView(getContext());
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AnimatedTextSwitch, defStyle, 0);
        ON_TOUCH_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_on_touch_icon);
        ON_RELEASE_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_on_release_icon);
        ON_ICON  = a.getDrawable(R.styleable.AnimatedTextSwitch_on_icon);
        OFF_ICON = a.getDrawable(R.styleable.AnimatedTextSwitch_off_icon);
        ON_PROGRESS_BACKGROUND = a.getDrawable(R.styleable.AnimatedTextSwitch_on_progress_background);
        OFF_PROGRESS_BACKGROUND = a.getDrawable(R.styleable.AnimatedTextSwitch_off_progress_background);
        OFF_COLOR = a.getColor(R.styleable.AnimatedTextSwitch_off_color, 0);
        ON_COLOR = a.getColor(R.styleable.AnimatedTextSwitch_on_color, 0);
        mTextView.setText(a.getString(R.styleable.AnimatedTextSwitch_text));
        ORIENTATION = a.getString(R.styleable.AnimatedTextSwitch_textOrientaion);

        if(ORIENTATION == null){
            ORIENTATION = "";
        }else{
            ORIENTATION = ORIENTATION.toLowerCase();
        }
        super.setWeightSum(4f);
        LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        tvLP.weight = 1.0f;

        LinearLayout.LayoutParams paramsSB = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paramsSB.weight = 3.0f;
        paramsSB.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int FIFTEEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int TEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int FIVE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        paramsSB.setMarginEnd(FIVE);
        paramsSB.setMarginStart(FIVE);
        mSeekBar.setPadding(FIFTEEN,TEN,FIFTEEN,TEN);

        switch (ORIENTATION){
            case "top":
                super.setOrientation(LinearLayout.VERTICAL);
                paramsSB.gravity = Gravity.CENTER;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.CENTER;
                mTextView.setLayoutParams(tvLP);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
            case "bottom":
                super.setOrientation(LinearLayout.VERTICAL);
                paramsSB.gravity = Gravity.CENTER;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.CENTER;
                mTextView.setLayoutParams(tvLP);
                super.addView(mTextView);
                super.addView(mSeekBar,0);
                break;
            case "start":
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mTextView.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                mTextView.setLayoutParams(tvLP);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
            case "end":
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mTextView.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);
                mTextView.setLayoutParams(tvLP);
                super.addView(mTextView);
                super.addView(mSeekBar,0);
                break;
            default:
                //Default to text at start
                super.setOrientation(LinearLayout.HORIZONTAL);
                paramsSB.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
                mSeekBar.setLayoutParams(paramsSB);
                tvLP.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
                mTextView.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                mTextView.setLayoutParams(tvLP);
                super.addView(mSeekBar);
                super.addView(mTextView,0);
                break;
        }
        a.recycle();
        mSeekBar.setMax(MAX);
        mSeekBar.setOnSeekBarChangeListener(mChangeListener);
        switchOff();
    }

    interface OnChangeListener{
        void SwitchedOn(boolean bool);
    }
    AnimatedTextSwitch.OnChangeListener mOnChangeListener = new AnimatedTextSwitch.OnChangeListener() {
        @Override
        public void SwitchedOn(boolean bool) {

        }
    };

    void setOnChangeListener(AnimatedTextSwitch.OnChangeListener onChangeListener){
        AnimatedTextSwitch.this.mOnChangeListener = onChangeListener;
    }

    private void setProgressIcon(Integer c, Drawable b, Drawable f){
        if(f != null){
            Drawable i;

            Bitmap b2 = Bitmap.createBitmap(f.getIntrinsicWidth(), f.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(b2);
            f.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            f.draw(canvas);

//            Bitmap b2 =((BitmapDrawable) f).getBitmap();
            Bitmap bitmap2 = CircleBitmap.get(b2);
            Drawable d2 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap2, 75, 75, true));
            if(b!= null){
                // Read your drawable from somewhere
                Bitmap bitmap1 = Bitmap.createBitmap(b.getIntrinsicWidth(), b.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

                Canvas canvas2 = new Canvas(b2);
                f.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
                f.draw(canvas);

                Drawable d1 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap1, 150, 150, true));
                Drawable[] layers = new Drawable[2];
                layers[0] = d1;
                layers[1] = d2;
                Drawable i2 = new LayerDrawable(layers);
                try {
                    //noinspection ConstantConditions
                    Bitmap bd = UtilityModule.drawableToBitmap(i2);
                    i = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bd, 75, 75, true));
                }catch (ClassCastException e){
                    e.printStackTrace();
                    i=d2;
                }
            }else{
                i=d2;
            }
            if(c != null && c !=0){
                i.setColorFilter(new PorterDuffColorFilter(c, PorterDuff.Mode.MULTIPLY));
            }
            mSeekBar.setThumb(i);
        }else if(b!= null){
            if(c != null && c !=0){
                b.setColorFilter(new PorterDuffColorFilter(c, PorterDuff.Mode.MULTIPLY));
            }
            mSeekBar.setThumb(b);
        }
        if(c != null && c != 0){
            mSeekBar.getProgressDrawable().setColorFilter(
                    c, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    void switchOn(){
        AnimatedTextSwitch.this.TARGET = mSeekBar.getMax();
        switchedOn = true;
        mOnChangeListener.SwitchedOn(switchedOn);
        setProgressIcon(ON_COLOR,ON_PROGRESS_BACKGROUND,ON_ICON);
        Animate();
    }
    void switchOff(){
        switchedOn = false;
        mOnChangeListener.SwitchedOn(switchedOn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TARGET = mSeekBar.getMin();
        }else {
            TARGET = 0;
        }
        setProgressIcon(OFF_COLOR,OFF_PROGRESS_BACKGROUND,OFF_ICON);
        Animate();
    }

    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(AnimatedTextSwitch.this.mSeekBar.getProgress() != TARGET){
                if(AnimatedTextSwitch.this.mSeekBar.getProgress() > TARGET){
                    AnimatedTextSwitch.this.mSeekBar.setProgress(AnimatedTextSwitch.this.mSeekBar.getProgress() - 1);
                }else{
                    AnimatedTextSwitch.this.mSeekBar.setProgress(AnimatedTextSwitch.this.mSeekBar.getProgress() + 1);
                }
                h.postDelayed(r,5);
            }
        }
    };
    void Animate(){
        h.postDelayed(r,10);
    }

    SeekBar.OnSeekBarChangeListener mChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            START = seekBar.getProgress();
            if(ON_TOUCH_ICON != null){
                setProgressIcon(OFF_COLOR,OFF_PROGRESS_BACKGROUND,ON_TOUCH_ICON);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(ON_RELEASE_ICON != null){
                setProgressIcon(OFF_COLOR,OFF_PROGRESS_BACKGROUND,ON_RELEASE_ICON);
            }
            if(START < mSeekBar.getMax()/2){
                //SWITCH ON
                switchOn();
            }else{
                //SWITCH OFF
                switchOff();

            }
        }
    };
    void setText(String text){
        mTextView.setText(text);
    }
    
    boolean isSwitchedOn(){
        return mSeekBar.getProgress() > mSeekBar.getMax()/2;
    }
}
