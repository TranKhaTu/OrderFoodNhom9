package com.example.doanorderfood.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.doanorderfood.MainActivity;
import com.example.doanorderfood.R;
import com.example.doanorderfood.util.Const;
import com.example.doanorderfood.util.SharePreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkInternet();
    }

    private void checkInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        if (!is3g && !isWifi) {
            dialog();
            return;
        } else {
            countDownSwitch();
        }
    }

    private void dialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // khởi tạo dialog
        alertDialogBuilder.setMessage("Vui lòng kiểm tra kết nối mạng của bạn.");
        // thiết lập nội dung cho dialog
        alertDialogBuilder.setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                checkInternet();
            }
        });

        alertDialogBuilder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // tạo dialog
        alertDialog.show();
        // hiển thị dialog
    }


    // đếm ngược thời gian để chuyển màn hình
    private void countDownSwitch() {
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                String email = SharePreferenceUtils.getStringData(SplashActivity.this, Const.KEY_NAME);
                if (email.isEmpty() || email.equals("")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // trong anim ta tạo hai file rồi gọi ở đây để tạo animation khi chuyển activity
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                } else if (email.contains("nv_") || email.contains("nv")) {
                    Intent intent = new Intent(SplashActivity.this, WaiterActivity.class);
                    startActivity(intent);
                    // trong anim ta tạo hai file rồi gọi ở đây để tạo animation khi chuyển activity
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                } else if (email.contains("daubep") || email.contains("daubep_")) {
                    Intent intent = new Intent(SplashActivity.this, CookerActivity.class);
                    startActivity(intent);
                    // trong anim ta tạo hai file rồi gọi ở đây để tạo animation khi chuyển activity
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                } else {
                    Intent intent = new Intent(SplashActivity.this, ManagerMainActivity.class);
                    startActivity(intent);
                    // trong anim ta tạo hai file rồi gọi ở đây để tạo animation khi chuyển activity
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }

            }
        }.start();
    }
}