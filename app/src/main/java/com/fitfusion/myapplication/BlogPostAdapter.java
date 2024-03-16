package com.fitfusion.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fitfusion.myapplication.Model.BlogPost;

import java.util.List;

public class BlogPostAdapter extends BaseAdapter {

    private Context context;
    private List<BlogPost> posts;

    public BlogPostAdapter(Context context, List<BlogPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_blog_post, parent, false);
        }
        BlogPost post = posts.get(position);

        TextView title = convertView.findViewById(R.id.blogTextViewTitle);
        title.setText(post.getTitle());

        return convertView;
    }

}
