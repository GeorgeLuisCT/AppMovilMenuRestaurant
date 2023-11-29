package com.gaedet.menujosue.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.activities.MakePaymentActivity;


public class PayOrderFragment extends Fragment {

    ImageView payYape, payPlin, payBN;
    public PayOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_order, container, false);

        payYape = view.findViewById(R.id.payYape);
        payPlin = view.findViewById(R.id.payPlin);
        payBN = view.findViewById(R.id.payBN);

        changeActivity(payYape, "yape");
        changeActivity(payPlin, "plin");
        changeActivity(payBN, "bn");


        return view;
    }

    public void changeActivity(ImageView view, String type){
        view.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), MakePaymentActivity.class);
            intent.putExtra("tipo", type);
            startActivity(intent);
        });

    }
}