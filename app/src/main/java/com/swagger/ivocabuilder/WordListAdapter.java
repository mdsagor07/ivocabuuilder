package com.swagger.ivocabuilder;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements Filterable {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Data data);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordText;
        public int mPosition;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_item);

        }

        public void setData(String note, int position) {
            wordText.setText(note);
            mPosition = position;
        }
    }

    private final LayoutInflater mInflater;
    Context mContext;
    private OnDeleteClickListener onDeleteClickListener;
    private List<Data> mWords; // Cached copy of words
    private List<Data> mWordsfull; // Cached copy of words

    WordListAdapter(Context context, OnDeleteClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_items, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, final int position) {

        final String word;
        final String meaning;
        final String explanation;
        if (mWords != null) {
            Data current = mWords.get(position);

            word = current.getWord();
            meaning = current.getMeaning();
            explanation = current.getExplanation();

            holder.setData(word, position);
        //    holder.word_meaing.setText(current.getMeaning());
            //  holder.wordText.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordText.setText("No Word");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Data current = mWords.get(position);
                                                   Intent intent = new Intent(view.getContext(), InfoActivity.class);
                                                   intent.putExtra("word", current.getWord());
                                                   intent.putExtra("meaning", current.getMeaning());
                                                   intent.putExtra("exp", current.getExplanation());
                                                   intent.putExtra("date", current.getDate());
                                                   view.getContext().startActivity(intent);
                                               }
                                           }
        );

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final int word_id = mWords.get(position).getId();

                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(),view);
                //inflating menu from xml resource
                popup.inflate(R.menu.onclick);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                             Intent intent = new Intent(view.getContext(),EditActivity.class);
                             intent.putExtra("word_id",word_id);
                              view.getContext().startActivity(intent);
                                return true;
                            case R.id.delete:
                               openDialog(view, position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

                return false;
            }
        });

    }

    void setWords(List<Data> words) {
        mWords = words;
        mWordsfull = new ArrayList<>(words);
        notifyDataSetChanged();
    }

//    List<Data> getmWords() {
//        return mWords;
//    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        return 0;
    }

    @Override
    public Filter getFilter(){
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Data> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mWordsfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Data item : mWordsfull) {
                    if (item.getWord().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mWords.clear();
            mWords.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    private void openDialog(View view, final int positionint) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Do you want to Delete This Word");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (onDeleteClickListener != null) {
                            onDeleteClickListener.OnDeleteClickListener(mWords.get(positionint));
                        }

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
