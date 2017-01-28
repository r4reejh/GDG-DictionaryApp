package com.test.reejh.gradletest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {


    android.support.v7.widget.CardView card;
    android.support.design.widget.TextInputEditText input_word;
    android.support.design.widget.TextInputLayout textinputlayout;
    RadioButton synonym;
    RadioButton antonym;
    RadioGroup group;
    String option="";
    Toast toast;
    TextView t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Realm.init(this);

        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder().name("SynonymAntonym.realm").build();
        Realm.setDefaultConfiguration(realmConfiguration);


        t1=(TextView)findViewById(R.id.textView2);
        t2=(TextView)findViewById(R.id.textView3);
        b1=(Button)findViewById(R.id.button);
        synonym=(RadioButton)findViewById(R.id.synonym);
        antonym=(RadioButton)findViewById(R.id.antonym);
        group=(RadioGroup)findViewById(R.id.group);
        input_word=(android.support.design.widget.TextInputEditText)findViewById(R.id.input_word);
        textinputlayout=(android.support.design.widget.TextInputLayout)findViewById(R.id.textinputlayout);

        Realm realm=Realm.getDefaultInstance();

        if(realm.isEmpty()) {
            insertData();
        }

        input_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //need to be present, abstract class
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0 &&((s.toString()).charAt(s.length()-1))=='\n') {
                    String st=input_word.getText().toString();
                    st=st.substring(0,st.length()-1);
                    input_word.setText(st);
                    input_word.clearFocus();
                    textinputlayout.clearFocus();

                    InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //need to be present, abstract class

            }
        });

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        input_word.clearFocus();
                        textinputlayout.clearFocus();
                        if (checkedId == R.id.synonym) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm bgRealm) {
                                    String query = input_word.getText().toString();
                                    try {
                                        Word res = bgRealm.where(Word.class).equalTo("word", query).findFirst();
                                        if (res.getWord() != null) {
                                            RealmList<stringRealm> res_syn = res.getSynonyms();
                                            String syn_out = "";
                                            for (stringRealm c : res_syn) {
                                                syn_out += c.getEntry() + "\n";
                                            }
                                            makeToast(syn_out);
                                        } else {
                                            makeToast("No Data");
                                        }
                                    } catch (Exception e) {
                                        makeToast("No Data");
                                    }

                                }
                            });
                        } else if (checkedId == R.id.antonym) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm bgRealm) {
                                    String query = input_word.getText().toString();
                                    try {
                                        Word res = bgRealm.where(Word.class).equalTo("word", query).findFirst();
                                        if (res.getWord() != null) {
                                            RealmList<stringRealm> res_ant = res.getAntonyms();
                                            String ant_out = "";
                                            for (stringRealm c : res_ant) {
                                                ant_out += c.getEntry() + "\n";
                                            }
                                            makeToast(ant_out);
                                        } else {
                                            makeToast("No Data");
                                        }
                                    } catch (Exception e) {
                                        makeToast("No Data");
                                    }

                                }
                            });
                        }
                    }
                });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_word.clearFocus();
                textinputlayout.clearFocus();
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        if(toast!=null){
                            toast.cancel();
                        }
                        String query = input_word.getText().toString();
                        try {
                            Word res = bgRealm.where(Word.class).equalTo("word", query).findFirst();
                            if (res.getWord() == "null") {
                                makeToast("No Data");
                            } else {
                                RealmList<stringRealm> res_syn = res.getSynonyms();
                                RealmList<stringRealm> res_ant = res.getAntonyms();
                                String syn_out = "", ant_out = "";
                                for (stringRealm c : res_syn) {
                                    syn_out += c.getEntry() + "\n";
                                }
                                for (stringRealm c : res_ant) {
                                    ant_out += c.getEntry() + "\n";
                                }
                                syn_out = "SYNONYMS\n\n" + syn_out + "\n";
                                ant_out = "ANTONYMS\n\n" + ant_out + "\n";
                                t1.setText(syn_out.trim());
                                t2.setText(ant_out.trim());
                            }
                        }
                       catch (Exception e){
                           makeToast("No Data");
                       }
                    }
                });
            }
        });
    }
    public void makeToast(String s){
        input_word.clearFocus();
        textinputlayout.clearFocus();
        if(toast!=null){
            toast.cancel();
        }
        Context context = getApplicationContext();
        CharSequence text = s.trim();
        int duration = Toast.LENGTH_SHORT;

        toast= Toast.makeText(context, text, duration);
        toast.show();
    }

    public void insertData(){
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream d = getResources().openRawResource(R.raw.data);
                try {
                    realm.createAllFromJson(Word.class, d);
                }
                catch (Exception e){
                    realm.cancelTransaction();
                }
            }
        });
    }

}
