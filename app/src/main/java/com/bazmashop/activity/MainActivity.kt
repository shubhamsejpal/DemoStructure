package com.bazmashop.activity

import android.os.Bundle
import android.os.Handler
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.bazmashop.dashboard.view.ShopFragment
import com.bazmashop.R
import com.bazmashop.adapter.VpHomeAdapter
import com.bazmashop.main.base.BaseAct
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseAct() {
    override val className: String
        get() = this.javaClass.simpleName
    private var doubleBackToExitPressedOnce = false
    private var shopFragment: ShopFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTasks()
        listeners()

    }

    private fun initTasks() {
        //Sticky all Label in Bottom Navigation
        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottom_navigation.selectedItemId = R.id.action_shop
    }

    private fun listeners() {
        ivCartBag.setOnClickListener{
            showSnackbar("Cart Clicked",llRoot)
        }
        ivSearch.setOnClickListener{
            showSnackbar("Search Clicked",llRoot)
        }
        ivBack.setOnClickListener {
            showSnackbar("Back Arrow Clicked",llRoot)
        }

        //View Pager and Bottom Navigation
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    showSnackbar("Home Clicked",llRoot)
                }
//                R.id.action_shop -> act_home_vp.currentItem = 1
                R.id.action_brands -> {
                    showSnackbar("Brands Clicked",llRoot)
                }
                R.id.action_wishlist -> {
                    showSnackbar("Wishlist Clicked",llRoot)
                }
                R.id.action_me -> {
                    showSnackbar("Me Clicked",llRoot)
                }
            }
            false
        })

        act_main_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    1 -> {
                        //TextView
                        tvTitle.text = resources.getString(R.string.shemagh)
                    }
                }

                bottom_navigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

//        act_home_vp.setPageTransformer(false, FadeOutTransformation())
        act_main_vp.setOnTouchListener { v, event -> true }         // This line will use for disable the view pager scrolling
        setupViewPager(act_main_vp)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val viewPagerAdapter = VpHomeAdapter(supportFragmentManager)
        shopFragment = ShopFragment()
        viewPagerAdapter.addFragmentTabName(shopFragment, ShopFragment::class.java.simpleName)
        viewPager.adapter = viewPagerAdapter
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
