package com.dsv2019.pvt15.prepapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

public class TipsFragment extends Fragment {

    ArrayList<Tip> tipsList = new ArrayList<>();
    Tip[] newListToSort;
    private int categoryNR;
    private String categoryName = "Alla tipsen";
    private TextView categoryText;
    private ImageButton createNewTipButton;
    private ArrayList<String> categoryList = new ArrayList<>();
    private View view;
    private List<Tip> allTips;
    private LinearLayout hamburger;
    private LinearLayout layout;
    private int categoryType;

    public static final int ALL_CATEGORY = 9;
    public static final int MY_CATEGORY = 10;
    public static final int WARMTH_CATEGORY = 1;
    public static final int WATER_CATEGORY = 2;
    public static final int SHELTER_CATEGORY = 3;
    public static final int FOOD_CATEGORY = 4;
    public static final int HEALTH_CATEGORY = 5;
    public static final int SECURITY_CATEGORY = 6;
    public static final int STORAGE_CATEGORY = 7;
    public static final int OTHER_CATEGORY = 8;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tips, container, false);
        categoryText = view.findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);
        //categoryNR = (int) getActivity().getIntent().getExtras().get("category");
        createNewTipButton();
        loadTheTips();
        setHamburgerButton();

        return view;
    }

    private void setCategoryView() {

        if (categoryType == WARMTH_CATEGORY) {
            categoryName = "Värmetips";

        } else if (categoryType == WATER_CATEGORY) {
            categoryName = "Vattentips";

        } else if (categoryType == SHELTER_CATEGORY) {
            categoryName = "Skyddtips";

        } else if (categoryType == FOOD_CATEGORY) {
            categoryName = "Mattips";

        } else if (categoryType == HEALTH_CATEGORY) {
            categoryName = "Sjukvårdstips";

        } else if (categoryType == SECURITY_CATEGORY) {
            categoryName = "Säkerhetstips";

        } else if (categoryType == STORAGE_CATEGORY) {
            categoryName = "Förvaringstips";

        } else if (categoryType == OTHER_CATEGORY) {
            categoryName = "Övriga tips";

        } else if (categoryType == ALL_CATEGORY) {
            categoryName = "Alla tips";

        } else {
            categoryName = "Mina skapade tips";
        }

        categoryText.setText(categoryName);
    }

    public void createNewTipButton() {
        createNewTipButton = view.findViewById(R.id.createNewTipButton);
        createNewTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), CreateNewTip.class);
                startIntent.putExtra("source", "TA");
                startActivity(startIntent);
            }
        });
    }


    public void loadTheTips() {
        if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
            final ProgressDialog dialog;


            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Laddar in tipsen");
            dialog.setMessage("var god vänta");
            dialog.show();

            layout = view.findViewById(R.id.newsLinearLayout);

            BaseAPIService api = RetrofitClient.getApiService();

            Call<List<Tip>> call = api.getTips();
            //Call<String> call =api.getHelloString();

            call.enqueue(new Callback<List<Tip>>() {
                @Override
                public void onResponse(Call<List<Tip>> call, Response<List<Tip>> response) {
                    dialog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Tipsen har inte laddats" + response.code(), Toast.LENGTH_LONG).show();
                        call.clone().enqueue(this);
                    }
                    allTips = response.body();
                    sort(allTips);


                    for (int i = 0; i < newListToSort.length; i++) {
                        if (view.isAttachedToWindow())
                            addTips(newListToSort[i]);
                    }

                }

                @Override
                public void onFailure(Call<List<Tip>> call, Throwable t) {
                    if (view.isAttachedToWindow()) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Tipsen har inte laddats2", Toast.LENGTH_LONG).show();
                        call.clone().enqueue(this);
                    }
                }
            });
        }
    }

    private void sort(List<Tip> listToSort) {

        newListToSort = new Tip[listToSort.size()];
        int n = listToSort.size();
        for (int u = 0; u < listToSort.size(); u++) {
            newListToSort[u] = listToSort.get(u);
        }

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (newListToSort[j].equals(newListToSort[min_idx])) {
                    if (newListToSort[j].getLikes() < newListToSort[min_idx].getLikes()) {
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

    private void checkTheTipsCategoryandAdd(Tip tipToCheck) {

        if (categoryType == 1) {
            if (tipToCheck.isWarmth() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 2) {
            if (tipToCheck.isWater() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 3) {
            if (tipToCheck.isShelter() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 4) {
            if (tipToCheck.isFood() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 5) {
            if (tipToCheck.isHealth() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 6) {
            if (tipToCheck.isSecurity() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 7) {
            if (tipToCheck.isStorage() == true) {
                tipsList.add(tipToCheck);
            }
        }
        if (categoryType == 8) {
            if (tipToCheck.isOther() == true) {
                tipsList.add(tipToCheck);
            }
        }
    }


    private void addTips(Tip tip) {

        TipsItemView tipsItemView = new TipsItemView(getActivity(), tip);

        tipsItemView.setOnClickListener(l ->
        {
            Intent startIntent = new Intent(getActivity().getApplicationContext(), ManipulateTip.class);
            startIntent.putExtra("theTip", tip);
            startActivity(startIntent);

        });

        layout.addView(tipsItemView);
    }

    private void setHamburgerButton() {
        hamburger = view.findViewById(R.id.insideTopLinewarLayout);
        hamburger.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(getContext(), v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        layout.removeAllViews();

                        int id = item.getItemId();

                        switch (id) {
                            case R.id.tipsMenuMy:
                                categoryType = TipsFragment.MY_CATEGORY;
                                break;
                            case R.id.tipsMenuAll:
                                categoryType = TipsFragment.ALL_CATEGORY;
                                break;
                            case R.id.tipsMenuWarmth:
                                categoryType = TipsFragment.WARMTH_CATEGORY;
                                break;
                            case R.id.tipsMenuWater:
                                categoryType = TipsFragment.WATER_CATEGORY;
                                break;
                            case R.id.tipsMenuShelter:
                                categoryType = TipsFragment.SHELTER_CATEGORY;
                                break;
                            case R.id.tipsMenuFood:
                                categoryType = TipsFragment.FOOD_CATEGORY;
                                break;
                            case R.id.tipsMenuHealth:
                                categoryType = TipsFragment.HEALTH_CATEGORY;
                                break;
                            case R.id.tipsMenuSecurity:
                                categoryType = TipsFragment.SECURITY_CATEGORY;
                                break;
                            case R.id.tipsMenuStorage:
                                categoryType = TipsFragment.STORAGE_CATEGORY;
                                break;
                            case R.id.tipsMenuOther:
                                categoryType = TipsFragment.OTHER_CATEGORY;
                                break;

                        }

                        showTipsInLayout();
                        setCategoryView();

                        return false;
                    }

                });

                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.tips_popup_menu, menu.getMenu());
                insertMenuItemIcons(getContext(), menu);
                menu.show();
            }
        });
    }

    public void showTipsInLayout() {
        tipsList.clear();

        if (categoryType == MY_CATEGORY) {
            for (int i = 0; i < allTips.size(); i++) {
                if (allTips.get(i).getCreator().equals(MainActivity.CREATOR_NAME)) {
                    tipsList.add(allTips.get(i));
                }
            }
        } else if (categoryType == ALL_CATEGORY) {
            for (int i = 0; i < allTips.size(); i++) {
                tipsList.add(allTips.get(i));
            }
        } else {
            for (int i = 0; i < allTips.size(); i++) {
                Tip tipToCheck = allTips.get(i);
                checkTheTipsCategoryandAdd(tipToCheck);
            }
        }
        sort(tipsList);

        for (int i = 0; i < newListToSort.length; i++) {
            addTips(newListToSort[i]);

        }
    }

    /**
     * Moves icons from the PopupMenu's MenuItems' icon fields into the menu title as a Spannable with the icon and title text.
     */
    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }

    /**
     * @return true if the menu has at least one MenuItem with an icon.
     */
    private static boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null) return true;
        }
        return false;
    }

    /**
     * Converts the given MenuItem's title into a Spannable containing both its icon and title.
     */
    private static void insertMenuItemIcon(Context context, MenuItem menuItem) {
        Drawable icon = menuItem.getIcon();

        // If there's no icon, we insert a transparent one to keep the title aligned with the items
        // which do have icons.
        if (icon == null) icon = new ColorDrawable(Color.TRANSPARENT);

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.menu_item_icon_size);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + menuItem.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        menuItem.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        menuItem.setIcon(null);


    }


}

