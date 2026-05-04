package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculadora.model.LoanResult
import com.example.calculadora.screen.LoanCalculatorScreen
import com.example.calculadora.screen.LoanResultScreen
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    var loanResult by remember { mutableStateOf<LoanResult?>(null) }

    NavHost(navController = navController, startDestination = "calculator") {
        composable("calculator") {
            LoanCalculatorScreen(
                onCalculate = { result ->
                    loanResult = result
                    navController.navigate("result")
                }
            )
        }
        composable("result") {
            loanResult?.let {
                LoanResultScreen(
                    loanResult = it,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}