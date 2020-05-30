package com.example.com.data.entity.other

class InitializationStatus(val status: Status = Status.NOT_INITIALIZED, val error: String = "") {

    enum class Status {
        NOT_INITIALIZED,
        INITIALIZED,
        INITIALIZED_NO_SESSION,
        ERROR
    }
}