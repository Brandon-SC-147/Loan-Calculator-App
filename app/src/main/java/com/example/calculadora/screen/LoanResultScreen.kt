package com.example.calculadora.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.model.Currency
import com.example.calculadora.model.LoanResult
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanResultScreen(
    loanResult: LoanResult,
    onBack: () -> Unit
) {
    val decimalFormat = DecimalFormat("#,##0.00")

    fun formatCurrency(amount: Double, currency: Currency): String {
        return "${currency.symbol} ${decimalFormat.format(amount)}"
    }

    // Degradado de fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White,
            Color(0xFFEDE7F6) // Lila muy suave
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultados del Cálculo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resumen de tu Préstamo",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contenedor centrado para la tarjeta
                Box(
                    modifier = Modifier.width(340.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Moneda seleccionada
                            ResultRow(
                                label = "Moneda",
                                value = loanResult.currency.displayName,
                                valueColor = MaterialTheme.colorScheme.primary,
                                isHighlight = true
                            )

                            HorizontalDivider()

                            // Monto del Préstamo
                            ResultRow(
                                label = "Monto del Préstamo",
                                value = formatCurrency(loanResult.loanAmount, loanResult.currency),
                                valueColor = MaterialTheme.colorScheme.primary
                            )

                            HorizontalDivider()

                            // Cuota Mensual (Destacada como principal)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Cuota Mensual",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = formatCurrency(loanResult.monthlyPayment, loanResult.currency),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 18.sp
                                    )
                                }
                            }

                            HorizontalDivider()

                            // Total de Intereses
                            ResultRow(
                                label = "Total de Intereses",
                                value = formatCurrency(loanResult.totalInterest, loanResult.currency),
                                valueColor = MaterialTheme.colorScheme.error
                            )

                            HorizontalDivider()

                            // Total a Pagar
                            ResultRow(
                                label = "Total a Pagar",
                                value = formatCurrency(loanResult.totalPaid, loanResult.currency),
                                valueColor = MaterialTheme.colorScheme.secondary,
                                isHighlight = true
                            )

                            HorizontalDivider()

                            // Plazo
                            ResultRow(
                                label = "Plazo",
                                value = "${loanResult.totalMonths} meses (${loanResult.totalMonths / 12} años ${loanResult.totalMonths % 12} meses)"
                            )

                            HorizontalDivider()

                            // Tasa Mensual
                            ResultRow(
                                label = "Tasa Mensual",
                                value = String.format("%.4f%%", loanResult.monthlyRate * 100)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para volver (centrado con el mismo ancho que la tarjeta)
                Box(
                    modifier = Modifier
                        .width(340.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = onBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Calcular Otro Préstamo",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isHighlight) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = if (isHighlight) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            color = valueColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

