package com.example.coronatracker.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coronatracker.News.Models.Source;
import com.example.coronatracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {

    String title,desc,content,imageUrl,url;

    ImageView idIVNewsImage;
    TextView TVNewstxt,idTVSubTitletxt,idTVContenttxt;
    Button see;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        idIVNewsImage =(ImageView) findViewById(R.id.idIVNewsImage);
        TVNewstxt =(TextView) findViewById(R.id.TVNewstxt);
        idTVSubTitletxt =(TextView) findViewById(R.id.idTVSubTitletxt);
        idTVContenttxt =(TextView) findViewById(R.id.idTVContenttxt);
        see=(Button)findViewById(R.id.see);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        Picasso.get().load(imageUrl).into(idIVNewsImage);
        TVNewstxt.setText(title);
        idTVSubTitletxt.setText(desc);
        idTVContenttxt.setText(content);

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetailActivity.this,WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                finish();
            }
        });

    }
}