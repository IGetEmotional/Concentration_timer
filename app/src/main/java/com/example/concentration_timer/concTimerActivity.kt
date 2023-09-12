package com.example.concentration_timer

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.concentration_timer.ui.theme.Concentration_timerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat

enum class ProgressBarColor(val gradientStart: Color, val gradientEnd: Color) {
    Red(Color(254, 222, 224), Color(255, 31, 43)),
    Green(Color(168, 242, 205), Color(38, 222, 129)),
    Blue(Color(219, 229, 251), Color(75, 123, 236)),
    Purple(Color(224, 204, 255), Color(98, 0, 254, 255));

    operator fun invoke(): Color {
        return gradientEnd
    }
}

val trackColor = White

class ConcTimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Concentration_timerTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                        Timer()
                }

            }
        }

    }


    @Composable
    @Preview
    fun Timer(
        timerViewModel: TimerViewModel = viewModel(factory = TimerViewModel.fatory
        )) {

        val maxMinute = 59

        var writtenTime by remember {
            mutableStateOf("")
        }
        var writtenTimeMin by remember {
            mutableStateOf("")
        }
        var timeSeconds by remember {
            mutableStateOf(0)
        }

        var timeFixed by remember {
            mutableStateOf(0)
        }

        var timeProgress by remember{
            mutableStateOf(0)
        }

        var isTimerRunning by remember {
            mutableStateOf(false)
        }


        LaunchedEffect(key1 = timeSeconds, key2 = isTimerRunning) {
            /*
            if (writtenTime.isNotBlank() && writtenTime.toInt() > 0 && isTimerRunning) {
                delay(1000)
                writtenTime = (writtenTime.toInt() - 1).toString()
                Log.d("TIME FIXED", timeFixed.toString())
                if(writtenTime.toInt() == 0)
                    isTimerRunning = !isTimerRunning
            }

             */

            if (isTimerRunning) {
                delay(1000)
                if(timeSeconds == 0){
                    if(writtenTimeMin.toInt() != 0){
                        timeSeconds = 59
                        writtenTimeMin = (writtenTimeMin.toInt() -1).toString()
                    }
                    else if(writtenTimeMin.toInt() == 0 && writtenTime.toInt() != 0){
                        writtenTime = (writtenTime.toInt() - 1).toString()
                        writtenTimeMin = "59"
                        timeSeconds = 59
                    }
                }
                else{
                timeSeconds -= 1
                }
                timeProgress = (writtenTime.toInt() * 3600 + writtenTimeMin.toInt() * 60 + timeSeconds)
             //   if(writtenTime.toInt() == 0 && writtenTimeMin.toInt() == 0 && timeSeconds == 0)
                if(timeProgress == 0) {
                    timerViewModel.insertItem(timeFixed.toString(), true)
                    isTimerRunning = !isTimerRunning
                }
            }


        }


     //   Column(modifier = Modifier
     //       .fillMaxSize()
     //       .padding(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
              //  if (!isTimerRunning) {
                    TextField(
                        value = writtenTime,
                        onValueChange = { text ->
                                if( text.isNotBlank()) {
                                    writtenTime = text
                                }
                            if(text.isBlank()){
                                writtenTime = ""
                            }

                        },
                        Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        enabled = !isTimerRunning

                    )
                //////////////////////////////////////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.width(20.dp))
                TextField(
                    value = writtenTimeMin,
                    onValueChange = { text ->
                        if( text.isNotBlank() && text.toInt() <= maxMinute) {
                            writtenTimeMin = text
                        }
                        if(text.isBlank()){
                            writtenTimeMin = ""
                        }

                    },
                    Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    enabled = !isTimerRunning

                )
                Spacer(modifier = Modifier.width(20.dp))
                ///////////////////////////////////////////////////////////////////////////////////////////////////
            //    }
                Button(onClick = {
            /*
                    if(writtenTime.isNotBlank() && writtenTime.toInt() != 0) {
                        timeFixed = writtenTime.toInt()
                        isTimerRunning = !isTimerRunning
                    }
             */

                    timeSeconds = 0
                    if (writtenTime.isBlank())
                        writtenTime = "0"
                    if (writtenTimeMin.isBlank())
                        writtenTimeMin = "0"
                    if(writtenTime == "0" &&  writtenTimeMin == "0"){

                    }
                    else {
                        if(!isTimerRunning) {
                            timeFixed =
                                writtenTime.toInt() * 3600 + writtenTimeMin.toInt() * 60 + timeSeconds
                        }
                        if (timeFixed != 0) {
                            if(isTimerRunning)
                                timerViewModel.insertItem(timeFixed.toString(), false)
                            isTimerRunning = !isTimerRunning
                        }
                    }


                  // Log.d("SMT", (writtenTime.toFloat()/timeFixed.toFloat()).toString())
                }, Modifier.weight(1f)) {
                    Text(text = "Старт")
                }
            }

                DrawProgressBar(
                    isTimerRunning = isTimerRunning,
                    timeProgress = timeProgress,
                    timeFixed = timeFixed,
                    writtenTime = writtenTime,
                    writtenTimeMin = writtenTimeMin,
                    timeSeconds = timeSeconds
                )

    //    }
        }

    @Composable
    fun DrawProgressBar(isTimerRunning: Boolean, timeProgress:Int, timeFixed: Int, writtenTime: String,
    writtenTimeMin: String, timeSeconds: Int){
        if (isTimerRunning) {
            Column(
                Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
              //      .padding(30.dp)
             //       .then(
             //           Modifier
             //               .fillMaxSize()
              //              .then(Modifier.wrapContentSize(Alignment.Center))
                    ) {
                Box(
                    Modifier
                        .fillMaxWidth(), Alignment.Center
                )
                 {

                    GradientProgressIndicator(
                        //progress = 1f,
                        progress = (timeProgress.toFloat() / timeFixed.toFloat()),
                        Modifier.size(300.dp),
                        strokeWidth = 20.dp,
                        gradientStart = ProgressBarColor.Green.gradientStart,
                        gradientEnd = ProgressBarColor.Green.gradientEnd,
                        trackColor = trackColor
                    )



                     Text(
                         //   text = writtenTime + " : " + writtenTimeMin + " : " + timeSeconds.toString(),
                         text = writtenTime + " : " + (DecimalFormat("00").format(writtenTimeMin.toInt())).toString() + " : " + DecimalFormat("00").format(timeSeconds).toString(),
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Cursive
                     )

                }
            }
        }
        }
    }




