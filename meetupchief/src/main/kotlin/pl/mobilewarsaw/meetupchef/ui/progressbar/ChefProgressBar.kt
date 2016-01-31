package pl.mobilewarsaw.meetupchef.ui.progressbar

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import pl.mobilewarsaw.meetupchef.R


class ChefProgressBar(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {

    private val showAnimator: Animator by lazy { loadAnimator(R.animator.progress_bar_show) }
    private val hideAnimator: Animator by lazy { loadAnimator(R.animator.progress_bar_hide) }

    init {
        setImageResource(R.mipmap.ic_search)
        visibility = View.GONE
        alpha = 0f
    }

    private fun loadAnimator(animatorResId: Int): Animator {
        val animator = AnimatorInflater.loadAnimator(context, animatorResId)
        animator.setTarget(this)
        return animator
    }

    fun show() {
        visibility = View.VISIBLE
        showAnimator.start()
    }

    fun hide() {
        if (showAnimator.isRunning) {
            hideAnimator.onEnd { showAnimator.cancel() }
            hideAnimator.start()
        }
    }

    private inline fun Animator.onEnd(noinline action: () -> Unit) {
        addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                action()
                removeListener(this)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }
}

