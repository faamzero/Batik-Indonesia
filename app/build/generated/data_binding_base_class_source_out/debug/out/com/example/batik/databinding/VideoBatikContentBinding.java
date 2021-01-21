// Generated by view binder compiler. Do not edit!
package com.example.batik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.batik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class VideoBatikContentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView asalTextView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView dateTextView;

  @NonNull
  public final TextView nameTextView;

  @NonNull
  public final CardView posterCardView;

  @NonNull
  public final ImageView posterImgeView;

  private VideoBatikContentBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView asalTextView, @NonNull CardView cardView, @NonNull TextView dateTextView,
      @NonNull TextView nameTextView, @NonNull CardView posterCardView,
      @NonNull ImageView posterImgeView) {
    this.rootView = rootView;
    this.asalTextView = asalTextView;
    this.cardView = cardView;
    this.dateTextView = dateTextView;
    this.nameTextView = nameTextView;
    this.posterCardView = posterCardView;
    this.posterImgeView = posterImgeView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static VideoBatikContentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static VideoBatikContentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.video_batik_content, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static VideoBatikContentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.asalTextView;
      TextView asalTextView = rootView.findViewById(id);
      if (asalTextView == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = rootView.findViewById(id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.dateTextView;
      TextView dateTextView = rootView.findViewById(id);
      if (dateTextView == null) {
        break missingId;
      }

      id = R.id.nameTextView;
      TextView nameTextView = rootView.findViewById(id);
      if (nameTextView == null) {
        break missingId;
      }

      id = R.id.posterCardView;
      CardView posterCardView = rootView.findViewById(id);
      if (posterCardView == null) {
        break missingId;
      }

      id = R.id.posterImgeView;
      ImageView posterImgeView = rootView.findViewById(id);
      if (posterImgeView == null) {
        break missingId;
      }

      return new VideoBatikContentBinding((ConstraintLayout) rootView, asalTextView, cardView,
          dateTextView, nameTextView, posterCardView, posterImgeView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}