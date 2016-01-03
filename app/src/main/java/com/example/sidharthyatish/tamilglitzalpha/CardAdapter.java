package com.example.sidharthyatish.tamilglitzalpha;

import android.app.Activity;
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
 */

    public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private ImageLoader imageLoader;
        private Context context;

        //List of superHeroes
        List<Article> articles;

        public CardAdapter(List<Article> articles, Context context){
            super();
            //Getting all the superheroes
            this.articles=articles;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType==0){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_card_view, parent, false);
            return new UViewHolder(v);
            }
            else {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.progressbar_item, parent, false);
                return new ProgressViewHolder(v);
            }



        }
    @Override
    public int getItemViewType(int position) {
        return articles.get(position) == null ? 1 : 0;
    }
        @Override
        public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

          //  if (holder instanceof UViewHolder) {
                Article article = articles.get(position);
                UViewHolder uViewHolder =(UViewHolder)holder;
                imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
                imageLoader.get(article.getThumbUrl(), ImageLoader.getImageListener(uViewHolder.thumbView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

                uViewHolder.thumbView.setImageUrl(article.getThumbUrl(), imageLoader);
                uViewHolder.textViewTitle.setText(Html.fromHtml(article.getTitle()));

                uViewHolder.textViewDate.setText(article.getDate());
                uViewHolder.thumbView.setTag(holder);
                uViewHolder.currentArticle = article;

          //  }
          /*  else if(holder instanceof ProgressViewHolder){
                ProgressViewHolder progressViewHolder =(ProgressViewHolder)holder;
                progressViewHolder.progressBar.setIndeterminate(true);
            }
           */
            //holder.cardView.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
            //        Toast.makeText(context, article.getContent(), Toast.LENGTH_SHORT).show();
//
           //             }
          //  });


        }

        @Override
        public int getItemCount() {
            return articles.size();
        }



    public class UViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public NetworkImageView thumbView;
            public TextView textViewTitle;
            public TextView textViewDate;
            public CardView cardView;
            public Article currentArticle;

            public UViewHolder(View itemView) {
                super(itemView);
                thumbView = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
                textViewTitle = (TextView) itemView.findViewById(R.id.textTitle);
                textViewDate= (TextView) itemView.findViewById(R.id.textDate);
                cardView= (CardView) itemView.findViewById(R.id.cardView);
                cardView.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
              //  Toast.makeText(context,articles.get(getAdapterPosition()).getContent(),Toast.LENGTH_SHORT).show();
                Bundle args=new Bundle();
                args.putString("content", articles.get(getAdapterPosition()).getContent());
                Intent intent=new Intent(((Activity)context),ReaderActivity.class);
                intent.putExtra("content",articles.get(getAdapterPosition()).getContent());
            ((Activity)context).startActivity(intent);
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
    }

