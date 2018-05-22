package com.zcy.ygs.retrofitdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zcy.ygs.retrofitdemo.HttpLoggingInterceptor.REQUEST_LOG;
import static com.zcy.ygs.retrofitdemo.Main2Activity.MAIN2_STR;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_STR="Aaaaaaaaaaaaaaa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("param","value");
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.tv)).setText(MAIN2_STR);

        Call<List<GithubRepos>> call = ServiceGenerator.createService(GithubClient.class).getReposCall("CY");
        call.enqueue(new Callback<List<GithubRepos>>() {
            @Override
            public void onResponse(Call<List<GithubRepos>> call, Response<List<GithubRepos>> response) {
                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<GithubRepos>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Observable<List<GithubRepos>> observable = ServiceGenerator.createService(GithubClient.class).getReposObser("chunyaZOU");
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Consumer<List<GithubRepos>>() {
                    @Override
                    public void accept(List<GithubRepos> githubRepos) throws Exception {
                        Toast.makeText(MainActivity.this, githubRepos.get(0).toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Gson gson = new Gson();
        String strArray = "[\"aaa\",\"bbbb\",\"ccccc\"]";
        String[] strings = gson.fromJson(strArray, String[].class);
        Log.i(REQUEST_LOG, strings[0]);
        List<String> stringList = gson.fromJson(strArray, new TypeToken<List<String>>() {
        }.getType());
        Log.i(REQUEST_LOG, stringList.get(0));


        Gson gson1 = new GsonBuilder()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Integer.class, new JsonSerializer<Integer>() {
                    @Override
                    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
                        return null;
                    }
                })
                .create();

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(REQUEST_LOG, "Disposable");
            }

            @Override
            public void onNext(Object value) {
                Log.i(REQUEST_LOG, value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(REQUEST_LOG, "completed");
            }
        };

        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("1111111");
                e.onNext("2222222");
                e.setDisposable(new Disposable() {
                    @Override
                    public void dispose() {
                        Log.i(REQUEST_LOG, "dis");
                    }

                    @Override
                    public boolean isDisposed() {
                        return true;
                    }
                });
                e.onComplete();

            }
        });
        observable1.subscribe(observer);

        Subscriber subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(REQUEST_LOG, o.toString());
            }

            @Override
            public void onError(Throwable t) {
                Log.i(REQUEST_LOG, t.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        subscriber.onNext("3333");
        subscriber.onError(new Throwable("4444"));

        Observable observable2 = Observable.just(123);
        Observable observable3 = Observable.fromArray(123, "123");
        Observable.fromArray("hello", "world")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(REQUEST_LOG, value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Observable.just("hello world")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(REQUEST_LOG, s.toString());
                    }
                });
        final List<GithubRepos> githubReposList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GithubRepos githubRepos = new GithubRepos();
            githubRepos.id = i;
            githubRepos.name = "aaaaaa" + i;
            for (int j = 0; j < 3; j++) {
                GithubRepos.Follow follow=githubRepos.new Follow();
                follow.name="bbbb"+j;
                follow.age=j;
                githubRepos.follows.add(follow);
            }
            githubReposList.add(githubRepos);
        }

        Observable.fromArray(githubReposList)
                .map(new Function<List<GithubRepos>, List<String>>() {
                    @Override
                    public List<String> apply(List<GithubRepos> githubRepos) throws Exception {
                        List<String> reposName = new ArrayList<>();
                        for (GithubRepos repos : githubRepos) {
                            if (repos.id % 2 == 0) {
                                reposName.add(repos.name);
                            }
                        }
                        return reposName;
                    }
                })
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> names) throws Exception {
                        for (String s : names) {
                            Log.i(REQUEST_LOG, s.toString());
                        }
                    }
                });

        Observable.fromIterable(githubReposList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())//可以随意切换线程 后续代码将在该线程中处理
                .flatMap(new Function<GithubRepos, Observable<List<GithubRepos.Follow>>>() {
                    @Override
                    public Observable<List<GithubRepos.Follow>> apply(GithubRepos githubRepos) throws Exception {

                        return Observable.fromArray(githubRepos.follows);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GithubRepos.Follow>>() {
                    @Override
                    public void accept(List<GithubRepos.Follow> follow) throws Exception {
                        Log.i(REQUEST_LOG, follow.get(0).name);
                    }
                });


        //背压
        Subscriber<Data> subscriber1=new Subscriber<Data>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Data o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        Flowable.create(new FlowableOnSubscribe<Data>() {
            @Override
            public void subscribe(FlowableEmitter<Data> e) throws Exception {
                Data data=new Data();
                data.data="123";
                e.onNext(data);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribe(subscriber1);
        Flowable<List<GithubRepos>> listFlowable=Flowable.just(githubReposList);
        listFlowable.subscribe(new Consumer<List<GithubRepos>>() {
            @Override
            public void accept(List<GithubRepos> githubRepos) throws Exception {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

//        ExecutorService servicePool=new ThreadPoolExecutor(0,0,0l, TimeUnit.SECONDS,null);
//        servicePool= Executors.newCachedThreadPool();
//        servicePool=Executors.newFixedThreadPool(10);
//        servicePool=Executors.newSingleThreadExecutor();
//        servicePool=Executors.newScheduledThreadPool(10);
//        servicePool.submit(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        final String params,result;
//        AsyncTask task=new AsyncTask() {
//
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                return null;
//            }
//        };
//        task.execute("aaa");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


}
