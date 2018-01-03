// Tank driving mode with strafing ability. Using constant speed mode for
// finer control at low speeds and power levels.

package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp_Mecanum_Tank")
@SuppressWarnings({"unused", "WeakerAccess"})
public class TeleOp_Mecanum_Tank extends OpMode {
    public static final double CLAW_OPEN_POSITION = 0.05;
    public static final double CLAW_OPEN_PARTIALLY = 0.5;
    public static final double CLAW_CLOSED_POSITION = 0.7;

    public static final double JEWEL_ARM_UP = 0.0;
    public static final double JEWEL_ARM_DOWN = 1.0;

    DcMotor mFL, mBL, mFR, mBR, mLS;
    Servo SR, SL, SJ1, SJ2;

    @Override
    public void init() {
        mFL = hardwareMap.dcMotor.get("mFL");
        mBL = hardwareMap.dcMotor.get("mBL");
        mFR = hardwareMap.dcMotor.get("mFR");
        mBR = hardwareMap.dcMotor.get("mBR");
        mLS = hardwareMap.dcMotor.get("mLS");

        mFL.setDirection(DcMotor.Direction.REVERSE);
        mBL.setDirection(DcMotor.Direction.REVERSE);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.FORWARD);
        mLS.setDirection(DcMotor.Direction.FORWARD);
        mLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        SR = hardwareMap.servo.get("SR");
        SL = hardwareMap.servo.get("SL");
        SJ1 = hardwareMap.servo.get("SJ1");
        SJ2 = hardwareMap.servo.get("SJ2");

        SR.setDirection(Servo.Direction.FORWARD);
        SL.setDirection(Servo.Direction.REVERSE);
        SJ1.setDirection(Servo.Direction.REVERSE);
        SJ2.setDirection(Servo.Direction.FORWARD);

        gamepad1.setJoystickDeadzone(0.05f);
    }

    @Override
    public void start() {
        SR.setPosition(CLAW_OPEN_POSITION);
        SL.setPosition(CLAW_OPEN_POSITION);
        SJ1.setPosition(JEWEL_ARM_UP);
        SJ2.setPosition(JEWEL_ARM_UP);
    }

    @Override
    public void loop() {
        // Drive the left and right sides with the Y axis of left and right sticks respectively.
        double DriveLeft = gamepad1.left_stick_y;
        double DriveRight = gamepad1.right_stick_y;

        // Strafe with the left and right analog trigger buttons.
        double Strafe = -gamepad1.left_trigger + gamepad1.right_trigger;

        // Set the appropriate power levels for each motor.
        mFL.setPower(DriveLeft - Strafe);
        mBL.setPower(DriveLeft + Strafe);
        mFR.setPower(DriveRight + Strafe);
        mBR.setPower(DriveRight - Strafe);

        // Lower and raise the grabber slide with the left and right bumper buttons respectively.
        double LiftSlide = (gamepad1.left_bumper || gamepad2.left_bumper ? -1.0 : 0.0) +
                (gamepad1.right_bumper || gamepad2.right_bumper ? +1.0 : 0.0);

        // Set the appropriate power level for the grabber claw lift slide.
        mLS.setPower(LiftSlide);

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
            SJ1.setPosition(JEWEL_ARM_DOWN);
        } else if (raiseRightJewelArm) {
            SJ1.setPosition(JEWEL_ARM_UP);
        }

        if (lowerLeftJewelArm) {
            SJ2.setPosition(JEWEL_ARM_DOWN);
        } else if (raiseLeftJewelArm) {
            SJ2.setPosition(JEWEL_ARM_UP);
        }

        // Set the appropriate positions for the grabber claw servos.
        if (OpenClaw) {
            SL.setPosition(CLAW_OPEN_POSITION);
            SR.setPosition(CLAW_OPEN_POSITION);
        } else if (CloseClaw) {
            SL.setPosition(CLAW_CLOSED_POSITION);
            SR.setPosition(CLAW_CLOSED_POSITION);
        } else if (OpenClawPartially) {
            SL.setPosition(CLAW_OPEN_PARTIALLY);
            SR.setPosition(CLAW_OPEN_PARTIALLY);
        }
    }

    @Override
    public void stop() {
        mLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        SL.close();
        SR.close();
        SJ1.close();
        SJ2.close();
    }
}
