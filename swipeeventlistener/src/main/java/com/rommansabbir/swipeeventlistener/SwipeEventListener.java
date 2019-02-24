package com.rommansabbir.swipeeventlistener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeEventListener implements View.OnTouchListener {
    private static final String TAG = "SwipeEventListener";
    private Context context;
    private final GestureDetector gestureDetector;
    private SwipeEventListenerInterface touchListenerInterface;

    /**
     * Get context from parent activity
     * Instantiate GestureDetector
     * Instantiate SwipeEventListener interface
     * @param context
     */
    public SwipeEventListener(Context context){
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
        touchListenerInterface = (SwipeEventListenerInterface) context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * This class handle the motion event stuff
     */
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            /**
                             * Notify the interface on right swipe
                             */
                            touchListenerInterface.onSwipeRight();
                        } else {
                            /**
                             * Notify the interface on left swipe
                             */
                            touchListenerInterface.onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        /**
                         * Notify the interface on bottom swipe
                         */
                        touchListenerInterface.onSwipeBottom();
                    } else {
                        /**
                         * Notify the interface on top swipe
                         */
                        touchListenerInterface.onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public interface SwipeEventListenerInterface {
        void onSwipeRight();
        void onSwipeLeft();
        void onSwipeTop();
        void onSwipeBottom();
    }
}
