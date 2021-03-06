package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Created by nguyennghia on 29/06/2016.
 */

public class DialogItemView extends View {
    private static final String TAG = "DialogItemView";

    private static final short ALPHA_DEFAULT = 255;
    private static final int ANIMATION_DURATION = 300;
    private static final int TIME_REFESH = 10;
    private static final int ALPHA_STEP = ALPHA_DEFAULT / (ANIMATION_DURATION / TIME_REFESH);

    private static final int VALUE_INVALID = -1;
    public static final int AVATAR_ONE_BITMAP = 0;
    public static final int AVATAR_ONE_BITMAP_AND_TEXT = 1;
    public static final int AVATAR_TWO_BITMAP = 2;
    public static final int AVATAR_THREE_BITMAP = 3;
    public static final int AVATAR_FOUR_BITMAP = 4;
    public static final int AVATAR_THREE_BITMAP_AND_TEXT = 5;

    public static final int TOTAL_MEMBER_CIRCLE = 0;
    public static final int TOTAL_MEMBER_SQUARE = 1;

    private float mAvatarBoxWidth;
    private float mAvatarBoxHeight;

    private float mItemAvatarWidth;
    private float mItemAvatarHeight;

    private int mSize;
    private Paint[] mBitmapPaints;
    private Bitmap[] mBitmaps;

    private Paint mUnReadPaint;
    private TextPaint mUnReadPaintText;
    private boolean mIsDrawUnRead;
    private String mUnReadText;
    private float mPaddingUnread;
    private float mUnreadTextSize;
    private int mUnreadMinHeight;
    private int mUnreadMinWidth;
    private int mUnreadColor;
    private int mUnreadTextColor;

    private TextPaint mTitlePaint;
    private String mTitleText;
    private int mTitleMarginTop;
    private int mTitleMarginBottom;
    private int mTitleMarginLeft;
    private int mTitleMarginRight;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private TextPaint mContentPaint;
    private String mContentText;
    private int mContentMarginTop;
    private int mContentMarginBottom;
    private int mContentMarginLeft;
    private int mContentMarginRight;
    private int mContentTextColor;
    private int mContentTextSize;


    private TextPaint mStatusPaint;
    private String mStatusText;
    private int mStatusMarginTop;
    private int mStatusMarginBottom;
    private int mStatusMarginLeft;
    private int mStatusMarginRight;
    private int mStatusTextColor;
    private int mStatusTextSize;

    private Paint mDividerPaint;
    private int mDividerHeight;
    private int mDividerColor;

    private float mHeightDraw;
    private String mTotalMemberText;
    private TextPaint mTotalMemberTextPaint;
    private Paint mTotalMemberPaint;
    private int mTotalMemberColor;
    private int mTotalMemberTextSize;
    private int mTotalMemberTextColor;
    private int mTotalMemberDrawType = TOTAL_MEMBER_CIRCLE;

    private boolean mIsAnimation0;
    private boolean mIsAnimation1;
    private boolean mIsAnimation2;
    private boolean mIsAnimation3;

    private short mCurrentBitmapAlpha0;
    private short mCurrentBitmapAlpha1;
    private short mCurrentBitmapAlpha2;
    private short mCurrentBitmapAlpha3;

    private boolean mIsDrawBitmap0;
    private boolean mIsDrawBitmap1;
    private boolean mIsDrawBitmap2;
    private boolean mIsDrawBitmap3;

    private short mCurrentDrawable0 = ALPHA_DEFAULT;
    private short mCurrentDrawable1 = ALPHA_DEFAULT;
    private short mCurrentDrawable2 = ALPHA_DEFAULT;
    private short mCurrentDrawable3 = ALPHA_DEFAULT;

    private Drawable mDrawableDefault0;
    private Drawable mDrawableDefault1;
    private Drawable mDrawableDefault2;
    private Drawable mDrawableDefault3;

    private Drawable mIconNotifyDrawable;
    private boolean mIsDrawIconNotifyDrawable;
    private int mIconNotifyDrawableWidth;
    private int mIconNotifyDrawableHeight;

    private Drawable mIconFailDrawable;
    private boolean mIsDrawIconFailDrawable;
    private int mIconFailDrawableWidth;
    private int mIconFailDrawableHeight;

    private Rect mRectBoundText;
    private Rect mItemAvatarBoxBound = new Rect();

    private int mAvatarType = VALUE_INVALID;

    public DialogItemView(Context context) {
        this(context, null);
    }

