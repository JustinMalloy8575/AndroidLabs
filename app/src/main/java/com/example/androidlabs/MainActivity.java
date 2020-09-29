package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);
        final Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.ToastMessage);
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        });

        final Switch s = findViewById(R.id.on_off_switch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton cb, final boolean b) {

                if (b) {
                    Snackbar snackon = Snackbar.make(s, "The switch is now on", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cb.setChecked(!b);
                        }
                    });
                    snackon.show();

                } else {

                    Snackbar snackoff = Snackbar.make(s, "The switch is now off", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cb.setChecked(!b);
                        }
                    });
                    snackoff.show();

                }

            }
        });
    }
}