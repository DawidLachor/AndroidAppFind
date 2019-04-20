package com.example.skorp.find;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button past;
    private Button find;
    private Button reset;
    private EditText text;
    private TextView textView;
    private int ile;
    private String orginalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dekralacja GUI
        past = findViewById(R.id.past);
        find = findViewById(R.id.find);
        reset = findViewById(R.id.reset);
        text = findViewById(R.id.texte);
        textView = findViewById(R.id.textView);


        //Wklejanie
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obsługa wklejania
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                text.setText(item.getText().toString());
                orginalText = text.getText().toString();
            }
        });


        //Szukanie
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ile > 0){
                    text.setText(orginalText);
                }

                ile = 0;

                //Tworzymy alert by zapytać o wyraz
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final EditText input = new EditText(MainActivity.this);//Tworzenie EditText
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                builder.setView(input);
                builder.setMessage("Find");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        //Wyszukiwanie słowa

                        SpannableString spannable = new SpannableString(orginalText);
                        String perhaps = input.getText().toString();
                        int ile = 0;
                        int index = orginalText.indexOf(perhaps);
                        int perhapsLenght = perhaps.length();

                        while (index >= 0) {
                            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index + perhapsLenght, 0);
                            index = orginalText.indexOf(perhaps, index + perhapsLenght);
                            ile++;
                        }
                        textView.setText("Find " + ile + " words");
                        text.setText(spannable);

//                        Pattern pattern = Pattern.compile(input.getText().toString());
//                        Matcher matcher = pattern.matcher(text.getText().toString());
//                        StringBuffer stringBuffer = new StringBuffer();
//
//                        input.setHighlightColor(Color.YELLOW);
//                        String change = input.getText().toString();
//
//                        boolean found = matcher.find();
//                        while (found){
//                            matcher.appendReplacement(stringBuffer, change.toUpperCase());
//                            found = matcher.find();
//                            ile++;
//                        }
//
//                        matcher.appendTail(stringBuffer);
//                        text.setText(stringBuffer.toString());
//                        textView.setText("Find " + ile + " words");

//                        //Alert czy znalazło słowo
//                        if (matcher.find()){
//                            alert("Find");
//                        } else {
//                            alert("No find");
//                        }
//                    }
                    }
                    }
                    ).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        //Reset programu
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
                textView.setText("");
            }
        });
    }

    //Alert by pokazywał czy znalazł słowo
    private void alert(String t){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setMessage(t).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = b.create();
        dialog.show();
    }
}
