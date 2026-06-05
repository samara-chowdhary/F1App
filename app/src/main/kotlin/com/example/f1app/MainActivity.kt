package com.example.f1app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.f1app.components.LightsOutTopBar
import com.example.f1app.machineLearning.PredictionRepository
import com.example.f1app.machineLearning.test
import com.example.f1app.ui.theme.F1AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = F1Database.getInstance(this)
        val predictionRepo = PredictionRepository(database.driverDao())

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val ingestor = DatabaseImport(this@MainActivity as Context)
                ingestor.startImport()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val result = predictionRepo.predictNextPosition(
                firstName = "Lando",
                lastName = "Norris",
                trackLocation = "Monaco"
            )

            if (result != null) {
                println("Lando's predicted finishing position is: $result")
            } else {
                println("Could not calculate prediction: No historical data found.")
            }
        }

        setContent {
            F1AppTheme {
                test()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun F1AppApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    //nav bar build
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Box(modifier = Modifier.height(25.dp), contentAlignment = Alignment.Center) {
                            Icon(painterResource(it.icon), it.label)
                        }
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        //nav and top bar
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { LightsOutTopBar() }
        ) { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {
                when (currentDestination) {
                    AppDestinations.HOME -> Text("Home Screen Content")
                    AppDestinations.RESULTS -> Text("Results Screen Content")
                    AppDestinations.DRIVERS -> Text("Drivers Screen Content")
                    AppDestinations.RACES -> Text("Races Screen Content")
                    AppDestinations.CHAMPIONSHIPS -> Text("Championships Screen Content")

                }
            }
        }
    }
}

//nav bar icons
enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    DRIVERS("Drivers", R.drawable.ic_account_box),
    RESULTS("Results", R.drawable.ic_flag),
    HOME("Home", R.drawable.ic_home),
    RACES("Races", R.drawable.ic_calendar),
    CHAMPIONSHIPS("Champs", R.drawable.ic_trophy)
}


//greetings
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    F1AppTheme {
        Greeting("Android")
    }
}




