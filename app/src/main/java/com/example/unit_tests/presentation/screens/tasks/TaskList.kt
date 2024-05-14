package com.example.unit_tests.presentation.screens.tasks

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.unit_tests.R
import com.example.unit_tests.presentation.modalDialog.AddTaskDialog
import es.dmoral.toasty.Toasty


@Composable
fun TaskList() {

    val taskViewModel: TaskViewModel = hiltViewModel()
    val filteredTasks = taskViewModel.filteredTasks.collectAsState()
    val openAddTaskDialog = taskViewModel.openAddTaskDialog
    val newTask = taskViewModel.newTask.collectAsState()
    val isToastShown = taskViewModel.isToastShown
    val toastText = taskViewModel.toastText
    val searchField = taskViewModel.searchField

    if (openAddTaskDialog.value) {
        AddTaskDialog(
            newTask.value,
            onChangeTaskName = { taskViewModel.changeTaskName(it) },
            onChangeTaskDescription = { taskViewModel.changeTaskDescription(it) },
            addNewTask = { taskViewModel.addNewTask() },
            onExit = { taskViewModel.clearTask() })
    }

    if (isToastShown.value) {
        Toasty.error(LocalContext.current, toastText.value, Toast.LENGTH_LONG).show()
        taskViewModel.changeToastShown()
    }

    Scaffold(topBar = {
        Text(text = "ToDo App", modifier = Modifier.padding(20.dp))
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { taskViewModel.changeAddTaskDialogState(true) },
            containerColor = Color(0xFF9395D3)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(Color(0xFFB3B7EE))
        ) {
            item {
                OutlinedTextField(
                    value = searchField.value,
                    onValueChange = { taskViewModel.changeSearchField(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }
            itemsIndexed(filteredTasks.value) { index, it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = it.taskName, color = Color(0xFF9395D3), fontSize = 16.sp)
                            Text(
                                text = it.taskDescription,
                                color = Color(0xFF000000),
                                fontSize = 14.sp
                            )
                        }
                        Row {
                            IconButton(onClick = {
                                taskViewModel.editTask(index)
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.pencil),
                                    contentDescription = null,
                                    tint = Color(0xFF9395D3)
                                )
                            }
                            IconButton(onClick = { taskViewModel.deleteTaskUseCase(index) }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.trash),
                                    contentDescription = null,
                                    tint = Color(0xFF9395D3)
                                )
                            }
                            IconButton(onClick = { taskViewModel.changeTaskStatus(index) }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.checkcircle),
                                    contentDescription = null,
                                    tint = if (it.taskStatus == 0) Color.Red else Color.Green
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}