package ru.a777alko.sales777.ui.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ru.a777alko.sales777.mvp.model.image.IImageLoader;


public class GlideImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadInto(String url, ImageView container) {
        Glide.with(container.getContext())
                .load(ROOT + url.replace('\\', '/'))
                .into(container);
    }

//    public void loadDrawable(@Res)
//    Glide.with(this).load(getImage(my_drawable_image_name)).into(myImageView);
//
//    public int getImage(String imageName) {
//
//        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
//
//        return drawableResourceId;
//    }
}
