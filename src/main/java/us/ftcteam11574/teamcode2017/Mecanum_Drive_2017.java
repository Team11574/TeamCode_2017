package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Mecanum Teleop", group = "Mecanum")
@SuppressWarnings({"unused", "WeakerAccess"})
public class Mecanum_Drive_2017 extends OpMode {
    public static final double CLAW_CLOSED_POSITION = 1.0;
    public static final double CLAW_OPEN_POSITION = 0.0;
    public static final double CLAW_OPEN_PARTIALLY = 0.5;
    DcMotor mFL, mBL, mFR, mBR, mLS;
    Servo SR, SL;


    @Override
    public void init() {
        mFL = hardwareMap.dcMotor.get("mFL");
        mBL = hardwareMap.dcMotor.get("mBL");
        mFR = hardwareMap.dcMotor.get("mFR");
        mBR = hardwareMap.dcMotor.get("mBR");
        mLS = hardwareMap.dcMotor.get("mLS");
        SR = hardwareMap.servo.get("SR");
        SL = hardwareMap.servo.get("SL");

        mFL.setDirection(DcMotor.Direction.REVERSE);
        mBL.setDirection(DcMotor.Direction.REVERSE);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.FORWARD);
        mLS.setDirection(DcMotor.Direction.REVERSE);
        mLS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SL.setDirection(Servo.Direction.REVERSE);

        mFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mLS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gamepad1.setJoystickDeadzone(0.2f);
    }

    @Override
    public void loop() {
        double motorPower = 1.0;
        double slowMotorPower = 0.30;
        double DriveX = gamepad1.right_stick_x;
        double DriveY = gamepad1.right_stick_y;
        double SpinLeft = gamepad1.left_trigger;
        double SpinRight = gamepad1.right_trigger;
        double LiftSlide = gamepad1.left_stick_y;
        boolean OpenClaw = gamepad1.x;
        boolean CloseClaw = gamepad1.y;
        boolean OpenClawPartially = gamepad1.b;

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

        mFL.setPower(DriveY - DriveX + SpinLeft - SpinRight);
        mBL.setPower(DriveY + DriveX + SpinLeft - SpinRight);
        mFR.setPower(DriveY + DriveX - SpinLeft + SpinRight);
        mBR.setPower(DriveY - DriveX - SpinLeft + SpinRight);
        mLS.setPower(LiftSlide);

    }
}