    public DialogItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DialogItemView, 0, 0);
        try {
            mTitleMarginLeft = a.getDimensionPixelSize(R.styleable.DialogItemView_title_margin_left, 0);
            mTitleMarginTop = a.getDimensionPixelSize(R.styleable.DialogItemView_title_margin_top, 0);
            mTitleMarginRight = a.getDimensionPixelSize(R.styleable.DialogItemView_title_margin_right, 0);
            mTitleMarginBottom = a.getDimensionPixelSize(R.styleable.DialogItemView_title_margin_bottom, 0);
            mTitleTextColor = a.getColor(R.styleable.DialogItemView_title_color, Color.BLACK);
            mTitleTextSize = a.getDimensionPixelSize(R.styleable.DialogItemView_title_text_size, 30);

            mContentMarginLeft = a.getDimensionPixelSize(R.styleable.DialogItemView_content_margin_left, 0);
            mContentMarginTop = a.getDimensionPixelSize(R.styleable.DialogItemView_content_margin_top, 0);
            mContentMarginRight = a.getDimensionPixelSize(R.styleable.DialogItemView_content_margin_right, 0);
            mContentMarginBottom = a.getDimensionPixelSize(R.styleable.DialogItemView_content_margin_bottom, 0);
            mContentTextColor = a.getColor(R.styleable.DialogItemView_content_color, Color.BLACK);
            mContentTextSize = a.getDimensionPixelSize(R.styleable.DialogItemView_content_text_size, 30);

            mStatusMarginLeft = a.getDimensionPixelSize(R.styleable.DialogItemView_status_margin_left, 0);
            mStatusMarginTop = a.getDimensionPixelSize(R.styleable.DialogItemView_status_margin_top, 0);
            mStatusMarginRight = a.getDimensionPixelSize(R.styleable.DialogItemView_status_margin_right, 0);
            mStatusMarginBottom = a.getDimensionPixelSize(R.styleable.DialogItemView_status_margin_bottom, 0);
            mStatusTextColor = a.getColor(R.styleable.DialogItemView_status_color, Color.BLACK);
            mStatusTextSize = a.getDimensionPixelSize(R.styleable.DialogItemView_status_text_size, 30);

            mTotalMemberColor = a.getColor(R.styleable.DialogItemView_total_member_color, Color.GRAY);
            mTotalMemberTextSize = a.getDimensionPixelSize(R.styleable.DialogItemView_total_member_text_size, 30);
            mTotalMemberTextColor = a.getColor(R.styleable.DialogItemView_total_member_text_color, Color.parseColor("#ecf0f1"));

            mDividerColor = a.getColor(R.styleable.DialogItemView_divider_color, Color.GRAY);
            mDividerHeight = a.getDimensionPixelSize(R.styleable.DialogItemView_divider_height, 1);
            mUnreadColor = a.getColor(R.styleable.DialogItemView_unread_color, Color.RED);
            mPaddingUnread = a.getDimensionPixelSize(R.styleable.DialogItemView_unread_padding, 0);
            mUnreadTextColor = a.getColor(R.styleable.DialogItemView_unread_text_color, Color.WHITE);
            mUnreadTextSize = a.getDimensionPixelSize(R.styleable.DialogItemView_unread_text_size, 10);
            mUnreadMinWidth = a.getDimensionPixelSize(R.styleable.DialogItemView_unread_min_width, 0);
            mUnreadMinHeight = a.getDimensionPixelSize(R.styleable.DialogItemView_unread_min_height, 0);

            mIconFailDrawableWidth = a.getDimensionPixelSize(R.styleable.DialogItemView_icon_fail_width, 0);
            mIconFailDrawableHeight = a.getDimensionPixelSize(R.styleable.DialogItemView_icon_fail_height, 0);

            mIconNotifyDrawableWidth = a.getDimensionPixelSize(R.styleable.DialogItemView_icon_notify_width, 0);
            mIconNotifyDrawableHeight = a.getDimensionPixelSize(R.styleable.DialogItemView_icon_notify_height, 0);

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            setAttributeValueDefault();
        } finally {
            if (a != null)
                a.recycle();
        }
        mAvatarBoxWidth = mAvatarBoxHeight = getResources().getDimension(R.dimen.dialog_item_view_height_width_avatar_box);
    }

    private void setAttributeValueDefault() {
        mTitleMarginLeft = mTitleMarginTop = mTitleMarginRight = mTitleMarginBottom = 0;
        mTitleTextColor = Color.BLACK;
        mTitleTextSize = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_medium_font_size);

        mContentMarginLeft = mContentMarginTop = mContentMarginRight = mContentMarginBottom = 0;
        mContentTextColor = Color.BLACK;
        mContentTextSize = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_small_font_size);

        mStatusMarginLeft = mStatusMarginTop = mStatusMarginRight = mStatusMarginBottom = 0;
        mStatusTextColor = Color.BLACK;
        mStatusTextSize = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_small_font_size);

        mTotalMemberColor = Color.GRAY;
        mTotalMemberTextSize = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_small_font_size);
        mTotalMemberTextColor = Color.WHITE;

        mDividerColor = Color.BLACK;
        mDividerHeight = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_divider_size);
        mUnreadColor = Color.RED;
        mPaddingUnread = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_padding_unread_size);

        mUnreadTextColor = Color.WHITE;
        mUnreadTextSize = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_small_font_size);

        mUnreadMinWidth = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_unread_min_width);
        mUnreadMinHeight = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_unread_min_width);

        mIconFailDrawableWidth = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_icon_fail_width);
        mIconFailDrawableHeight = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_icon_fail_height);

        mIconNotifyDrawableWidth = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_icon_notify_width);
        mIconNotifyDrawableHeight = getResources().getDimensionPixelSize(R.dimen.dialog_item_view_icon_notify_height);

    }

    public void setDefaultDrawable(Drawable drawableDefault) {
        if (drawableDefault != null) {
            mDrawableDefault0 = mDrawableDefault1 = mDrawableDefault2 = mDrawableDefault3 = drawableDefault;
            invalidate();
        }
    }

    public void setUnreadText(String text) {
        if (mUnReadPaint == null) {
            mUnReadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mUnReadPaint.setColor(mUnreadColor);
        }

        if (mUnReadPaintText == null) {
            mUnReadPaintText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mUnReadPaintText.setTextSize(mUnreadTextSize);
            mUnReadPaintText.setColor(mUnreadTextColor);
            mUnReadPaintText.setTypeface(Typeface.create(mUnReadPaintText.getTypeface(), Typeface.BOLD));
        }
        if (!TextUtils.isEmpty(text)) {
            mUnReadText = text;
            mIsDrawUnRead = true;
            invalidate();
        }
    }

    public void reset() {
        mCurrentDrawable0 = mCurrentDrawable1 = mCurrentDrawable2 = mCurrentDrawable3 = ALPHA_DEFAULT;
        mCurrentBitmapAlpha0 = mCurrentBitmapAlpha1 = mCurrentBitmapAlpha2 = mCurrentBitmapAlpha3 = 0;
        mIsDrawBitmap0 = mIsDrawBitmap1 = mIsDrawBitmap2 = mIsDrawBitmap3 = mIsDrawUnRead = false;
        mIsAnimation0 = mIsAnimation1 = mIsAnimation2 = mIsAnimation3 = false;

        mTitleText = mContentText = mStatusText = mUnReadText = mTotalMemberText = null;

        mAvatarType = VALUE_INVALID;
        if (mBitmaps != null) {
            int size = mBitmaps.length;
            for (int i = 0; i < size; i++) {
                mBitmaps[i] = null;
            }
        }

    }

    public void setTitle(String text) {
        if (mTitlePaint == null) {
            mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mTitlePaint.setTextSize(mTitleTextSize);
            mTitlePaint.setColor(mTitleTextColor);
        }

        if (!TextUtils.isEmpty(text)) {
            mTitleText = text;
            invalidate();
        }
    }

    public void setContent(String text) {
        if (mContentPaint == null) {
            mContentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mContentPaint.setTextSize(mContentTextSize);
            mContentPaint.setColor(mContentTextColor);
        }

        if (!TextUtils.isEmpty(text)) {
            mContentText = text;
            invalidate();
        }
    }

    public void setStatus(String text) {
        if (mStatusPaint == null) {
            mStatusPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mStatusPaint.setTextSize(mStatusTextSize);
            mStatusPaint.setColor(mStatusTextColor);
        }

        if (!TextUtils.isEmpty(text)) {
            mStatusText = text;
            invalidate();
        }
    }

    public void setTitleTextBold(boolean flag) {
        if (mTitlePaint != null) {
            if (flag) {
                mTitlePaint.setTypeface(Typeface.create(mTitlePaint.getTypeface(), Typeface.BOLD));
            } else
                mTitlePaint.setTypeface(Typeface.create(mTitlePaint.getTypeface(), Typeface.NORMAL));
            invalidate();
        }
    }

    public void setContentTextBold(boolean flag) {
        if (mContentPaint != null) {
            if (flag)
                mContentPaint.setTypeface(Typeface.create(mContentPaint.getTypeface(), Typeface.BOLD));
            else
                mContentPaint.setTypeface(Typeface.create(mContentPaint.getTypeface(), Typeface.NORMAL));
            invalidate();
        }
    }

    public void setStatusTextBold(boolean flag) {
        if (mStatusPaint != null) {
            if (flag)
                mStatusPaint.setTypeface(Typeface.create(mStatusPaint.getTypeface(), Typeface.BOLD));
            else
                mStatusPaint.setTypeface(Typeface.create(mStatusPaint.getTypeface(), Typeface.NORMAL));
            invalidate();
        }
    }

    public void setIconFailDrawable(Drawable drawable) {
        if (drawable != null) {
            mIconFailDrawable = drawable;
            mIsDrawIconFailDrawable = true;
            invalidate();
        }
    }

    public void setIconNotifyDrawable(Drawable drawable) {
        if (drawable != null) {
            mIsDrawIconNotifyDrawable = true;
            mIconNotifyDrawable = drawable;
            invalidate();
        }
    }

    public void setBitmapUrl(String text, String... urls) {
        if (urls != null) {
            if (urls.length <= 4 && mSize != urls.length) {
                mSize = urls.length;
                mBitmapPaints = new Paint[mSize];
                mBitmaps = new Bitmap[mSize];
                for (int i = 0; i < mSize; i++) {
                    mBitmapPaints[i] = new Paint();
                    mBitmapPaints[i].setAlpha(0);
                }
            }

            if (!TextUtils.isEmpty(text)) {
                mTotalMemberText = text;
                if (mTotalMemberPaint == null) {
                    mTotalMemberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mTotalMemberPaint.setColor(mTotalMemberColor);
                }

                if (mTotalMemberTextPaint == null) {
                    mTotalMemberTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    mTotalMemberTextPaint.setTextSize(mTotalMemberTextSize);
                    mTotalMemberTextPaint.setTypeface(Typeface.create(mTotalMemberTextPaint.getTypeface(), Typeface.BOLD));
                    mTotalMemberTextPaint.setColor(mTotalMemberTextColor);
                }
            }
        }
        configWidthAndHeightAvatarBox();
        invalidate();
    }


    public void setBitmapUrls(String text, List<String> urls) {
        if (urls != null) {
            if (urls.size() <= 4 && mSize != urls.size()) {
                mSize = urls.size();
                mBitmapPaints = new Paint[mSize];
                mBitmaps = new Bitmap[mSize];
                for (int i = 0; i < mSize; i++) {
                    mBitmapPaints[i] = new Paint();
                    mBitmapPaints[i].setAlpha(0);
                }
            }

            if (!TextUtils.isEmpty(text)) {
                mTotalMemberText = text;
                if (mTotalMemberPaint == null) {
                    mTotalMemberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mTotalMemberPaint.setColor(mTotalMemberColor);
                }

                if (mTotalMemberTextPaint == null) {
                    mTotalMemberTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    mTotalMemberTextPaint.setTextSize(mTotalMemberTextSize);
                    mTotalMemberTextPaint.setTypeface(Typeface.create(mTotalMemberTextPaint.getTypeface(), Typeface.BOLD));
                    mTotalMemberTextPaint.setColor(mTotalMemberTextColor);
                }
            }
        }
        configWidthAndHeightAvatarBox();
        invalidate();
    }


    private void configWidthAndHeightAvatarBox() {
        if (mSize == 1) {
            if (mTotalMemberText == null) {
                mItemAvatarWidth = mItemAvatarHeight = mAvatarBoxWidth;
                mAvatarType = AVATAR_ONE_BITMAP;
            } else {
                mItemAvatarWidth = mItemAvatarHeight = getResources().getDimension(R.dimen.dialog_item_view_height_item_2_avatar);
                mAvatarType = AVATAR_ONE_BITMAP_AND_TEXT;
            }
        } else if (mSize == 2 && mTotalMemberText == null) {
            mItemAvatarWidth = mItemAvatarHeight = getResources().getDimension(R.dimen.dialog_item_view_height_item_2_avatar);
            mAvatarType = AVATAR_TWO_BITMAP;
        } else if (mSize == 3) {
            if (mTotalMemberText == null) {
                mItemAvatarWidth = mItemAvatarHeight = getResources().getDimension(R.dimen.dialog_item_view_height_item_3_avatar);
                mAvatarType = AVATAR_THREE_BITMAP;
                mHeightDraw = getResources().getDimension(R.dimen.dialog_item_view_height_bound_case_3_avatar);
            } else {
                mItemAvatarWidth = mItemAvatarHeight = getResources().getDimension(R.dimen.dialog_item_view_height_item_3_avatar);
                mAvatarType = AVATAR_THREE_BITMAP_AND_TEXT;
            }
        } else if (mSize == 4 && mTotalMemberText == null) {
            mItemAvatarWidth = mItemAvatarHeight = getResources().getDimension(R.dimen.dialog_item_view_height_item_3_avatar);
            mAvatarType = AVATAR_FOUR_BITMAP;
        }
        mItemAvatarBoxBound.set(0, 0, (int) mItemAvatarWidth, (int) mItemAvatarHeight);
    }


    public void setDrawTypeTotalMember(int type) {
        if (type != TOTAL_MEMBER_CIRCLE && type != TOTAL_MEMBER_SQUARE)
            return;
        mTotalMemberDrawType = type;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure: ");
        int widthView = View.resolveSize((int) mAvatarBoxWidth, widthMeasureSpec);
        int heightView = View.resolveSize((int) mAvatarBoxHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec);
        setMeasuredDimension(widthView, heightView);
    }

    public void setBitmapAt(Bitmap bitmap, int index, boolean animation) {
        if (index > mSize - 1 || bitmap == null)
            return;

        if (mBitmaps != null) {
            mBitmaps[index] = bitmap;
            switch (index) {
                case 0:
                    mIsDrawBitmap0 = true;
                    mIsAnimation0 = animation;
                    break;
                case 1:
                    mIsDrawBitmap1 = true;
                    mIsAnimation1 = animation;
                    break;
                case 2:
                    mIsDrawBitmap2 = true;
                    mIsAnimation2 = animation;
                    break;
                case 3:
                    mIsDrawBitmap3 = true;
                    mIsAnimation3 = animation;
                    break;
            }
            invalidate();
        }
    }

    public int getAvatarType() {
        return mAvatarType;
    }

    public Bitmap[] getBitmapsData() {
        return mBitmaps;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Log.d(TAG, "onDraw");
        long startTime = System.nanoTime();
        if (mRectBoundText == null)
            mRectBoundText = new Rect();

        // TODO: 04/07/2016 Draw AvatarBox
        drawAvatarBox(canvas);

        float tranX = mAvatarBoxWidth + getPaddingLeft();
        canvas.translate(tranX, 0);
        float availableWidth = getWidth() - tranX - getPaddingRight();

        // TODO: 29/06/2016 Draw Status
        if (mStatusText != null) {
            //Log.d(TAG, "onDraw: Draw Status");
            float measureStatusWidth = mStatusPaint.measureText(mStatusText);
            mStatusPaint.getTextBounds(mStatusText, 0, mStatusText.length(), mRectBoundText);
            canvas.drawText(mStatusText, availableWidth - measureStatusWidth, getPaddingTop() + mRectBoundText.height() + mStatusMarginTop, mStatusPaint);
            availableWidth -= measureStatusWidth;
        }

        // TODO: 06/07/2016 Draw Icon Fail
        if (mIsDrawIconFailDrawable && mIconFailDrawable != null) {
            int left = (int) availableWidth - mIconFailDrawableWidth;
            mIconFailDrawable.setBounds(left, getPaddingTop(), left + mIconFailDrawableWidth, mIconFailDrawableHeight);
            mIconFailDrawable.draw(canvas);
            availableWidth -= mIconFailDrawableWidth;
        }

        CharSequence str;
        // TODO: 29/06/2016 Draw Title
        if (mTitleText != null) {
            // Log.i(TAG, "onDraw: Draw Title");
            str = TextUtils.ellipsize(mTitleText, mTitlePaint, availableWidth - mTitleMarginLeft, TextUtils.TruncateAt.END);
            mTitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRectBoundText);
            canvas.drawText(str, 0, str.length(), mTitleMarginLeft, getPaddingTop() + mRectBoundText.height() + mTitleMarginTop, mTitlePaint);
        }
        // TODO: 29/06/2016 Draw Content
        if (mContentText != null) {
            //Log.d(TAG, "onDraw: Draw Content");
            str = TextUtils.ellipsize(mContentText, mContentPaint, availableWidth - mContentMarginLeft, TextUtils.TruncateAt.END);
            canvas.drawText(str, 0, str.length(), mContentMarginLeft, getHeight() - mContentPaint.descent() - getPaddingBottom() - mContentMarginBottom, mContentPaint);
        }

        // TODO: 04/07/2016  Draw Divider DialogItemView
        if (!TextUtils.isEmpty(mTitleText) || !TextUtils.isEmpty(mContentText)) {
            if (mDividerPaint == null) {
                mDividerPaint = new Paint();
                mDividerPaint.setStrokeWidth(mDividerHeight);
                mDividerPaint.setColor(mDividerColor);
            }
            canvas.drawLine(mTitleMarginLeft, getHeight(), getWidth() - tranX, getHeight(), mDividerPaint);
        }

        // TODO: 06/07/2016 Draw Notify Icon
        if (mIsDrawIconNotifyDrawable && mIconNotifyDrawable != null) {
            //Log.d(TAG, "onDraw: Draw Notify");
            int top = (getHeight() - mIconNotifyDrawableHeight) / 2;
            int left = getWidth() - (int) tranX - getPaddingRight() - mIconNotifyDrawableWidth;
            mIconNotifyDrawable.setBounds(left, top, left + mIconNotifyDrawableWidth, top + mIconNotifyDrawableHeight);
            mIconNotifyDrawable.draw(canvas);
        }
        if (mIsAnimation0 || mIsAnimation1 || mIsAnimation2 || mIsAnimation3) {
            postInvalidateDelayed(TIME_REFESH);
        }

        //Log.e(TAG, "onDraw: Total Time: " + (System.nanoTime() - startTime));
    }

    private void drawAvatarBox(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float radius;
        float tranX;
        float tranY;
        if (mAvatarType == AVATAR_ONE_BITMAP) { // TODO: 06/07/2016 Case1: 1 Bitmap
            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, 0, 0);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }

        } else if (mAvatarType == AVATAR_ONE_BITMAP_AND_TEXT) { // TODO: 06/07/2016 Case 2: 1 bitmap and 1 text
            radius = mItemAvatarWidth / 2.0f;
            tranX = mAvatarBoxWidth - mItemAvatarWidth;
            tranY = mAvatarBoxHeight - mItemAvatarHeight;

            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }

            float widthTextMeasure = mTotalMemberTextPaint.measureText(mTotalMemberText, 0, mTotalMemberText.length());
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            mTotalMemberTextPaint.getTextBounds(mTotalMemberText, 0, mTotalMemberText.length(), mRectBoundText);
            canvas.translate(-tranX, tranY);
            if (mTotalMemberDrawType == TOTAL_MEMBER_CIRCLE)
                canvas.drawCircle(radius, radius, radius, mTotalMemberPaint); //right bottom
            else
                canvas.drawRect(mItemAvatarBoxBound, mTotalMemberPaint);
            canvas.drawText(mTotalMemberText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mTotalMemberTextPaint);
            canvas.translate(0, -tranY);

        } else if (mAvatarType == AVATAR_TWO_BITMAP) {  // TODO: 06/07/2016 Case 3: 2 bitmap
            tranX = mAvatarBoxWidth - mItemAvatarWidth;
            tranY = mAvatarBoxHeight - mItemAvatarHeight;
            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[1], null, mItemAvatarBoxBound, mBitmapPaints[1]);
                }
            }
            canvas.translate(0, -tranY);
            // TODO: 06/07/2016  Case 4: Draw 3 bitmap
        } else if (mAvatarType == AVATAR_THREE_BITMAP) {
            radius = mItemAvatarWidth / 2.0f;
            tranX = (mAvatarBoxWidth / 2) - radius;
            tranY = (mAvatarBoxHeight - mHeightDraw) / 2.0f;

            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, tranY);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, tranY);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }

            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, mItemAvatarHeight - tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, mItemAvatarHeight - tranY);
                    canvas.drawBitmap(mBitmaps[1], null, mItemAvatarBoxBound, mBitmapPaints[1]);
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(mAvatarBoxWidth - mItemAvatarWidth, 0);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(mAvatarBoxWidth - mItemAvatarWidth, 0);
                    canvas.drawBitmap(mBitmaps[2], null, mItemAvatarBoxBound, mBitmapPaints[2]);
                }
            }
            canvas.translate(-(mAvatarBoxWidth - mItemAvatarWidth), -(mItemAvatarHeight - tranY) - ((mAvatarBoxHeight - mHeightDraw) / 2.0f));

        } else if (mAvatarType == AVATAR_FOUR_BITMAP) {   // TODO: 06/07/2016 case 5: Draw 4 bitmap
            tranX = mAvatarBoxWidth - mItemAvatarWidth;
            tranY = mAvatarBoxHeight - mItemAvatarHeight;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[1], null, mItemAvatarBoxBound, mBitmapPaints[1]);
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[2], null, mItemAvatarBoxBound, mBitmapPaints[2]);
                }
            }
            if (!mIsDrawBitmap3) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault3 != null) {
                    mDrawableDefault3.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault3.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault3.draw(canvas);
                }
            } else {
                if (mIsAnimation3) {
                    processAnimationBitmap3(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[3].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[3], null, mItemAvatarBoxBound, mBitmapPaints[3]);
                }
            }
            canvas.translate(-tranX, -tranY);

        } else if (mAvatarType == AVATAR_THREE_BITMAP_AND_TEXT) {  // TODO: 06/07/2016 Case 5: Draw 3 bitmap and 1 text
            radius = mItemAvatarWidth / 2.0f;
            tranX = mAvatarBoxWidth - mItemAvatarWidth;
            tranY = mAvatarBoxHeight - mItemAvatarHeight;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[1], null, mItemAvatarBoxBound, mBitmapPaints[1]);
                }
            }
            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mItemAvatarBoxBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[2], null, mItemAvatarBoxBound, mBitmapPaints[2]);
                }
            }

            if (mTotalMemberText != null) {
                float widthTextMeasure = mTotalMemberTextPaint.measureText(mTotalMemberText, 0, mTotalMemberText.length());
                if (mRectBoundText == null)
                    mRectBoundText = new Rect();
                mTotalMemberTextPaint.getTextBounds(mTotalMemberText, 0, mTotalMemberText.length(), mRectBoundText);
                canvas.translate(tranX, 0);
                if (mTotalMemberDrawType == TOTAL_MEMBER_CIRCLE)
                    canvas.drawCircle(radius, radius, radius, mTotalMemberPaint); //right bottom
                else
                    canvas.drawRect(mItemAvatarBoxBound, mTotalMemberPaint);
                canvas.drawText(mTotalMemberText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mTotalMemberTextPaint);
            }
            canvas.translate(-tranX, -tranY);
        }

        // TODO: 29/06/2016 Draw unread count message
        if (mIsDrawUnRead) {
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            if (mUnReadText != null) {
                float widthMeasureText = mUnReadPaintText.measureText(mUnReadText, 0, mUnReadText.length());
                float width = Math.max(widthMeasureText, mUnreadMinWidth);
                width += (mPaddingUnread * 2);
                mUnReadPaintText.getTextBounds(mUnReadText, 0, mUnReadText.length(), mRectBoundText);

                radius = width / 2.0f;
                canvas.translate(mAvatarBoxWidth - width, 0);
                canvas.drawCircle(radius, radius, radius, mUnReadPaint);

                float x = (width - widthMeasureText) / 2.0f;
                float y = radius + (mRectBoundText.height() / 2.0f);
                canvas.drawText(mUnReadText, x, y, mUnReadPaintText);
                canvas.translate(-(mAvatarBoxWidth - width), 0);
            }
        }
        canvas.translate(-getPaddingLeft(), -getPaddingTop());
    }

    private void processAnimationBitmap0(Canvas canvas, float tranX, float tranY) {
        Log.e(TAG, "processAnimationBitmap0: " + mAvatarType);
        if (mAvatarType == AVATAR_ONE_BITMAP_AND_TEXT || mAvatarType == AVATAR_TWO_BITMAP) {
            canvas.translate(tranX, 0);
        } else if (mAvatarType == AVATAR_THREE_BITMAP) {
            canvas.translate(tranX, tranY);
        }

        if (mCurrentDrawable0 < 0)
            mCurrentDrawable0 = 0;

        if (mDrawableDefault0 != null) {
            mDrawableDefault0.setBounds(mItemAvatarBoxBound);
            mDrawableDefault0.setAlpha(mCurrentDrawable0);
            mDrawableDefault0.draw(canvas);
        }
        if (mCurrentBitmapAlpha0 > ALPHA_DEFAULT) {
            mCurrentBitmapAlpha0 = ALPHA_DEFAULT;
            mIsAnimation0 = false;
        }
        mBitmapPaints[0].setAlpha(mCurrentBitmapAlpha0);
        canvas.drawBitmap(mBitmaps[0], null, mItemAvatarBoxBound, mBitmapPaints[0]);
        if (mCurrentBitmapAlpha0 < 255) {
            mCurrentBitmapAlpha0 += ALPHA_STEP;
            mCurrentDrawable0 -= ALPHA_STEP;
        }
    }

    private void processAnimationBitmap1(Canvas canvas, float tranX, float tranY) {
        if (mAvatarType == AVATAR_TWO_BITMAP) {
            canvas.translate(-tranX, tranY);
        } else if (mAvatarType == AVATAR_THREE_BITMAP) {
            canvas.translate(-tranX, mItemAvatarHeight - tranY);
        } else if (mAvatarType == AVATAR_FOUR_BITMAP || mAvatarType == AVATAR_THREE_BITMAP_AND_TEXT) {
            canvas.translate(tranX, 0);
        }

        if (mCurrentDrawable1 < 0)
            mCurrentDrawable1 = 0;

        if (mDrawableDefault1 != null) {
            mDrawableDefault1.setBounds(mItemAvatarBoxBound);
            mDrawableDefault1.setAlpha(mCurrentDrawable1);
            mDrawableDefault1.draw(canvas);
        }
        if (mCurrentBitmapAlpha1 > ALPHA_DEFAULT) {
            mCurrentBitmapAlpha1 = ALPHA_DEFAULT;
            mIsAnimation1 = false;
        }

        mBitmapPaints[1].setAlpha(mCurrentBitmapAlpha1);
        canvas.drawBitmap(mBitmaps[1], null, mItemAvatarBoxBound, mBitmapPaints[1]);
        if (mCurrentBitmapAlpha1 < 255) {
            mCurrentBitmapAlpha1 += ALPHA_STEP;
            mCurrentDrawable1 -= ALPHA_STEP;
        }
    }

    private void processAnimationBitmap2(Canvas canvas, float tranX, float tranY) {
        if (mAvatarType == AVATAR_THREE_BITMAP) {
            canvas.translate(mAvatarBoxWidth - mItemAvatarWidth, 0);
        } else if (mAvatarType == AVATAR_FOUR_BITMAP || mAvatarType == AVATAR_THREE_BITMAP_AND_TEXT) {
            canvas.translate(-tranX, tranY);
        }

        if (mCurrentDrawable2 < 0)
            mCurrentDrawable2 = 0;

        if (mDrawableDefault2 != null) {
            mDrawableDefault2.setBounds(mItemAvatarBoxBound);
            mDrawableDefault2.setAlpha(mCurrentDrawable2);
            mDrawableDefault2.draw(canvas);
        }

        if (mCurrentBitmapAlpha2 > ALPHA_DEFAULT) {
            mCurrentBitmapAlpha2 = ALPHA_DEFAULT;
            mIsAnimation2 = false;
        }

        mBitmapPaints[2].setAlpha(mCurrentBitmapAlpha2);
        canvas.drawBitmap(mBitmaps[2], null, mItemAvatarBoxBound, mBitmapPaints[2]);
        if (mCurrentBitmapAlpha2 < 255) {
            mCurrentBitmapAlpha2 += ALPHA_STEP;
            mCurrentDrawable2 -= ALPHA_STEP;
        }
    }

    private void processAnimationBitmap3(Canvas canvas, float tranX, float tranY) {
        if (mAvatarType == AVATAR_FOUR_BITMAP) {
            if (mCurrentDrawable3 < 0)
                mCurrentDrawable3 = 0;
            canvas.translate(tranX, 0);
            if (mDrawableDefault3 != null) {
                mDrawableDefault3.setBounds(mItemAvatarBoxBound);
                mDrawableDefault3.setAlpha(mCurrentDrawable3);
                mDrawableDefault3.draw(canvas);
            }

            if (mCurrentBitmapAlpha3 > ALPHA_DEFAULT) {
                mCurrentBitmapAlpha3 = ALPHA_DEFAULT;
                mIsAnimation3 = false;
            }

            mBitmapPaints[3].setAlpha(mCurrentBitmapAlpha3);
            canvas.drawBitmap(mBitmaps[3], null, mItemAvatarBoxBound, mBitmapPaints[3]);
            if (mCurrentBitmapAlpha3 < 255) {
                mCurrentBitmapAlpha3 += ALPHA_STEP;
                mCurrentDrawable3 -= ALPHA_STEP;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmaps != null) {
            int size = mBitmaps.length;
            for (int i = 0; i < size; i++) {
                if (mBitmaps[i] != null && !mBitmaps[i].isRecycled()) {
                    Log.e(TAG, "onDetachedFromWindow: Recycled Bitmap");
                    mBitmaps[i].recycle();
                }
            }
        }
        reset();
    }
}
