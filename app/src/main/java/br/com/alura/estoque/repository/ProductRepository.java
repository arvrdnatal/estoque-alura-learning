package br.com.alura.estoque.repository;

import android.content.Context;

import java.util.List;

import br.com.alura.estoque.asynctask.BaseAsyncTask;
import br.com.alura.estoque.database.EstoqueDatabase;
import br.com.alura.estoque.database.dao.ProductDAO;
import br.com.alura.estoque.model.Product;
import br.com.alura.estoque.retrofit.EstoqueRetrofit;
import br.com.alura.estoque.retrofit.callback.BaseCallback;
import br.com.alura.estoque.retrofit.callback.NoReturnCallback;
import br.com.alura.estoque.retrofit.service.ProductService;
import retrofit2.Call;

public class ProductRepository {

    private final Context context;
    private final ProductDAO dao;
    private final ProductService service;

    public ProductRepository(Context context) {
        EstoqueDatabase db = EstoqueDatabase.getInstance(context);
        this.context = context;
        this.dao = db.getProdutoDAO();
        service = new EstoqueRetrofit().getProductService();
    }

    public void searchProducts(LoadedDataCallback<List<Product>> callback) {
        Call<List<Product>> call = service.getAll();
        call.enqueue(new BaseCallback<>(new BaseCallback.AnswerCallback<List<Product>>() {
            @Override
            public void success(List<Product> products) {
                new BaseAsyncTask<>(context, () -> {
                    dao.save(products);
                    return dao.getAll();
                }, callback::success).run();
            }

            @Override
            public void error(String error) {
                callback.error(error);
            }
        }));
    }

    public void save(Product product, LoadedDataCallback<Product> callback) {
        Call<Product> call = service.save(product);
        call.enqueue(new BaseCallback<>(new BaseCallback.AnswerCallback<Product>() {
            @Override
            public void success(Product savedProduct) {
                new BaseAsyncTask<>(context, () -> {
                    long id = dao.save(savedProduct);
                    return dao.getProduct(id);
                }, callback::success).run();
            }

            @Override
            public void error(String error) {
                callback.error(error);
            }
        }));
    }

    public void edit(Product product, LoadedDataCallback<Product> callback) {
        Call<Product> call = service.edit(product.getId(), product);
        call.enqueue(new BaseCallback<>(new BaseCallback.AnswerCallback<Product>() {
            @Override
            public void success(Product editedProduct) {
                new BaseAsyncTask<>(context, () -> {
                    dao.update(product);
                    return product;
                }, callback::success).run();
            }

            @Override
            public void error(String error) {
                callback.error(error);
            }
        }));
    }

    public void remove(Product product, LoadedDataCallback<Void> callback) {
        Call<Void> call = service.remove(product.getId());
        call.enqueue(new NoReturnCallback(new NoReturnCallback.AnswerCallback() {
            @Override
            public void success() {
                new BaseAsyncTask<>(context, () -> {
                    dao.remove(product);
                    return null;
                }, callback::success).run();
            }

            @Override
            public void error(String error) {
                callback.error(error);
            }
        }));
    }

    public interface LoadedDataCallback<T> {
        void success(T result);

        void error(String error);
    }
}