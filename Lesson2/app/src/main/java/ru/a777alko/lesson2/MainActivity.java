package ru.a777alko.lesson2;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Disposable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        d = RxTextView.textChanges(editText)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        textView.setText(charSequence);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (d != null) {
            d.dispose();
        }
    }
}
