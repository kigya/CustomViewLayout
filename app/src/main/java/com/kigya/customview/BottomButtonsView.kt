package com.kigya.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kigya.customview.databinding.PartButtonsBinding

enum class BottomButtonAction {
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: PartButtonsBinding
    private var listener: OnBottomButtonActionListener? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        R.style.MyButtonStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomButtonsStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
        initListeners()
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            binding.positiveButton.text = positiveButtonText ?: "Ok"
            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            binding.negativeButton.text = negativeButtonText ?: "Cancel"
            val positiveButtonColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveButtonBackgroundColor,
                Color.BLACK
            )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonColor)
            val negativeButtonColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomNegativeButtonBackgroundColor,
                Color.WHITE
            )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonColor)

            val isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)
            if (isProgressMode) {
                positiveButton.visibility = GONE
                negativeButton.visibility = GONE
                progress.visibility = VISIBLE
            } else {
                progress.visibility = GONE
                positiveButton.visibility = VISIBLE
                negativeButton.visibility = VISIBLE
            }
        }

        typedArray.recycle()
    }

    private fun initListeners() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()!!
        val savedState = SavedState(superState)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        binding.positiveButton.text = savedState.positiveButtonText
    }

    class SavedState : BaseSavedState {

        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return Array(size) { null }
                }
            }
        }
    }
}