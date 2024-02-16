package com.goldentech.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goldentech.R;
import com.goldentech.adapter.CategoryListAdapter;
import com.goldentech.model.Category;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CategoryListAdapter.OnButtonClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private View view;

    private RecyclerView recycle_category_list;
    private SwipeRefreshLayout layout_swipe_refresh;

    private RadioButton rb_video, rb_book;
    private boolean isVideo;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initView();
    }

    private void initView() {

        layout_swipe_refresh = view.findViewById(R.id.layout_swipe_refresh);
        layout_swipe_refresh.setOnRefreshListener(this);

        recycle_category_list = view.findViewById(R.id.recycle_category_list);
        recycle_category_list.setHasFixedSize(true);
//        recycle_category_list.setLayoutManager(new LinearLayoutManager(getContext(),
//                LinearLayoutManager.HORIZONTAL, false));
        // recycle_category_list.setNestedScrollingEnabled(false);
        recycle_category_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycle_category_list.setItemAnimator(new DefaultItemAnimator());

        initializeRadioButton();

        rb_book.setChecked(true);
        if (rb_book.isChecked())
            isVideo = false;

        setData();
    }


    @Override
    public void onClickRowItem(Category model) {
        if (isVideo) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
            try {
                getActivity().startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "No any browser to view the video", Toast.LENGTH_SHORT).show();
            }
        } else {

            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putString("URL", model.getUrl());
            fragment = new PDFViewerFragment();
            fragment.setArguments(bundle);
            if (fragment != null) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameContainer, fragment).addToBackStack(null);
                ft.commit();
            }

//            Intent intent = new Intent(getActivity(), PDFViewerActivity.class);
//            intent.putExtra("URL", model.getUrl());
//            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        layout_swipe_refresh.setRefreshing(false);
        initView();
    }

    private void initializeRadioButton() {
        //initialize radio group
        RadioGroup rg_category_type = view.findViewById(R.id.rg_category_type);
        rb_video = view.findViewById(R.id.rb_video);
        rb_book = view.findViewById(R.id.rb_book);

        rg_category_type.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_video:
                    isVideo = true;
                    setData();
                    break;
                case R.id.rb_book:
                    isVideo = false;
                    setData();
                    break;
            }
        });

    }

    private void setData() {

        if (isVideo) {
            List<Category> categoryList = new ArrayList<>();
            categoryList.add(new Category("1", "रामायण", String.valueOf(R.drawable.ramayan),
                    false, "https://www.youtube.com/watch?v=-vgYRr5Tt0k"));
            categoryList.add(new Category("2", "महाभारत", String.valueOf(R.drawable.mahabharat),
                    false, "https://www.youtube.com/channel/UCHKGDg0GJKBsA9mFraDOLHA"));
            categoryList.add(new Category("3", "विष्णु पुराण", String.valueOf(R.drawable.vishnu_puran),
                    false, "https://www.youtube.com/watch?v=wQZpS-9ubic&list=PLNKJJImmGYISh0bc__cVPUuDy2TDB5PZt"));
            categoryList.add(new Category("4", "श्री कृष्णा", String.valueOf(R.drawable.shree_krishna),
                    false, "https://www.youtube.com/watch?v=xXcSORMsg6s&t=1084s"));
            categoryList.add(new Category("5", "ॐ नमः शिवाय", String.valueOf(R.drawable.om_namah_shivay),
                    false, "https://www.youtube.com/watch?v=UcthDsdD8Wg&list=PLlbad35XexJCueScvgT__mxw5BRL_WlYG"));
            categoryList.add(new Category("6", "ओम नमो नारायण", String.valueOf(R.drawable.shree_vishnu),
                    false, "https://www.youtube.com/watch?v=9OkmrPpCo18&list=PLAQgJHZrkrEqrebGdU3iBP9pwPkQMDr1P"));
            setAdapter(categoryList);
        } else {
            List<Category> categoryList = new ArrayList<>();
            categoryList.add(new Category("1", "रामायण", String.valueOf(R.drawable.ramayan),
                    false, "http://www.africau.edu/images/default/sample.pdf"));
            categoryList.add(new Category("2", "महाभारत", String.valueOf(R.drawable.mahabharat),
                    false, "http://www.africau.edu/images/default/sample.pdf"));
            categoryList.add(new Category("3", "विष्णु पुराण", String.valueOf(R.drawable.vishnu_puran),
                    false, "http://www.africau.edu/images/default/sample.pdf"));
            categoryList.add(new Category("4", "श्री कृष्णा", String.valueOf(R.drawable.shree_krishna),
                    false, "http://download.vedpuran.net/Files/Mahabhart-Gorkhpur.pdf"));
            setAdapter(categoryList);
        }
    }

    private void setAdapter(List<Category> categoryList) {
        CategoryListAdapter adapter = new CategoryListAdapter(getActivity(), categoryList, HomeFragment.this, isVideo);
        recycle_category_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
