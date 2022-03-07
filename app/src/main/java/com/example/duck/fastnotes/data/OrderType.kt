package com.example.duck.fastnotes.data

import com.example.duck.fastnotes.features.create.ColorTypeWrapper

sealed class OrderType(val type: String? = null, val color: ColorTypeWrapper? = null) {

    object Normal : OrderType()
    object Time: OrderType("Time")
    class Color(color: ColorTypeWrapper): OrderType("Color", color)
}