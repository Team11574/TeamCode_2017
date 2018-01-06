// Tank driving mode with strafing ability. Using constant speed mode for
// finer control at low speeds and power levels.

package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp_Mecanum_Tank")
@SuppressWarnings({"unused", "WeakerAccess"})
public class TeleOp_Mecanum_Tank extends Generic_Drive {

    @Override
    public void robotInit() {
        super.robotInit();
        allow_control_all_motors();
        motorGrabberLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void robotLoop() {
        // Drive the left and right sides with the Y axis of left and right sticks respectively.
        double DriveLeft = -gamepad1.left_stick_y;
        double DriveRight = -gamepad1.right_stick_y;

        // Strafe with the left and right analog trigger buttons.
        double Strafe = gamepad1.left_trigger - gamepad1.right_trigger;

        // Set the appropriate power levels for each motor.
        motor[mFL].setPower(DriveLeft - Strafe);
        motor[mBL].setPower(DriveLeft + Strafe);
        motor[mFR].setPower(DriveRight + Strafe);
        motor[mBR].setPower(DriveRight - Strafe);

        // Lower and raise the grabber slide with the left and right bumper buttons respectively.
        double LiftSlide = (gamepad1.left_bumper || gamepad2.left_bumper ? -1.0 : 0.0) +
                (gamepad1.right_bumper || gamepad2.right_bumper ? +1.0 : 0.0);

        // Set the appropriate power level for the grabber claw lift slide.
        motorGrabberLift.setPower(LiftSlide);

        // Use a few buttons to operate the grabber claw:
        //   X: Open the claw fully.
        //   Y: Close the claw fully.
        //   B: Open the claw partially (to release a block).
        boolean OpenClaw = gamepad1.x || gamepad2.x;
        boolean CloseClaw = gamepad1.y || gamepad2.y;
        boolean OpenClawPartially = gamepad1.b || gamepad2.b;

        //    Dpad down: lower jewel arm
        //    Dpad up: raise jewel arm
        boolean lowerRightJewelArm = gamepad1.dpad_down;
        boolean raiseRightJewelArm = gamepad1.dpad_up;
        boolean lowerLeftJewelArm = gamepad1.dpad_right;
        boolean raiseLeftJewelArm = gamepad1.dpad_left;

        if (lowerRightJewelArm) {
            lowerRightJewel();
        } else if (raiseRightJewelArm) {
            raiseRightJewel();
        }

        if (lowerLeftJewelArm) {
            lowerLeftJewel();
        } else if (raiseLeftJewelArm) {
            raiseLeftJewel();
        }

        // Set the appropriate positions for the grabber claw servos.
        if (OpenClaw) {
            openGrabber();
        } else if (CloseClaw) {
            closeGrabber();
        } else if (OpenClawPartially) {
            OpenClawPartially();
        }
    }

    @Override
    public void robotRun() {
        while (should_keep_running()) {
            robotLoop();
        }
    }
}
