package com.example.sidharthyatish.tamilglitzalpha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Article> listArticles;
    private ProgressBar progressBar;
    public static LinearLayout progressLayout;
    LinearLayoutManager llm;
    private int visibleThreshold = 4;
    private int previousTotal=0;
    private int page=1;

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_hot_news,container,false);


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

            llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            listArticles = new ArrayList<>();
            getData(page);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = llm.getItemCount();
                    firstVisibleItem = llm.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached

                        // Do something

                        page++;
                        getData(page);
                        loading = true;

                    }

                }
            });

        adapter=new CardAdapter(listArticles,getContext());
        recyclerView.setAdapter(adapter);

    }
    private void getData(int page){
        //Showing a progress dialog
       // final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);

       // listArticles.add(null);
      //  adapter.notifyDataSetChanged();
        System.out.println("The articles size is "+listArticles.size());
        String url="https://tamilglitz.in/api/get_recent_posts/?count=5&page=";
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url+String.valueOf(page),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                      //  loading.dismiss();
                        listArticles.remove(listArticles.size()-1);
                        adapter.notifyItemRemoved(listArticles.size()-1);
                      //  adapter.notifyDataSetChanged();
                        try {
                            JSONArray post = response.getJSONArray("posts");
                            for (int i = 0; i < post.length(); i++) {
                                JSONObject obj = post.getJSONObject(i);
                                Article topic = new Article();
                                topic.setTitle(obj.getString("title"));
                                topic.setDate(obj.getString("date"));
                                topic.setThumbUrl(obj.getString("thumbnail"));
                                topic.setContent(obj.getString("content"));
                                // topic.setDesc("Desc "+i);
                                //  topic.setTitle("Title "+i);
                                listArticles.add(topic);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listArticles.remove(null);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Some Volley error");
                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);



    }


}