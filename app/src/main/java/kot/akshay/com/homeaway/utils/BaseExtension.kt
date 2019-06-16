package kot.akshay.com.homeaway.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target

/**
 * Extensions to be used
 */

fun showToast(activity: Activity, message: String?) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun hideAndShowKeyboard(activity: Activity) {
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun isInternetOn(context: Context?): Boolean {
    val conn = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (null != conn) {
        val networkInfo = conn.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected)
            return true
    }

    return false
}

fun showSnackbar(viewGroup: View, message: String){
    Snackbar.make(viewGroup, message, Snackbar.LENGTH_LONG).show()
}

fun ImageView.loadImage(url: String?, it: Context) {
    @Suppress("DEPRECATION")
    Glide.with(it)
        .load(url)
        .apply(RequestOptions().transforms(RoundedCorners(18)))
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        })
        .into(DrawableImageViewTarget(this))
}
