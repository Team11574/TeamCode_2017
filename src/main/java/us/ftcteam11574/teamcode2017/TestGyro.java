package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestGyro extends Generic_Drive {
    @Override
    public void robotRun() {
        while (should_keep_running()) {
            telemetry.addData("Heading", gyro.getHeading());
            telemetry.addData("Integrated Z", gyro.getIntegratedZValue());
            telemetry.update();
        }
    }
}