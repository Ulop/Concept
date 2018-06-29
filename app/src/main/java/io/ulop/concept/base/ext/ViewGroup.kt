package io.ulop.concept.base.ext

import android.view.ViewGroup
import io.ulop.concept.common.layoutInflater

fun ViewGroup.inflate(layout: Int) =
        this.context.layoutInflater.inflate(layout, this, false)