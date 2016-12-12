package jain.samkit.motivationwatch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

import java.util.concurrent.TimeUnit;

public class WatchFaceService extends CanvasWatchFaceService {
    private static final long TICK_PERIOD_MILLIS = TimeUnit.SECONDS.toMillis(1);

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {
        private Handler timeTick;
        private WatchFace watchFace;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            setWatchFaceStyle(new WatchFaceStyle.Builder(WatchFaceService.this).setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT).setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE).setShowSystemUiTime(false).build());

            timeTick = new Handler(Looper.myLooper());
            startTimerIfNecessary();

            watchFace = WatchFace.newInstance(WatchFaceService.this);
        }

        private void startTimerIfNecessary() {
            timeTick.removeCallbacks(timeRunnable);

            if(isVisible() && !isInAmbientMode()) {
                timeTick.post(timeRunnable);
            }
        }

        private final Runnable timeRunnable = new Runnable() {
            @Override
            public void run() {
                onSecondTick();

                if(isVisible() && !isInAmbientMode()) {
                    timeTick.postDelayed(this, TICK_PERIOD_MILLIS);
                }
            }
        };

        private void onSecondTick() {
            invalidateIfNecessary();
        }

        private void invalidateIfNecessary() {
            if(isVisible() && !isInAmbientMode()) {
                invalidate();
            }
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();

            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            watchFace.setAntiAlias(!inAmbientMode);
            watchFace.setColor(inAmbientMode ? Color.GRAY : Color.WHITE);
            watchFace.setShowSeconds(!isInAmbientMode());
            invalidate();

            startTimerIfNecessary();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            watchFace.draw(canvas, bounds);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            startTimerIfNecessary();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onDestroy() {
            timeTick.removeCallbacks(timeRunnable);
            super.onDestroy();
        }
    }
}
