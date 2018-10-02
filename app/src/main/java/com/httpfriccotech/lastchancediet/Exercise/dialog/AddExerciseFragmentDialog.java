package com.httpfriccotech.lastchancediet.Exercise.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.Exercise.SelectExerciseData;
import com.httpfriccotech.lastchancediet.Exercise.interfaces.AddExerciseListener;
import com.httpfriccotech.lastchancediet.R;

public class AddExerciseFragmentDialog extends DialogFragment {
    private View rootView, parentView;
    private AddExerciseListener addExerciseListener;
    private String title;
    private String type;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            title = getArguments().getString("title");
            type = getArguments().getString("type");
        }
        if (type!=null&& type.equalsIgnoreCase("c")){
            rootView = inflater.inflate(R.layout.exercise_popup_cardio, container, false);
        }
        else if (type!=null&& type.equalsIgnoreCase("s")){
            rootView = inflater.inflate(R.layout.exercise_popup, container, false);
        }

        if (title!=null)
            ((TextView)rootView.findViewById(R.id.title)).setText(title);

        if (type!=null&& type.equalsIgnoreCase("c")){
            ((Button)rootView.findViewById(R.id.btn_add_c)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectExerciseData selectExerciseData=new SelectExerciseData();
                    selectExerciseData.setTitle(((EditText)rootView.findViewById(R.id.et_c_name)).getText().toString());
                    selectExerciseData.setHowlong(((EditText)rootView.findViewById(R.id.et_c_minute)).getText().toString());
                    if (selectExerciseData.getTitle().isEmpty()||selectExerciseData.getHowlong().isEmpty())
                        Toast.makeText(getContext(),"Fields can't be empty",Toast.LENGTH_LONG).show();
                    else
                    addExerciseListener.addExercise(selectExerciseData,"cardiovascular");
                }
            });
        }
        else if (type!=null&& type.equalsIgnoreCase("s")){
            ((Button)rootView.findViewById(R.id.btn_add_s)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectExerciseData selectExerciseData=new SelectExerciseData();
                    selectExerciseData.setTitle(((EditText)rootView.findViewById(R.id.et_s_name)).getText().toString());
                    selectExerciseData.setStrength_training_set(((EditText)rootView.findViewById(R.id.et_s_sets)).getText().toString());
                    selectExerciseData.setStrength_training_reps_set(((EditText)rootView.findViewById(R.id.et_s_reps)).getText().toString());
                    selectExerciseData.setStrength_training_weight_set(((EditText)rootView.findViewById(R.id.et_s_weight)).getText().toString());
                    if (selectExerciseData.getTitle().isEmpty()||selectExerciseData.getStrength_training_set().isEmpty()||selectExerciseData.getStrength_training_reps_set().isEmpty()||selectExerciseData.getStrength_training_weight_set().isEmpty())
                        Toast.makeText(getContext(),"Fields can't be empty",Toast.LENGTH_LONG).show();
                    else
                        addExerciseListener.addExercise(selectExerciseData,"strength_training");
                }
            });
        }
        return rootView;
    }

    public static AddExerciseFragmentDialog newInstance(String type,String title) {
        AddExerciseFragmentDialog addExerciseFragmentDialog = new AddExerciseFragmentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("type", type);
        addExerciseFragmentDialog.setArguments(args);
        return addExerciseFragmentDialog;
    }

    public void addExercise(AddExerciseListener addExerciseListener) {
        this.addExerciseListener = addExerciseListener;
    }
}

