package com.sillylife.news.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sillylife.news.Database.Modal.RssFeedModel;
import com.sillylife.news.R;
import com.sillylife.news.Utils.ImageHelper;

import java.util.List;

public class RssFeedListAdapter
		extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

	public interface onClick {
		void onClickListener(RssFeedModel rssFeedModel);
	}

	final private int POSITION_1 = 0;
	final private int POSITION_2 = 1;

	private List<RssFeedModel> mRssFeedModels;
	private onClick click;

	public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
		mRssFeedModels = rssFeedModels;
	}


	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return POSITION_1;
		} else {
			return POSITION_2;
		}
	}

	@Override
	public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
//		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline_with_smallimage, parent, false);
//		FeedModelViewHolder holder = new FeedModelViewHolder(v);
//		return holder;


		switch (getItemViewType(type)) {
			case POSITION_1:
				View pos1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline_with_bigimage, parent, false);
				return new FeedModelViewHolder(pos1);
			case POSITION_2:
				View pos2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline_with_smallimage, parent, false);
				return new FeedModelViewHolder(pos2);
		}
		return null;
	}


	public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
		private View rssFeedView;
		private ImageView imageView;

		public FeedModelViewHolder(View v) {
			super(v);
			rssFeedView = v;
			imageView = v.findViewById(R.id.news_image);
		}
	}

	@Override
	public void onBindViewHolder(FeedModelViewHolder holder, int position) {
		final RssFeedModel rssFeedModel = mRssFeedModels.get(position);
		((TextView) holder.rssFeedView.findViewById(R.id.news_heading)).setText(rssFeedModel.title);
		((TextView) holder.rssFeedView.findViewById(R.id.numbers)).setText(String.valueOf(position + 1));
		((TextView) holder.rssFeedView.findViewById(R.id.news_time)).setText(rssFeedModel.pubDate);
		//((TextView) holder.rssFeedView.findViewById(R.id.linkText)).setText(rssFeedModel.link);

//		holder.rssFeedView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				click.onClickListener(rssFeedModel);
//			}
//		});
		ImageHelper.getInstance().loadImage(holder.imageView, rssFeedModel.StoryImage, R.drawable.ic_launcher_background);
	}

	public void setRssFeedClick(onClick itemClickListener) {
		this.click = itemClickListener;
	}

	@Override
	public int getItemCount() {
		return mRssFeedModels.size();
	}
}

