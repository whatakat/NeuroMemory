package com.bankmtk.neuromemory.ui.main


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.alert.AlertActivity
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.splash.SplashActivity
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
class MainActivity : BaseActivity<List<Sticker>?>() {

    @ExperimentalCoroutinesApi
    override val model:MainViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        overridePendingTransition(R.anim.slidein,R.anim.slideout)


        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemLongClick(sticker: Sticker) {
                openStickerScreen(sticker)
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemClick(itemView: View) {
                if (itemView.langOneI.visibility == View.VISIBLE){
                    animateView(itemView)
                }else{
                    animateViewCancel(itemView)
                }
            }
        })
        myRecycler.adapter = adapter

        fab.setOnClickListener { openStickerScreen(null) }
    }

    override fun renderData(data: List<Sticker>?) {
        if (data==null) return
        adapter.stickers = data
    }
    private fun openStickerScreen(sticker: Sticker?){
        StickerActivity.start(this,sticker?.id)
    }

     fun alertMe(view: View){
         val alertIntent = Intent(this,AlertActivity::class.java)
         startActivity(alertIntent)

//        val myToast = Toast.makeText(this,"You have no item",Toast.LENGTH_SHORT)
//        myToast.show()
    }

    companion object{
        fun getStartIntent(context: Context) = Intent(context,
        MainActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.logout -> showLogoutDialog().let{true}
            else -> false
    }
    private fun showLogoutDialog(){
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.ok_bth_title){onLogout()}
            negativeButton(R.string.logout_dialog_cancel){dialog -> dialog.dismiss()  }
        }.show()
    }
     fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener{
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slidein,R.anim.slideout)
    }


}



