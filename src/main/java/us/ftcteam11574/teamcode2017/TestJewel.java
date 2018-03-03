package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@SuppressWarnings({"unused", "WeakerAccess"})
public class TestJewel extends Generic_Drive {
    public void robotLoop() {
        double leftJewel = -gamepad1.left_stick_x;
        double rightJewel = -gamepad1.right_stick_x;

        servoJewelLeft.setPosition(leftJewel);
        servoJewelRight.setPosition(rightJewel);

        telemetry.addData("Left", leftJewel);
        telemetry.addData("Right", rightJewel);
        telemetry.update();
    }

    @Override
    public void robotRun() {
        openGrabber();
        while (should_keep_running()) {
            robotLoop();
        }
    }
}
