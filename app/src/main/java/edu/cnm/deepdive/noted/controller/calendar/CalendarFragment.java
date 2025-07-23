package edu.cnm.deepdive.noted.controller.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.noted.R;
import edu.cnm.deepdive.noted.controller.adapter.DayBinder;
import edu.cnm.deepdive.noted.databinding.CalendarDayLayoutBinding;
import edu.cnm.deepdive.noted.databinding.FragmentCalendarBinding;
import edu.cnm.deepdive.noted.databinding.HeaderCalenderBinding;
import edu.cnm.deepdive.noted.viewmodel.ReminderViewModel;
import edu.cnm.deepdive.noted.viewmodel.TaskViewModel;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javax.inject.Inject;

@AndroidEntryPoint
public class CalendarFragment extends Fragment {

  @Inject
  DayBinder dayBinder;
  @Inject
  HeaderCalenderBinding headerBinder;

  private static final String TAG = CalendarFragment.class.getSimpleName();

  private FragmentCalendarBinding binding;
  private YearMonth selectedMonth;
  private ReminderViewModel reminderViewModel;
  private TaskViewModel taskViewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    LocalDate firstReminderDate = LocalDate.parse(getString(R.string.first_reminder_date));
    LocalDate firstTaskDate = LocalDate.parse(getString(R.string.first_task_date));
    YearMonth firstReminderMonth = YearMonth.from(firstReminderDate);
    YearMonth firstTaskMonth = YearMonth.from(firstTaskDate);
    DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    YearMonth currentMonth = YearMonth.now();


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
