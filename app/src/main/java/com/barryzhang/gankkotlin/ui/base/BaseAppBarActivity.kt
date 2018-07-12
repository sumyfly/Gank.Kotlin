package com.barryzhang.gankkotlin.ui.base

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.barryzhang.gankkotlin.R

/**
 * https://github.com/barryhappy
 * Created by Barry on 16/8/13
 */
abstract class BaseAppBarActivity : BaseActivity() {

    val mLayoutContent: ViewGroup by lazy { findViewById<ViewGroup>(R.id.mLayoutContent) }
    val fab: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.fab) }
    val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_base_app_bar)
        layoutInflater.inflate(layoutResID, mLayoutContent)
    }


}