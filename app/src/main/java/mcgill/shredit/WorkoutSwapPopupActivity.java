package mcgill.shredit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

import mcgill.shredit.model.Exercise;

public class WorkoutSwapPopupActivity extends AppCompatDialogFragment {

//    int index = -1;
    private WorkoutSwapDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("exerciseName") + ": Select Replacement Exercise")
            .setItems((CharSequence[]) getArguments().getStringArray("exercisePool"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int index = which;

                    listener.applyIndex(index);

                    dialog.cancel();    // close popup
                }
            });

        return builder.create();
    }

    @SuppressLint("ValidFragment")
    public static WorkoutSwapPopupActivity newInstance(String exerciseName, String[] exercisePool) {
        WorkoutSwapPopupActivity res = new WorkoutSwapPopupActivity();

        Bundle args = new Bundle();
        args.putString("exerciseName", exerciseName);
        args.putStringArray("exercisePool", exercisePool);

        res.setArguments(args);
        return res;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (WorkoutSwapDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "WorkoutSwapDialogListener not implemented");
        }
    }

    public interface WorkoutSwapDialogListener{
        void applyIndex(int index);
    }
}
