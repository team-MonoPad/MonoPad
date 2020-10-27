package com.project.monopad.util

import com.project.monopad.model.network.response.MovieInfoResultResponse
import java.util.*

/**
 * ArrayList 객체의 특정 필드 값을 기준으로 정렬하기 위해 사용
 */
internal class DateComparator : Comparator<MovieInfoResultResponse?> {
    override fun compare(o1: MovieInfoResultResponse?, o2: MovieInfoResultResponse?): Int {
        return o1!!.release_date.compareTo(o2!!.release_date)
    }
}