package development.dreamcatcher.muzmatchlight.adapters

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import development.dreamcatcher.muzmatchlight.R
import development.dreamcatcher.muzmatchlight.models.message.MessageEntity
import kotlinx.android.synthetic.main.listview_message.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessageAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var messages: List<MessageEntity> = ArrayList()
    private var context: Context? = null
    private val dateTimeInstance = SimpleDateFormat.getDateTimeInstance()

    private val RIGHT_SIDE = "RIGHT_SIDE"
    private val LEFT_SIDE = "LEFT_SIDE"

    private var displayedAnimationsCounter = 0

    fun setMessages(messages: List<MessageEntity>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    fun addMessage(message: MessageEntity) {
        (messages as ArrayList).add(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listview_message,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bubbleSideStatus = getSideStatus(position)

        when (bubbleSideStatus) {
            RIGHT_SIDE -> {
                holder.messageContainer.gravity  = Gravity.END
                holder.messageText?.setTextColor(ContextCompat.getColor(context!!, R.color.badgeTextRight))
                holder.messageText?.background = ContextCompat.getDrawable(context!!, R.drawable.bubble_right_no_tail)
            }
            LEFT_SIDE -> {
                holder.messageContainer.gravity  = Gravity.START
                holder.messageText?.setTextColor(ContextCompat.getColor(context!!, R.color.badgeTextLeft))
                holder.messageText?.background = ContextCompat.getDrawable(context!!, R.drawable.bubble_left_no_tail)
            }
        }

        if (shouldHaveTail(position)) {
            when(bubbleSideStatus) {
                RIGHT_SIDE -> {
                    holder.messageText?.background = ContextCompat.getDrawable(context!!,
                            R.drawable.bubble_right_with_tail)
                }
                LEFT_SIDE -> {
                    holder.messageText?.background = ContextCompat.getDrawable(context!!,
                            R.drawable.bubble_left_with_tail)
                }
            }
        }

        // Check if the message should take whole parent width
        val width: Int
        if (shouldHaveFullWidth(position)) {
            width = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        val params = LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        holder.messageText.layoutParams = params

        // Check if the message should display the timestamp
        if (shouldHaveTimestamp(position)) {
            val date = dateTimeInstance.parse(messages[position].timestamp)
            val weekday = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date)
            val time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date)
            val timeForDisplay = weekday + " " + time
            holder.messageTimestamp.text = timeForDisplay
            holder.messageTimestamp.visibility = View.VISIBLE
        } else {
            holder.messageTimestamp.visibility = View.GONE
        }

        // Set message text
        holder.messageText?.text = messages[position].messageText

        // Set new message animation
        if (displayedAnimationsCounter < messages.size) {

            // Initialize set of animations
            val animationSet = AnimationSet(true)

            // Initialize Translate Animation
            val translateAnimation: Animation = TranslateAnimation(
                    Animation.ABSOLUTE, -600.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.ABSOLUTE, 100f,
                    Animation.RELATIVE_TO_SELF, 0.0f)

            // Initialize Alpha (Fade In) Animation
            val alphaAnimation = AlphaAnimation(0f, 1f)

            // Initialize Scale Animation
            val scaleAnimation = ScaleAnimation(
                    4f, 1f,
                    1f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
            scaleAnimation.fillAfter = true

            // Join animations together
            animationSet.addAnimation(translateAnimation)
            animationSet.addAnimation(alphaAnimation)
            animationSet.addAnimation(scaleAnimation)

            // Set time for the animation set
            animationSet.duration = 700

            // Start the animation
            holder.messageContainer?.startAnimation(animationSet)
            displayedAnimationsCounter++
        }
    }

    private fun getSideStatus(position: Int) :String {
        val sideStatus = messages[position].isOwnMessage
        when(sideStatus) {
            true -> return RIGHT_SIDE
            false -> return LEFT_SIDE
        }
    }

    private fun shouldHaveTail(position: Int) :Boolean {
        val timeDifference = getTimeDifferenceFromPreviousMessage(position)
        return (!hasSameOwnerAsPreviousMessage(position) || timeDifference!= null && timeDifference > 20)
    }

    private fun shouldHaveTimestamp(position: Int) :Boolean {
        val timeDifference = getTimeDifferenceFromPreviousMessage(position)
        return (position==0 || (timeDifference!= null && timeDifference > 3600))
    }

    private fun shouldHaveFullWidth(position: Int) :Boolean {
        val timeDifference = getTimeDifferenceFromCurrentMoment(position)
        return (position==0 || (timeDifference!= null && timeDifference > 3600))
    }

    private fun getTimeDifferenceFromPreviousMessage(position: Int) :Long? {
        // Time difference between current and previous message
        if (position > 0) {
            val previousItemTime = messages[position - 1].timestamp
            val currentItemTime = messages[position].timestamp

            val previousItemTimeValue = dateTimeInstance.parse(previousItemTime)
            val currentItemTimeValue = dateTimeInstance.parse(currentItemTime)
            val timeDifferenceSeconds = (currentItemTimeValue.time - previousItemTimeValue.time) / 1000
//            Log.d("FlagTest01", previousItemTimeValue.toString())
//            Log.d("FlagTest01", currentItemTimeValue.toString())
//            Log.d("FlagTest01", timeDifferenceSeconds.toString())
            return timeDifferenceSeconds
        }
        else return null
    }

    private fun getTimeDifferenceFromCurrentMoment(position: Int) :Long? {
        // Time difference between current moment and given message
        val currentItemTime = messages[position].timestamp
        val currentTime = java.util.Date().time
        val currentItemTimeValue = dateTimeInstance.parse(currentItemTime)
        val timeDifferenceSeconds = (currentTime - currentItemTimeValue.time) / 1000
        Log.d("FlagTest01", currentTime.toString())
        Log.d("FlagTest01", currentItemTimeValue.time.toString())
        Log.d("FlagTest01", timeDifferenceSeconds.toString())
        return timeDifferenceSeconds
    }

    private fun hasSameOwnerAsPreviousMessage(position: Int) :Boolean {
        return (position > 0 && messages[position-1].isOwnMessage == messages[position].isOwnMessage)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val messageText = view.textView_messageText
    val messageTimestamp = view.textView_timestamp
    val messageContainer = view.message_container
}