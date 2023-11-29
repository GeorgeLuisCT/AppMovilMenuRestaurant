package com.gaedet.menujosue.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.interfaces.DataUpdateListener;
import com.gaedet.menujosue.interfaces.OnLongPressListener;


public class ContainerShoppingCartFragment extends Fragment {

    private LinearLayout LLMyOrder;
    private LinearLayout LLDetails;
    private LinearLayout LLPay;
    private TextView TTMyOrder;
    private TextView TTDetails;
    private View ViewLevel1;
    private View ViewLevel2;
    private TextView TTPay;
    private LinearLayout containrbtnNextPage;
    Button btnNextPage;
    DataUpdateListener dataUpdateListener;
    private TextView info;
    private ConstraintLayout content;
    DatabaseManager databaseManager;

    public ContainerShoppingCartFragment() {
        // Constructor público vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_container_shoping_cart, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        LLMyOrder = view.findViewById(R.id.LLMyOrder);
        LLDetails = view.findViewById(R.id.LLDetails);
        LLPay = view.findViewById(R.id.LLPay);

        TTMyOrder = view.findViewById(R.id.TTMyOrder);
        TTDetails = view.findViewById(R.id.TTDetails);
        TTPay = view.findViewById(R.id.TTPay);

        ViewLevel1 = view.findViewById(R.id.ViewLevel1);
        ViewLevel2 = view.findViewById(R.id.ViewLevel2);

        info = view.findViewById(R.id.info);
        content = view.findViewById(R.id.contet);


        // Configura el adaptador de fragmento
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        // Agrega un listener al ViewPager para detectar cambios de página
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // No es necesario implementar este método
            }

            @Override
            public void onPageSelected(int position) {
                // Cambia las propiedades de los botones según la página seleccionada
                updateButtonProperties(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // No es necesario implementar este método
            }
        });

        // Asigna un listener a cada botón para cambiar de página
        LLMyOrder.setOnClickListener(v -> viewPager.setCurrentItem(0));
        LLDetails.setOnClickListener(v -> viewPager.setCurrentItem(1));
        LLPay.setOnClickListener(v -> viewPager.setCurrentItem(2));

        containrbtnNextPage = view.findViewById(R.id.containrbtnNextPage);
        btnNextPage = view.findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener(v -> {
            int nextPage = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(nextPage);
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new MyOrderFragment();
            } else if (position == 1) {
                return new DetailsOrderFragment();
            }else if (position == 2) {
                return new PayOrderFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            // Número total de pestañas
            return 3;
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            // Títulos de las pestañas
            if (position == 0) {
                return "Estados";
            } else if (position == 1) {
                return "Llamadas";
            }
            return null;
        }
    }

    // Método para actualizar las propiedades de los botones
    private void updateButtonProperties(int selectedPage) {
        // Restaura las propiedades predeterminadas para todos los botones
        resetButtonProperties();

        // Dependiendo de la página seleccionada, actualiza las propiedades de los botones
        switch (selectedPage) {
            case 0: // Página 1 (SimpleMenuFragment)
                // Personaliza las propiedades del botón para la página 1
                applyButtonProperties(LLMyOrder,TTMyOrder, R.drawable.primary_button, R.color.base_color, Typeface.BOLD);
                applyButtonProperties(LLDetails,TTDetails, R.drawable.rounded_shape, R.color.primary_color, Typeface.NORMAL);
                applyButtonProperties(LLPay,TTPay ,R.drawable.rounded_shape, R.color.primary_color, Typeface.NORMAL);
                ViewLevel1.setBackgroundColor(getResources().getColor(R.color.tertiary_variant_color));
                ViewLevel2.setBackgroundColor(getResources().getColor(R.color.tertiary_variant_color));
                containrbtnNextPage.setVisibility(View.VISIBLE);
                break;
            case 1: // Página 2 (ExtrasMenuFragment)
                // Personaliza las propiedades del botón para la página 2
                applyButtonProperties(LLDetails,TTDetails, R.drawable.primary_button, R.color.base_color, Typeface.BOLD);
                applyButtonProperties(LLMyOrder,TTMyOrder, R.drawable.primary_button, R.color.base_color, Typeface.NORMAL);
                applyButtonProperties(LLPay,TTPay, R.drawable.rounded_shape, R.color.primary_color, Typeface.NORMAL);
                ViewLevel1.setBackgroundColor(getResources().getColor(R.color.primary_color));
                ViewLevel2.setBackgroundColor(getResources().getColor(R.color.tertiary_variant_color));
                updateShopping();
                containrbtnNextPage.setVisibility(View.VISIBLE);
                break;
            case 2: // Página 3 (BeveragesMenuFragment)
                // Personaliza las propiedades del botón para la página 3
                applyButtonProperties(LLPay,TTPay, R.drawable.primary_button, R.color.base_color, Typeface.BOLD);
                applyButtonProperties(LLMyOrder,TTMyOrder, R.drawable.primary_button, R.color.base_color, Typeface.NORMAL);
                applyButtonProperties(LLDetails,TTDetails, R.drawable.primary_button, R.color.base_color, Typeface.NORMAL);
                ViewLevel1.setBackgroundColor(getResources().getColor(R.color.primary_color));
                ViewLevel2.setBackgroundColor(getResources().getColor(R.color.primary_color));
                containrbtnNextPage.setVisibility(View.GONE);
                break;
        }
    }

    // Método para restaurar las propiedades predeterminadas de todos los botones
    private void resetButtonProperties() {
        LLMyOrder.setBackgroundResource(R.drawable.primary_button);
        LLDetails.setBackgroundResource(R.drawable.secondary_button);
        LLPay.setBackgroundResource(R.drawable.secondary_button);
    }

    private void applyButtonProperties(LinearLayout button,TextView textView, int backgroundResource, int textColor, int typefaceStyle) {
        button.setBackgroundResource(backgroundResource);
        textView.setTextColor(getResources().getColor(textColor));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataUpdateListener) {
            dataUpdateListener = (DataUpdateListener) context;
        } else {
            throw new RuntimeException("La actividad no implementa la interfaz MiInterfaz");
        }
    }

    public void updateShopping() {
        // Llama al método en la página 2
        if (dataUpdateListener != null) {
            dataUpdateListener.onDataUpdated();
            System.out.println("el escuchador no es nulo");
        }else {
            System.out.println("el escuchador es nulo");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("hola");

        databaseManager = new DatabaseManager(getContext());
        databaseManager.open();
        boolean isShoppingCartEmpty = databaseManager.isShoppingCartEmpty();
        databaseManager.close();

        if (!isShoppingCartEmpty){
            info.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }else{
            info.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
    }
}
