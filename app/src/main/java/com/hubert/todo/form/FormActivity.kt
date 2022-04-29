package com.hubert.todo.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hubert.todo.R
import com.hubert.todo.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val task = intent.getSerializableExtra("task") as? Task
        val editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val validateButton = findViewById<Button>(R.id.buttonValidate)

        val id = task?.id ?: UUID.randomUUID().toString()
        editTextTitle.setText(task?.title)
        editTextDescription.setText(task?.description)

        validateButton.setOnClickListener {
            val newTask = Task(id = id, title = editTextTitle.text.toString(), description = editTextDescription.text.toString())
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}