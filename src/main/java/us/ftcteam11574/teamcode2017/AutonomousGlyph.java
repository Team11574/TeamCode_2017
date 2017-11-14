package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Autonomous Glyph", group="Autonomous")
@SuppressWarnings({"unused", "WeakerAccess"})
public class AutonomousGlyph extends Generic_Drive {
    public static final double CLAW_CLOSED_POSITION = 1.0;
    public static final double CLAW_OPEN_POSITION = 0.0;
    DcMotor mFL, mBL, mFR, mBR, mLS;
    Servo SR, SL;
    
    private enum StartingPosition{
        Unknown,
        North,
        South,
    }
    mFL = hardwareMap.dcMotor.get("mFL");
    mBL = hardwareMap.dcMotor.get("mBL");
    mFR = hardwareMap.dcMotor.get("mFR");
    mBR = hardwareMap.dcMotor.get("mBR");
    mLS = hardwareMap.dcMotor.get("mLS");
    SR = hardwareMap.servo.get("SR");
    SL = hardwareMap.servo.get("SL");

    @Override
    public void robotRun() {
        StartingPosition sp;
        final AllianceColor ac = check_alliance();
        final LeftRight lr = check_LeftRight();
        if (ac == AllianceColor.Blue && lr == LeftRight.Right)
            sp = StartingPosition.South;
        else if (ac == AllianceColor.Blue && lr == LeftRight.Left)
            sp = StartingPosition.North;
        else if (ac == AllianceColor.Red && lr == LeftRight.Right)
            sp = StartingPosition.North;
        else if (ac == AllianceColor.Red && lr == LeftRight.Left)
            sp = StartingPosition.South;
        else
            sp = StartingPosition.Unknown;

        if (sp == StartingPosition.South) {
            int strafe_direction;
            if (ac == AllianceColor.Blue)
                strafe_direction = STRAFE_LEFT;
            else
                strafe_direction = STRAFE_RIGHT;
            // Drive to park in front of Cryptobox.
            SL.setPosition(CLAW_CLOSED_POSITION);
            SR.setPosition(CLAW_CLOSED_POSITION);
            drive_distance(DRIVE_FORWARD, 32.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 11.0, 0.5);
            stop_all_motors();
        } else if (sp == StartingPosition.North) {
            int strafe_direction;
            if (ac == AllianceColor.Blue)
                strafe_direction = STRAFE_RIGHT;
            else
                strafe_direction = STRAFE_LEFT;
            // Drive to park in front of Cryptobox.
            drive_distance(DRIVE_FORWARD, 24.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 6.0, 0.5);
            stop_all_motors();
            drive_distance(DRIVE_FORWARD, 3.0, 0.5);
            stop_all_motors();

        }


    }

}
