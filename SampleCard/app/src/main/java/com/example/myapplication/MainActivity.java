package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.model.Example;
import com.google.gson.Gson;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    CardStackView cardStackView;
    CardStackLayoutManager manager;
    StackAdapter adapter;
    Button callBack;
    List<String> abc=new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/anishbajpai014/d482191cb4fff429333c5ec64b38c197/raw/b11f56c3177a9ddc6649288c80a004e7df41e3b9/")
            .addConverterFactory(ScalarsConverterFactory.create()).build();
    private NetworkCall networkCall = retrofit.create(NetworkCall.class);
    ProgressBar prgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStackView=findViewById(R.id.stackme);
        prgress=findViewById(R.id.progressBar);
        callBack=findViewById(R.id.button);
        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStackView.rewind();
            }
        });



        manager=new CardStackLayoutManager(this,this);
        getList();

//

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

     //   Toast.makeText(getApplicationContext(),"Dragged",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCardSwiped(Direction direction) {

     //   Toast.makeText(getApplicationContext(),"Swiped",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCardRewound() {



    }

    @Override
    public void onCardCanceled() {

     //   Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCardAppeared(View view, int position) {

      //  Toast.makeText(getApplicationContext(),"appeare"+Integer.toString(position),Toast.LENGTH_SHORT).show();
        prgress.setProgress(position+1);

    }

    @Override
    public void onCardDisappeared(View view, int position) {

       // Toast.makeText(getApplicationContext(),"disappeared"+Integer.toString(position),Toast.LENGTH_SHORT).show();

    }

    private void getList() {

        Call<String> call = networkCall.call();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    //callAdapter();
                    String a = response.body().substring(1, response.body().length() - 1) + "}";
                    Example target2 = null;
                    try {
                        JSONObject obj = new JSONObject(a);
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
//                        Example target = new Example();
//                        String json = gson.toJson(target); // serializes target to Json
                        target2 = gson.fromJson(a, Example.class);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    prgress.setMax(target2.getData().size());
                    for (int i = 0; i < target2.getData().size(); i++) {
                        abc.add(target2.getData().get(i).getText());
                    }
                    adapter=new StackAdapter(getApplicationContext(),abc);

                    manager.setStackFrom(StackFrom.None);
                    manager.setVisibleCount(3);
                    manager.setTranslationInterval(8.0f);
                    manager.setScaleInterval(0.95f);
                    manager.setSwipeThreshold(0.3f);
                    manager.setMaxDegree(20.0f);
                    manager.setDirections(Direction.HORIZONTAL);
                    manager.setCanScrollHorizontal(true);
                    manager.setCanScrollVertical(true);
                    manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                    manager.setOverlayInterpolator(new LinearInterpolator());
                    cardStackView.setLayoutManager(manager);
                    cardStackView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
