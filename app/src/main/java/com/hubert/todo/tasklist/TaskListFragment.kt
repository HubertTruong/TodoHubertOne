package com.hubert.todo.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hubert.todo.R
import java.util.*

class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        adapter.currentList = taskList

        val addButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            refreshAdapter()
        }

    }

    fun refreshAdapter(){
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }


}