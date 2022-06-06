package br.com.alura.estoque.database.converter;

import java.math.BigDecimal;

import androidx.room.TypeConverter;

public class BigDecimalConverter {

    @TypeConverter
    public Double toDouble(BigDecimal value) {
        return value.doubleValue();
    }

    @TypeConverter
    public BigDecimal toBigDecimal(Double value) {
        return (value != null) ? new BigDecimal(value) : BigDecimal.ZERO;
    }

}
