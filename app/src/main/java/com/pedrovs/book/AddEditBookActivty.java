package com.pedrovs.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddEditBookActivty extends AppCompatActivity {

    private EditText editTextBookName;
    private EditText editTextAuthorName;
    private String typeBook;
    public static final String EXTRA_ID = "book_id";
    public static final String EXTRA_NAME_BOOK = "book_name";
    public static final String EXTRA_AUTHOR_BOOK = "book_author";
    public static String EXTRA_TYPE_BOOK = "book_price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_activty);

        editTextBookName = findViewById(R.id.book_name_et);
        editTextAuthorName = findViewById(R.id.book_author_et);

        RadioGroup rg = findViewById(R.id.myRadioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.romance:
                        typeBook = "Romance";
                        break;
                    case R.id.mistery:
                        typeBook = "Mystery ";
                        break;
                    case R.id.religious:
                        typeBook = "Religious";
                        break;
                    case R.id.fantasy:
                        typeBook = "Fantasy";
                        break;
                    case R.id.educational:
                        typeBook = "Educational";
                        break;
                    case R.id.other:
                        typeBook = "Other";
                        break;

                }

            }
        });


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Book");
            editTextBookName.setText(intent.getStringExtra(EXTRA_NAME_BOOK));
            editTextAuthorName.setText(intent.getStringExtra(EXTRA_AUTHOR_BOOK));
        } else {
            setTitle("Add Book");
        }


    }


    private void saveBook() {
        String bookName = editTextBookName.getText().toString();
        String bookAuthor = editTextAuthorName.getText().toString();

        if (bookName.trim().isEmpty() || bookAuthor.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please insert Book/Author name", Toast.LENGTH_LONG).show();
            return;
        }
        if (typeBook == null) {
            Toast.makeText(getApplicationContext(), "Insert the Book type.", Toast.LENGTH_LONG).show();
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME_BOOK, bookName);
            data.putExtra(EXTRA_AUTHOR_BOOK, bookAuthor);
            data.putExtra(EXTRA_TYPE_BOOK, typeBook);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_book:
                saveBook();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


