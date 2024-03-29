package com.goldentech.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goldentech.R;
import com.goldentech.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListAdapter extends RecyclerView.Adapter {

    private List<Category> list;
    private Context mContext;
    private Activity activity;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private CategoryListHolder mHolder;

    //    private final int[] colors = {R.color.greenish, R.color.orange,
//            R.color.colorAccent, R.color.end_color, R.color.blue, R.color.darkpink, R.color.green};
//    int count;
    private boolean isVideo;

    public interface OnButtonClickListener {
        public void onClickRowItem(Category model);
    }

    public CategoryListAdapter.OnButtonClickListener mListener;

    public CategoryListAdapter(Context context, List<Category> list
            , CategoryListAdapter.OnButtonClickListener mListener
            , boolean isVideo

//            , int pager_selected_pos

    ) {
        this.list = list;
        this.mListener = mListener;
        this.isVideo = isVideo;
        mContext = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_category_list_item, parent, false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //  pager_position

        if (holder instanceof CategoryListHolder) {
            mHolder = ((CategoryListHolder) holder);
            final Category nmodel = list.get(holder.getAdapterPosition());

//            int strokeWidth = 0;
//            int strokeColor;
//            int fillColor;
//            if (holder.getAdapterPosition() >= colors.length) {
//                count = holder.getAdapterPosition() % colors.length;
//                //mHolder.tv_count.setBackgroundResource(colors[count]);
//
//                strokeColor = ResourcesCompat.getColor(mContext.getResources(), colors[count], null);
//                fillColor = ResourcesCompat.getColor(mContext.getResources(), colors[count], null);
//                GradientDrawable gD = new GradientDrawable();
//                gD.setColor(fillColor);
//                gD.setShape(GradientDrawable.OVAL);
//                gD.setStroke(strokeWidth, strokeColor);
//                mHolder.tv_count.setBackground(gD);
//            } else {
//                count = position;
//                //mHolder.tv_count.setBackgroundResource(colors[count]);
//                strokeColor = ResourcesCompat.getColor(mContext.getResources(), colors[count], null);
//                fillColor = ResourcesCompat.getColor(mContext.getResources(), colors[count], null);
//                GradientDrawable gD = new GradientDrawable();
//                gD.setColor(fillColor);
//                gD.setShape(GradientDrawable.OVAL);
//                gD.setStroke(strokeWidth, strokeColor);
//                mHolder.tv_count.setBackground(gD);
//            }
//
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int screenHeight = displaymetrics.heightPixels;
//            int screenWidth = displaymetrics.widthPixels;
//
//            mHolder.layoutCount.getLayoutParams().width = (int) (screenWidth * 0.21);
//            mHolder.layoutCount.getLayoutParams().height = (int) (screenHeight * 0.13);
//
            View.OnClickListener onClickListener = v -> {
                if (mListener != null) {
                    mListener.onClickRowItem(nmodel);
                }
            };
            ((CategoryListHolder) holder).itemView.setOnClickListener(onClickListener);

            if (!nmodel.isFromLive())
                ((CategoryListHolder) holder).image.setImageResource(Integer.parseInt(nmodel.getImage()));
            else {
                Picasso
                        .with(mContext)
                        .load(nmodel.getImage())
                        .placeholder(R.drawable.ic_bg_circle)
                        .error(R.drawable.ic_bg_circle)
                        .into(((CategoryListHolder) holder).image);
            }
            ((CategoryListHolder) holder).title.setText(nmodel.getName());
            if (!isVideo)
                ((CategoryListHolder) holder).image_icon.setImageResource(R.drawable.pdf);
            else
                ((CategoryListHolder) holder).image_icon.setImageResource(R.drawable.pdf);

        }

    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_HEADER;
//        }
//        return TYPE_ITEM;
//    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class CategoryListHolder extends RecyclerView.ViewHolder {
        //        TextView tv_sn, tv_column_one, tv_column_two, tv_column_three;
        TextView title;
        private LinearLayout layoutCount;
        private CircleImageView image;
        private ImageView image_icon;

        private CategoryListHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            image_icon = view.findViewById(R.id.image_icon);
//            tv_column_three = view.findViewById(R.id.tv_column_three);

        }
    }

}
