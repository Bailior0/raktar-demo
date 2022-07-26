package com.example.raktarappjustui1.data

data class Storage(
    var name: String,
    var address: String,
    var size: Double,
    var description: String,
    var workers: List<Worker>
)
