package com.project.monopad.haeseong.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappsample.base.NetworkManager
import com.project.monopad.haeseong.model.response.LatestMovieResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class HomeViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorSubject = BehaviorSubject.create<Throwable>()
    val loadingSubject = BehaviorSubject.createDefault(false)
    val latestMovieSubject = BehaviorSubject.create<LatestMovieResponse>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}