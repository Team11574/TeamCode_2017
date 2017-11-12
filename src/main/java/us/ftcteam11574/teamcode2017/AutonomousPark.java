package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutonomousPark", group="Autonomous")
@SuppressWarnings("unused")
public class AutonomousPark extends Generic_Drive {
    private enum StartingPosition{
        Unknown,
        North,
        South,
    }
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
