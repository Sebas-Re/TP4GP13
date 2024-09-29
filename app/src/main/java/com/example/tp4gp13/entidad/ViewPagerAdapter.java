package com.example.tp4gp13.entidad;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tp4gp13.fragment.AltaFragment;
import com.example.tp4gp13.fragment.BajaFragment;
import com.example.tp4gp13.fragment.ModificacionFragment;;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AltaFragment();
            case 1:
                return new ModificacionFragment();
            case 2:
                return new BajaFragment();
            default:
                throw new IllegalStateException("Invalid adapter position");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
