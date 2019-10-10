package com.pedrovs.book;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookViewModel bookViewModel;
    private RecyclerView recyclerView;
    public static final int ADD_BOOK_REQUEST = 1;
    public static final int EDIT_BOOK_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddBook = findViewById(R.id.btn_add_book);
        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditBookActivty.class);
                startActivityForResult(intent, ADD_BOOK_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));

        final BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                //update recyclerview
                adapter.setBooks(books);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                bookViewModel.delete(adapter.getBookPos(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(MainActivity.this, AddEditBookActivty.class);
                intent.putExtra(AddEditBookActivty.EXTRA_ID , book.getId());
                intent.putExtra(AddEditBookActivty.EXTRA_NAME_BOOK, book.getBookName());
                intent.putExtra(AddEditBookActivty.EXTRA_AUTHOR_BOOK, book.getAuthor());
                intent.putExtra(AddEditBookActivty.EXTRA_TYPE_BOOK, book.getBookType());
                startActivityForResult(intent,EDIT_BOOK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {
            String nameBook = data.getStringExtra(AddEditBookActivty.EXTRA_NAME_BOOK);
            String authorBook = data.getStringExtra(AddEditBookActivty.EXTRA_AUTHOR_BOOK);
            String bookType = data.getStringExtra(AddEditBookActivty.EXTRA_TYPE_BOOK);

            Book book = new Book(nameBook, authorBook, bookType);
            bookViewModel.insert(book);

            Toast.makeText(getApplicationContext(), "Book Saved!", Toast.LENGTH_LONG).show();

        }
        else if  (requestCode == EDIT_BOOK_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditBookActivty.EXTRA_ID , -1);

            if (id == -1){
                Toast.makeText(getApplicationContext(),"Note cant be updated" ,Toast.LENGTH_LONG).show();
                return;
            }

            String nameBook = data.getStringExtra(AddEditBookActivty.EXTRA_NAME_BOOK);
            String authorBook = data.getStringExtra(AddEditBookActivty.EXTRA_AUTHOR_BOOK);
            String priceBook = data.getStringExtra(AddEditBookActivty.EXTRA_TYPE_BOOK);

            Book book = new Book(nameBook,authorBook,priceBook);
            book.setId(id);
            bookViewModel.update(book);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all:
                bookViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
