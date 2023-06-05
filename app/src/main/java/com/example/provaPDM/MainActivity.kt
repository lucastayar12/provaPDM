package com.example.provaPDM

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import java.util.Currency

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(this)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListVehicles(items: List<Vehicle>) {
    Column() {
        items.forEach { item ->
            ListItem(text = { Text(text = item.formatCurrency()) },
                secondaryText = { Text(text = item.type!!.type) },
                overlineText = { Text(text = item.model) },
                trailing = { Text(text = item.status) })
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register New Car", color = Color.White) },
                backgroundColor = Color(0xFF0F5F9D)
            )
        },
        content = { MyContent(context) }
    )
}

@Composable
fun MyContent(context: Context) {
    var mExpanded by remember { mutableStateOf(false) }

    var model by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // Create a list of cities
    val mVehicleTypes = VehicleType.getList()
    // Create a string value to store the selected city


    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp))
    {

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text(text = "Model") },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Type") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )

            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                mVehicleTypes.forEach { label ->
                    DropdownMenuItem(onClick = {
                        type = label
                        mExpanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = price,
            onValueChange = {
                price = if (it.startsWith("0")) {
                    ""
                } else {
                    it
                }
            },
            label = { Text("Price") },
            visualTransformation = CurrencyAmountInputVisualTransformation(
                fixedCursorAtTheEnd = true
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )

        Button(
            onClick = {
                val vehicle = Vehicle(model, price.toFloat(), VehicleType.getType(type))
                vehicle.setStatus()
                DAOVehicle.saveVehicle(vehicle)
                price = ""
                model = ""
                type = ""
                Toast.makeText(context, "Vehicle has been added to list", Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0F5F9D)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Submit", color = Color(0xFFFFFFFF))
        }

        ListVehicles(items = DAOVehicle.getVehicles())

    }
}

@Preview
@Composable
fun PreviewGreeting() {
    MainContent(LocalContext.current)
}

