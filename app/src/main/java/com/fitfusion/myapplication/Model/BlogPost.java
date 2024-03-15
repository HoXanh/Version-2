package com.fitfusion.myapplication.Model;

public class BlogPost {
    private String title;
    private String description;
    private String imageUrl;
    private String postId;





    public BlogPost(String postId, String title, String imageUrl, String description) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Required empty constructor for Firebase
    public BlogPost() {}

    public String getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

