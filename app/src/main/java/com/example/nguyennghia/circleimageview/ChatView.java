package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.List;

/**
 * Created by nguyennghia on 29/06/2016.
 */

public class ChatView extends View {
    private static final String TAG = "ChatView";

    private static final short ALPHA_DEFAULT = 255;
    private static final int ANIMATION_DURATION = 300;
    private static final int TIME_REFESH = 10;
    private static final int ALPHA_STEP = ALPHA_DEFAULT / (ANIMATION_DURATION / TIME_REFESH);

    private float mHeight;
    private float mWidth;

    private float mWidthImage;
    private float mHeightImage;

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
    private boolean mIsDrawDivider;

    private float mHeightDraw;
    private Resources mResource;
    private String mTotalMemberText;
    private TextPaint mTotalMemberTextPaint;
    private Paint mTotalMemberPaint;
    private Rect mRectBoundText;

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

    private Rect mDrawableBound = new Rect();

    private ImageType mImageType;

    public enum ImageType {
        TYPE_1, //represent for 1 bitmap in view
        TYPE_2,  //represent for 2 bitmap in view
        TYPE_3, //represent for 3 bitmap in view
        TYPE_4,  //represent for 4 bitmap in view
        TYPE_5   //represent for 3 bitmap and text in view
    }

    public void setDefaultDrawable(Drawable drawableDefault) {
        mDrawableDefault0 = mDrawableDefault1 = mDrawableDefault2 = mDrawableDefault3 = drawableDefault;
        invalidate();
    }

    public ChatView(Context context) {
        this(context, null);
    }

