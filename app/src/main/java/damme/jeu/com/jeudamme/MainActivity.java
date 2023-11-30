package damme.jeu.com.jeudamme;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import damme.jeu.com.jeudamme.Draw.DrawPlateauView;

public class MainActivity extends AppCompatActivity {
    public static TextView numberOfBlackPions;
    public  static TextView numberOfRedPions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button);
        numberOfBlackPions=(TextView) findViewById(R.id.numberOfPionsBlack);
        numberOfRedPions=(TextView) findViewById(R.id.numberOfPionsRed);
        final DrawPlateauView drawPlateauView=findViewById(R.id.drawPlateauView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"reseet",Toast.LENGTH_SHORT).show();
                drawPlateauView.getPlateau().resetPlateau();
                drawPlateauView.invalidate();
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}
