package com.hubert.todo.tasklist

import java.io.FileDescriptor

data class Task(
    val id : String,
    val title : String,
    val description: String = "description"
) : java.io.Serializable
