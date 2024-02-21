package com.vikas.android.news2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vikas.android.news2.R;
import com.vikas.android.news2.data.Article;
import com.vikas.android.news2.databinding.FragmentArticleListBinding;

import java.util.List;

public class ArticleListFragment extends Fragment {
  private FragmentArticleListBinding binding;

  private ArticleListViewModel articleListViewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    articleListViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentArticleListBinding.inflate(inflater, container, false);
    binding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    articleListViewModel.getArticles().observe(ArticleListFragment.this.getViewLifecycleOwner(), new Observer<List<Article>>() {
      @Override
      public void onChanged(List<Article> articles) {
        binding.articleRecyclerView.setAdapter(new ArticleListAdapter(articles));
      }
    });


    setUpMenu();
  }

  private void setUpMenu() {

    MenuHost menuHost = requireActivity();
    menuHost.addMenuProvider(new MenuProvider() {
      @Override
      public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.fragment_article_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            articleListViewModel.setQuery(query);
            return true;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            return false;
          }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
          @Override
          public boolean onClose() {
            articleListViewModel.setQuery("");
            return true;
          }
        });
      }

      @Override
      public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return menuItem.getItemId() == R.id.menu_item_search;
      }
    }, this.getViewLifecycleOwner(), Lifecycle.State.CREATED);
  }
}
