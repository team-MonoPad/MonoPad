package com.project.monopad.ui.base

import android.util.Log.d
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
        d("베이스 뷰모델","add Disposbale")
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}