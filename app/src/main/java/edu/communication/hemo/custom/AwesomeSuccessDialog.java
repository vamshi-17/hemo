package edu.communication.hemo.custom;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import edu.communication.hemo.R;

public class AwesomeSuccessDialog extends AwesomeDialogBuilder<AwesomeSuccessDialog> {
    private Button positiveButton = (Button) findView(R.id.btDialogYes);
    private Button negativeButton = (Button) findView(R.id.btDialogNo);
    private Button doneButton = (Button) findView(R.id.btDialogDone);
    private RelativeLayout dialogBody = (RelativeLayout) findView(R.id.dialog_body);

    public AwesomeSuccessDialog(Context context) {
        super(context);
        setColoredCircle(R.color.dialogSuccessBackgroundColor);
        setDialogIconAndColor(R.drawable.ic_success, R.color.white);
        setNegativeButtonbackgroundColor(R.color.dialogSuccessBackgroundColor);
        setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor);
        setDoneButtonbackgroundColor(R.color.dialogSuccessBackgroundColor);
    }

    public AwesomeSuccessDialog setDialogBodyBackgroundColor(int color) {
        RelativeLayout relativeLayout = this.dialogBody;
        if (relativeLayout != null) {
            relativeLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeSuccessDialog setPositiveButtonClick(final Closure selectedYes) {
        this.positiveButton.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.custom.AwesomeSuccessDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Closure closure = selectedYes;
                if (closure != null) {
                    closure.exec();
                }
                AwesomeSuccessDialog.this.hide();
            }
        });
        return this;
    }

    public AwesomeSuccessDialog setNegativeButtonClick(final Closure selectedNo) {
        this.negativeButton.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.custom.AwesomeSuccessDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Closure closure = selectedNo;
                if (closure != null) {
                    closure.exec();
                }
                AwesomeSuccessDialog.this.hide();
            }
        });
        return this;
    }

    public AwesomeSuccessDialog setDoneButtonClick(final Closure selectedDone) {
        this.doneButton.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.custom.AwesomeSuccessDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Closure closure = selectedDone;
                if (closure != null) {
                    closure.exec();
                }
                AwesomeSuccessDialog.this.hide();
            }
        });
        return this;
    }

    public AwesomeSuccessDialog showPositiveButton(boolean show) {
        Button button = this.positiveButton;
        if (button != null) {
            button.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog showNegativeButton(boolean show) {
        Button button = this.negativeButton;
        if (button != null) {
            button.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog showDoneButton(boolean show) {
        Button button = this.doneButton;
        if (button != null) {
            button.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog setPositiveButtonbackgroundColor(int buttonBackground) {
        Button button = this.positiveButton;
        if (button != null) {
            button.getBackground().setColorFilter(ContextCompat.getColor(getContext(), buttonBackground), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeSuccessDialog setPositiveButtonTextColor(int textColor) {
        Button button = this.positiveButton;
        if (button != null) {
            button.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
        return this;
    }

    public AwesomeSuccessDialog setPositiveButtonText(String text) {
        Button button = this.positiveButton;
        if (button != null) {
            button.setText(text);
            this.positiveButton.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog setNegativeButtonbackgroundColor(int buttonBackground) {
        Button button = this.negativeButton;
        if (button != null) {
            button.getBackground().setColorFilter(ContextCompat.getColor(getContext(), buttonBackground), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeSuccessDialog setNegativeButtonText(String text) {
        Button button = this.negativeButton;
        if (button != null) {
            button.setText(text);
            this.negativeButton.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog setNegativeButtonTextColor(int textColor) {
        Button button = this.negativeButton;
        if (button != null) {
            button.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
        return this;
    }

    public AwesomeSuccessDialog setDoneButtonbackgroundColor(int buttonBackground) {
        Button button = this.doneButton;
        if (button != null) {
            button.getBackground().setColorFilter(ContextCompat.getColor(getContext(), buttonBackground), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public AwesomeSuccessDialog setDoneButtonText(String text) {
        Button button = this.doneButton;
        if (button != null) {
            button.setText(text);
            this.doneButton.setVisibility(0);
        }
        return this;
    }

    public AwesomeSuccessDialog setDoneButtonTextColor(int textColor) {
        Button button = this.doneButton;
        if (button != null) {
            button.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
        return this;
    }

    @Override // edu.communication.hemo.custom.AwesomeDialogBuilder
    protected int getLayout() {
        return R.layout.dialog_success;
    }
}
