package com.example.unit_tests.presentation.modalDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsDialog(
    groups: List<Group>,
    onExit: () -> Unit,
    changeSelectedGroup: (id: Int) -> Unit
) {
    AlertDialog(onDismissRequest = { onExit() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (groups.isEmpty()) {
                Text(text = "Вы ещё не добавили ни одной группы!")
            } else {
                groups.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { changeSelectedGroup(it.idGroup) }) {
                        Text(text = it.groupName)
                    }
                }
            }
        }
    }
}