    public ChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResource = getResources();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChatView, 0, 0);
        if (a != null) {
            try {
                mTitleMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_left, 0);
                mTitleMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_top, 0);
                mTitleMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_right, 0);
                mTitleMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_bottom, 0);
                mTitleTextColor = a.getColor(R.styleable.ChatView_title_color, Color.BLACK);
                mTitleTextSize = a.getDimensionPixelSize(R.styleable.ChatView_title_text_size, 30);

                mContentMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_left, 0);
                mContentMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_top, 0);
                mContentMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_right, 0);
                mContentMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_bottom, 0);
                mContentTextColor = a.getColor(R.styleable.ChatView_content_color, Color.BLACK);
                mContentTextSize = a.getDimensionPixelSize(R.styleable.ChatView_content_text_size, 30);

                mStatusMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_left, 0);
                mStatusMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_top, 0);
                mStatusMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_right, 0);
                mStatusMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_bottom, 0);
                mStatusTextColor = a.getColor(R.styleable.ChatView_status_color, Color.BLACK);
                mStatusTextSize = a.getDimensionPixelSize(R.styleable.ChatView_status_text_size, 30);

                mDividerColor = a.getColor(R.styleable.ChatView_divider_color, Color.GRAY);
                mDividerHeight = a.getDimensionPixelSize(R.styleable.ChatView_divider_height, 1);

                mUnreadColor = a.getColor(R.styleable.ChatView_unread_color, Color.RED);
                mPaddingUnread = a.getDimensionPixelSize(R.styleable.ChatView_unread_padding, 0);
                mUnreadTextColor = a.getColor(R.styleable.ChatView_unread_text_color, Color.WHITE);
                mUnreadTextSize = a.getDimensionPixelSize(R.styleable.ChatView_unread_text_size, 10);
                mUnreadMinWidth = a.getDimensionPixelSize(R.styleable.ChatView_unread_min_width, 0);
                mUnreadMinHeight = a.getDimensionPixelSize(R.styleable.ChatView_unread_min_height, 0);

                mIconFailDrawableWidth = a.getDimensionPixelSize(R.styleable.ChatView_icon_fail_width, 0);
                mIconFailDrawableHeight = a.getDimensionPixelSize(R.styleable.ChatView_icon_fail_height, 0);

                mIconNotifyDrawableWidth = a.getDimensionPixelSize(R.styleable.ChatView_icon_notify_width, 0);
                mIconNotifyDrawableHeight = a.getDimensionPixelSize(R.styleable.ChatView_icon_notify_height, 0);
            } finally {
                a.recycle();
            }

            mHeight = mWidth = mResource.getDimension(R.dimen.height_width_avatar_box);
        }
    }

    public void setUnreadText(String text) {
        if (mUnReadPaint == null) {
            mUnReadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mUnReadPaint.setColor(Color.RED);
        }

        if (mUnReadPaintText == null) {
            mUnReadPaintText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mUnReadPaintText.setTextSize(mUnreadTextSize);
            mUnReadPaintText.setColor(Color.WHITE);
            mUnReadPaintText.setTypeface(Typeface.create(mUnReadPaintText.getTypeface(), Typeface.BOLD));
        }
        mUnReadText = text;
        mIsDrawUnRead = true;
        invalidate();
    }

    public void reset() {
        mCurrentBitmapAlpha0 = mCurrentBitmapAlpha1 = mCurrentBitmapAlpha2 = mCurrentBitmapAlpha3 = 0;
        mIsDrawBitmap0 = mIsDrawBitmap1 = mIsDrawBitmap2 = mIsDrawBitmap3 = false;
        mIsAnimation0 = mIsAnimation1 = mIsAnimation2 = mIsAnimation3 = false;
        mCurrentDrawable0 = mCurrentDrawable1 = mCurrentDrawable2 = mCurrentDrawable3 = ALPHA_DEFAULT;
        mIsDrawUnRead = mIsDrawDivider = false;
        mUnReadText = null;
        mTitleText = mContentText = mStatusText = null;
        mTotalMemberText = null;


    }

    public void setTitle(String text) {
        if (mTitlePaint == null) {
            mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mTitlePaint.setTextSize(mTitleTextSize);
            mTitlePaint.setColor(mTitleTextColor);

        }
        mTitleText = text;
        invalidate();
    }

    public void setContent(String text) {
        if (mContentPaint == null) {
            mContentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mContentPaint.setTextSize(mContentTextSize);
            mContentPaint.setColor(mContentTextColor);
        }
        mContentText = text;
        invalidate();
    }

    public void setStatus(String text) {
        if (mStatusPaint == null) {
            mStatusPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mStatusPaint.setTextSize(mStatusTextSize);
            mStatusPaint.setColor(mStatusTextColor);
        }
        mStatusText = text;
        invalidate();
    }

    public void setTitleTextBold(boolean flag) {
        if (mTitlePaint != null) {
            if (flag)
                mTitlePaint.setTypeface(Typeface.create(mTitlePaint.getTypeface(), Typeface.BOLD));
            else
                mTitlePaint.setTypeface(Typeface.create(mTitlePaint.getTypeface(), Typeface.NORMAL));
        }
    }

    public void setContentTextBold(boolean flag) {
        if (mContentPaint != null) {
            if (flag)
                mContentPaint.setTypeface(Typeface.create(mContentPaint.getTypeface(), Typeface.BOLD));
            else
                mContentPaint.setTypeface(Typeface.create(mContentPaint.getTypeface(), Typeface.NORMAL));
        }
    }

    public void setStatusTextBold(boolean flag) {
        if (mStatusPaint != null) {
            if (flag)
                mStatusPaint.setTypeface(Typeface.create(mStatusPaint.getTypeface(), Typeface.BOLD));
            else
                mStatusPaint.setTypeface(Typeface.create(mStatusPaint.getTypeface(), Typeface.NORMAL));
        }

    }

    public void setIconFailDrawable(Drawable drawable) {
        mIconFailDrawable = drawable;
        mIsDrawIconFailDrawable = true;
        invalidate();
    }

    public void setIconNotifyDrawable(Drawable drawable) {
        mIsDrawIconNotifyDrawable = true;
        mIconNotifyDrawable = drawable;
        invalidate();
    }

    public void setBitmapUrls(List<String> urls) {
        mSize = urls.size();
        configWidthAndHeightCircleImage();

    }

    public void setBitmapUrls(String... urls) {
        mSize = urls.length;
        configWidthAndHeightCircleImage();
    }

    //Method only using for draw one bitmap and text
    public void setBitmapUrl(String url, String text) {
        mSize = 1;
        mBitmapPaints = new Paint[1];
        mBitmapPaints[0] = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaints[0].setAlpha(0);

        mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_item_2_avatar);

        mDrawableBound.set(0, 0, (int) mWidthImage, (int) mHeightImage);

        if (mTotalMemberPaint == null) {
            mTotalMemberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTotalMemberPaint.setColor(Color.parseColor("#95a5a6"));
        }
        if (mTotalMemberTextPaint == null) {
            mTotalMemberTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mTotalMemberTextPaint.setTextSize(50);
            mTotalMemberTextPaint.setColor(Color.parseColor("#ecf0f1"));
        }

        mTotalMemberText = text;
    }

    private void configWidthAndHeightCircleImage() {
        int size = mSize > 4 ? 3 : mSize;
        mBitmapPaints = new Paint[size];
        for (int i = 0; i < size; i++) {
            mBitmaps = new Bitmap[size];
            mBitmapPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBitmapPaints[i].setAlpha(0);
        }

        if (mSize == 1) {
            mWidthImage = mHeightImage = mWidth;
            mImageType = ImageType.TYPE_1;
        } else if (mSize == 2) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_item_2_avatar);
            mImageType = ImageType.TYPE_2;
        } else if (mSize == 3) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_item_3_avatar);
            mImageType = ImageType.TYPE_3;
            mHeightDraw = mResource.getDimension(R.dimen.height_bound_case_3_avatar);
        } else if (mSize == 4) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_item_3_avatar);
            mImageType = ImageType.TYPE_4;
        } else {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_item_3_avatar);
            mImageType = ImageType.TYPE_5;
        }

        mDrawableBound.set(0, 0, (int) mWidthImage, (int) mHeightImage);

        if (mSize > 4) {
            if (mTotalMemberPaint == null) {
                mTotalMemberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mTotalMemberPaint.setColor(Color.parseColor("#95a5a6"));
            }
            if (mTotalMemberTextPaint == null) {
                mTotalMemberTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                mTotalMemberTextPaint.setTextSize(50);
                mTotalMemberTextPaint.setColor(Color.parseColor("#ecf0f1"));
            }
            mTotalMemberText = String.valueOf(mSize);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Math.max((int)mWidth, MeasureSpec.getSize(widthMeasureSpec));
        int height = Math.max((int)mHeight + getPaddingTop()+ getPaddingBottom(), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(width, height);
    }

    public void setBitmapAt(Bitmap bitmap, int index, boolean animation) {
        if (index > mSize - 1)
            return;
        if (bitmap == null)
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
            Log.d(TAG, "setBitmapAt: ");
            invalidate();
        }


        // mBitmapPaints[index].setShader(new BitmapShader(centerCropImage(bitmap, mWidthImage, mHeightImage), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

    }

