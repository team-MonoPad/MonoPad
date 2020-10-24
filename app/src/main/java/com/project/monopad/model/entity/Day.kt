package com.project.monopad.model.entity

data class Day (
    var year : Int,
    var month : Int,
    var day : Int,
    var reviews : List<Review>
)