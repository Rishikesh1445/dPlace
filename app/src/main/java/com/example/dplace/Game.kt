package com.example.dplace

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch

@Composable
fun Game(){
    val coroutineScope = rememberCoroutineScope()
    lateinit var socket: Socket
    DisposableEffect(Unit){
        coroutineScope.launch {
            try {
                socket = IO.socket("http://127.0.0.1:4000")
                socket.connect()
                socket.on(Socket.EVENT_CONNECT) {
                    Log.d("SocketIO", "Connected to server")
                }
                socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
                    Log.e("SocketIO", "Connection error: ${args[0]}")
                }
                socket.on(Socket.EVENT_DISCONNECT) {
                    Log.d("SocketIO", "Disconnected from server")
                }
                Log.d("Rishi", socket.connected().toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Rishi", e.toString())
            }
        }
        onDispose {
            socket.disconnect()
            socket.off("updateArray")
        }
    }

    var colourState by remember{ mutableStateOf(0) }
    var colourList by remember { mutableStateOf(List(100){0}) }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        LazyVerticalGrid(columns = GridCells.Fixed(10) ){
            items(100){itemNo->
                Button(onClick = { colourList = colourList.toMutableList().also { it[itemNo] = colourState }},shape = RoundedCornerShape(2.dp), modifier = Modifier
                    .aspectRatio(1f)
                    .padding(2.dp),
                    colors = ButtonDefaults.buttonColors(colour(colourList[itemNo]))) {

                }
            }
        }

        Spacer(modifier = Modifier.padding(32.dp))

        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { colourState=1 }, colors = ButtonDefaults.buttonColors(Color.Red), shape = RoundedCornerShape(20)) {
                
            }
            Button(onClick = { colourState=2 }, colors = ButtonDefaults.buttonColors(Color.Blue), shape = RoundedCornerShape(20)) {
                
            }
            Button(onClick = { colourState=3 }, colors = ButtonDefaults.buttonColors(Color.Green), shape = RoundedCornerShape(20)) {
                
            }
            Button(onClick = { colourState=4 }, colors = ButtonDefaults.buttonColors(Color.Yellow), shape = RoundedCornerShape(20)) {
                
            }
        }
    }
}

fun colour(number:Int):Color{
    if(number==1){
        return Color.Red
    }
    if(number==2){
        return Color.Blue
    }
    if(number==3){
        return Color.Green
    }
    if(number==4){
        return Color.Yellow
    }
   return Color.White
}