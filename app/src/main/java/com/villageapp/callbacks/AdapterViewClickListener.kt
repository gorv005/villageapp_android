package com.villageapp.callbacks

interface AdapterViewClickListener<T> {

    fun onClickAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}