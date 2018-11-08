package me.worric.kotlinplayground.ui.activities

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import me.worric.kotlinplayground.R
import me.worric.kotlinplayground.extensions.ctx
import me.worric.kotlinplayground.extensions.slideExit
import me.worric.kotlinplayground.ui.App
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager {

    val toolBar: Toolbar

    var toolbarTitle: String
        get() = toolBar.title.toString()
        set(value) {
            toolBar.title = value
        }

    fun initToolbar() {
        toolBar.inflateMenu(R.menu.menu_main)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolBar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolBar.navigationIcon = createUpDrawable()
        toolBar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(toolBar.ctx).apply { progress = 1f }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) toolBar.slideExit()
            }
        })
    }

}