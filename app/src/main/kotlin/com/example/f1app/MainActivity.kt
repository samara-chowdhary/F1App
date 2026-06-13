package com.example.f1app

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.f1app.components.LightsOutTopBar
import com.example.f1app.machineLearning.PredictionRepository
import com.example.f1app.screens.AbuDhabiScreen
import com.example.f1app.screens.AustraliaScreen
import com.example.f1app.screens.AustriaScreen
import com.example.f1app.screens.BahrainScreen
import com.example.f1app.screens.AzerbaijanScreen
import com.example.f1app.screens.BelgiumScreen
import com.example.f1app.screens.BrazilScreen
import com.example.f1app.screens.BritainScreen
import com.example.f1app.screens.CanadaScreen
import com.example.f1app.screens.ChinaScreen
import com.example.f1app.screens.EmiliaRomagnaScreen
import com.example.f1app.screens.HungaryScreen
import com.example.f1app.screens.JapanScreen
import com.example.f1app.screens.LasVegasScreen
import com.example.f1app.screens.MexicoScreen
import com.example.f1app.screens.MiamiScreen
import com.example.f1app.screens.MonacoScreen
import com.example.f1app.screens.NetherlandsScreen
import com.example.f1app.screens.QatarScreen
import com.example.f1app.screens.SaudiArabiaScreen
import com.example.f1app.screens.SingaporeScreen
import com.example.f1app.screens.SpainScreen
import com.example.f1app.screens.USAScreen
import com.example.f1app.screens.RacesScreen
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

            val rawPositions = database.driverDao().getHistoricalPositions("Charles", "Leclerc", "%Barcelona%")
            Log.d("PREDICTION", "Found ${rawPositions.size} historical positions: $rawPositions")

            val result = predictionRepo.predictNextPosition(
                firstName = "Charles",
                lastName = "Leclerc",
                trackLocation = "Barcelona"
            )

            if (result != null) {
                val rounded = Math.round(result).toInt()
                Log.d("PREDICTION", "Predicted position: $rounded")
            } else {
                Log.d("PREDICTION","Could not calculate prediction: No historical data found.")
            }
        }

        setContent {
            F1AppTheme {
                F1AppApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun F1AppApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val navController = rememberNavController()

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
                    onClick = {
                        currentDestination = it
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { LightsOutTopBar() }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestinations.HOME.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(AppDestinations.HOME.route) { Text("Home Screen Content") }
                composable(AppDestinations.RESULTS.route) { Text("Results Screen Content") }
                composable(AppDestinations.DRIVERS.route) { Text("Drivers Screen Content") }
                composable(AppDestinations.RACES.route) {
                    RacesScreen(onCardClick = { route ->
                        navController.navigate(route)
                    })
                }
                composable(AppDestinations.CHAMPIONSHIPS.route) { Text("Championships Screen Content") }

                // race detail screens
                composable("australia") {
                    AustraliaScreen(onBack = { navController.popBackStack() })
                }
                composable("abu_dhabi") {
                    AbuDhabiScreen(onBack = { navController.popBackStack() })
                }
                composable("austria") {
                    AustriaScreen(onBack = { navController.popBackStack() })
                }
                composable("azerbaijan") {
                    AzerbaijanScreen(onBack = { navController.popBackStack() })
                }
                composable("bahrain") {
                    BahrainScreen(onBack = { navController.popBackStack() })
                }
                composable("belgium") {
                    BelgiumScreen(onBack = { navController.popBackStack() })
                }
                composable("brazil") {
                    BrazilScreen(onBack = { navController.popBackStack() })
                }
                composable("britain") {
                    BritainScreen(onBack = { navController.popBackStack() })
                }
                composable("canada") {
                    CanadaScreen(onBack = { navController.popBackStack() })
                }
                composable("china") {
                    ChinaScreen(onBack = { navController.popBackStack() })
                }
                composable("emilia_romagna") {
                    EmiliaRomagnaScreen(onBack = { navController.popBackStack() })
                }
                composable("britain") {
                    BritianScreen(onBack = { navController.popBackStack() })
                }
                composable("hungary") {
                    HungaryScreen(onBack = { navController.popBackStack() })
                }
                composable("japan") {
                    JapanScreen(onBack = { navController.popBackStack() })
                }
                composable("las_vegas") {
                    LasVegasScreen(onBack = { navController.popBackStack() })
                }
                composable("mexico") {
                    MexicoScreen(onBack = { navController.popBackStack() })
                }
                composable("miami") {
                    MiamiScreen(onBack = { navController.popBackStack() })
                }
                composable("monaco") {
                    MonacoScreen(onBack = { navController.popBackStack() })
                }
                composable("netherlands") {
                    NetherlandsScreen(onBack = { navController.popBackStack() })
                }
                composable("qatar") {
                    QatarScreen(onBack = { navController.popBackStack() })
                }
                composable("saudi") {
                    SaudiArabiaScreen(onBack = { navController.popBackStack() })
                }
                composable("singapore") {
                    SingaporeScreen(onBack = { navController.popBackStack() })
                }
                composable("spain") {
                    SpainScreen(onBack = { navController.popBackStack() })
                }
                composable("usa") {
                    USAScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
    val route: String
) {
    DRIVERS("Drivers", R.drawable.ic_account_box, "drivers"),
    RESULTS("Results", R.drawable.ic_flag, "results"),
    HOME("Home", R.drawable.ic_home, "home"),
    RACES("Races", R.drawable.ic_calendar, "races"),
    CHAMPIONSHIPS("Champs", R.drawable.ic_trophy, "championships")
}

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