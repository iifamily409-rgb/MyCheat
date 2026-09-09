package com.yourpackage.carromcheat; 
import android.accessibilityservice.AccessibilityService; 
import android.accessibilityservice.GestureDescription; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Build; 
import android.view.accessibility.AccessibilityEvent; 
public class TouchHelper extends AccessibilityService { 
    private static TouchHelper instance; 
    public static void init(Context c) { c.startService(new Intent(c, TouchHelper.class)); } 
    @Override public void onAccessibilityEvent(AccessibilityEvent e) {} 
    @Override public void onInterrupt() {} 
    @Override public void onCreate() { super.onCreate(); instance = this; } 
    public static void performSwipe(float sx, float sy, float ang, int p) { 
        if(instance == null) return; 
        double rad = Math.toRadians(ang); 
        float ex = sx + (float)(p * 12 * Math.cos(rad)); 
        float ey = sy + (float)(p * 12 * Math.sin(rad)); 
        GestureDescription.Builder builder = new GestureDescription.Builder(); 
        builder.addStroke(new GestureDescription.StrokeDescription(new android.graphics.Path(){{ moveTo(sx, sy); lineTo(ex, ey); }}, 0, 300)); 
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) instance.dispatchGesture(builder.build(), null, null); 
    } 
} 
