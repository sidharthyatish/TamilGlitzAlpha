package com.example.sidharthyatish.tamilglitzalpha;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Sidharth Yatish on 11-12-2015.
 * No pagination
 */

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of superHeroes
    List<Article> articles;

    public CardAdapter(List<Article> articles, Context context) {
        super();
        //Getting all the superheroes
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_view, parent, false);
        return new ViewHolder(v);


    }



    @Override
    public int getItemViewType(int position) {
        return articles.get(position) == null ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Article article = articles.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(article.getThumbUrl(), ImageLoader.getImageListener(holder.thumbView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.thumbView.setImageUrl(article.getThumbUrl(), imageLoader);
        holder.textViewTitle.setText(Html.fromHtml(article.getTitle()));

        holder.textViewDate.setText(article.getDate());
        holder.thumbView.setTag(holder);
        holder.currentArticle = article;


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NetworkImageView thumbView;
        public TextView textViewTitle;
        public TextView textViewDate;
        public CardView cardView;
        public Article currentArticle;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbView = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            textViewTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.textDate);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //  Toast.makeText(context,articles.get(getAdapterPosition()).getContent(),Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putString("content", articles.get(getAdapterPosition()).getContent());
            Intent intent = new Intent(context, ReaderActivity.class);
            intent.putExtra("content", articles.get(getAdapterPosition()).getContent());
            //((Activity) context).startActivity(intent);
            context.startActivity(intent);
        }
    }
}

