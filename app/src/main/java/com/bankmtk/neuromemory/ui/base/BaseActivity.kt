package com.bankmtk.neuromemory.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bankmtk.neuromemory.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

abstract class BaseActivity <T, S : BaseViewState<T>> : AppCompatActivity(){

    abstract val viewModel: BaseViewModel<T,S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe(this, object : Observer<S>{
            override fun onChanged(t:S?){
                if (t == null) return
                if (t.data != null) renderData(t.data!!)
                if (t.error != null)renderError(t.error)
            }
        })
    }
    protected fun renderError(error: Throwable){
        if (error.message != null) showError(error.message!!)
    }
    abstract fun renderData(data: T)

    protected fun showError(error: String){
        val snackbar = Snackbar.make(myRecycler,error,
        Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.ok_bth_title,View.OnClickListener {
            snackbar.dismiss()})
        snackbar.show()
    }
}