package com.example.unit_tests.presentation.screens.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.useCases.AddNewTaskUseCase
import com.example.unit_tests.domain.useCases.DeleteTaskUseCase
import com.example.unit_tests.domain.useCases.GetAllTasksUseCase
import com.example.unit_tests.domain.useCases.changeTaskStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val addNewTaskUseCase: AddNewTaskUseCase,
    private val changeTaskStatusUseCase: changeTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private var _tasks = MutableStateFlow(listOf<Task>())
    var tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var _filteredTasks = MutableStateFlow(listOf<Task>())
    var filteredTasks: StateFlow<List<Task>> = _filteredTasks.asStateFlow()

    private val _openAddTaskDialog = mutableStateOf(false)
    var openAddTaskDialog: State<Boolean> = _openAddTaskDialog

    private val _isToastShown = mutableStateOf(false)
    var isToastShown: State<Boolean> = _isToastShown

    private val _toastText = mutableStateOf("")
    var toastText: State<String> = _toastText

    private val _searchField = mutableStateOf("")
    var searchField: State<String> = _searchField

    private var _newTask = MutableStateFlow(Task(taskName = "", taskDescription = ""))
    var newTask: StateFlow<Task> = _newTask.asStateFlow()

    init {
        getAllTasks()
    }

    private fun getAllTasks() = viewModelScope.launch {
        getAllTasksUseCase.invoke().collectLatest {
            _tasks.value = it
            filterTasks()
        }
    }

    private fun filterTasks() {
        if (_searchField.value == "") {
            _filteredTasks.value = _tasks.value
        }
        else {
            _filteredTasks.value = _tasks.value.filter {
                it.taskName.contains(_searchField.value, ignoreCase = true)
            }
        }
    }


    fun changeAddTaskDialogState(state: Boolean) {
        _openAddTaskDialog.value = state
    }

    fun changeTaskName(taskName: String) {
        _newTask.update {
            it.copy(taskName = taskName)
        }
    }

    fun changeTaskDescription(taskDescription: String) {
        _newTask.update {
            it.copy(taskDescription = taskDescription)
        }
    }

    fun addNewTask() = viewModelScope.launch {
        if (_newTask.value.taskName == "") {
            _toastText.value = "Название задачи не должно быть пустым!"
            _isToastShown.value = true
        } else if (_newTask.value.taskDescription == "") {
            _toastText.value = "Описание задачи не должно быть пустым!"
            _isToastShown.value = true
        } else {
            addNewTaskUseCase.invoke(_newTask.value)
            _openAddTaskDialog.value = false
            _newTask.update {
                it.copy(idTask = 0, taskName = "", taskDescription = "", taskStatus = 0)
            }
        }
    }

    fun changeTaskStatus(index: Int) = viewModelScope.launch {
        changeTaskStatusUseCase.invoke(
            Task(
                idTask = _tasks.value[index].idTask,
                taskName = _tasks.value[index].taskName,
                taskDescription = _tasks.value[index].taskDescription,
                taskStatus = if (_tasks.value[index].taskStatus == 0) 1 else 0
            )
        )
    }

    fun editTask(index: Int) {
        _newTask.update {
            it.copy(idTask = _tasks.value[index].idTask,
                taskName = _tasks.value[index].taskName,
                taskDescription = _tasks.value[index].taskDescription,
                taskStatus = _tasks.value[index].taskStatus
            )
        }
        _openAddTaskDialog.value = true
    }

    fun clearTask() {
        _newTask.update {
            it.copy(idTask = 0, taskName = "", taskDescription = "", taskStatus = 0)
        }
        _openAddTaskDialog.value = false
    }

    fun deleteTaskUseCase(index: Int) = viewModelScope.launch {
        deleteTaskUseCase.invoke(_tasks.value[index])
    }

    fun changeToastShown() {
        _isToastShown.value = false
    }

    fun changeSearchField(value: String) {
        _searchField.value = value
        filterTasks()
    }
}