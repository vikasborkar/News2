package com.vikas.android.news2.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vikas.android.news2.R;
import com.vikas.android.news2.data.Article;
import com.vikas.android.news2.databinding.ListItemArticleBinding;

import java.text.SimpleDateFormat;
import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleHolder> {

  private List<Article> articles;

  public ArticleListAdapter(List<Article> articles) {
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemArticleBinding binding = ListItemArticleBinding.inflate(inflater, parent, false);
    return new ArticleHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
    holder.bindItem(articles.get(position));
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }


  public class ArticleHolder extends RecyclerView.ViewHolder {

    private ListItemArticleBinding binding;

    public ArticleHolder(ListItemArticleBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }


    public void bindItem(Article item) {
      Picasso.get()
          .load(item.getUrlToImage())
          .resize(100, 100)
          .centerCrop()
          .error(R.drawable.image_placeholder)
          .into(binding.articleImage);

      binding.articleTitle.setText(item.getTitle());

      SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
      String dateString = sdf.format(item.getPublishedAt());
      binding.articleDate.setText(dateString);
    }

  }
}


