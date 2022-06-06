package br.com.alura.estoque.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.alura.estoque.R;
import br.com.alura.estoque.model.Product;
import br.com.alura.estoque.repository.ProductRepository;
import br.com.alura.estoque.ui.dialog.EditProductDialog;
import br.com.alura.estoque.ui.dialog.SaveProductDialog;
import br.com.alura.estoque.ui.recyclerview.adapter.ProductsListAdapter;

public class ProductsListActivity extends AppCompatActivity {

    private static final String PRODUCTS_LIST_ACTIVITY_TITLE = "Lista de produtos";
    private static final String LOAD_PRODUCTS_ERROR = "Não foi possível carregar os produtos novos";
    private static final String DELETE_PRODUCT_ERROR = "Não foi possível remover o produto";
    private static final String SAVE_PRODUCT_ERROR = "Não foi possível salvar o produto";
    private ProductsListAdapter adapter;
    private ProductRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products_list);
        setTitle(PRODUCTS_LIST_ACTIVITY_TITLE);

        configProductsList();
        configuraFabSalvaProduto();

        repository = new ProductRepository(this);
        repository.searchProducts(new ProductRepository.LoadedDataCallback<List<Product>>() {
            @Override
            public void success(List<Product> products) {
                adapter.update(products);
            }

            @Override
            public void error(String error) {
                showError(LOAD_PRODUCTS_ERROR + "(" + error + ")");
            }
        });
    }

    private void configProductsList() {
        RecyclerView ProductsListRecycler = findViewById(R.id.activity_products_list_recyclerview);
        adapter = new ProductsListAdapter(this, this::openEditProductForm);
        ProductsListRecycler.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener((position, product) -> repository.remove(product, new ProductRepository.LoadedDataCallback<Void>() {
            @Override
            public void success(Void result) {
                adapter.remove(position);
            }

            @Override
            public void error(String error) {
                showError(DELETE_PRODUCT_ERROR);
            }
        }));
    }

    private void showError(String error) {
        Toast.makeText(ProductsListActivity.this, error, Toast.LENGTH_LONG).show();
    }

    private void configuraFabSalvaProduto() {
        FloatingActionButton fabAddProduct = findViewById(R.id.activity_products_list_fab_add_product);
        fabAddProduct.setOnClickListener(v -> openSaveProductForm());
    }

    private void openSaveProductForm() {
        new SaveProductDialog(this, product -> repository.save(product, new ProductRepository.LoadedDataCallback<Product>() {
            @Override
            public void success(Product savedProduct) {
                adapter.add(savedProduct);
            }

            @Override
            public void error(String error) {
                showError(SAVE_PRODUCT_ERROR);
            }
        })).show();
    }

    private void openEditProductForm(int position, Product product) {
        new EditProductDialog(this, product, editedProduct -> repository.edit(editedProduct, new ProductRepository.LoadedDataCallback<Product>() {
            @Override
            public void success(Product result) {
                adapter.edit(position, result);
            }

            @Override
            public void error(String error) {
                showError("Não foi possível editar o produto");
            }
        })).show();
    }
}
