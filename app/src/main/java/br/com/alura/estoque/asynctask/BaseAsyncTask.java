package br.com.alura.estoque.asynctask;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.alura.estoque.ui.activity.ProductsListActivity;

public class BaseAsyncTask<T> {
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final Context context;
    private final ExecuteListener<T> executeListener;
    private final FinishListener<T> finishListener;
    private T object;

    public BaseAsyncTask(Context context, ExecuteListener<T> executeListener, FinishListener<T> finishListener) {
        this.context = context;
        this.executeListener = executeListener;
        this.finishListener = finishListener;
    }

    public void run() {
        service.execute(() -> {
            try {
                object = executeListener.whenExecuted();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((ProductsListActivity) context).runOnUiThread(() -> finishListener.whenFinished(object));
        });
        service.shutdown();
    }

    public interface ExecuteListener<T> {
        T whenExecuted();
    }

    public interface FinishListener<T> {
        void whenFinished(T result);
    }
}

