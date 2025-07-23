package edu.cnm.deepdive.noted.controller.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.noted.databinding.FragmentCalendarBinding;
import java.time.MonthDay;
import java.time.YearMonth;

@AndroidEntryPoint
public class CalendarFragment extends Fragment {

  private static final String TAG = CalendarFragment.class.getSimpleName();

  private FragmentCalendarBinding binding;
  private YearMonth selectedMonth;
  private MonthDay selectedDay;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
