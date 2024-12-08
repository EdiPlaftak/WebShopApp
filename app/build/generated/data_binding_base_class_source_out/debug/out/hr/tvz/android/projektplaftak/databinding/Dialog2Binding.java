// Generated by view binder compiler. Do not edit!
package hr.tvz.android.projektplaftak.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import hr.tvz.android.projektplaftak.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class Dialog2Binding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button negativeDialog2Button;

  @NonNull
  public final Button positiveDialog2Button;

  @NonNull
  public final TextView textView;

  private Dialog2Binding(@NonNull LinearLayout rootView, @NonNull Button negativeDialog2Button,
      @NonNull Button positiveDialog2Button, @NonNull TextView textView) {
    this.rootView = rootView;
    this.negativeDialog2Button = negativeDialog2Button;
    this.positiveDialog2Button = positiveDialog2Button;
    this.textView = textView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static Dialog2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static Dialog2Binding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static Dialog2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.negativeDialog2Button;
      Button negativeDialog2Button = ViewBindings.findChildViewById(rootView, id);
      if (negativeDialog2Button == null) {
        break missingId;
      }

      id = R.id.positiveDialog2Button;
      Button positiveDialog2Button = ViewBindings.findChildViewById(rootView, id);
      if (positiveDialog2Button == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new Dialog2Binding((LinearLayout) rootView, negativeDialog2Button,
          positiveDialog2Button, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
