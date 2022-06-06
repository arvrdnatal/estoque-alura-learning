package br.com.alura.estoque.retrofit;

import br.com.alura.estoque.retrofit.service.ProductService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstoqueRetrofit {

    private final ProductService productService;

    public EstoqueRetrofit() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.102:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//                .client(client)
        productService = retrofit.create(ProductService.class);
    }

    public ProductService getProductService() {
        return productService;
    }
}
