package mcgill.shredit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class WorkoutSwapPopupActivity extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("exerciseName"))
            .setMessage("Replace exercise with random exercise")
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });

        return builder.create();
    }

    @SuppressLint("ValidFragment")
    public static WorkoutSwapPopupActivity newInstance(String exerciseName) {
        WorkoutSwapPopupActivity res = new WorkoutSwapPopupActivity();

        Bundle args = new Bundle();
        args.putString("exerciseName", exerciseName);

        res.setArguments(args);
        return res;
    }
}
