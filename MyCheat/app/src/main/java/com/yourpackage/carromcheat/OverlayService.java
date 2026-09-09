package com.yourpackage.carromcheat; 
import android.app.Service; 
import android.content.Context; 
import android.content.Intent; 
import android.graphics.Color; 
import android.graphics.PixelFormat; 
import android.os.Build; 
import android.os.IBinder; 
import android.view.Gravity; 
import android.view.LayoutInflater; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.WindowManager; 
import android.widget.Button; 
import android.widget.SeekBar; 
import android.widget.TextView; 
public class OverlayService extends Service { 
    private WindowManager wm; private View overlayView; private SeekBar powerSeek; 
    private Button autoShoot, togglePath; private TextView status; private float dX, dY; 
    @Override public IBinder onBind(Intent i) { return null; } 
    @Override public void onCreate() { 
        super.onCreate(); wm = (WindowManager) getSystemService(WINDOW_SERVICE); 
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE); 
        overlayView = inflater.inflate(R.layout.overlay_layout, null); 
        status = overlayView.findViewById(R.id.tv_status); 
        powerSeek = overlayView.findViewById(R.id.power_seek); 
        autoShoot = overlayView.findViewById(R.id.btn_auto_shoot); 
        togglePath = overlayView.findViewById(R.id.btn_toggle_path); 
        overlayView.setOnTouchListener((v, event) -> { 
            switch (event.getAction()) { 
                case MotionEvent.ACTION_DOWN: dX = v.getX() - event.getRawX(); dY = v.getY() - event.getRawY(); break; 
                case MotionEvent.ACTION_MOVE: v.setX(event.getRawX() + dX); v.setY(event.getRawY() + dY); break; 
            } return true; 
        }); 
        autoShoot.setOnClickListener(v -> { status.setText("Auto-shooting..."); new AimEngine(getApplicationContext()).executeAutoShot(powerSeek.getProgress()); }); 
        togglePath.setOnClickListener(v -> { status.setText("Path toggled"); }); 
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE; 
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT); 
        params.gravity = Gravity.TOP | Gravity.START; params.x = 100; params.y = 200; 
        wm.addView(overlayView, params); 
    } 
    @Override public void onDestroy() { if (overlayView != null) wm.removeView(overlayView); super.onDestroy(); } 
} 
