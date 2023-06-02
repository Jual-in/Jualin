package com.jualin.apps.utils

import java.text.NumberFormat
import java.util.Locale

object StringUtils {
    fun formatCurrency(amount: String): String {
        val locale = Locale("id", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.minimumFractionDigits = 0
        val parsedAmount = try {
            amount.toDouble()
        } catch (e: NumberFormatException) {
            return amount
        }
        return numberFormat.format(parsedAmount).replace("Rp", "Rp ")
    }
}
