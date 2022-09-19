package com.kiran.fampaytask.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.card.MaterialCardView
import com.kiran.fampaytask.MainActivity
import com.kiran.fampaytask.R
import com.kiran.fampaytask.models.Card
import com.kiran.fampaytask.models.CardGroup
import com.kiran.fampaytask.models.CardGroup.DesignType.*

class ContextualCardsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cardGroupList: List<CardGroup>? = ArrayList()
    private var scrollable: Boolean? = false
    private var onItemRemoveListener: OnItemRemoveListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCardGroupList(cardGroupList: List<CardGroup>?) {
        this.cardGroupList = cardGroupList
        notifyDataSetChanged()
    }

    fun setOnItemRemoveListener(onItemRemoveListener: MainActivity) {
        this.onItemRemoveListener = onItemRemoveListener
    }

    internal class NonScrollableLLViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var nonScrollableLl: LinearLayout

        init {
            nonScrollableLl = itemView.findViewById(R.id.non_scrollable)
        }
    }

    internal class ScrollableViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var scrollableHv: HorizontalScrollView
        var scrollableLl: LinearLayout

        init {
            scrollableHv = itemView.findViewById(R.id.horizontal_scrollable_item)
            scrollableLl = itemView.findViewById(R.id.scrollable)
        }
    }

    internal class BigDisplayCardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnLongClickListener {
        var bigCardViewRl: RelativeLayout
        var remindLaterCv: MaterialCardView
        var dismissNowCv: MaterialCardView
        var bigCard: MaterialCardView
        var bigCardDisplayTv: TextView
        var withActionTv: TextView
        var descriptionTv: TextView
        var actionBtn: Button
        var start = 0.0f
        var end = 0.5f
        override fun onLongClick(v: View): Boolean {
            val animation: Animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,  //fromXType
                start,  //fromXValue
                Animation.RELATIVE_TO_SELF,  //toXType
                end,  //toXValue
                Animation.RELATIVE_TO_SELF,  //fromYType
                0.0f,  //fromYValue
                Animation.RELATIVE_TO_SELF,  //toYType
                0.0f
            ) //toYValue
            animation.duration = 500
            animation.fillAfter = true
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    val temp = start
                    start = end
                    end = temp
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            bigCard.startAnimation(animation)
            return true
        }

        init {
            itemView.setOnLongClickListener(this)
            bigCardViewRl = itemView.findViewById(R.id.big_card_items_rl)
            remindLaterCv = itemView.findViewById(R.id.remind_later_cv)
            dismissNowCv = itemView.findViewById(R.id.dismiss_now_cv)
            bigCard = itemView.findViewById(R.id.big_card)
            bigCardDisplayTv = itemView.findViewById(R.id.big_card_display_tv)
            withActionTv = itemView.findViewById(R.id.with_action_tv)
            descriptionTv = itemView.findViewById(R.id.description_tv)
            actionBtn = itemView.findViewById(R.id.big_card_btn)
            bigCardViewRl.visibility = View.VISIBLE
        }
    }

    internal class SmallDisplayCardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var smallDisplayCard: MaterialCardView
        var smallDisplayCardIv: ImageView
        var smallDisplayCardTv: TextView
        var aryaStarkTv: TextView

        init {
            smallDisplayCard = itemView.findViewById(R.id.small_display_card)
            smallDisplayCardIv = itemView.findViewById(R.id.small_display_card_pic)
            smallDisplayCardTv = itemView.findViewById(R.id.small_display_card_tv)
            aryaStarkTv = itemView.findViewById(R.id.arya_stark_tv)
        }
    }

    internal class DynamicCardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var dynamicCardLayout: LinearLayout
        var dynamicCardImage: MaterialCardView

        init {
            dynamicCardLayout = itemView.findViewById(R.id.dynamic_card_layout)
            dynamicCardImage = itemView.findViewById(R.id.dynamic_card)
        }
    }

    internal class ImageCardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageCardLl: LinearLayout
        private var imageCard: MaterialCardView

        init {
            imageCard = itemView.findViewById(R.id.card_image)
            imageCardLl = itemView.findViewById(R.id.card_image_layout)
        }
    }

    internal class SmallCardWithArrowViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var smallArrowCard: MaterialCardView?
        var smallArrowCardTv: TextView?

        init {
            smallArrowCard = itemView.findViewById(R.id.small_arrow_card)
            smallArrowCardTv = itemView.findViewById(R.id.small_card_with_arrow_tv)
        }
    }

    override fun getItemViewType(position: Int): Int {
        scrollable = cardGroupList?.get(position)?.isScrollable
        val itemViewTypeNo: Int = when (cardGroupList?.get(position)?.designType) {
            BIG_DISPLAY_CARD -> 3 // Big Display Card
            SMALL_DISPLAY_CARD -> 1 // Small Display Card
            DYNAMIC_WIDTH_CARD -> 9 // dynamic card
            IMAGE_CARD -> 5 // Image Card
            SMALL_CARD_WITH_ARROW -> 6 // Small Card with Arrow
            null -> 3
        } //Big Display Card Default
        return itemViewTypeNo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!scrollable!!) NonScrollableLLViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.horizontal_non_scrollable_item, parent, false)
        ) else ScrollableViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.horizontal_scrollable_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> if (!scrollable!!) layNonScrollableSmallDisplayCard(
                holder as NonScrollableLLViewHolder,
                cardGroupList?.get(position)?.cards
            ) else layScrollableSmallDisplayCard(
                holder as ScrollableViewHolder,
                cardGroupList?.get(position)?.cards
            )
            3 -> layNonScrollableBigCards(
                holder as NonScrollableLLViewHolder,
                cardGroupList?.get(position)?.cards, position
            )
            9 -> if (!scrollable!!) layNonScrollableDynamicCard(
                holder as NonScrollableLLViewHolder,
                cardGroupList?.get(position)?.cards
            ) else layNonScrollableDynamicCard(
                holder as ScrollableViewHolder,
                cardGroupList?.get(position)?.cards
            )
            5 -> if (!scrollable!!) layNonScrollableImageCard(
                holder as NonScrollableLLViewHolder,
                cardGroupList?.get(position)?.cards
            ) else layNonScrollableImageCard(
                holder as ScrollableViewHolder,
                cardGroupList?.get(position)?.cards
            )
            6 -> layNonScrollableSmallArrowCard(
                holder as NonScrollableLLViewHolder,
                cardGroupList?.get(position)?.cards
            )
        }
    }

    private fun layNonScrollableBigCards(
        nonScrollableLLViewHolder: NonScrollableLLViewHolder,
        cards: List<Card>?, position: Int
    ) {
        for (card in cards!!) {
            val bigDisplayCardViewHolder = BigDisplayCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.big_card_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
            bigDisplayCardViewHolder.bigCardViewRl.visibility = View.VISIBLE
            bigDisplayCardViewHolder.bigCard.setCardBackgroundColor(
                Color
                    .parseColor(card.bgColor)
            )
            bigDisplayCardViewHolder.bigCardDisplayTv.text = card.formattedTitle
                ?.entities?.get(0)!!.text
            bigDisplayCardViewHolder.bigCardDisplayTv.setTextColor(
                Color.parseColor(
                    card.formattedTitle
                        ?.entities?.get(0)!!.color
                )
            )
            bigDisplayCardViewHolder.withActionTv.text = card.formattedTitle
                ?.entities?.get(1)!!.text
            bigDisplayCardViewHolder.withActionTv.setTextColor(
                Color.parseColor(
                    card.formattedTitle
                        ?.entities?.get(1)!!.color
                )
            )
//            bigDisplayCardViewHolder.descriptionTv.text = card.formattedDescription
//                ?.entities?.get(0)!!.text
//            bigDisplayCardViewHolder.descriptionTv.setTextColor(
//                Color.parseColor(
//                    card.formattedDescription
//                        ?.entities?.get(0)!!.color
//                )
//            )
            bigDisplayCardViewHolder.actionBtn.text = card.cta?.get(0)!!.text
            bigDisplayCardViewHolder.actionBtn.setTextColor(
                Color.parseColor(
                    card.cta?.get(0)!!.textColor
                )
            )
            bigDisplayCardViewHolder.actionBtn.setBackgroundColor(
                Color.parseColor(
                    card.cta?.get(0)!!.bgColor
                )
            )
            bigDisplayCardViewHolder.remindLaterCv.setOnClickListener {
                bigDisplayCardViewHolder.bigCardViewRl.visibility = View.GONE
                if (onItemRemoveListener != null) onItemRemoveListener!!.onItemRemoved(position)
            }
            bigDisplayCardViewHolder.dismissNowCv.setOnClickListener {
                bigDisplayCardViewHolder.bigCardViewRl.visibility = View.GONE
                if (onItemRemoveListener != null) onItemRemoveListener!!.onItemRemoved(position)
            }
            bigDisplayCardViewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.nonScrollableLl.addView(bigDisplayCardViewHolder.itemView)
        }
    }

    private fun layNonScrollableSmallDisplayCard(
        nonScrollableLLViewHolder: NonScrollableLLViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val smallDisplayCardViewHolder = SmallDisplayCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.small_display_card_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
//            smallDisplayCardViewHolder.smallDisplayCard.setCardBackgroundColor(
//                Color.parseColor(
//                    card.bgColor
//                )
//            )
            smallDisplayCardViewHolder.smallDisplayCardTv.text =
                card.title // could be formatted title
            smallDisplayCardViewHolder.aryaStarkTv.text =
                card.description // could be formatted desc
            Glide.with(smallDisplayCardViewHolder.itemView)
                .load(card.icon?.imageUrl)
                .into(smallDisplayCardViewHolder.smallDisplayCardIv)
            smallDisplayCardViewHolder.smallDisplayCard.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.nonScrollableLl.addView(smallDisplayCardViewHolder.itemView)
        }
    }

    private fun layNonScrollableDynamicCard(
        nonScrollableLLViewHolder: NonScrollableLLViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val dynamicCardViewHolder = DynamicCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.dynamic_width_card_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
//            dynamicCardViewHolder.dynamicCardImage.setCardBackgroundColor(
//                Color.parseColor(
//                    card.bgColor
//                )
//            )
            Glide.with(dynamicCardViewHolder.itemView)
                .load(card.icon?.imageUrl)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        dynamicCardViewHolder.dynamicCardLayout.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        dynamicCardViewHolder.dynamicCardLayout.background = placeholder
                    }
                })
            nonScrollableLLViewHolder.nonScrollableLl.addView(dynamicCardViewHolder.itemView)
            dynamicCardViewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
        }
    }

    private fun layNonScrollableDynamicCard(
        nonScrollableLLViewHolder: ScrollableViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val dynamicCardViewHolder = DynamicCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.dynamic_width_card_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
//            dynamicCardViewHolder.dynamicCardImage.setCardBackgroundColor(
//                Color.parseColor(
//                    card.bgColor
//                )
//            )
            Glide.with(dynamicCardViewHolder.itemView)
                .load(card.icon?.imageUrl)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        dynamicCardViewHolder.dynamicCardLayout.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        dynamicCardViewHolder.dynamicCardLayout.background = placeholder
                    }
                })
            dynamicCardViewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.scrollableLl.addView(dynamicCardViewHolder.itemView)
        }
    }

    private fun layNonScrollableImageCard(
        nonScrollableLLViewHolder: NonScrollableLLViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val imageCardViewHolder = ImageCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.card_image_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
            Glide.with(imageCardViewHolder.itemView)
                .load(card.bgImage?.imageUrl)
                .centerCrop()
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        imageCardViewHolder.imageCardLl.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        imageCardViewHolder.imageCardLl.background = placeholder
                    }
                })
            imageCardViewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.nonScrollableLl.addView(imageCardViewHolder.itemView)
        }
    }

    private fun layNonScrollableImageCard(
        nonScrollableLLViewHolder: ScrollableViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val imageCardViewHolder = ImageCardViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.card_image_item,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
            Glide.with(imageCardViewHolder.itemView)
                .load(card.bgImage?.imageUrl)
                .centerCrop()
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        imageCardViewHolder.imageCardLl.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        imageCardViewHolder.imageCardLl.background = placeholder
                    }
                })
            imageCardViewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.scrollableLl.addView(imageCardViewHolder.itemView)
        }
    }

    private fun layNonScrollableSmallArrowCard(
        nonScrollableLLViewHolder: NonScrollableLLViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val smallCardWithArrowViewHolder = SmallCardWithArrowViewHolder(
                LayoutInflater.from(nonScrollableLLViewHolder.itemView.context)
                    .inflate(
                        R.layout.small_card_with_arrow,
                        nonScrollableLLViewHolder.itemView as ViewGroup,
                        false
                    )
            )
//            smallCardWithArrowViewHolder.smallArrowCard?.setCardBackgroundColor(
//                Color.parseColor(
//                    card.bgColor
//                )
//            )
            smallCardWithArrowViewHolder.smallArrowCardTv?.text = card.title
            smallCardWithArrowViewHolder.smallArrowCard?.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            )
            nonScrollableLLViewHolder.nonScrollableLl.addView(smallCardWithArrowViewHolder.itemView)
        }
    }

    // Currently, we are allowing small cards scrollable.
    // The same functionality needs to be applied for other types
    // of scrollable cards as well.
    private fun layScrollableSmallDisplayCard(
        scrollableViewHolder: ScrollableViewHolder,
        cards: List<Card>?
    ) {
        for (card in cards!!) {
            val smallDisplayCardViewHolder = SmallDisplayCardViewHolder(
                LayoutInflater.from(scrollableViewHolder.itemView.context)
                    .inflate(
                        R.layout.small_display_card_item,
                        scrollableViewHolder.itemView as ViewGroup,
                        false
                    )
            )
//            smallDisplayCardViewHolder.smallDisplayCard.setCardBackgroundColor(
//                Color.parseColor(
//                    card.bgColor
//                )
//            )
            smallDisplayCardViewHolder.smallDisplayCardTv.text =
                card.title // could be formatted title
            smallDisplayCardViewHolder.aryaStarkTv.text =
                card.description // could be formatted desc
            Glide.with(smallDisplayCardViewHolder.itemView)
                .load(card.icon?.imageUrl)
                .into(smallDisplayCardViewHolder.smallDisplayCardIv)
            scrollableViewHolder.scrollableLl.addView(smallDisplayCardViewHolder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return cardGroupList?.size!!
    }

    interface OnItemRemoveListener {
        fun onItemRemoved(position: Int)
    }
}