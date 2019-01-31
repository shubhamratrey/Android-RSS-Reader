package com.sillylife.news.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.sillylife.news.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class ImageHelper {

	public interface ImageLoaderListener {
		public void onImageLoaded(String slug);

		public void onImageLoadFailed();
	}


	private static ImageHelper instance;

	private Context mContext;

	private RequestBuilder<PictureDrawable> requestBuilder;

	private ImageHelper() {
	}

	public static synchronized void init(Context context) {
		instance = new ImageHelper();
		instance.mContext = context;
	}

	public static synchronized ImageHelper getInstance() {
		return instance;
	}

	private RequestOptions getDefaultRequestOptions() {
		RequestOptions requestOptions = new RequestOptions();
		CircularProgressDrawable progressDrawable = new CircularProgressDrawable(mContext);
		progressDrawable.setStrokeWidth(5);
		progressDrawable.setCenterRadius(8);
		progressDrawable.start();
		requestOptions = requestOptions.placeholder(progressDrawable);
		requestOptions = requestOptions.error(R.drawable.ic_launcher_background);
		return requestOptions;
	}

	private RequestOptions getRequestOptions(int resourceId) {
		RequestOptions requestOptions = new RequestOptions();
		requestOptions = requestOptions.placeholder(resourceId);
		requestOptions = requestOptions.error(resourceId);
		return requestOptions;
	}

	private RequestOptions getRequestOptions(Drawable drawable) {
		RequestOptions requestOptions = new RequestOptions();
		requestOptions = requestOptions.placeholder(drawable);
		requestOptions = requestOptions.error(drawable);
		return requestOptions;
	}

//    public void loadImage(ImageView imageView, String url) {
//        Glide.with(mContext).clear(imageView);
//        if (Util.isSVG(url)) {
//            loadSVGImage(imageView, url);
//        } else {
//            Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable())).load(url).into(imageView);
//        }
//    }

	public void loadImageFile(ImageView imageView, File file) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable())).load(file).into(imageView);
	}

	public void loadImageUri(ImageView imageView, Uri uri) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable())).load(uri).into(imageView);
	}

//	public void loadImageAndBlur(ImageView imageView, String url, int blur, int sampling) {
//		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable())).load(url).apply(RequestOptions.bitmapTransform(new BlurTransformation(blur, sampling))).into(imageView);
//	}

//    public void loadSVGImage(ImageView imageView, String url) {
//        Glide.with(mContext)
//                .setDefaultRequestOptions(getRequestOptions(imageView.getDrawable()))
//                .as(PictureDrawable.class)
//                .listener(new SvgSoftwareLayerSetter())
//                .load(url)
//                .into(imageView);
//    }

	public void loadImage(final ImageView imageView, final String url, final String slug, final ImageLoaderListener listener) {
		Glide.with(mContext)
				.setDefaultRequestOptions(getRequestOptions(imageView.getDrawable()))
				.load(url)
				.listener(new RequestListener<Drawable>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
						if (listener != null) {
							listener.onImageLoadFailed();
						}
						return false;
					}

					@Override
					public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
						if (listener != null && imageView.getTag() == null) {
							imageView.setTag(slug);
							listener.onImageLoaded(slug);
						}
						return false;
					}
				}).into(imageView);
	}

	public void loadImage(final ImageView imageView, final String url, int resourceId, final String slug, final ImageLoaderListener listener) {
		Glide.with(mContext)
				.setDefaultRequestOptions(getRequestOptions(resourceId))
				.load(url)
				.listener(new RequestListener<Drawable>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
						if (listener != null) {
							listener.onImageLoadFailed();
						}
						return false;
					}

					@Override
					public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
						if (listener != null && imageView.getTag() == null) {
							imageView.setTag(slug);
							listener.onImageLoaded(slug);
						}
						return false;
					}
				}).into(imageView);
	}

	public void loadImage(ImageView imageView, String url, int resourceId) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(resourceId)).load(url).into(imageView);
	}

//	public void loadGifImage(ImageView imageView) {
//		Glide.with(mContext).asGif().load(R.drawable.audio_spectrum).into(imageView);
//	}

	public void loadVectorImage(ImageView imageView, int resourceId) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable())).load(resourceId).into(imageView);
	}

	public void loadCircularImage(ImageView imageView, String url) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(imageView.getDrawable()))
				.load(url)
				.apply(RequestOptions.circleCropTransform())
				.into(imageView);
	}

	public void loadCircularImageWithPlaceholder(ImageView imageView, String url, int resourceId) {
		Glide.with(mContext).setDefaultRequestOptions(getRequestOptions(resourceId))
				.load(url)
				.apply(RequestOptions.circleCropTransform())
				.into(imageView);
	}

	public void loadImage(String url, int width, int height, SimpleTarget<Bitmap> bitmapSimpleTarget) {
		Glide.with(mContext)
				.asBitmap()
				.load(url)
				.apply(getDefaultRequestOptions().override(width, height))
				.into(bitmapSimpleTarget);
	}

	public Bitmap getBitmapSync(String url, int width, int height) throws InterruptedException, ExecutionException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = Glide.with(mContext)
				.asBitmap()
				.load(url)
				.submit(width, height).get();
		if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
			return Glide.with(mContext).asBitmap().load(stream.toByteArray()).submit(width, height).get();
		} else {
			return bitmap;
		}
	}

	public Bitmap getBitmapSyncFile(File file, int width, int height) throws InterruptedException, ExecutionException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = Glide.with(mContext)
				.asBitmap()
				.load(file).listener(new RequestListener<Bitmap>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
						return false;
					}

					@Override
					public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
						return false;
					}
				})
				.submit(width, height).get();
		if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
			return Glide.with(mContext).asBitmap().load(stream.toByteArray()).submit(width, height).get();
		} else {
			return bitmap;
		}
	}

	public Bitmap getBitmapSync(String url) throws InterruptedException, ExecutionException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = Glide.with(mContext)
				.asBitmap()
				.load(url)
				.submit().get();
		if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
			return Glide.with(mContext).asBitmap().load(stream.toByteArray()).submit().get();
		} else {
			return bitmap;
		}
	}

//	public void generateColorSwatch(ImageView imageView, final Palette.PaletteAsyncListener listener) {
//		if (imageView != null) {
//			Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//			Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//				@Override
//				public void onGenerated(Palette palette) {
//					listener.onGenerated(palette);
////                    sample ussages
////                    Palette.Swatch swatch = palette.getVibrantSwatch();
////                    if (swatch != null) {
////                        imgAlbum.setBackgroundColor(swatch.getRgb());
////                    }
//				}
//			});
//		}
//	}
}