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
import com.dsv2019.pvt15.prepapp.tipsrelated.ManipulateTip;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.dsv2019.pvt15.prepapp.tipsrelated.TipsItemView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadFragment extends Fragment {
    private View view;
    private ImageButton hamburger;
    private LinearLayout layout;
    Tip[] newListToSort;
    private int categoryType;
    public static final int DOCUMENTS_CATEGORY = 1;
    public static final int TIPS_CATEGORY = 2;
    private TextView categoryText;
    private String categoryName = "Alla tipsen";
    private List<Tip> allTips;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //menyn + det som man valt i menyn.
        view = inflater.inflate(R.layout.fragment_download, container, false);
        categoryText = view.findViewById(R.id.categoryTextView);
        categoryText.setText(categoryName);


        /*Se avsnitt 1*/
        loadTheTips();

        /*Se avsnitt 2*/
        setHamburgerButton();

        return view;
    }

    /*Avsnitt 1*/

    /*Hämtar (Alla tipsen) just nu och laddar in dem oavsett om de är likeade eller inte.
    i load the tips anropas Sort() och den skickar med en lista på alla tipsen som den hämtade.*/
    public void loadTheTips() {
        if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
            final ProgressDialog dialog;


            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Getting the tips");
            dialog.setMessage("please wait");
            dialog.show();

            layout = view.findViewById(R.id.downloadedLinearLayout);

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
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Tipsen har inte laddats2", Toast.LENGTH_LONG).show();
                    call.clone().enqueue(this);
                }
            });
        }
    }

    /* Denna metod tar emot en lista med alla tipsen och lägger över dem i en annan Array då den är lätt att sortera.*/
    private void sort(List<Tip> listToSort) {

        int n = listToSort.size();
        newListToSort = new Tip[n];
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



    /*Avsnitt 2*/

    /*Denna skapar kategoriHamburgaren, sätter tipsen i fönstret och sätter kategorinamnet precis under rubriken.
    * I denna kommer du få redigera vad som ska hända om man klickar på Documents */
    private void setHamburgerButton() {
        hamburger = view.findViewById(R.id.tipsActivityHamburger);
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
                            case R.id.downMenuDocuments:
                                categoryType = DownloadFragment.DOCUMENTS_CATEGORY;
                                break;
                            case R.id.downMenuTips:
                                categoryType = DownloadFragment.TIPS_CATEGORY;
                                break;

                        }


                        if(categoryType==TIPS_CATEGORY){
                        /*Se avsnitt 2.1*/
                        showItemsInLayout();
                        }else{
                            /* Här får du skriva vad vill ska ske om DocumentKategorin har iklickats.
                            Du kommer nog få skapa en egen klass som motsvarar min TipsItemView */
                        }
                        /*Se avsnitt 2.2*/
                        setCategoryView();

                        return false;
                    }
                });

                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.download_pop_up_menu, menu.getMenu());
                insertMenuItemIcons(getContext(), menu);
                menu.show();
            }
        });
    }

   /*Avsnitt 2.1 - Denna anropar inladdning av tips till ScrollViewn en för en.*/
    public void showItemsInLayout() {
        //Tar bort det som låg i Viewn Innan ny inladdning.
        layout.removeAllViews();

        for (int i = 0; i < newListToSort.length; i++) {
            addTips(newListToSort[i]);
        }
    }


    /*Avsnitt 2.2 - Denna sätter textVien i toppen av rutan till den kategori som valts.*/
    private void setCategoryView() {

        if (categoryType == DOCUMENTS_CATEGORY) {
            categoryName = "Viktiga Dokument";

        } else if (categoryType == TIPS_CATEGORY) {
            categoryName = "Mina sparade tips";
        }
        categoryText.setText(categoryName);
    }

    /* Denna lägger in Tipsen i min TextView som motsvarar varje tips, dvs varje item.*/
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

    /* Dessa sätter Iconer och dylikt till Menyn / Hamburgaren.*/
    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }
    private static boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null) return true;
        }
        return false;
    }
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
