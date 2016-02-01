package com.jsqix.dianwo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.DateUtils;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_data_range)
public class DateRangeActivity extends ToolBarActivity {
    @ViewInject(R.id.calendar_view)
    private CalendarPickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("日期选择");
        initView();
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setText("确定");
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Date> list = pickerView.getSelectedDates();
                if (list.size() > 1) {
                    showDialogTip("您选择的时间：" + DateUtils.format(list.get(0), DateUtils.MONTH_DAY_FORMAT) + "+++++" + DateUtils.format(list.get(list.size() - 1), DateUtils.MONTH_DAY_FORMAT));
                } else {
                    showDialogTip("您选择的时间：" + DateUtils.format(list.get(0), DateUtils.MONTH_DAY_FORMAT));
                }

            }
        });
    }

    private void initView() {
       /* pickerView.setDecorators(Arrays.<CalendarCellDecorator>asList(new CalendarCellDecorator() {
            @Override
            public void decorate(CalendarCellView cellView, Date date) {
                String dateString = Integer.toString(date.getDate());
                SpannableString string = new SpannableString(dateString);
                string.setSpan(new RelativeSizeSpan(0.5f), 0, dateString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                cellView.setText(string);
            }
        }));*/
        pickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        ArrayList<Date> dates = new ArrayList<Date>();
        dates.add(DateUtils.getCurrentDate());
        dates.add(DateUtils.getCurrentDate(1));
        pickerView.init(DateUtils.getCurrentDate(), DateUtils.getCurrentDate(60))
                .inMode(SelectionMode.RANGE)
                .withSelectedDates(dates);

    }

}
