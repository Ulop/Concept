package io.ulop.concept.base.ext

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.*
import android.view.View
import android.widget.TextView

fun TextView.appendCounter(count: Int) {
    val oldText = text.toString().substringBefore('\n')
    text = buildSpanned {
        appendln(oldText)
        append("$count", Bold, foregroundColor(Color.WHITE), RelativeSizeSpan(1.25f))
    }
}

inline fun buildSpanned(f: SpannableStringBuilder.() -> Unit): Spanned =
        SpannableStringBuilder().apply(f)

inline val SpannableStringBuilder.Bold: StyleSpan
    get() = StyleSpan(Typeface.BOLD)

inline val SpannableStringBuilder.Italic: StyleSpan
    get() = StyleSpan(Typeface.ITALIC)

inline val SpannableStringBuilder.Underline: UnderlineSpan
    get() = UnderlineSpan()

inline val SpannableStringBuilder.Strikethrough: StrikethroughSpan
    get() = StrikethroughSpan()

inline fun SpannableStringBuilder.foregroundColor(color: Int): ForegroundColorSpan =
        ForegroundColorSpan(color)

inline fun SpannableStringBuilder.backgroundColor(color: Int): BackgroundColorSpan =
        BackgroundColorSpan(color)

inline fun SpannableStringBuilder.clickable(crossinline onClick: (View) -> Unit): ClickableSpan {
    return object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick(widget)
        }
    }
}

inline fun SpannableStringBuilder.link(url: String): URLSpan {
    return URLSpan(url)
}

fun SpannableStringBuilder.append(text: CharSequence, vararg spans: Any) {
    val textLength = text.length
    append(text)
    spans.forEachByIndex { span ->
        setSpan(span, this.length - textLength, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun SpannableStringBuilder.append(text: CharSequence, span: Any) {
    val textLength = text.length
    append(text)
    setSpan(span, this.length - textLength, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

inline fun SpannableStringBuilder.append(span: Any, f: SpannableStringBuilder.() -> Unit) = apply {
    val start = length
    f()
    setSpan(span, start, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

inline fun SpannableStringBuilder.appendln(text: CharSequence, vararg spans: Any) {
    append(text, *spans)
    appendln()
}

inline fun SpannableStringBuilder.appendln(text: CharSequence, span: Any) {
    append(text, span)
    appendln()
}

inline fun <T> Array<T>.forEachByIndex(f: (T) -> Unit) {
    val lastIndex = size - 1
    for (i in 0..lastIndex) {
        f(get(i))
    }
}
