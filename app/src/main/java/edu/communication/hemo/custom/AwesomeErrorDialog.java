package edu.communication.hemo.custom;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import edu.communication.hemo.R;

public class AwesomeErrorDialog extends AwesomeDialogBuilder<AwesomeErrorDialog> {
    private Button btDialogOk = (Button) findView(R.id.btDialogOk);
    private RelativeLayout dialogBody = (RelativeLayout) findView(R.id.dialog_body);

    public AwesomeErrorDialog(Context context) {
        super(context);
        setColoredCircle(R.color.dialogErrorBackgroundColor);
        setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white);
        setButtonBackgroundColor(R.color.dialogErrorBackgroundColor);
        setCancelable(true);
    }

    public AwesomeErrorDialog setDialogBodyBackgroundColor(int color) {
        RelativeLayout relativeLayout = this.dialogBody;
        if (relativeLayout != null) {
            relativeLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeErrorDialog setErrorButtonClick(final Closure selecteOk) {
        this.btDialogOk.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.custom.AwesomeErrorDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Closure closure = selecteOk;
                if (closure != null) {
                    closure.exec();
                }
                AwesomeErrorDialog.this.hide();
            }
        });
        return this;
    }

    public AwesomeErrorDialog setButtonBackgroundColor(int buttonBackground) {
        Button button = this.btDialogOk;
        if (button != null) {
            button.getBackground().setColorFilter(ContextCompat.getColor(getContext(), buttonBackground), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeErrorDialog setButtonTextColor(int textColor) {
        Button button = this.btDialogOk;
        if (button != null) {
            button.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
        return this;
    }

    public AwesomeErrorDialog setButtonText(String text) {
        Button button = this.btDialogOk;
        if (button != null) {
            button.setText(text);
            this.btDialogOk.setVisibility(0);
        }
        return this;
    }

    @Override // edu.communication.hemo.custom.AwesomeDialogBuilder
    protected int getLayout() {
        return R.layout.dialog_error;
    }
}
