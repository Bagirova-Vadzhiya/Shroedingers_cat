package com.example.shroedingers_cat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView coordinatesOut;
    private float x;
    private float y;
    private String sDown;
    private String sMove;
    private String sUp;

    // задание дополнительных полей координат кота Шрёдингера
    private final float X_CAT = 500; // задание поля для координаты X
    private final float Y_CAT = 500; // задание поля для координаты Y
    private final float DELTA_CAT = 50; // допустимая погрешность в нахождении кота
    private final float DISTANCE_X = 10;
    private final float DISTANCE_Y = 85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatesOut = findViewById(R.id.coordinatesOut);

        // выполнение действий при касании экрана
        coordinatesOut.setOnTouchListener(listener);
    }
    // объект обработки касания экрана
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) { // в motionEvent пишутся координаты
             x = motionEvent.getX();
             y = motionEvent.getY();
             switch (motionEvent.getAction()) { // метод getAction() считывает состояние касания
                 case MotionEvent.ACTION_DOWN: // нажатие
                     sDown = "Нажатие: координата X = " + x + ", координата y = " + y;
                     sMove = "";
                     sUp = "";
                     break;
                 case MotionEvent.ACTION_MOVE: // движение
                     sMove = "Движение: координата x = " + x + ", координата y = " + y;
                     // задание условия нахождения кота Шрёдингера
                     if (x < (X_CAT + DELTA_CAT) && x > (X_CAT - DELTA_CAT)  && y < (Y_CAT + DELTA_CAT) && y > (Y_CAT - DELTA_CAT)) {
                         Toast toast = Toast.makeText(getApplicationContext(), R.string.successful_search, Toast.LENGTH_LONG);
                         toast.setGravity(Gravity.CENTER, (int) DISTANCE_X, (int) DISTANCE_Y);
                         LinearLayout toastContainer = (LinearLayout) toast.getView();
                         ImageView image = new ImageView(getApplicationContext());
                         image.setImageResource(R.drawable.image_foreground);
                         toastContainer.addView(image, 1);
                         toast.show();
                     }
                     break;
                 case MotionEvent.ACTION_UP: // отпускание
                 case MotionEvent.ACTION_CANCEL: // внутрений сбой (аналогичный ACTION_UP)
                     sMove = "";
                     sUp = "Отпускание: координата X = " + x + ", координата Y = " + y;
                     break;
             }

             // вывод на экран в три строки считанных значений координат
            coordinatesOut.setText(sDown + "\n" + sMove + "\n" + sUp);

            return true; // подтвеждение нашей обработки событий
        }
    };
}