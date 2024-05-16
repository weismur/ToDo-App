package com.example.unit_tests.presentation.modalDialog

import androidx.compose.foundation.background
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
fun AddGroupDialog(
    group: Group,
    onChangeGroupName: (name: String) -> Unit,
    addNewGroup: () -> Unit,
    onExit: () -> Unit
) {
    AlertDialog(onDismissRequest = { onExit() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp)
        ) {
            OutlinedTextField(value = group.groupName, onValueChange = {
                onChangeGroupName(it)
            }, placeholder = { Text(text = "Название группы") })
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = { addNewGroup() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9395D3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Добавить группу")
            }
        }
    }
}