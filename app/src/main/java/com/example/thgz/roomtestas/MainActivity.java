
package com.example.thgz.roomtestas;

        import android.arch.lifecycle.Observer;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Intent;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import android.content.ClipData;
        import android.content.ClipboardManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.text.TextUtils;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;


        import static com.example.thgz.roomtestas.Passgen.NEW_WORD_ACTIVITY_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =  "com.example.thgz.testgen-master.REPLY";



    private EditText mEditWordView;

    private WordViewModel mWordViewModel;
    @BindView(R.id.passwordTv)
     EditText passwordTv;
    @BindView(R.id.passwordlengthtv)
    TextView passwordlengthtv;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.button)
    Button button;
    Button button2;
    ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passgen);
        mEditWordView = findViewById(R.id.passwordTv);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
       // recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });


        Button btn = (Button)findViewById(R.id.database);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Passgen.class));
            }
        });

        Button btn1 = (Button)findViewById(R.id.savetodb);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = passwordTv.getText().toString();
                if (!text.equals("")) {
                    Word word = new Word(passwordTv.getText().toString());
                    mWordViewModel.insert(word);
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.saved,
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();
                }


              //  startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        button2 = findViewById(R.id.button2);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = passwordTv.getText().toString();
                if (!text.equals("")) {
                    ClipData clipData = ClipData.newPlainText("text", text);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ButterKnife.bind(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passwordlengthtv.setText("Length:" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @OnClick(R.id.button)
    public void generate (View v) {
        String password = passwordgenerator.process(seekBar.getProgress());
        passwordTv.setText(password);
    }

}