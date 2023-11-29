package com.gaedet.menujosue.views;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseHelper;
import com.gaedet.menujosue.database.DatabaseManager;


public class ContainerMenuFragment extends Fragment {

    private Button buttonSimpleMenu;
    private Button buttonExtras;
    private Button buttonBeverages;

    public  static String NAME_CURRENT_TABLE = "food";
    public ContainerMenuFragment() {
        // Constructor público vacío
    }

    public static ContainerMenuFragment newInstance() {
        return new ContainerMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_container_menu, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
         buttonSimpleMenu = view.findViewById(R.id.buttonSimpleMenu);
         buttonExtras = view.findViewById(R.id.buttonExtras);
         buttonBeverages = view.findViewById(R.id.buttonBeverages);

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
        buttonSimpleMenu.setOnClickListener(v -> viewPager.setCurrentItem(0));
        buttonExtras.setOnClickListener(v -> viewPager.setCurrentItem(1));
        buttonBeverages.setOnClickListener(v -> viewPager.setCurrentItem(2));
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new SimpleMenuFragment();
            } else if (position == 1) {
                return new ExtrasMenuFragment();
            }else if (position == 2) {
                return new BeveragesMenuFragment();
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
                applyButtonProperties(buttonSimpleMenu, R.drawable.primary_button, R.color.tertiary_color, Typeface.BOLD);
                applyButtonProperties(buttonExtras, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                applyButtonProperties(buttonBeverages, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                NAME_CURRENT_TABLE = DatabaseHelper.TABLE_FOOD;
                break;
            case 1: // Página 2 (ExtrasMenuFragment)
                // Personaliza las propiedades del botón para la página 2
                applyButtonProperties(buttonExtras, R.drawable.primary_button, R.color.tertiary_color, Typeface.BOLD);
                applyButtonProperties(buttonSimpleMenu, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                applyButtonProperties(buttonBeverages, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                NAME_CURRENT_TABLE = DatabaseHelper.TABLE_EXTRAS;
                break;
            case 2: // Página 3 (BeveragesMenuFragment)
                // Personaliza las propiedades del botón para la página 3
                applyButtonProperties(buttonBeverages, R.drawable.primary_button, R.color.tertiary_color, Typeface.BOLD);
                applyButtonProperties(buttonSimpleMenu, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                applyButtonProperties(buttonExtras, R.drawable.secondary_button, R.color.primary_color, Typeface.NORMAL);
                NAME_CURRENT_TABLE = DatabaseHelper.TABLE_BEVERAGES;

                break;
        }
    }

    // Método para restaurar las propiedades predeterminadas de todos los botones
    private void resetButtonProperties() {
        buttonSimpleMenu.setBackgroundResource(R.drawable.primary_button);
        buttonExtras.setBackgroundResource(R.drawable.secondary_button);
        buttonBeverages.setBackgroundResource(R.drawable.secondary_button);

        buttonSimpleMenu.setTextColor(getResources().getColor(R.color.tertiary_color));
        buttonExtras.setTextColor(getResources().getColor(R.color.primary_color));
        buttonBeverages.setTextColor(getResources().getColor(R.color.primary_color));
    }

    private void applyButtonProperties(Button button, int backgroundResource, int textColor, int typefaceStyle) {
        button.setBackgroundResource(backgroundResource);
        button.setTextColor(getResources().getColor(textColor));
        Typeface typeface = Typeface.create(button.getTypeface(), typefaceStyle);
        button.setTypeface(typeface);
    }
}
