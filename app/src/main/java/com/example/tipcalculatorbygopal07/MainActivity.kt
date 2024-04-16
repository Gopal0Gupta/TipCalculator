package com.example.tipcalculatorbygopal07

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculatorbygopal07.ui.theme.TipCalculatorTheme
import org.intellij.lang.annotations.JdkConstants.FontStyle
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.DecimalFormat
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                myApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun myApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TipCalculator()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TipCalculator() {
    val Amount = remember{
        mutableStateOf("")
    }
    val Counter = remember{
        mutableStateOf(1)
    }
    val tipPercentage = remember{
        mutableStateOf(0f)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        TotalHeading(Amount= twoDecimalPoints(getTotalHeaderAmount(Amount.value,Counter.value,tipPercentage.value)))
        userInputArea(
            Amount = Amount.value,
            AmountChange = {Amount.value = it},
            Counter = Counter.value,
            onaddorreduce = {
                            if(it<0){
                                if(Counter.value!=1){
                                    Counter.value--
                                }
                            }else{
                                Counter.value++
                            }
            },
            tipPercentage = tipPercentage.value,
            tipPercentageChange = {
                tipPercentage.value=it
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TotalHeading(Amount : String) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        color = colorResource(id = R.color.cyan),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Per Person",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                    )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$$Amount",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun userInputArea(Amount: String,
                  AmountChange: (String)->Unit,
                  Counter:Int,
                  onaddorreduce: (Int)-> Unit,
                  tipPercentage: Float,
                  tipPercentageChange: (Float)->Unit
                  ) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
        shadowElevation = 12.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = Amount, onValueChange = {AmountChange.invoke(it)},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text(text = "Enter Your Amount")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    autoCorrect = true,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
            if(Amount.isNotBlank()){
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "Split", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.fillMaxWidth(.50f))
                    customButton(imageVector = Icons.Default.KeyboardArrowUp) {
                        onaddorreduce.invoke(1)
                    }
                    Text(text = "$Counter", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 8.dp))
                    customButton(imageVector = Icons.Default.KeyboardArrowDown) {
                        onaddorreduce.invoke(-1)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "Tip", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.fillMaxWidth(.70f))
                    Text(text = "$ ${twoDecimalPoints(getTipAmount(Amount,tipPercentage))}", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${twoDecimalPoints(tipPercentage.toString())} %", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Slider(value = tipPercentage, onValueChange = {
                    tipPercentageChange.invoke(it)
                }, valueRange = 0f..100f, steps = 5, modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth())
            }
        }
    }
}
@Composable
fun previewCustomButton() {
    customButton(imageVector = Icons.Default.KeyboardArrowDown) {
        
    }
}
@Composable
fun customButton(imageVector: ImageVector,onclick: ()->Unit) {
    Card(modifier = Modifier
        .wrapContentSize()
        .padding(12.dp)
        .clickable {
            onclick.invoke()
        },
    shape = CircleShape
    ) {
        Icon(imageVector = imageVector, contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(4.dp)
            )
    }
}

fun getTipAmount(userInput:String,tipPercentage: Float):String{
    return when{
        userInput.isEmpty()->{
            "0"
        }
        else -> {
            val Amount = userInput.toFloat()
                (Amount*tipPercentage.div(100)).toString()
        }
    }
}

fun getTotalHeaderAmount(amount:String,personCounter:Int,tipPercentage: Float):String {
    return when{
        amount.isEmpty()->{
            "0"
        }
        else->{
            val userInput = amount.toFloat()
            val tipAmount = userInput*tipPercentage.div(100)
            val perHeadAmount = (userInput+tipAmount).div(personCounter)
            perHeadAmount.toString()
        }
    }
}

fun twoDecimalPoints(str:String):String{
    return if(str.isEmpty()){
        ""
    }
    else{
        val format = DecimalFormat("#########################.##")
        format.format(str.toFloat())
    }
}
