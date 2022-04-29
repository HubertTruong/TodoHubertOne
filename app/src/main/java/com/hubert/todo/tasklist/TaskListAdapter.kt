package com.hubert.todo.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hubert.todo.R

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    // Déclaration de la variable lambda dans l'adapter:
    var onClickDelete: (Task) -> Unit = {}
    var onClickEdit: (Task) -> Unit = {}


    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            val titleTextView = itemView.findViewById<TextView>(R.id.task_title)
            titleTextView.text = task.title
            val descTextView = itemView.findViewById<TextView>(R.id.task_description)
            descTextView.text = task.description
            val deleteButton = itemView.findViewById<ImageButton>(R.id.imageButtonDelete)
            deleteButton.setOnClickListener {
                onClickDelete(task)
            }
            val editButton = itemView.findViewById<ImageButton>(R.id.imageButtonEdit)
            editButton.setOnClickListener {
                onClickEdit(task)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}