package com.example.lukaszreszetow.organizationview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.organization_details_view.*

class OrganizationDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.organization_details_view)
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        organizationDetailsCollapseBT.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(0, R.anim.animation)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}
