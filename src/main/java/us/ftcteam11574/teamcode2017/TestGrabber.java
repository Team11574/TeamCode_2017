package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@SuppressWarnings({"unused", "WeakerAccess"})
public class TestGrabber extends Generic_Drive {
    public void robotLoop() {
        double leftGrabber = -gamepad1.left_stick_y;
        double rightGrabber = -gamepad1.right_stick_y;

        servoGrabberLeft.setPosition(leftGrabber);
        servoGrabberRight.setPosition(rightGrabber);

        telemetry.addData("Left", leftGrabber);
        telemetry.addData("Right", rightGrabber);
        telemetry.update();
    }

    @Override
    public void robotRun() {
        while (should_keep_running()) {
            robotLoop();
        }
    }
}