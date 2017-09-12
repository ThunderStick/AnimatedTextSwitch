
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ExampleActivity extends AppCompatActivity {
    AnimatedTextSwitch mATS_Example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mATS_Example = findViewById(R.id.ats_example);
        mATS_Example.setOnChangeListener(mOnChangeListener);
        mATS_Example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mATS_Example.isSwitchedOn()){
                    mATS_Example.switchOff();
                }else{
                    mATS_Example.switchOff();
                }
            }
        });
        

    }

    AnimatedTextSwitch.OnChangeListener mOnChangeListener = new AnimatedTextSwitch.OnChangeListener() {
        @Override
        public void SwitchedOn(boolean bool) {
            Toast.makeText(ExampleActivity.this, "Toggled ON : " + String.valueOf(bool), Toast.LENGTH_SHORT).show();
            mATS_Example.setText("SWITCHED : " + String.valueOf(bool));
        }
    };

}
