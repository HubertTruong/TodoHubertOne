package com.hubert.todo.tasklist

import kotlinx.serialization.Serializable
import java.io.FileDescriptor

@Serializable
data class Task(
    val id : String,
    val title : String,
    val description: String = "description"
) : java.io.Serializable
