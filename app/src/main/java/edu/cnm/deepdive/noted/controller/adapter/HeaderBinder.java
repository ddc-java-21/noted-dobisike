package edu.cnm.deepdive.noted.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;
import dagger.hilt.android.scopes.FragmentScoped;
import edu.cnm.deepdive.noted.R;
import edu.cnm.deepdive.noted.databinding.HeaderCalenderBinding;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.stream.IntStream;
import javax.inject.Inject;

@FragmentScoped
public class HeaderBinder implements MonthHeaderFooterBinder<ViewContainer> {

  private final LayoutInflater inflater;
  private final DateTimeFormatter formatter;


  @Inject
  public HeaderBinder(LayoutInflater inflater, DateTimeFormatter formatter) {
    this.inflater = inflater;
    this.formatter = formatter;
  }

  @Override
  public ViewContainer create(@NonNull View view) {
    return new HeaderHolder(view);
  }

  @Override
  public void bind(@NonNull ViewContainer container, CalendarMonth calendarMonth) {
    ((HeaderHolder) container).bind(calendarMonth);
  }

  private class HeaderHolder extends ViewContainer {

    private final HeaderCalenderBinding binding;

    private boolean bounded;

    public HeaderHolder(@NonNull View view) {
      super(view);
      binding = HeaderCalenderBinding.bind(view);
    }

    public void bind(CalendarMonth calendarMonth) {
      binding.monthYear.setText(calendarMonth.getYearMonth().format(formatter));
      if(!bounded) {
        bounded = true;
        ViewGroup columnHeaderRoot = binding.dayNames;
        columnHeaderRoot.removeAllViews();
        Locale locale = Locale.getDefault();
        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        IntStream.range(0, 7)
            .mapToObj(firstDayOfWeek::plus)
            .forEach((dayOfWeek) -> {
              TextView dayHeader =
                  (TextView) inflater.inflate(R.layout.day_header, columnHeaderRoot, false);
              dayHeader.setText(dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, locale));
              columnHeaderRoot.addView(dayHeader);
            });
      }
    }

  }

}
