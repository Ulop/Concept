package io.ulop.concept.adapter

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.ViewPropertyTransition
import io.ulop.concept.R
import io.ulop.concept.common.GlideApp
import io.ulop.concept.data.ListItem
import kotlinx.android.synthetic.main.item_image.view.*


fun AbstractAdapter.imageDelegate() = delegate<ListItem.Shot>(R.layout.item_image) { vh, shot ->
    GlideApp.with(vh.itemView)
            .load(shot.url)
            .placeholder(ColorDrawable(shot.color))
            .thumbnail(
                    GlideApp.with(vh.itemView)
                            .load(shot.url.replace("00", "0").replace("photos/", "photos/g/"))
                            .centerCrop()
                            .transition(withCrossFade())
            )
            .transition(withCrossFade())
            .centerCrop()
            .into(vh.itemView.image)
}

var fadeAnimation: ViewPropertyTransition.Animator = ViewPropertyTransition.Animator { view ->
    val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
    fadeAnim.duration = 500
    fadeAnim.start()
}