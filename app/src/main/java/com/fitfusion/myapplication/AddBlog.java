package com.fitfusion.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fitfusion.myapplication.Model.BlogPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddBlog extends AppCompatActivity {
    private EditText postTitle, postDescription;
    private ImageView postImage;
    private Button postUpLoadBtn, uploadImageBtn;
    private Uri imageUri;
    private String imageUrl = "";
    private FirebaseAuth authProfile;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        postTitle = findViewById(R.id.postTitle);
        postDescription = findViewById(R.id.postDescription);
        postImage = findViewById(R.id.postImage);

        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        postUpLoadBtn = findViewById(R.id.postUpLoadBtn);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser fbUser = authProfile.getCurrentUser();

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        postUpLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {postBlog();}
        });
    }

    private void postBlog() {
        // Get the current user
        FirebaseUser fbUser = authProfile.getCurrentUser();

        if (fbUser != null) {
            // Get the values from the EditText fields
            String title = postTitle.getText().toString().trim();
            String description = postDescription.getText().toString().trim();

            if (!title.isEmpty() && !description.isEmpty() && !imageUrl.isEmpty()) {
                // Create a reference to the Firebase Realtime Database
                DatabaseReference databaseRef = FirebaseDatabase
                        .getInstance(Blog.DATABASE_URL)
                        .getReference(Blog.BLOGS_TABLE_NAME);

                // Generate a unique key for the new blog post
                String postId = databaseRef.push().getKey();

                // Create a Blog object with the data
                BlogPost blog = new BlogPost(postId, title, imageUrl, description,
                        System.currentTimeMillis());

                // Push the blog object to the database under a common node
                if (postId != null) {
                    databaseRef.child(postId).setValue(blog)
                            .addOnSuccessListener(aVoid -> {
                                // Clear the fields after successful upload
                                postTitle.setText("");
                                postDescription.setText("");
                                postImage.setImageResource(android.R.color.transparent);

                                // Notify the user of successful upload
                                Toast.makeText(AddBlog.this, "Blog Posted Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddBlog.this,Blog.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                // Notify the user of any errors
                                Toast.makeText(AddBlog.this, "Failed to post blog: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                // Notify the user to fill in all fields
                Toast.makeText(AddBlog.this, "Please fill in all fields and upload an image", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Notify the user to log in first
            Toast.makeText(AddBlog.this, "Please log in to post a blog", Toast.LENGTH_SHORT).show();
        }
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        FirebaseUser fbUser = authProfile.getCurrentUser();
        if (fbUser != null && imageUri != null) {
            // Correctly initialized Firebase Storage reference
            StorageReference fileRef = FirebaseStorage.getInstance().getReference("ProfileImages").child(fbUser.getUid() + ".jpg");
            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUrl = uri.toString();
                    Glide.with(this).load(imageUri).into(postImage);
                });
            }).addOnFailureListener(e -> Toast.makeText(AddBlog.this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


}