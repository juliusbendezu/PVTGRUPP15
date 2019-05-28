package com.dsv2019.pvt15.prepapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.apihandler.BaseAPIService;
import com.dsv2019.pvt15.prepapp.apihandler.InternetConnection;
import com.dsv2019.pvt15.prepapp.apihandler.RetrofitClient;
import com.dsv2019.pvt15.prepapp.tipsrelated.CreateNewTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.ManipulateTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TipsFragment extends Fragment
{

    ArrayList<Tip> tipsList = new ArrayList<>();
    Tip[] newListToSort;
    private int categoryNR;
    private String categoryName;
    private TextView categoryText;
    private ImageButton backButton;
    private ImageButton createNewTipButton;
    private ArrayList<String> categoryList = new ArrayList<>();
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_tips, container, false);

        //categoryNR = (int) getActivity().getIntent().getExtras().get("category");

        createBackBtn();
        createNewTipButton();
        loadTheTips();
        setCategoryView();

        return view;
    }

    private void setCategoryView()
    {

        if (categoryNR == 1)
        {
            categoryName = "Värme";

        }
        else if (categoryNR == 2)
        {
            categoryName = "Vatten";

        }
        else if (categoryNR == 3)
        {
            categoryName = "Skydd";

        }
        else if (categoryNR == 4)
        {
            categoryName = "Mat";

        }
        else if (categoryNR == 5)
        {
            categoryName = "Sjukvård";

        }
        else if (categoryNR == 6)
        {
            categoryName = "Säkerhet";

        }
        else if (categoryNR == 7)
        {
            categoryName = "Förvaring";

        }
        else
        {
            categoryName = "Övrigt";

        }
        categoryText = view.findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);
    }

    public void createNewTipButton()
    {
        createNewTipButton = view.findViewById(R.id.createNewTipButton);
        createNewTipButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "TA");
                startActivity(startIntent);
            }
        });
    }

    public void createBackBtn()
    {
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                startActivity(startIntent);
            }
        });
    }


    public void loadTheTips()
    {
        if (InternetConnection.checkConnection(getActivity().getApplicationContext()))
        {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Getting the tips");
            dialog.setMessage("please wait");
            dialog.show();


            BaseAPIService api = RetrofitClient.getApiService();

            Call<List<Tip>> call = api.getTips();
            //Call<String> call =api.getHelloString();

            call.enqueue(new Callback<List<Tip>>()
            {
                @Override
                public void onResponse(Call<List<Tip>> call, Response<List<Tip>> response)
                {
                    dialog.dismiss();
                    if (!response.isSuccessful())
                    {
                        Toast.makeText(getActivity(), "Tipsen har inte laddats" + response.code(), Toast.LENGTH_LONG).show();
                        call.clone().enqueue(this);
                    }

                    List<Tip> allTips = response.body();
                    for (int i = 0; i < allTips.size(); i++)
                    {
                        Tip tipToCheck = allTips.get(i);
                        checkTheTipsCategoryandAdd(tipToCheck);
                    }
                    sort(tipsList);


                    for (int i = 0; i < newListToSort.length; i++)
                    {
                        addTips(newListToSort[i]);
                    }

                }

                @Override
                public void onFailure(Call<List<Tip>> call, Throwable t)
                {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Tipsen har inte laddats2", Toast.LENGTH_LONG).show();
                    call.clone().enqueue(this);
                }
            });
        }
    }

    private void sort(List<Tip> listToSort)
    {

        newListToSort = new Tip[listToSort.size()];
        int n = listToSort.size();
        for (int u = 0; u < listToSort.size(); u++)
        {
            newListToSort[u] = listToSort.get(u);
        }

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
            {
                if (newListToSort[j].equals(newListToSort[min_idx]))
                {
                    if (newListToSort[j].getLikes() < newListToSort[min_idx].getLikes())
                    {
                        newListToSort[min_idx] = newListToSort[j];
                    }
                }
            }


            // Swap the found minimum element with the first
            // element

            Tip temp = newListToSort[min_idx];
            newListToSort[min_idx] = newListToSort[i];
            newListToSort[i] = temp;
        }

    }

    private void checkTheTipsCategoryandAdd(Tip tipToCheck)
    {

        if (categoryNR == 1)
        {
            if (tipToCheck.isWarmth() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 2)
        {
            if (tipToCheck.isWater() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 3)
        {
            if (tipToCheck.isShelter() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 4)
        {
            if (tipToCheck.isFood() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 5)
        {
            if (tipToCheck.isHealth() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 6)
        {
            if (tipToCheck.isSecurity() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 7)
        {
            if (tipToCheck.isStorage() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryNR == 8)
        {
            if (tipToCheck.isOther() == true)
            {
                tipsList.add(tipToCheck);
            }
        }
    }


    private void addTips(Tip tip)
    {
        LinearLayout layout = view.findViewById(R.id.newsLinearLayout);

        TipsItemView tipsItemView = new TipsItemView(getActivity(), tip);

        tipsItemView.setOnClickListener(l ->
        {
            Intent startIntent = new Intent(getActivity().getApplicationContext(), ManipulateTip.class);
            startIntent.putExtra("theTip", tip);
            startActivity(startIntent);

        });

        layout.addView(tipsItemView);
    }
}

