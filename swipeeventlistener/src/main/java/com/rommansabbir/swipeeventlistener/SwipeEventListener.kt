package com.rommansabbir.swipeeventlistener

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class SwipeEventListener : View.OnTouchListener {
    private var context: Context ? = null
    private var gestureDetector: GestureDetector? = null
    private var callback : SwipeEventCallback? = null
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        callback
        return gestureDetector!!.onTouchEvent(event)
    }

    /**
     * Set component
     * @param context [Context]
     * @param callback [SwipeEventCallback]
     */

    fun setComponent(context: Context, callback: SwipeEventCallback){
        this.context = context
        this.callback = callback
        gestureDetector = GestureDetector(context, GestureListener())
    }

    /**
     * This class handle the motion event stuff
     */
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            /**
                             * Notify the interface on right swipe
                             */
                            callback!!.onSwipeRight()
                        } else {
                            /**
                             * Notify the interface on left swipe
                             */
                            callback!!.onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        /**
                         * Notify the interface on bottom swipe
                         */
                        callback!!.onSwipeBottom()
                    } else {
                        /**
                         * Notify the interface on top swipe
                         */
                        callback!!.onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

}
