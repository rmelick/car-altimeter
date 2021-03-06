package net.rmelick.caraltimeter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author rmelick
 */
public class AltimeterFragment extends Fragment {
    private Button mStartButton;
    private Button mStopButton;
    private TextView mAltitudeTextView;
    private double mCurrentAltitude;
    private AltitudeManager mAltitudeManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAltitudeManager = AltitudeManager.get(getActivity());
        mAltitudeManager.registerCallback(new AltitudeManager.NewAltitudeCallback() {
            @Override
            public void newAltitude(double altitude) {
                mCurrentAltitude = altitude;
                updateUI();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_altimeter_activity, container, false);

        mAltitudeTextView = (TextView) rootView.findViewById(R.id.current_altitudeTextView);

        mStartButton = (Button) rootView.findViewById(R.id.start_driveButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAltitudeManager.startPressureUpdates();
            }
        });

        mStopButton = (Button) rootView.findViewById(R.id.stop_driveButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAltitudeManager.stopPressureUpdates();
            }
        });

        updateUI();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateUI() {
        //%2.3f
        mAltitudeTextView.setText(String.format("%.5g", mCurrentAltitude));
    }
}
