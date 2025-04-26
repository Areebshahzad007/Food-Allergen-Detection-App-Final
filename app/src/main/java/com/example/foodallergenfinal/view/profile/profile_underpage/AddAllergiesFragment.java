package com.example.foodallergenfinal.view.profile.profile_underpage;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodallergenfinal.R;
import com.example.foodallergenfinal.adapter.AllergicAdapter;
import com.example.foodallergenfinal.databinding.FragmentAddAllergiesBinding;
import com.example.foodallergenfinal.model.AllergicItem;
import com.example.foodallergenfinal.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddAllergiesFragment extends Fragment {
    private FragmentAddAllergiesBinding binding;

    private AllergicAdapter adapter;
    private List<AllergicItem> allergicItems;

    public AddAllergiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddAllergiesBinding.inflate(inflater, container, false);

        allergicItems = new ArrayList<>();
        allergicItems.add(new AllergicItem(R.drawable.objects,"Celery", false));
        allergicItems.add(new AllergicItem(R.drawable.white_egg,"Eggs", false));
        allergicItems.add(new AllergicItem(R.drawable.fish_food,"Fish", false));
        allergicItems.add(new AllergicItem(R.drawable.gluten,"Gluten", false));
        allergicItems.add(new AllergicItem(R.drawable.lupin,"Lupine", false));
        allergicItems.add(new AllergicItem(R.drawable.peanuts1,"Peanuts", false));
        allergicItems.add(new AllergicItem(R.drawable.soy,"Soybean", false));
        allergicItems.add(new AllergicItem(R.drawable.hazelnut,"Tree Nut", false));
        allergicItems.add(new AllergicItem(R.drawable.ic_milk,"Milk", false));
        allergicItems.add(new AllergicItem(R.drawable.ic_shellfish,"Shellfish", false));
        allergicItems.add(new AllergicItem(R.drawable.ic_mustarf,"Mustard", false));
        allergicItems.add(new AllergicItem(R.drawable.ic_sesame,"Sesame", false));
        allergicItems.add(new AllergicItem(R.drawable.ic_gelatin,"Gelatin", false));

        // Load saved allergies
        Set<String> savedItems = PrefsManager.getSavedAllergies(requireContext());
        for (AllergicItem item : allergicItems) {
            item.setSelected(savedItems.contains(item.getName()));
        }

        adapter = new AllergicAdapter(allergicItems, this::saveAllergicItem);
        binding.recAllergiesItem.setAdapter(adapter);

        binding.backBtn.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        return binding.getRoot();
    }

    // Method to save selected allergic item
    private void saveAllergicItem(String itemName, boolean isSelected) {
        PrefsManager.saveAllergicItem(requireContext(), itemName, isSelected);
    }

}