package com.example.lukaszreszetow.organizationview

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sortedList: List<OrganizationListObject> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        slidingLayoutSetup()
    }

    private fun slidingLayoutSetup() {
        slideLayoutBt.setOnClickListener {
            if (slidingLayout.panelState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                slideLayoutBt.setImageDrawable(getDrawable(R.drawable.arrow_up))
                slidingHeaderTitle.visibility = View.GONE
                sliderHeaderList.visibility = View.VISIBLE
            } else {
                slidingLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                slideLayoutBt.setImageDrawable(getDrawable(R.drawable.arrow_down))
                slidingHeaderTitle.visibility = View.VISIBLE
                sliderHeaderList.visibility = View.GONE
            }
        }
        slidingLayout.isTouchEnabled = false
        setInvitationsFirstInList()
        setupHeaderList()
        setupGridList()
    }

    private fun setupHeaderList() {
        sliderHeaderList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sliderHeaderList.adapter = SliderHeaderAdapter(ArrayList(sortedList), this)
    }

    private fun calculateHeightOfScreen(): Int {
        var size = Point()
        windowManager.defaultDisplay.getSize(size)
        return size.y
    }

    private fun setupGridList() {
        val screenHeight = calculateHeightOfScreen()
        val params = slidingOrganizationsGridList.layoutParams as LinearLayout.LayoutParams
        sliderHeaderContainer.viewTreeObserver.addOnGlobalLayoutListener {
            params.height = (screenHeight * slidingLayout.anchorPoint).toInt() -
                    sliderHeaderContainer.height
            slidingOrganizationsGridList.layoutParams = params
//            var locations = IntArray(2)
//            sliderHeaderContainer.getLocationOnScreen(locations)
//            params.height = screenHeight - locations[1]
        }
        slidingOrganizationsGridList.layoutManager = GridLayoutManager(this, 3)
        slidingOrganizationsGridList.adapter = OrganizationsAdapter(sortedList, this)
    }

    private fun setInvitationsFirstInList() {
        sortedList =
                fakeDataOrganizations.sortedBy { !it.hasNews }.sortedBy { !it.isThisInvitation }
    }

    fun dpFromPx(px: Float): Int {
        return (px / resources.displayMetrics.density).toInt()
    }

    data class Organization(val name: String)
    data class OrganizationListObject(
        val organization: Organization,
        val isThisInvitation: Boolean,
        val hasNews: Boolean
    )

    companion object {
        val fakeDataOrganizations: List<OrganizationListObject> =
            listOf(
                OrganizationListObject(Organization("Organizacja numer jeden"), false, true),
                OrganizationListObject(Organization("OrganizacjaDwa"), true, false),
                OrganizationListObject(Organization("OrganizacjaDwa"), true, false),
                OrganizationListObject(Organization("OrganizacjaDwa"), true, false),
                OrganizationListObject(Organization("Krotka"), false, false),
                OrganizationListObject(Organization("Organija piec"), false, false),
                OrganizationListObject(Organization("Organija piec"), false, true),
                OrganizationListObject(Organization("Organija piec"), false, true),
                OrganizationListObject(Organization("Organija piec"), false, false),
                OrganizationListObject(Organization("Organija piec"), false, false),
                OrganizationListObject(Organization("Organija piec"), false, false),
                OrganizationListObject(Organization("To jest organizacja numer szesc"), true, false)
            )
    }
}
