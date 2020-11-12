package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.monopad.data.model.network.response.PersonDetailCreditsCastResponse
import com.project.monopad.data.model.network.response.PersonDetailResponse
import com.project.monopad.data.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.AppUtil.API_KEY
import com.project.monopad.util.AppUtil.KR_LANGUAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PersonViewModel (private val repo: MovieRepoImpl) : BaseViewModel(){

    private val _personDetailInfo = MutableLiveData<PersonDetailResponse>()
    val personDetailInfo = _personDetailInfo

    private val _personDetailMovieData = MutableLiveData<List<PersonDetailCreditsCastResponse>>()
    val personDetailMovie = _personDetailMovieData

    private val _count = MutableLiveData<Int>()
    val count : LiveData<Int>
        get() = _count

    init{
        _count.value = 0
    }

    fun getPersonDetail(people_Id: Int) {
        /* 배우 상세 정보 가져오기 */
        addDisposable(
            repo.getPeopleDetail(
                people_id = people_Id,
                apikey = API_KEY,
                language = KR_LANGUAGE,
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _personDetailInfo.value = it
                    _count.value = (_count.value)?.plus(1)
                }, {
                    Log.d("PERSON VIEWMODEL", it.localizedMessage)
                })
        )
    }

    fun getPersonDetailCredits(people_Id : Int) {
        /* 배우 출연 작품 데이터 가져오기 */
        addDisposable(
            repo.getPersonDetailCredits(
                people_id = people_Id,
                apikey = API_KEY,
                language = KR_LANGUAGE,
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        _personDetailMovieData.value = this.cast
                        _count.value = (_count.value)?.plus(1)
                    }

                }, {
                    Log.d("PERSON VIEWMODEL", it.localizedMessage)
                })
        )
    }

}