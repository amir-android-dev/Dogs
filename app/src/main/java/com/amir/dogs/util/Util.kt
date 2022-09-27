package com.amir.dogs.util
import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.amir.dogs.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//it gives us an small spinner image, while the actual images is loaded
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
//extention function for the imageView Element
//in this function we use Glide to load uri of image into ImageView
fun ImageView.loadImage(uri: String, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.img_dog)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}