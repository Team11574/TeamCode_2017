package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Mecanum_Drive_2017", group = "Mecanum")
@SuppressWarnings({"unused", "WeakerAccess"})
public class Mecanum_Drive_2017 extends OpMode {
    DcMotor mFL, mBL, mFR, mBR;
    Servo SR;
    Servo SL;

    @Override
    public void init() {
        mFL = hardwareMap.dcMotor.get("mFL");
        mBL = hardwareMap.dcMotor.get("mBL");
        mFR = hardwareMap.dcMotor.get("mFR");
        mBR = hardwareMap.dcMotor.get("mBR");

        mFL.setDirection(DcMotor.Direction.FORWARD);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.REVERSE);
        mBR.setDirection(DcMotor.Direction.REVERSE);

        mFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        SR = hardwareMap.servo.get("SR");
        SL = hardwareMap.servo.get("SL");
        SL.setDirection(Servo.Direction.REVERSE);

        gamepad1.setJoystickDeadzone(0.2f);
    }

    @Override
    public void loop() {
        double motorPower = 1.0;
        double slowMotorPower = 0.30;
        double xs = -gamepad1.left_stick_x;
        double ys = -gamepad1.left_stick_y;
        double lt = -gamepad1.left_trigger;
        double rt = -gamepad1.right_trigger;
        boolean dpd = gamepad1.dpad_down;
        boolean dpu = gamepad1.dpad_up;
        boolean dpl = gamepad1.dpad_left;
        boolean dpr = gamepad1.dpad_right;

        if (gamepad1.dpad_left) {
            // Strafe left.
            mFL.setPower(-motorPower);
            mBL.setPower(motorPower);
            mFR.setPower(motorPower);
            mBR.setPower(-motorPower);
        } else if (gamepad1.dpad_right) {
            // Strafe right.
            mFL.setPower(motorPower);
            mBL.setPower(-motorPower);
            mFR.setPower(-motorPower);
            mBR.setPower(motorPower);
        } else if (gamepad1.dpad_up) {
            // Drive forward.
            mFL.setPower(motorPower);
            mBL.setPower(motorPower);
            mFR.setPower(motorPower);
            mBR.setPower(motorPower);
        } else if (gamepad1.dpad_down) {
            // Drive backward.
            mFL.setPower(-motorPower);
            mBL.setPower(-motorPower);
            mFR.setPower(-motorPower);
            mBR.setPower(-motorPower);
        } else if(gamepad1.left_trigger > 0.5) {
            //Spin Left
            mFL.setPower(-motorPower);
            mBL.setPower(-motorPower);
            mFR.setPower(motorPower);
            mBR.setPower(motorPower);
        } else if(gamepad1.right_trigger > 0.5) {
            //Spin Right
            mFL.setPower(motorPower);
            mBL.setPower(motorPower);
            mFR.setPower(-motorPower);
            mBR.setPower(-motorPower);
        } else {
            // Allow use of the analog left/right sticks to control the robot in differential
            // steering (tank driving) mode. Allow use of the left and right trigger buttons
            // to rotate the robot.
            mFL.setPower((ys - xs + lt - rt) * slowMotorPower);
            mBL.setPower((ys + xs + lt - rt) * slowMotorPower);
            mFR.setPower((ys + xs - lt + rt) * slowMotorPower);
            mBR.setPower((ys - xs - lt + rt) * slowMotorPower);

        }

        float l = (gamepad1.left_stick_x);
        float r = (gamepad1.right_stick_x * -1);
        SR.setPosition(l);
        SL.setPosition(r);

    }
}