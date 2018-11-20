package com.example.lukaszreszetow.organizationview

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.nex3z.notificationbadge.NotificationBadge
import kotlinx.android.synthetic.main.organization_item.view.*

class OrganizationsAdapter(
    val organizations: List<MainActivity.OrganizationListObject>,
    val context: Context,
    val listener: OrganizationInterface
) :
    RecyclerView.Adapter<OrganizationsAdapter.ViewHolder>() {

    interface OrganizationInterface {
        fun organizedPicked(organizationId: Int, isThisInvitation: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.organization_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return organizations.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position == organizations.size) {
            addFindMoreButton(holder)
        } else {
            val organizationObject = organizations[position]
            holder?.container?.setOnClickListener {
                listener.organizedPicked(position, organizationObject.isThisInvitation)
            }
            holder?.name?.text = organizationObject.organization.name
            when {
                organizationObject.isThisInvitation -> {
                    holder?.name?.alpha = 0.5f
                    holder?.icon?.alpha = 0.5f
                    holder?.badge?.badgeBackgroundDrawable =
                            context.getDrawable(R.drawable.andrzej_badge)
                    holder?.badge?.setText("")
                }
                organizationObject.hasNews -> {
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

    private fun addFindMoreButton(holder: ViewHolder?) {
        holder?.badge?.visibility = View.GONE
        holder?.name?.text = "Find more"
        holder?.name?.setTextColor(context.resources.getColor(R.color.mainColor, context.theme))
        holder?.icon?.setImageDrawable(context.getDrawable(R.drawable.andrzej))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.followed_organization_icon
        val name: TextView = view.followed_organization_name
        val badge: NotificationBadge = view.organizationBadge
        val container: RelativeLayout = view.sliderOrganizationTemplate
    }
}