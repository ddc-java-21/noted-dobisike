package edu.cnm.deepdive.noted.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.scopes.FragmentScoped;
import edu.cnm.deepdive.noted.R;
import edu.cnm.deepdive.noted.databinding.DayCalendarBinding;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.entity.Task;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@FragmentScoped
public class DayBinder implements MonthDayBinder<ViewContainer> {

  private static final String TAG = DayBinder.class.getSimpleName();

  private final Map<LocalDate, Reminder> reminderMap;
  private final Map<LocalDate, Task> taskMap;
//  private final List<Instant> task;

  private OnReminderClickListener reminderClickListener;
  private OnTaskClickListener taskClickListener;

  @Inject
  public DayBinder(@ActivityContext Context context) {
    this.reminderMap = new  HashMap<>();
    this.taskMap = new  HashMap<>();
    reminderClickListener = (reminder) -> {};
    taskClickListener = (task) -> {};
  }

  @NonNull
  @Override
  public ViewContainer create(@NonNull View view) {
    return new DayHolder(view);
  }

  @Override
  public void bind(@NonNull ViewContainer holder, CalendarDay calendarDay) {
    ((DayHolder) holder).bind(calendarDay);
  }

  public Map<LocalDate, Reminder> getReminderMap() {
    return reminderMap;
  }

  public Map<LocalDate, Task> getTaskMap() {
    return taskMap;
  }

  public void setListener(OnReminderClickListener reminderClickListener, OnTaskClickListener taskClickListener) {
    this.reminderClickListener = reminderClickListener;
    this.taskClickListener = taskClickListener;
  }

  public class DayHolder extends ViewContainer {

    private static final OnReminderClickListener NO_REMINDER_CLICK_LISTENER = (reminder) -> {};
    private static final OnReminderClickListener  NO_TASK_CLICK_LISTENER = (task) -> {};

    private final DayCalendarBinding binding;
    private final Drawable clickableBackground;

    private Reminder reminder;
    private Task task;

    public DayHolder(@NonNull View view) {
      super(view);
      binding = DayCalendarBinding.bind(view);
      clickableBackground = view.getBackground();
    }

    public void bind(CalendarDay calendarDay) {
      TextView dayText = binding.getRoot();
      dayText.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
      Reminder reminder = reminderMap.get(calendarDay.getDate());
      Task task = taskMap.get(calendarDay.getDate());
      if (reminder != null) {
        this.reminder = reminder;
        dayText.setClickable(true);
        dayText.setFocusable(true);
        dayText.setOnClickListener(this::translateClicks);
        dayText.setBackground(clickableBackground);
        dayText.setTextAppearance(
            (calendarDay.getPosition() == DayPosition.MonthDate)
                ? R.style.CalendarTextAppearance_AvailableDay
                : R.style.CalendarTextAppearance_AvailableDay_OutOfMonth
        );
      } else {
        dayText.setClickable(false);
        dayText.setFocusable(false);
        dayText.setOnClickListener(null);
        dayText.setBackground(null);
        dayText.setTextAppearance(R.style.CalendarTextAppearance);
      }
      if (task != null) {
        this.task = task;
        dayText.setClickable(true);
        dayText.setFocusable(true);
        dayText.setOnClickListener(this::translateClicks);
        dayText.setBackground(clickableBackground);
        dayText.setTextAppearance(
            (calendarDay.getPosition() == DayPosition.MonthDate)
                ? R.style.CalendarTextAppearance_AvailableDay
                : R.style.CalendarTextAppearance_AvailableDay_OutOfMonth
        );
      } else {
        dayText.setClickable(false);
        dayText.setFocusable(false);
        dayText.setOnClickListener(null);
        dayText.setBackground(null);
        dayText.setTextAppearance(R.style.CalendarTextAppearance);
      }
    }

    private void translateClicks(View view) {
      reminderClickListener.onReminderClick(reminder);
      taskClickListener.onTaskClick(task);
    }

  }

  @FunctionalInterface
  public interface OnTaskClickListener {

    void onTaskClick(Task task);
  }

  @FunctionalInterface
  public interface OnReminderClickListener {

    void onReminderClick(Reminder reminder);
  }
}
