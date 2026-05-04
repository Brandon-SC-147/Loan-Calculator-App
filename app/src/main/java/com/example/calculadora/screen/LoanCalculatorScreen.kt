package com.example.calculadora.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.calculadora.model.Currency
import com.example.calculadora.model.LoanResult
import kotlin.math.pow

enum class PeriodType {
    YEARS, MONTHS
}

@Composable
fun LoanCalculatorScreen(
    onCalculate: (LoanResult) -> Unit
) {
    var loanAmount by remember { mutableStateOf("") }
    var period by remember { mutableStateOf("") }
    var periodType by remember { mutableStateOf(PeriodType.YEARS) }
    var annualRate by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf(Currency.SOLES) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Degradado de fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White,
            Color(0xFFEDE7F6) // Lila muy suave
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Ícono superior
            Icon(
                imageVector = Icons.Filled.AttachMoney,
                contentDescription = "Calculadora de Préstamos",
                modifier = Modifier
                    .size(56.dp)
                    .padding(bottom = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            // Título
            Text(
                text = "Calculadora de Préstamos",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontSize = 28.sp
            )

            // Subtítulo
            Text(
                text = "Calcula tu cuota mensual de forma rápida y sencilla",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card del formulario
            Card(
                modifier = Modifier
                    .width(340.dp)
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Selección de moneda
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Moneda:",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Row(
                            modifier = Modifier.weight(1.5f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ElevatedFilterChip(
                                selected = currency == Currency.SOLES,
                                onClick = {
                                    currency = Currency.SOLES
                                    errorMessage = ""
                                },
                                label = { Text("S/") },
                                modifier = Modifier.weight(1f)
                            )
                            ElevatedFilterChip(
                                selected = currency == Currency.DOLLARS,
                                onClick = {
                                    currency = Currency.DOLLARS
                                    errorMessage = ""
                                },
                                label = { Text("$") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Monto del préstamo
                    OutlinedTextField(
                        value = loanAmount,
                        onValueChange = {
                            loanAmount = it
                            errorMessage = ""
                        },
                        label = { Text("Monto del Préstamo (${currency.symbol})") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Plazo
                    OutlinedTextField(
                        value = period,
                        onValueChange = {
                            period = it
                            errorMessage = ""
                        },
                        label = { Text("Plazo") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Selección de tipo de plazo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tipo:",
                            modifier = Modifier,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ElevatedFilterChip(
                                selected = periodType == PeriodType.YEARS,
                                onClick = { periodType = PeriodType.YEARS },
                                label = { Text("Años") },
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                            ElevatedFilterChip(
                                selected = periodType == PeriodType.MONTHS,
                                onClick = { periodType = PeriodType.MONTHS },
                                label = { Text("Meses") },
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    // Tasa de interés anual
                    OutlinedTextField(
                        value = annualRate,
                        onValueChange = {
                            annualRate = it
                            errorMessage = ""
                        },
                        label = { Text("Tasa de Interés Anual (%)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Mensaje de error
                    if (errorMessage.isNotEmpty()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botón Calcular
                    Button(
                        onClick = {
                            val result = validateAndCalculateLoan(loanAmount, period, annualRate, periodType, currency)
                            if (result.first != null) {
                                isLoading = true
                                val loanResult = result.first!!
                                onCalculate(loanResult)
                            } else {
                                errorMessage = result.second ?: "Error desconocido"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Calcular préstamo",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

fun validateAndCalculateLoan(
    loanAmountStr: String,
    periodStr: String,
    annualRateStr: String,
    periodType: PeriodType = PeriodType.YEARS,
    currency: Currency = Currency.SOLES
): Pair<LoanResult?, String?> {
    // Validar que no estén vacíos
    if (loanAmountStr.isBlank() || periodStr.isBlank() || annualRateStr.isBlank()) {
        return Pair(null, "Todos los campos son requeridos")
    }

    // Validar que sean numéricos
    val loanAmount = loanAmountStr.toDoubleOrNull() ?: return Pair(null, "El monto debe ser un número válido")
    val period = periodStr.toIntOrNull() ?: return Pair(null, "El plazo debe ser un número válido")
    val annualRate = annualRateStr.toDoubleOrNull() ?: return Pair(null, "La tasa de interés debe ser un número válido")

    // Validar que sean positivos
    if (loanAmount <= 0) return Pair(null, "El monto debe ser mayor a 0")
    if (period <= 0) return Pair(null, "El plazo debe ser mayor a 0")
    if (annualRate < 0) return Pair(null, "La tasa de interés no puede ser negativa")

    // Convertir plazo a meses si es en años
    val totalMonths = if (periodType == PeriodType.YEARS) period * 12 else period

    // Calcular tasa mensual
    val monthlyRate = annualRate / 12 / 100

    // Calcular cuota mensual
    val monthlyPayment = if (annualRate == 0.0) {
        loanAmount / totalMonths
    } else {
        val numerator = monthlyRate * (1 + monthlyRate).pow(totalMonths)
        val denominator = (1 + monthlyRate).pow(totalMonths) - 1
        loanAmount * (numerator / denominator)
    }

    // Calcular total pagado e intereses
    val totalPaid = monthlyPayment * totalMonths
    val totalInterest = totalPaid - loanAmount

    return Pair(
        LoanResult(
            monthlyPayment = monthlyPayment,
            loanAmount = loanAmount,
            totalInterest = totalInterest,
            monthlyRate = monthlyRate,
            totalMonths = totalMonths,
            totalPaid = totalPaid,
            currency = currency
        ),
        null
    )
}

