package ru.dvsoshnikov.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView  helloTv;
    private Timer     mTimer;
    private TimerTask mMyTimerTask;
    private Button  helloBtn;
    private Button  helloBtn1;
    private Button  helloBtn2;

    public MainActivity() throws UnknownHostException {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloBtn = findViewById(R.id.button);
        helloBtn1 = findViewById(R.id.button3);
        helloBtn2 = findViewById(R.id.button2);
        helloBtn.setOnClickListener(onClickListener);
        helloBtn1.setOnClickListener(onClickListener);
        helloBtn2.setOnClickListener(onClickListener);

    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    int Payment=100;

                    //Контрольный вопрос "Оплата столько-то рублей. Да-Нет."
                    //  процесс оплаты
                    // если оплата прошла, тогда

                    Toast toast = Toast.makeText(getApplicationContext(), "Оплачено: "+ Payment+" рублей",
                                Toast.LENGTH_SHORT);
                        toast.show();

                    //Иначе
                        break;

                case R.id.button2:
                        Payment=200;

                    //Контрольный вопрос "Оплата столько-то рублей. Да-Нет."
                    //  процесс оплаты
                    // если оплата прошла, тогда

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Оплачено: "+ Payment+" рублей",
                                Toast.LENGTH_SHORT);
                        toast1.show();

                    //Иначе
                    break;

                case R.id.button3:
                        Payment=300;

                    //Контрольный вопрос "Оплата столько-то рублей. Да-Нет."
                    //  процесс оплаты
                    // если оплата прошла, тогда

                        Toast toast2 = Toast.makeText(getApplicationContext(), "Оплачено: "+ Payment+" рублей",
                                Toast.LENGTH_SHORT);
                        toast2.show();

                    //Иначе
                    break;
            }
        }
    };
}