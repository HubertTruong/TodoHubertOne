package com.hubert.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hubert.todo.R
import com.hubert.todo.form.FormActivity
import com.hubert.todo.network.Api
import com.hubert.todo.network.TasksListViewModel
import kotlinx.coroutines.launch
import java.util.*

class TaskListFragment : Fragment() {

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?: return@registerForActivityResult
        viewModel.create(task)
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val newTask = result.data?.getSerializableExtra("task") as? Task
        viewModel.update(newTask ?: return@registerForActivityResult)
    }

    private val adapter = TaskListAdapter()

    private val viewModel: TasksListViewModel by viewModels()

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

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                adapter.currentList = newList
                adapter.notifyDataSetChanged()
            }
        }

        val addButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)
        }

        // "implémentation" de la lambda dans le fragment:
        adapter.onClickDelete = { task ->
            viewModel.delete(task)
        }

        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        val tmp = requireView().findViewById<TextView>(R.id.textView)
        //val imageView = view?.findViewById<ImageView>(R.id.imageView)
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            tmp.text = "Bonjour " + userInfo.firstName + " " + userInfo.lastName
            //viewModel.refresh()
        }
    }
}