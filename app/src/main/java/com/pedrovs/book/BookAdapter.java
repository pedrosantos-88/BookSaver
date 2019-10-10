package com.pedrovs.book;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private List<Book> books = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_item, viewGroup, false);
        return new BookHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int position) {
        Book curremtBook = books.get(position);

        bookHolder.textViewbookTitle.setText(curremtBook.getBookName());
        bookHolder.textViewbookAuthor.setText(curremtBook.getAuthor());
        bookHolder.textViewType.setText(String.valueOf(curremtBook.getBookType()));

    }

    public Book getBookPos(int position) {
        return books.get(position);
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private TextView textViewbookTitle;
        private TextView textViewbookAuthor;
        private TextView textViewType;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewbookTitle = itemView.findViewById(R.id.text_view_book_title);
            textViewbookAuthor = itemView.findViewById(R.id.text_view_author);
            textViewType = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(books.get(pos));
                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

