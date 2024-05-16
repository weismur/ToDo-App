package com.example.unit_tests.presentation.modalDialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    task: Task,
    onChangeTaskName: (name: String) -> Unit,
    onChangeTaskDescription: (name: String) -> Unit,
    addNewTask: () -> Unit,
    onExit: () -> Unit,
    openGroupDialog: () -> Unit,
    groups: List<Group>
) {

    val groupName = remember {
        mutableStateOf("")
    }

    groupName.value = findGroupName(groups, task)

    AlertDialog(onDismissRequest = { onExit() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp)
        ) {
            OutlinedTextField(value = task.taskName, onValueChange = {
                onChangeTaskName(it)
            }, placeholder = { Text(text = "Название задачи") })
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(value = task.taskDescription, onValueChange = {
                onChangeTaskDescription(it)
            }, placeholder = { Text(text = "Описание задачи") })
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = { openGroupDialog() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0x1F9395D3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Группа: ${groupName.value}", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = { addNewTask() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9395D3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (task.idTask == 0) "Добавить задачу" else "Изменить задачу")
            }
        }
    }
}

fun findGroupName(groups: List<Group>, task: Task): String {
    val index = groups.indexOfFirst {
        it.idGroup == task.groupNumber
    }
    return if (index == -1) "" else groups[index].groupName
}
