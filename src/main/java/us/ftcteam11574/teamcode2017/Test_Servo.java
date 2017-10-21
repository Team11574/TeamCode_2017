package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Test Servo")
@SuppressWarnings({"WeakerAccess", "unused"})
public class Test_Servo extends OpMode {
    Servo TS;
    Servo TS2;
    @Override
    public void init() {
        TS = hardwareMap.servo.get("TS");
        TS2 = hardwareMap.servo.get("SL");
TS2.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        float x = (gamepad1.left_stick_x);
        float y = (gamepad1.right_stick_x * -1);
        TS.setPosition(x);
        TS2.setPosition(y);


    }
}
