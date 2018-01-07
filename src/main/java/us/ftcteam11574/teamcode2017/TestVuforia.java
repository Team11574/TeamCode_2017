package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@TeleOp(name = "TestVuforia")
@SuppressWarnings({"unused"})
public class TestVuforia extends OpMode {
    private VuforiaTrackable mRelicRecoveryVuMarks;

    private Telemetry.Item visibleVuMarkTelemetry;

    @Override
    public void init() {
        // Find the Android View for Vuforia to display the camera's view.
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());

        // Initialize the Vuforia parameters.
        VuforiaLocalizer.Parameters vuforiaParameters =
                new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        vuforiaParameters.vuforiaLicenseKey =
                hardwareMap.appContext.getString(R.string.VuforiaLicenseKey);
        vuforiaParameters.cameraDirection =
                VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforiaLocalizer =
                ClassFactory.createVuforiaLocalizer(vuforiaParameters);

        // Load the Relic Recovery VuMarks and activate them.
        VuforiaTrackables vuforiaTrackables =
                vuforiaLocalizer.loadTrackablesFromAsset("RelicVuMark");
        vuforiaTrackables.activate();

        // Save the Relic Recovery VuMarks in an instance variable for access later.
        mRelicRecoveryVuMarks = vuforiaTrackables.get(0);

        // Add a telemetry item so we can set its value efficiently in loop().
        visibleVuMarkTelemetry = telemetry.addData("1. VuMark visible", "(Not Ready)");
    }

    @Override
    public void loop() {
        // Find whatever VuMark trackable is visible to the camera.
        RelicRecoveryVuMark visibleVuMark = RelicRecoveryVuMark.from(mRelicRecoveryVuMarks);

        // Sent a telemetry message with the name of the visible VuMark.
        visibleVuMarkTelemetry.setValue(visibleVuMark);
        telemetry.update();
    }
}
