package com.fitfusion.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.BlogPost;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        TextView time = convertView.findViewById(R.id.timestamp);
        ImageView imageView = convertView.findViewById(R.id.blogpic);
        Button button = convertView.findViewById(R.id.viewBlog);
        title.setText(post.getTitle());
        Date date = new Date(post.getTimestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        time.setText(dateString.substring(0,10));

        Glide.with(context).load(post.getImageUrl()).into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = post.getPostId();
                Intent intent = new Intent(context, ReadBlog.class);
                intent.putExtra("NUMBER_KEY", id);
                context.startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = post.getPostId();
                Intent intent = new Intent(context, ReadBlog.class);
                intent.putExtra("NUMBER_KEY", id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
