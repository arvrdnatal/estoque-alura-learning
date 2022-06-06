package br.com.alura.estoque.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.estoque.database.converter.BigDecimalConverter;
import br.com.alura.estoque.database.dao.ProductDAO;
import br.com.alura.estoque.model.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
@TypeConverters(value = {BigDecimalConverter.class})
public abstract class EstoqueDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "estoque.db";

    public abstract ProductDAO getProdutoDAO();

    public static EstoqueDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, EstoqueDatabase.class, DATABASE_NAME).build();
    }
}
