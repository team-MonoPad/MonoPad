package com.project.monopad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.model.network.response.MovieCastResponse
import com.project.monopad.model.network.response.MovieCrewResponse
import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val DIRECTOR = "Director"

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()
    val movieDetailData = _movieDetailData

    private val _movieCastData = MutableLiveData<List<MovieCastResponse>>()
    val movieCastData = _movieCastData

    private val _movieCrewData = MutableLiveData<List<MovieCrewResponse>>()
    val movieCrewData = _movieCrewData

    fun getDetailData(){
        /* 영화 상세 정보 데이터 가져오기 */
        addDisposable(repo.getMovieDetail(
            movie_id = 396535,
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieDetailData.value = it
                }
            },{
                //
            })
        )

        /* 영화 출연진, 스태프 정보 가져오기 */
        addDisposable(repo.getMovieCredits(
            movie_id = 396535,
            apikey = "84301bd818cef2f63643e7dffa8998ab",
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieCastData.value = it.cast
                    _movieCrewData.value = it.crew
                }
            },{
                //
            })
        )
    }

    /* view에 보여질 데이터 적절히 parsing */
    fun releaseDateParsing(releaseDate: String) = releaseDate.replace("-","/")

    fun runtimeParsing(runtime: Int) = runtime.let { "${it/60}h ${it-(it/60)*60}m" }

    fun genreParsing(genreList: List<Genre>) : String{
        var genre = ""
        for(g in genreList){
            genre += "${g.name}, "
        }
        return genre.substring(0, genre.length-2)
    }

    fun directorParsing(crewList : List<MovieCrewResponse>) : String{
        for(crew in crewList){
            if(crew.job == DIRECTOR)  return "Director : ${crew.name}"
        }
        return ""
    }

    fun casterParsing(casters: List<MovieCastResponse>): List<MovieCastResponse> {
        val list = ArrayList<MovieCastResponse>()
        for(c in casters){
            if(c.profile_path!=null) list.add(c)
        }
        return list
    }
}


