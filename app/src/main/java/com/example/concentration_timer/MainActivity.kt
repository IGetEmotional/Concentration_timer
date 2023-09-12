package com.example.concentration_timer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.concentration_timer.ui.theme.Concentration_timerTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {

   // lateinit var db: RecordDatabase
  //  lateinit var dao: RecordDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Concentration_timerTheme {
              //  val state by viewModel._state.collectAsState()

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                Button(onClick = {
                                 val navigate = Intent(this@MainActivity, ConcTimerActivity::class.java)
                    startActivity(navigate)
                                 }, Modifier.padding(50.dp)) {
                    Text(text = "Click me")
                }

                ConcentrateList()
            }
            }

        }
    }

    @Composable
    fun ConcentrateList(
        mainViewModel: MainViewModel = viewModel(factory = MainViewModel.fatory)
    ){

        val itemList = mainViewModel.itemsList.collectAsState(initial = emptyList())


    //    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
     //       items(20){
     //           Text(text = "Ally", modifier = Modifier.padding(20.dp).fillMaxWidth())
    //            Divider()
    //        }
     //      }
        LazyColumn(Modifier.fillMaxSize(),verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(itemList.value) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp), verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = (DecimalFormat("00").format((it.declaredTime).toInt()/3600)) + ":" +
                        DecimalFormat("00").format(((it.declaredTime).toInt()/60 - (it.declaredTime).toInt()/3600 * 60)), modifier = Modifier.weight(2f))
                    Text(text = it.isComplete.toString(),modifier = Modifier.weight(1f))
                    Button(onClick = { mainViewModel.deleteItem(it) }, Modifier.weight(1f)) {
                        Text(text = "Delete")
                    }
                        
                    }
                }
            }
        }

    }





