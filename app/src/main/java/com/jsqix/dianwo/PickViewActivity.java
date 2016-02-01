package com.jsqix.dianwo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

@ContentView(R.layout.activity_pick_view)
public class PickViewActivity extends ToolBarActivity {
    @ViewInject(R.id.picker_text)
    private TextView picker_text;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("PickerView控件");
        initView();
    }

    private void initView() {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                picker_text.setText(format.format(date));
            }
        });
    }

    @Event(value = {R.id.picker_button}, type = View.OnClickListener.class)
    private void click(View view) {
        switch (view.getId()) {
            case R.id.picker_button:
                pvTime.show();
                break;
            default:
                break;
        }
    }
}
