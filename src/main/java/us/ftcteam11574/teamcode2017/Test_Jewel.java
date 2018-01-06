package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Sara N on 1/6/2018.
 */

@TeleOp
public class Test_Jewel extends Generic_Drive {
    public void robotLoop() {
        double leftJewel = -gamepad1.left_stick_y;
        double rightJewel = -gamepad1.right_stick_y;

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
