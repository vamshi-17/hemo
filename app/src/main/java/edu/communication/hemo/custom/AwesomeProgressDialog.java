package edu.communication.hemo.custom;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import edu.communication.hemo.R;

public class AwesomeProgressDialog extends AwesomeDialogBuilder<AwesomeProgressDialog> {
    private ProgressBar progressBar = (ProgressBar) findView(R.id.dialog_progress_bar);
    private RelativeLayout dialogBody = (RelativeLayout) findView(R.id.dialog_body);

    public AwesomeProgressDialog(Context context) {
        super(context);
        setColoredCircle(R.color.dialogProgressBackgroundColor);
        setProgressBarColor(R.color.white);
    }

    public AwesomeProgressDialog setDialogBodyBackgroundColor(int color) {
        RelativeLayout relativeLayout = this.dialogBody;
        if (relativeLayout != null) {
            relativeLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeProgressDialog setProgressBarColor(int color) {
        this.progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        return this;
    }

    @Override // edu.communication.hemo.custom.AwesomeDialogBuilder
    protected int getLayout() {
        return R.layout.dialog_progress;
    }
}
