package pollub.ism.lab4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] przyciski = new Button[3][3];
    private boolean ruchGracz1 = true;
    private int licznikRund;
    private int punktyGracz1;
    private int punktyGracz2;
    private TextView textViewGracz1;
    private TextView textViewGracz2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewGracz1 = findViewById(R.id.text_view_g1);
        textViewGracz2 = findViewById(R.id.text_view_g2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                przyciski[i][j] = findViewById(resID);
                przyciski[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               resetGry();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (ruchGracz1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        licznikRund++;
        if (sprawdzWygrana()) {
            if (ruchGracz1) {
                gracz1Wygrywa();
            } else {
                gracz2Wygrywa();
            }
        } else if (licznikRund == 9) {
            remis();
        } else {
            ruchGracz1 = !ruchGracz1;
        }
    }
    private boolean sprawdzWygrana() {
        String[][] pole = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pole[i][j] = przyciski[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (pole[i][0].equals(pole[i][1])
                    && pole[i][0].equals(pole[i][2])
                    && !pole[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (pole[0][i].equals(pole[1][i])
                    && pole[0][i].equals(pole[2][i])
                    && !pole[0][i].equals("")) {
                return true;
            }
        }
        if (pole[0][0].equals(pole[1][1])
                && pole[0][0].equals(pole[2][2])
                && !pole[0][0].equals("")) {
            return true;
        }
        if (pole[0][2].equals(pole[1][1])
                && pole[0][2].equals(pole[2][0])
                && !pole[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void gracz1Wygrywa() {
        punktyGracz1++;
        Toast.makeText(this, "Wygrywa gracz 1!", Toast.LENGTH_SHORT).show();
        aktualizujPunkty();
        wyczyscPlansze();
    }
    private void gracz2Wygrywa() {
        punktyGracz2++;
        Toast.makeText(this, "Wygrywa gracz 2!", Toast.LENGTH_SHORT).show();
        aktualizujPunkty();
        wyczyscPlansze();
    }
    private void remis() {
        Toast.makeText(this, "Remis!", Toast.LENGTH_SHORT).show();
        wyczyscPlansze();
    }
    private void aktualizujPunkty() {
        textViewGracz1.setText("Gracz 1: " + punktyGracz1);
        textViewGracz2.setText("Gracz 2: " + punktyGracz2);
    }
    private void wyczyscPlansze() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                przyciski[i][j].setText("");
            }
        }
        licznikRund = 0;
        ruchGracz1 = true;
    }
    private void resetGry() {
        punktyGracz1 = 0;
        punktyGracz2 = 0;
        aktualizujPunkty();
        wyczyscPlansze();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("licznikRund", licznikRund);
        outState.putInt("punktyGracz1", punktyGracz1);
        outState.putInt("punktyGracz2", punktyGracz2);
        outState.putBoolean("ruchGracz1", ruchGracz1);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        licznikRund = savedInstanceState.getInt("licznikRund");
        punktyGracz1 = savedInstanceState.getInt("punktyGracz1");
        punktyGracz2 = savedInstanceState.getInt("punktyGracz2");
        ruchGracz1 = savedInstanceState.getBoolean("ruchGracz1");
    }

}