package com.example.calculadora.model

data class LoanResult(
    val monthlyPayment: Double,
    val loanAmount: Double,
    val totalInterest: Double,
    val monthlyRate: Double,
    val totalMonths: Int,
    val totalPaid: Double,
    val currency: Currency = Currency.SOLES
)

