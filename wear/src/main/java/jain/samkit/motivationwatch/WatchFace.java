package jain.samkit.motivationwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.format.Time;

public class WatchFace {
    private final Paint timePaint;
    private final Paint circlePaint;
    private final Time time;

    private String[] numToWord = {
            "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
            "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN", "TWENTY",
            "TWENTY ONE", "TWENTY TWO", "TWENTY THREE", "TWENTY FOUR", "TWENTY FIVE", "TWENTY SIX", "TWENTY SEVEN", "TWENTY EIGHT", "TWENTY NINE", "THIRTY",
            "THIRTY ONE", "THIRTY TWO", "THIRTY THREE", "THIRTY FOUR", "THIRTY FIVE", "THIRTY SIX", "THIRTY SEVEN", "THIRTY EIGHT", "THIRTY NINE", "FOURTY",
            "FOURTY ONE", "FOURTY TWO", "FOURTY THREE", "FOURTY FOUR", "FOURTY FIVE", "FOURTY SIX", "FOURTY SEVEN", "FOURTY EIGHT", "FOURTY NINE", "FIFTY",
            "FIFTY ONE", "FIFTY TWO", "FIFTY THREE", "FIFTY FOUR", "FIFTY FIVE", "FIFTY SIX", "FIFTY SEVEN", "FIFTY EIGHT", "FIFTY NINE"};

    private boolean shouldShowSeconds = true;

    private final Context context;

    public static WatchFace newInstance(Context context) {
        Paint timePaint = new Paint();
        timePaint.setColor(Color.WHITE);
        timePaint.setAntiAlias(true);

        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#2196F3"));
        circlePaint.setStrokeWidth(20);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeCap(Paint.Cap.BUTT);
        circlePaint.setStyle(Paint.Style.STROKE);

        return new WatchFace(timePaint, circlePaint, new Time(), context);
    }

    WatchFace(Paint timePaint, Paint circlePaint, Time time, Context context) {
        this.timePaint = timePaint;
        this.circlePaint = circlePaint;
        this.time = time;
        this.context = context;
    }

    public void draw(Canvas canvas, Rect bounds) {
        time.setToNow();
        canvas.drawColor(Color.BLACK);

        int h = time.hour;
        int s = time.second;

        if(h > 12) {
            h -= 12;
        } else if(h == 0) {
            h = 12;
        }

        final RectF circleRect = new RectF();
        circleRect.set(bounds.exactCenterX() - (bounds.width() / 2), bounds.exactCenterY() - (bounds.height() / 2), bounds.exactCenterX() + (bounds.width() / 2), bounds.exactCenterY() + (bounds.height() / 2));
        //canvas.drawArc(circleRect, -90, s * 6, false, circlePaint);
        //canvas.drawArc(circleRect, s * 6 - 96, 6, false, circlePaint);

        for(int i = 1; i <= s; i++) {
            canvas.drawArc(circleRect, i * 6 - 96, 5, false, circlePaint);
        }

        String text1 = "HOLY\nSHIT\nIT'S";
        String text2 = numToWord[h];
        String text3 = "FUCKING";
        String text4 = numToWord[time.minute];
        String text5 = "MOTHERFUCKER";

        Rect textBounds = new Rect();
        timePaint.getTextBounds(text1, 0, text1.length(), textBounds);
        timePaint.setColor(Color.parseColor("#FFFFFF"));
        timePaint.setTextSize(context.getResources().getDimension(R.dimen.small_size));
        timePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        float textX = bounds.exactCenterX() - (timePaint.measureText(text1) / 2.0f);
        float textY = bounds.exactCenterY() + (textBounds.height() / 2.0f);
        textY -= 80.0f;

        canvas.drawText(text1, textX, textY, timePaint);

        timePaint.getTextBounds(text2, 0, text2.length(), textBounds);
        timePaint.setColor(Color.parseColor("#2196F3"));
        timePaint.setTextSize(context.getResources().getDimension(R.dimen.big_size));
        timePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));

        textX = bounds.exactCenterX() - (timePaint.measureText(text2) / 2.0f);
        textY = bounds.exactCenterY() + (textBounds.height() / 2.0f);
        textY -= 33.0f;

        canvas.drawText(text2, textX, textY, timePaint);

        timePaint.getTextBounds(text3, 0, text3.length(), textBounds);
        timePaint.setColor(Color.parseColor("#FFFFFF"));
        timePaint.setTextSize(context.getResources().getDimension(R.dimen.small_size));
        timePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        textX = bounds.exactCenterX() - (timePaint.measureText(text3) / 2.0f);
        textY = bounds.exactCenterY() + (textBounds.height() / 2.0f);
        textY -=  5.0f;

        canvas.drawText(text3, textX, textY, timePaint);

        timePaint.getTextBounds(text4, 0, text4.length(), textBounds);
        timePaint.setColor(Color.parseColor("#2196F3"));
        timePaint.setTextSize(context.getResources().getDimension(R.dimen.medium_size));
        timePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));

        textX = bounds.exactCenterX() - (timePaint.measureText(text4) / 2.0f);
        textY = bounds.exactCenterY() + (textBounds.height() / 2.0f);
        textY += 40.0f;

        canvas.drawText(text4, textX, textY, timePaint);

        timePaint.getTextBounds(text5, 0, text5.length(), textBounds);
        timePaint.setColor(Color.parseColor("#FFFFFF"));
        timePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        timePaint.setTextSize(context.getResources().getDimension(R.dimen.small_size));

        textX = bounds.exactCenterX() - (timePaint.measureText(text5) / 2.0f);
        textY = bounds.exactCenterY() + (textBounds.height() / 2.0f);
        textY += 70.0f;

        canvas.drawText(text5, textX, textY, timePaint);
    }

    public void setAntiAlias(boolean antiAlias) {
        timePaint.setAntiAlias(antiAlias);
        circlePaint.setAntiAlias(antiAlias);
    }

    public void setColor(int color) {
        timePaint.setColor(color);
        circlePaint.setColor(color);
    }

    public void setShowSeconds(boolean showSeconds) {
        shouldShowSeconds = showSeconds;
    }
}
