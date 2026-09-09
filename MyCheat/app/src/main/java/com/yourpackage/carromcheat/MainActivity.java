package com.yourpackage.carromcheat; 
import android.app.Activity; 
import android.content.Intent; 
import android.net.Uri; 
import android.os.Build; 
import android.os.Bundle; 
import android.provider.Settings; 
import android.widget.Button; 
import android.widget.Toast; 
import androidx.appcompat.app.AppCompatActivity; 
public class MainActivity extends AppCompatActivity { 
    private static final int OVERLAY_PERMISSION_REQ = 100; 
    private static final int ACCESSIBILITY_REQ = 101; 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
        Button btnOverlay = findViewById(R.id.btn_overlay); 
        Button btnAccess = findViewById(R.id.btn_access); 
        btnOverlay.setOnClickListener(v -> { 
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { 
                if (!Settings.canDrawOverlays(this)) { 
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, 
                            Uri.parse("package:" + getPackageName())); 
                    startActivityForResult(intent, OVERLAY_PERMISSION_REQ); 
                } else { startOverlayService(); } 
            } else { startOverlayService(); } 
        }); 
        btnAccess.setOnClickListener(v -> { 
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS); 
            startActivityForResult(intent, ACCESSIBILITY_REQ); 
        }); 
    } 
    private void startOverlayService() { 
        Intent intent = new Intent(this, OverlayService.class); 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(intent); else startService(intent); 
        Toast.makeText(this, "Overlay started! Open Carrom now.", Toast.LENGTH_SHORT).show(); 
    } 
} 
