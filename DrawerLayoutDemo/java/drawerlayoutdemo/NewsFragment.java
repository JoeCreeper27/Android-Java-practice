package com.example.drawerlayoutdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment,container,false);
        List<News>newsList=new ArrayList<>();
        newsList.add(new News("Title 1","Detail 1"));
        newsList.add(new News("Title 2","Detail 2"));
        newsList.add(new News("Title 3","Detail 3"));

        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NewsAdapter(inflater,newsList));
        return view;
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private LayoutInflater inflater;
        private List<News> newsList;
        private View visibleView;

        public NewsAdapter(LayoutInflater inflater,List<News> newsList){
            this.inflater=inflater;
            this.newsList=newsList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View itemView=inflater.inflate(R.layout.news_item,viewGroup,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewholder, int position) {
            News news=newsList.get(position);
            viewholder.tvTitle.setText(news.getTitle());
            viewholder.tvDetail.setText(news.getDetail());
            viewholder.tvTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    switch (viewholder.tvDetail.getVisibility()){
                        case View.VISIBLE:
                            viewholder.tvDetail.setVisibility(View.GONE);
                            break;
                        case View.GONE:
                            if(visibleView !=null){
                                visibleView.setVisibility(View.GONE);
                            }
                            viewholder.tvDetail.setVisibility(View.VISIBLE);
                            visibleView=viewholder.tvDetail;
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle,tvDetail;

            public ViewHolder(View itemView){
                super(itemView);
                tvTitle=(TextView) itemView.findViewById(R.id.tvTitle);
                tvDetail=(TextView) itemView.findViewById(R.id.tvDetail);
            }

        }
    }
}
