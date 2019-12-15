package com.example.mealbuddy;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.Arrays;
import java.util.List;


public class MultiSelectionSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener
{
    String[] _items = null;
    Integer[] minId = null;
    Integer[] maxId = null;
    boolean[] mSelection = null;
    View view = null;

    ArrayAdapter<String> simple_adapter;

    public MultiSelectionSpinner(Context context)
    {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;

            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, mSelection, this);
        builder.setTitle("Available nutrition filter");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                for (int i = 0; i < _items.length; i++){
                    RelativeLayout layout = view.findViewById(minId[i]);
                    RelativeLayout layout2 = view.findViewById(maxId[i]);
                    if (mSelection[i]){
                        layout.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                    else{
                        layout.setVisibility(View.GONE);
                        layout2.setVisibility(View.GONE);
                        switch (i){
                            case 0:
                                SeekBar minFat = view.findViewById(R.id.minFatBar);
                                minFat.setProgress(0);
                                SeekBar maxFat = view.findViewById(R.id.maxFatBar);
                                maxFat.setProgress(advancedsearchFragment.MAXFAT);
                                break;
                            case 1:
                                SeekBar minCal = view.findViewById(R.id.minCalBar);
                                minCal.setProgress(0);
                                SeekBar maxCal = view.findViewById(R.id.maxCalBar);
                                maxCal.setProgress(advancedsearchFragment.MAXCAL);
                                break;
                            case 2:
                                SeekBar minCarb = view.findViewById(R.id.minCarbsBar);
                                minCarb.setProgress(0);
                                SeekBar maxCarb = view.findViewById(R.id.maxCarbsBar);
                                maxCarb.setProgress(advancedsearchFragment.MAXCARB);
                                break;
                            case 3:
                                SeekBar minPro = view.findViewById(R.id.minProBar);
                                minPro.setProgress(0);
                                SeekBar maxPro = view.findViewById(R.id.maxProBar);
                                maxPro.setProgress(advancedsearchFragment.MAXPRO);
                                break;
                            case 4:
                                SeekBar minCaff = view.findViewById(R.id.minCaffBar);
                                minCaff.setProgress(0);
                                SeekBar maxCaff = view.findViewById(R.id.maxCaffBar);
                                maxCaff.setProgress(advancedsearchFragment.MAXCAFFEINE);
                                break;
                            case 5:
                                SeekBar minAlc = view.findViewById(R.id.minAlcBar);
                                minAlc.setProgress(0);
                                SeekBar maxAlc = view.findViewById(R.id.maxAlcBar);
                                maxAlc.setProgress(advancedsearchFragment.MAXALCOHOL);
                                break;
                            case 6:
                                SeekBar minCalcium = view.findViewById(R.id.minCalciumBar);
                                minCalcium.setProgress(0);
                                SeekBar maxCalcium = view.findViewById(R.id.maxCalciumBar);
                                maxCalcium.setProgress(advancedsearchFragment.MAXCALCIUM);
                                break;
                            case 7:
                                SeekBar minChol = view.findViewById(R.id.minCholBar);
                                minChol.setProgress(0);
                                SeekBar maxChol = view.findViewById(R.id.maxCholBar);
                                maxChol.setProgress(advancedsearchFragment.MAXCHOLESTEROL);
                                break;
                            case 8:
                                SeekBar minVitA = view.findViewById(R.id.minVitABar);
                                minVitA.setProgress(0);
                                SeekBar maxVitA = view.findViewById(R.id.maxVitABar);
                                maxVitA.setProgress(advancedsearchFragment.MAXVITAMIN);
                                break;
                            case 9:
                                SeekBar minVitC = view.findViewById(R.id.minVitCBar);
                                minVitC.setProgress(0);
                                SeekBar maxVitC = view.findViewById(R.id.maxVitCBar);
                                maxVitC.setProgress(advancedsearchFragment.MAXVITAMIN);
                                break;
                            case 10:
                                SeekBar minVitD = view.findViewById(R.id.minVitDBar);
                                minVitD.setProgress(0);
                                SeekBar maxVitD = view.findViewById(R.id.maxVitDBar);
                                maxVitD.setProgress(advancedsearchFragment.MAXVITAMIN);
                                break;
                            case 11:
                                SeekBar minVitE = view.findViewById(R.id.minVitEBar);
                                minVitE.setProgress(0);
                                SeekBar maxVitE = view.findViewById(R.id.maxVitEBar);
                                maxVitE.setProgress(advancedsearchFragment.MAXVITAMIN);
                                break;
                            case 12:
                                SeekBar minVitK = view.findViewById(R.id.minVitKBar);
                                minVitK.setProgress(0);
                                SeekBar maxVitK = view.findViewById(R.id.maxVitKBar);
                                maxVitK.setProgress(advancedsearchFragment.MAXVITAMIN);
                                break;
                        }
                    }
                }
            }
        });


        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(List<String> items, List<Integer> minLayout, List<Integer> maxLayout, View view) {
        this.view = view;
        _items = items.toArray(new String[items.size()]);
        minId = minLayout.toArray(new Integer[minLayout.size()]);
        maxId = maxLayout.toArray(new Integer[maxLayout.size()]);

        mSelection = new boolean[_items.length];

        simple_adapter.clear();
        simple_adapter.add("Select nutrition from here...");

        Arrays.fill(mSelection, false);


        for (int i = 0; i < _items.length; i++){
            RelativeLayout layout = view.findViewById(minId[i]);
            RelativeLayout layout2 = view.findViewById(maxId[i]);
            layout.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
        }



    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }


    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean isNotFirst = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (isNotFirst) {
                    sb.append(", ");
                }
                isNotFirst = true;

                sb.append(_items[i]);
            }
        }

        if (sb.toString() == ""){
            sb.append("Select nutrition from here...");
        }

        return sb.toString();
    }
}
