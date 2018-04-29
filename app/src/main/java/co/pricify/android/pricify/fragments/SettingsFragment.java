package co.pricify.android.pricify.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.pricify.android.pricify.R;


public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getActivity().setTitle("Settings");

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        return  view;
    }
}
