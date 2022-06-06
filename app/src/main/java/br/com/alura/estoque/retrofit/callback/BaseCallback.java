package br.com.alura.estoque.retrofit.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class BaseCallback<T> implements Callback<T> {

    private final AnswerCallback<T> callback;

    public BaseCallback(AnswerCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T result = response.body();
            if (result != null) callback.success(result);
        } else {
            callback.error("Resposta não sucedida");
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<T> call, Throwable t) {
        callback.error("Falha de comunicação: " + t.getMessage());
    }

    public interface AnswerCallback<T> {
        void success(T result);

        void error(String error);
    }
}
