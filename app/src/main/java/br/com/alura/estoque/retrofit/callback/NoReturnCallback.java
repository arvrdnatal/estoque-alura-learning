package br.com.alura.estoque.retrofit.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class NoReturnCallback implements Callback<Void> {

    private final AnswerCallback callback;

    public NoReturnCallback(AnswerCallback callback) {
        this.callback = callback;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            callback.success();
        } else {
            callback.error("Resposta não sucedida");
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<Void> call, Throwable t) {
        callback.error("Falha de comunicação: " + t.getMessage());
    }

    public interface AnswerCallback {
        void success();

        void error(String error);
    }
}
