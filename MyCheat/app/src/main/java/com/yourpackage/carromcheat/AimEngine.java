package com.yourpackage.carromcheat; 
import android.content.Context; 
import android.graphics.Bitmap; 
import android.graphics.Point; 
import android.graphics.Canvas; 
import android.graphics.Paint; 
import android.graphics.Path; 
public class AimEngine { 
    private Context ctx; private TouchHelper th; private int sw, sh; 
    public AimEngine(Context c) { ctx = c; th = new TouchHelper(ctx); sw = ctx.getResources().getDisplayMetrics().widthPixels; sh = ctx.getResources().getDisplayMetrics().heightPixels; } 
    public void executeAutoShot(int p) { Bitmap b = captureScreen(); Point striker = findStriker(b); Point pocket = findBestPocket(b); float angle = computeAngle(striker, pocket); int power = computePower(striker, pocket, p); th.performSwipe(striker.x, striker.y, angle, power); } 
    private Bitmap captureScreen() { return Bitmap.createBitmap(sw, sh, Bitmap.Config.ARGB_8888); } 
    private Point findStriker(Bitmap b) { return new Point(sw/2, sh*3/4); } 
    private Point findBestPocket(Bitmap b) { return new Point(sw/8, sh/8); } 
    private float computeAngle(Point f, Point t) { return (float)Math.toDegrees(Math.atan2(t.y-f.y, t.x-f.x)); } 
    private int computePower(Point f, Point t, int ub) { double d = Math.hypot(t.x-f.x, t.y-f.y); return (int)Math.min(100, (d/15)+ub); } 
} 