//    private Bitmap centerCropImage(Bitmap src, float widthView, float heightView) {
//        Bitmap des = null;
//        if (src != null) {
//            Matrix matrix = new Matrix();
//            int bitmapWidth = src.getWidth();
//            int bitmapHeight = src.getHeight();
//            float scaleX = widthView / (bitmapWidth * 1.0f);
//            float scaleY = heightView / (bitmapHeight * 1.0f);
//            float scale = Math.max(scaleX, scaleY);
//            float width = bitmapWidth * scale;
//            float height = bitmapHeight * scale;
//            matrix.postScale(scale, scale);
//            float startX = Math.abs(widthView - width) * 0.5f;
//            float startY = Math.abs(heightView - height) * 0.5f;
//            des = Bitmap.createBitmap(src, (int) startX, (int) startY, (int) (bitmapWidth - startX), (int) (bitmapHeight - startY), matrix, true);
//        }
//        return des;
//    }

    public int getImageType() {
        return mImageType.ordinal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        if (mRectBoundText == null)
            mRectBoundText = new Rect();

        // TODO: 04/07/2016 Draw CircleImageBox
        drawCircleImageBox(canvas);

        float tranX = mWidth + getPaddingLeft();
        canvas.translate(tranX, 0);
        float availableWidth = getWidth() - tranX - getPaddingRight();

        // TODO: 29/06/2016 Draw Status
        if (mStatusText != null) {
            Log.d(TAG, "onDraw: Draw Status");
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
            Log.i(TAG, "onDraw: Draw Title");
            mIsDrawDivider = true;
            str = TextUtils.ellipsize(mTitleText, mTitlePaint, availableWidth - mTitleMarginLeft, TextUtils.TruncateAt.END);
            mTitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRectBoundText);
            canvas.drawText(str, 0, str.length(), mTitleMarginLeft, getPaddingTop() + mRectBoundText.height() + mTitleMarginTop, mTitlePaint);
        }
        // TODO: 29/06/2016 Draw Content
        if (mContentText != null) {
            Log.d(TAG, "onDraw: Draw Content");
            str = TextUtils.ellipsize(mContentText, mContentPaint, availableWidth - mContentMarginLeft, TextUtils.TruncateAt.END);
            canvas.drawText(str, 0, str.length(), mContentMarginLeft, getHeight() - mContentPaint.descent() - getPaddingBottom() - mContentMarginBottom, mContentPaint);
        }

        // TODO: 04/07/2016  Draw Divider ChatView
        if (mIsDrawDivider) {
            if (mDividerPaint == null) {
                mDividerPaint = new Paint();
                mDividerPaint.setStrokeWidth(mDividerHeight);
                mDividerPaint.setColor(mDividerColor);
            }
            canvas.drawLine(mTitleMarginLeft, getHeight(), getWidth() - tranX, getHeight(), mDividerPaint);
        }

        // TODO: 06/07/2016 Draw Notify Icon
        if (mIsDrawIconNotifyDrawable && mIconNotifyDrawable != null) {
            Log.d(TAG, "onDraw: Draw Notify");
            int top = (getHeight() - mIconNotifyDrawableHeight) / 2;
            int left = getWidth() - (int) tranX - getPaddingRight() - mIconNotifyDrawableWidth;
            mIconNotifyDrawable.setBounds(left, top, left + mIconNotifyDrawableWidth, top + mIconNotifyDrawableHeight);
            mIconNotifyDrawable.draw(canvas);
        }
    }

    private void drawCircleImageBox(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float radius;
        float tranX;
        float tranY;
        if (mSize == 1) {
            // TODO: 06/07/2016 Case1: 1 Bitmap 
            if (mTotalMemberText == null) {
                radius = mWidthImage / 2.0f;
                if (!mIsDrawBitmap0) {
                    if (mDrawableDefault0 != null) {
                        mDrawableDefault0.setBounds(mDrawableBound);
                        mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                        mDrawableDefault0.draw(canvas);
                    }
                } else {
                    if (mIsAnimation0) {
                        processAnimationBitmap0(canvas, radius, 0, 0);
                    } else {
                        mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                        canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                        //canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]);
                    }
                }
                // TODO: 06/07/2016 Case 2: 1 bitmap and 1 text 
            } else {
                radius = mWidthImage / 2.0f;
                tranX = mWidth - mWidthImage;
                tranY = mHeight - mHeightImage;
                if (!mIsDrawBitmap0) {
                    canvas.translate(tranX, 0);
                    if (mDrawableDefault0 != null) {
                        mDrawableDefault0.setBounds(mDrawableBound);
                        mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                        mDrawableDefault0.draw(canvas);
                    }
                } else {
                    if (mIsAnimation0) {
                        processAnimationBitmap0(canvas, radius, tranX, tranY);
                    } else {
                        mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                        canvas.translate(tranX, 0);
                        canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                        //canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top right
                    }
                }

                float widthTextMeasure = mTotalMemberTextPaint.measureText(mTotalMemberText, 0, mTotalMemberText.length());
                if (mRectBoundText == null)
                    mRectBoundText = new Rect();
                mTotalMemberTextPaint.getTextBounds(mTotalMemberText, 0, mTotalMemberText.length(), mRectBoundText);
                canvas.translate(-tranX, tranY);
                canvas.drawCircle(radius, radius, radius, mTotalMemberPaint); //right bottom
                canvas.drawText(mTotalMemberText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mTotalMemberTextPaint);
                canvas.translate(0, -tranY);
            }
            // TODO: 06/07/2016 Case 3: 2 bitmap 
        } else if (mSize == 2) {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;
            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {

                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top right
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[1]); //bottom left
                }
            }
            canvas.translate(0, -tranY);
            // TODO: 06/07/2016  Case 4: Draw 3 bitmap
        } else if (mSize == 3) {
            radius = mWidthImage / 2.0f;
            tranX = (mWidth / 2) - radius;
            tranY = (mHeight - mHeightDraw) / 2.0f;

            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, tranY);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, tranY);
                    canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top
                }
            }

            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, mHeightImage - tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, mHeightImage - tranY);
                    canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[1]); //left
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(mWidth - mWidthImage, 0);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(mWidth - mWidthImage, 0);
                    canvas.drawBitmap(mBitmaps[2], null, mDrawableBound, mBitmapPaints[2]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[2]); //right
                }
            }
            canvas.translate(-(mWidth - mWidthImage), -(mHeightImage - tranY) - ((mHeight - mHeightDraw) / 2.0f));
            // TODO: 06/07/2016 case 5: Draw 4 bitmap 
        } else if (mSize == 4) {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top left
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[1]); //top right
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[2], null, mDrawableBound, mBitmapPaints[2]);
                    //canvas.drawCircle(radius, radius, radius, mBitmapPaints[2]); //left bottom
                }
            }
            if (!mIsDrawBitmap3) {
                Log.d(TAG, "drawCircleImageBox: Draw 4.");
                canvas.translate(tranX, 0);
                if (mDrawableDefault3 != null) {
                    mDrawableDefault3.setBounds(mDrawableBound);
                    mDrawableDefault3.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault3.draw(canvas);
                }
            } else {
                if (mIsAnimation3) {
                    processAnimationBitmap3(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[3].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[3], null, mDrawableBound, mBitmapPaints[3]);
                    // canvas.drawCircle(radius, radius, radius, mBitmapPaints[3]); //right bottom
                }
            }
            canvas.translate(-tranX, -tranY);
            // TODO: 06/07/2016 Case 5: Draw 3 bitmap and 1 text
        } else {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
                    // canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top left
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
                    // canvas.drawCircle(radius, radius, radius, mBitmapPaints[1]); //top right
                }
            }


            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mBitmapPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
                    // canvas.drawCircle(radius, radius, radius, mBitmapPaints[2]); //left bottom
                }
            }

            if (mTotalMemberText != null) {
                Log.d(TAG, "drawCircleImageBox:  Draw Total Member");
                float widthTextMeasure = mTotalMemberTextPaint.measureText(mTotalMemberText, 0, mTotalMemberText.length());
                if (mRectBoundText == null)
                    mRectBoundText = new Rect();
                mTotalMemberTextPaint.getTextBounds(mTotalMemberText, 0, mTotalMemberText.length(), mRectBoundText);
                canvas.translate(tranX, 0);
                canvas.drawCircle(radius, radius, radius, mTotalMemberPaint); //right bottom
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
                canvas.translate(mWidth - width, 0);
                canvas.drawCircle(radius, radius, radius, mUnReadPaint);

                float x = (width - widthMeasureText) / 2.0f;
                float y = radius + (mRectBoundText.height() / 2.0f);
                canvas.drawText(mUnReadText, x, y, mUnReadPaintText);
                canvas.translate(-(mWidth - width), 0);

            }
        }
        canvas.translate(-getPaddingLeft(), -getPaddingTop());
    }

    private void processAnimationBitmap0(Canvas canvas, float radius, float tranX, float tranY) {
        Log.d(TAG, "processAnimationBitmap0: " + mCurrentDrawable0);
        if (mSize == 1) {
            if (mTotalMemberText != null) {
                canvas.translate(tranX, 0);
            }
        } else if (mSize == 2) {
            canvas.translate(tranX, 0);
        } else if (mSize == 3) {
            canvas.translate(tranX, tranY);
        }
        if (mCurrentDrawable0 < 0)
            mCurrentDrawable0 = 0;

        if (mDrawableDefault0 != null) {
            mDrawableDefault0.setBounds(mDrawableBound);
            mDrawableDefault0.setAlpha(mCurrentDrawable0);
            mDrawableDefault0.draw(canvas);
        }
        if (mCurrentBitmapAlpha0 > ALPHA_DEFAULT)
            mCurrentBitmapAlpha0 = ALPHA_DEFAULT;
        mBitmapPaints[0].setAlpha(mCurrentBitmapAlpha0);
        canvas.drawBitmap(mBitmaps[0], null, mDrawableBound, mBitmapPaints[0]);
        // canvas.drawCircle(radius, radius, radius, mBitmapPaints[0]); //top right
        if (mCurrentBitmapAlpha0 < 255) {
            mCurrentBitmapAlpha0 += ALPHA_STEP;
            mCurrentDrawable0 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }
    }

    private void processAnimationBitmap1(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 2) {
            canvas.translate(-tranX, tranY);
        } else if (mSize == 3) {
            canvas.translate(-tranX, mHeightImage - tranY);
        } else if (mSize == 4 || mSize > 4) {
            canvas.translate(tranX, 0);
        }

        if (mCurrentDrawable1 < 0)
            mCurrentDrawable1 = 0;

        if (mDrawableDefault1 != null) {
            mDrawableDefault1.setBounds(mDrawableBound);
            mDrawableDefault1.setAlpha(mCurrentDrawable1);
            mDrawableDefault1.draw(canvas);
        }
        if (mCurrentBitmapAlpha1 > ALPHA_DEFAULT)
            mCurrentBitmapAlpha1 = ALPHA_DEFAULT;

        mBitmapPaints[1].setAlpha(mCurrentBitmapAlpha1);
        canvas.drawBitmap(mBitmaps[1], null, mDrawableBound, mBitmapPaints[1]);
        // canvas.drawCircle(radius, radius, radius, mBitmapPaints[1]); //bottom left
        if (mCurrentBitmapAlpha1 < 255) {
            mCurrentBitmapAlpha1 += ALPHA_STEP;
            mCurrentDrawable1 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }

    }

    private void processAnimationBitmap2(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 3) {
            canvas.translate(mWidth - mWidthImage, 0);
        } else if (mSize == 4 || mSize > 4) {
            canvas.translate(-tranX, tranY);
        }

        if (mCurrentDrawable2 < 0)
            mCurrentDrawable2 = 0;

        if (mDrawableDefault2 != null) {
            mDrawableDefault2.setBounds(mDrawableBound);
            mDrawableDefault2.setAlpha(mCurrentDrawable2);
            mDrawableDefault2.draw(canvas);
        }

        if (mCurrentBitmapAlpha2 > ALPHA_DEFAULT)
            mCurrentBitmapAlpha2 = ALPHA_DEFAULT;
        mBitmapPaints[2].setAlpha(mCurrentBitmapAlpha2);
        canvas.drawBitmap(mBitmaps[2], null, mDrawableBound, mBitmapPaints[2]);
        //canvas.drawCircle(radius, radius, radius, mBitmapPaints[2]); //right
        if (mCurrentBitmapAlpha2 < 255) {
            mCurrentBitmapAlpha2 += ALPHA_STEP;
            mCurrentDrawable2 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }
    }

    private void processAnimationBitmap3(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 4) {
            if (mCurrentDrawable3 < 0)
                mCurrentDrawable3 = 0;
            canvas.translate(tranX, 0);
            if (mDrawableDefault3 != null) {
                mDrawableDefault3.setBounds(mDrawableBound);
                mDrawableDefault3.setAlpha(mCurrentDrawable3);
                mDrawableDefault3.draw(canvas);
            }

            if (mCurrentBitmapAlpha3 > ALPHA_DEFAULT)
                mCurrentBitmapAlpha3 = ALPHA_DEFAULT;
            mBitmapPaints[3].setAlpha(mCurrentBitmapAlpha3);
            canvas.drawBitmap(mBitmaps[3], null, mDrawableBound, mBitmapPaints[3]);
            // canvas.drawCircle(radius, radius, radius, mBitmapPaints[3]); //left bottom
            if (mCurrentBitmapAlpha3 < 255) {
                mCurrentBitmapAlpha3 += ALPHA_STEP;
                mCurrentDrawable3 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        }
    }
}
