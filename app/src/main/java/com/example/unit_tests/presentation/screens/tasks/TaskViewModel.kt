package com.example.unit_tests.presentation.screens.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.useCases.AddNewGroupUseCase
import com.example.unit_tests.domain.useCases.AddNewTaskUseCase
import com.example.unit_tests.domain.useCases.DeleteTaskUseCase
import com.example.unit_tests.domain.useCases.GetAllGroupsUseCase
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
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val addNewGroupUseCase: AddNewGroupUseCase
) : ViewModel() {

    private var _tasks = MutableStateFlow(listOf<Task>())
    var tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var _groups = MutableStateFlow(listOf<Group>())
    var groups: StateFlow<List<Group>> = _groups.asStateFlow()

    private var _filteredTasks = MutableStateFlow(listOf<Task>())
    var filteredTasks: StateFlow<List<Task>> = _filteredTasks.asStateFlow()

    private val _openAddTaskDialog = mutableStateOf(false)
    var openAddTaskDialog: State<Boolean> = _openAddTaskDialog

    private val _openAddGroupDialog = mutableStateOf(false)
    var openAddGroupDialog: State<Boolean> = _openAddGroupDialog

    private val _openChooseDialog = mutableStateOf(false)
    var openChooseDialog: State<Boolean> = _openChooseDialog

    private val _groupsDialog = mutableStateOf(false)
    var groupsDialog: State<Boolean> = _groupsDialog

    private val _isToastShown = mutableStateOf(false)
    var isToastShown: State<Boolean> = _isToastShown

    private val _toastText = mutableStateOf("")
    var toastText: State<String> = _toastText

    private val _searchField = mutableStateOf("")
    var searchField: State<String> = _searchField

    private var _newTask = MutableStateFlow(Task(taskName = "", taskDescription = ""))
    var newTask: StateFlow<Task> = _newTask.asStateFlow()

    private var _newGroup = MutableStateFlow(Group(groupName = ""))
    var newGroup: StateFlow<Group> = _newGroup.asStateFlow()

    init {
        getAllTasks()
        getAllGroups()
    }

    private fun getAllGroups() = viewModelScope.launch {
        getAllGroupsUseCase.invoke().collectLatest {
            _groups.value = it
        }
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

    fun changeAddGroupDialogState(state: Boolean) {
        _openAddGroupDialog.value = state
    }

    fun changeOpenChooseDialog(state: Boolean) {
        _openChooseDialog.value = state
    }

    fun changeGroupsDialog(state: Boolean) {
        _groupsDialog.value = state
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

    fun changeGroupName(groupName: String) {
        _newGroup.update {
            it.copy(groupName = groupName)
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
                it.copy(idTask = 0, taskName = "", taskDescription = "", taskStatus = 0, groupNumber = null)
            }
        }
    }

    fun addNewGroup() = viewModelScope.launch {
        if (_newGroup.value.groupName == "") {
            _toastText.value = "Название группы не должно быть пустым!"
            _isToastShown.value = true
        } else {
            addNewGroupUseCase.invoke(_newGroup.value)
            _openAddGroupDialog.value = false
            _newGroup.update {
                it.copy(idGroup = 0, groupName = "")
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
            it.copy(idTask = 0, taskName = "", taskDescription = "", taskStatus = 0, groupNumber = null)
        }
        _openAddTaskDialog.value = false
    }

    fun clearGroup() {
        _newGroup.update {
            it.copy(idGroup = 0, groupName = "")
        }
        _openAddGroupDialog.value = false
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

    fun changeSelectedGroup(id: Int) {
        _newTask.update {
            it.copy(groupNumber = id)
        }
        _groupsDialog.value = false
    }
}