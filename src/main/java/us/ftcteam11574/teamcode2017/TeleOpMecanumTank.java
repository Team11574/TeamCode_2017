// Tank driving mode with strafing ability. Using constant speed mode for
// finer control at low speeds and power levels.

package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeleOpMecanumTank")
@SuppressWarnings({"unused", "WeakerAccess"})
public class TeleOpMecanumTank extends Generic_Drive {
    public void robotLoop() {
        // Drive the left and right sides with the Y axis of left and right sticks respectively.
        double DriveLeft = -gamepad1.left_stick_y;
        double DriveRight = -gamepad1.right_stick_y;

        // Strafe with the left and right analog trigger buttons.
        double Strafe = gamepad1.left_trigger - gamepad1.right_trigger
                - gamepad1.left_stick_x - gamepad1.right_stick_x;
        Strafe = Range.clip(Strafe, -1.0, +1.0);

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
        raiseLeftJewel();
        raiseRightJewel();
        allow_control_all_motors();
        motorGrabberLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (should_keep_running()) {
            robotLoop();
        }
    }
}
