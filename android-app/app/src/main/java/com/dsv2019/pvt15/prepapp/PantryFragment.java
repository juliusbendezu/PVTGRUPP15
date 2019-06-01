package com.dsv2019.pvt15.prepapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
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
import com.dsv2019.pvt15.prepapp.customcomponents.PantryCategoryView;
import com.dsv2019.pvt15.prepapp.models.PantryItem;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryFragment extends Fragment {

    private static final String EMPTY_PANTRY_MSG = "Du har inget i förrådet! Lägg till något?";
    private static final String ALL = "all";

    LinearLayout layout;
    TextView selectedTypeTV;
    ImageButton addItemButton;
    LinearLayout hamburger;
    Map<String, Set<PantryItem>> pantryMap;
    View view;

    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pantry, container, false);

        layout = view.findViewById(R.id.pantryActivityItemsLayout);
        selectedTypeTV = view.findViewById(R.id.pantrySelectedTypeTextview);
        addItemButton = view.findViewById(R.id.addPantryItemButton);
        addItemButton.setOnClickListener(l -> addPantryItem());

        hamburger = view.findViewById(R.id.pantryActivityHamburger);
        hamburger.setOnClickListener(l -> showMenu());

        refreshLayout = view.findViewById(R.id.pantryRefreshLayout);
        refreshLayout.setOnRefreshListener(PantryFragment.this::refresh);

        getPantry();


        return view;
    }

    public void refresh() {
        layout.removeAllViews();
        getPantry();
        refreshLayout.setRefreshing(false);
    }

    private void addPantryItem() {
        startActivity(new Intent(getActivity(), PantryAddItemForm.class));
    }

    private void getPantry() {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading pantry..");
        dialog.setMessage("please wait");
        dialog.show();

        if (!InternetConnection.checkConnection(getActivity())) {
            Toast.makeText(getActivity(), "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
        }

        BaseAPIService apiService = RetrofitClient.getApiService();
        Call<List<PantryItem>> call = apiService.getPantry();
        call.enqueue(new Callback<List<PantryItem>>() {
            @Override
            public void onResponse(Call<List<PantryItem>> call, Response<List<PantryItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                List<PantryItem> pantry = response.body();
                makePantry(pantry);
            }

            @Override
            public void onFailure(Call<List<PantryItem>> call, Throwable t) {
                dialog.dismiss();
                if (view.isAttachedToWindow())
                    Toast.makeText(getActivity(), "Could not load pantry, please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void makePantry(List<PantryItem> pantry) {
        if (pantry == null || pantry.isEmpty()) {
            showEmptyPantryMsg();
            return;
        }

        pantryMap = new TreeMap<>();

        for (PantryItem p : pantry) {
            String category = p.getCategory();
            if (pantryMap.containsKey(category)) {
                pantryMap.get(category).add(p);
            } else {
                Set<PantryItem> set = new TreeSet<>();
                set.add(p);
                pantryMap.put(category, set);
            }
        }

        showPantry(pantryMap, ALL);
    }

    private void showPantry(Map<String, Set<PantryItem>> pantryMap, String type) {
        if (type.equals("all"))
            for (Set<PantryItem> category : pantryMap.values()) {
                if (view.isAttachedToWindow())
                    layout.addView(new PantryCategoryView(getActivity(), category));
            }
        else
            for (Set<PantryItem> category : pantryMap.values()) {
                if (view.isAttachedToWindow() &&
                        category.iterator().next().getGeneralCategory().equals(type))
                    layout.addView(new PantryCategoryView(getActivity(), category));
            }

    }

    private void showEmptyPantryMsg() {
        TextView empty = new TextView(getActivity());
        empty.append(EMPTY_PANTRY_MSG);
        empty.setGravity(Gravity.CENTER);
        empty.setTypeface(empty.getTypeface(), Typeface.BOLD);

        empty.setPadding(20, 100, 20, 100);
        layout.addView(empty);
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), hamburger);
        popupMenu.setOnMenuItemClickListener(m ->
        {
            String type = "";
            switch (m.getItemId()) {
                case R.id.pantryMenuAll:
                    type = ALL;
                    break;
                case R.id.pantryMenuFood:
                    type = PantryItem.FOOD_CATEGORY;
                    break;
                case R.id.pantryMenuMedicine:
                    type = PantryItem.MEDICINE_CATEGORY;
                    break;
                case R.id.pantryMenuOther:
                    type = PantryItem.OTHER_CATEGORY;
                    break;
            }
            selectedTypeTV.setText(m.getTitle().toString());
            layout.removeAllViews();
            showPantry(pantryMap, type);

            return false;
        });

        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.pantry_popup_menu, popupMenu.getMenu());
        insertMenuItemIcons(getActivity(), popupMenu);
        popupMenu.show();
    }

    /**
     * Moves icons from the PopupMenu's MenuItems' icon fields into the menu title as a Spannable with the icon and title text.
     */
    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            insertMenuItemIcon(context, menu.getItem(i));
        }

    }

    /**
     * Converts the given MenuItem's title into a Spannable containing both its icon and title.
     */
    private static void insertMenuItemIcon(Context context, MenuItem item) {


        Drawable icon = item.getIcon();

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.pantry_menu_item_icon_size);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + item.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        item.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        item.setIcon(null);

    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.SOURCE, MainActivity.FROM_PANTRY);
        return intent;
    }


}
