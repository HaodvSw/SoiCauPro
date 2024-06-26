// Generated by view binder compiler. Do not edit!
package com.core.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.core.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SpinnerLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView body;

  @NonNull
  public final RecyclerView recyclerViewSpinner;

  private SpinnerLayoutBinding(@NonNull CardView rootView, @NonNull CardView body,
      @NonNull RecyclerView recyclerViewSpinner) {
    this.rootView = rootView;
    this.body = body;
    this.recyclerViewSpinner = recyclerViewSpinner;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static SpinnerLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SpinnerLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.spinner_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SpinnerLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView body = (CardView) rootView;

      id = R.id.recyclerViewSpinner;
      RecyclerView recyclerViewSpinner = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewSpinner == null) {
        break missingId;
      }

      return new SpinnerLayoutBinding((CardView) rootView, body, recyclerViewSpinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
