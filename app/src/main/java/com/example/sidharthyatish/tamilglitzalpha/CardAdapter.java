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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Sidharth Yatish on 11-12-2015.
 * No pagination
 */

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    int ITEM_VIEW_TYPE_BASIC=0,ITEM_VIEW_TYPE_FOOTER=1;
    //List of superHeroes
    List<Article> articles;

    public CardAdapter(List<Article> articles, Context context) {
        super();
        //Getting all the superheroes
        this.articles = articles;
        this.context = context;
    }

    public int getItemViewType(int position){
        if(articles.get(position)==null) return ITEM_VIEW_TYPE_FOOTER;
        else return  ITEM_VIEW_TYPE_BASIC;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_VIEW_TYPE_BASIC) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_card_view, parent, false);
            return new ArticleViewHolder(v);

        }
        else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar_item, parent, false);
            return new ProgressViewHolder(v);

        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArticleViewHolder) {
            Article article = articles.get(position);
            ArticleViewHolder aHolder= (ArticleViewHolder) holder;
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(article.getThumbUrl(), ImageLoader.getImageListener(aHolder.thumbView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

            aHolder.thumbView.setImageUrl(article.getThumbUrl(), imageLoader);
            aHolder.textViewTitle.setText(Html.fromHtml(article.getTitle()));

            aHolder.textViewDate.setText(article.getDate());
            aHolder.thumbView.setTag(aHolder);
            aHolder.currentArticle = article;
        }
        else if(holder instanceof ProgressViewHolder){
            ProgressViewHolder pHolder = (ProgressViewHolder) holder;
            pHolder.progressBar.setIndeterminate(true);

        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NetworkImageView thumbView;
        public TextView textViewTitle;
        public TextView textViewDate;
        public CardView cardView;
        public Article currentArticle;

        public ArticleViewHolder(View itemView) {
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
    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public ProgressBar getProgressBar() {
            return progressBar;
        }
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

    }
}

