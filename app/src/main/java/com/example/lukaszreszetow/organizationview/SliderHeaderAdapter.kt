package com.example.lukaszreszetow.organizationview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nex3z.notificationbadge.NotificationBadge
import kotlinx.android.synthetic.main.header_item.view.*


class SliderHeaderAdapter(
    val itemsList: ArrayList<MainActivity.OrganizationListObject>,
    val context: Context
) : RecyclerView.Adapter<SliderHeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return SliderHeaderAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.header_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemsList.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position == itemsList.size) {
            holder?.badge?.clear()
            holder?.icon?.setImageDrawable(context.getDrawable(R.drawable.andrzej))
        } else {
            val item = itemsList[position]
            holder?.icon?.setImageDrawable(context.getDrawable(R.drawable.icon_mockup))
            when {
                item.isThisInvitation -> {
                    holder?.badge?.badgeBackgroundDrawable =
                            context.getDrawable(R.drawable.andrzej_badge)
                    holder?.badge?.setText("")
                }
                item.hasNews -> {
                    holder?.badge?.badgeBackgroundDrawable =
                            context.getDrawable(R.drawable.badge_background)
                    holder?.badge?.setNumber(1)
                }
                else -> {
                    holder?.badge?.clear()
                }
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.sliderHeaderIcon
        val badge: NotificationBadge = view.sliderHeaderBadge
    }
}